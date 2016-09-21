package com.stuart.fileexplorer.music;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.stuart.fileexplorer.R;
import com.stuart.fileexplorer.aidl.IMusic;
import com.stuart.fileexplorer.entitiy.FileInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicService extends Service {

    private static final String TAG = "MusicService";
    private MultiPlayer mMultiPlayer;

    private List<FileInfo> mMusicFiles = new ArrayList<>();
    private int mCuurentPosition = 0;

    private MusicService() {
    }

    private int getNextPosition() {

        if (mMusicFiles.size() == 0) return -1;
        switch (getPlayerMode()) {
            case ALL_REPORT:
                return mCuurentPosition + 1 == mMusicFiles.size() ? 0 : mCuurentPosition + 1;

            case SINGLE_AROUND:
                return mCuurentPosition;

            case SHUFFLE:
                return (int) (Math.random() * mMusicFiles.size());
        }
        return -1;
    }

    private MODEL mCurrentModel = MODEL.ALL_REPORT;

    public enum MODEL {
        ALL_REPORT,// 列表循环
        SINGLE_AROUND,// 单曲循环
        SHUFFLE //随机播放
    }

    /**
     * 切换模式
     */
    public void changeMode() {
        switch (mCurrentModel) {
            case ALL_REPORT:
                mCurrentModel = MODEL.SHUFFLE;
                break;
            case SINGLE_AROUND:
                mCurrentModel = MODEL.ALL_REPORT;
                break;
            case SHUFFLE:
                mCurrentModel = MODEL.SINGLE_AROUND;
                break;
        }
    }

    /**
     * 返回播放模式
     * @return
     */
    public MODEL getPlayerMode() {
        return mCurrentModel;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMultiPlayer = new MultiPlayer();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void playFile() {

        FileInfo info = mMusicFiles.get(mCuurentPosition);
        mMultiPlayer.setDataSource(info.getFile().getAbsolutePath());
        mMultiPlayer.start();
//        mediaPlayer.seekTo();
        //

    }

    public IBinder mBinder = new IMusic.Stub() {
        @Override
        public void start() throws RemoteException {

            if (mCuurentPosition == -1) {
                Toast.makeText(MusicService.this, R.string.no_file_play, Toast.LENGTH_SHORT).show();
                return;
            }

        }

        @Override
        public void stop() throws RemoteException {
            mMultiPlayer.stop();
        }

        @Override
        public void pause() throws RemoteException {
            mMultiPlayer.pause();
        }

        @Override
        public void next() throws RemoteException {
            mCuurentPosition = getNextPosition();
            playFile();
        }

        @Override
        public void pre() throws RemoteException {

        }

        @Override
        public void seekTo(long time) throws RemoteException {
            mMultiPlayer.seek(time);
        }
    };



    private class MultiPlayer {
        private CompatMediaPlayer mCurrentMediaPlayer = new CompatMediaPlayer();
        private CompatMediaPlayer mNextMediaPlayer;
        private Handler mHandler;
        private boolean mIsInitialized = false;
        private boolean mIsComplete = false;


        public MultiPlayer() {
            //mCurrentMediaPlayer.setWakeMode(MediaPlaybackService.this, PowerManager.PARTIAL_WAKE_LOCK);
        }

        public void setDataSource(String path) {
            mIsInitialized = setDataSourceImpl(mCurrentMediaPlayer, path);
            if (mIsInitialized) {
                setNextDataSource(null);
            }
        }

        private boolean setDataSourceImpl(MediaPlayer player, String path) {
            boolean isNextPlayer = (mNextMediaPlayer != null) ?
                    (player == mNextMediaPlayer) : false;
            try {
                player.reset();
                player.setOnPreparedListener(null);
                if (path.startsWith("content://")) {
                    player.setDataSource(MusicService.this, Uri.parse(path));
                } else {
                    player.setDataSource(path);
                }
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.prepare();
            } catch (IOException ex) {
               /* if (!mQuietMode && !isNextPlayer) {
                    Toast.makeText(MusicService.this, R.string.open_failed, Toast.LENGTH_LONG).show();
                }*/
                return false;
            } catch (IllegalArgumentException ex) {
                // TODO: notify the user why the file couldn't be opened
                return false;
            } catch (IllegalStateException ex) {
                Log.d(TAG,"IllegalStateException exception occur");
                return false;
            }
            player.setOnCompletionListener(listener);
            player.setOnErrorListener(errorListener);
            return true;
        }

        public void setNextDataSource(String path) {
            mIsComplete = false;
            if (mIsInitialized == false) {
                return;
            }
            mCurrentMediaPlayer.setNextMediaPlayer(null);
            if (mNextMediaPlayer != null) {
                mNextMediaPlayer.release();
                mNextMediaPlayer = null;
            }
            if (path == null) {
                return;
            }
            mNextMediaPlayer = new CompatMediaPlayer();
            //mNextMediaPlayer.setWakeMode(MediaPlaybackService.this, PowerManager.PARTIAL_WAKE_LOCK);
            mNextMediaPlayer.setAudioSessionId(getAudioSessionId());
            if (setDataSourceImpl(mNextMediaPlayer, path)) {
                mCurrentMediaPlayer.setNextMediaPlayer(mNextMediaPlayer);
            } else {
                // failed to open next, we'll transition the old fashioned way,
                // which will skip over the faulty file
                mNextMediaPlayer.release();
                mNextMediaPlayer = null;
            }
        }

        public boolean isInitialized() {
            return mIsInitialized;
        }

        public void start() {
            mCurrentMediaPlayer.start();
            mIsComplete = false;
        }

        public void stop() {
            mCurrentMediaPlayer.reset();
            mIsInitialized = false;
        }

        /**
         * You CANNOT use this player anymore after calling release()
         */
        public void release() {
            stop();
            mCurrentMediaPlayer.release();
        }

        public void pause() {
            mCurrentMediaPlayer.pause();
        }

        public void setHandler(Handler handler) {
            mHandler = handler;
        }

        MediaPlayer.OnCompletionListener listener = new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mIsComplete = true;
                if (mp == mCurrentMediaPlayer && mNextMediaPlayer != null) {
                    mCurrentMediaPlayer.release();
                    mCurrentMediaPlayer = mNextMediaPlayer;
                    mNextMediaPlayer = null;

                } else {
                    // Acquire a temporary wakelock, since when we return from
                    // this callback the MediaPlayer will release its wakelock
                    // and allow the device to go to sleep.
                    // This temporary wakelock is released when the RELEASE_WAKELOCK
                    // message is processed, but just in case, put a timeout on it.
                   /* mWakeLock.acquire(30000);
                    mHandler.sendEmptyMessage(TRACK_ENDED);
                    mHandler.sendEmptyMessage(RELEASE_WAKELOCK);*/
                }
            }
        };

        MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                        mIsInitialized = false;
                        mCurrentMediaPlayer.release();
                        // Creating a new MediaPlayer and settings its wakemode does not
                        // require the media service, so it's OK to do this now, while the
                        // service is still being restarted
                        mCurrentMediaPlayer = new CompatMediaPlayer();
                        //mCurrentMediaPlayer.setWakeMode(MediaPlaybackService.this, PowerManager.PARTIAL_WAKE_LOCK);
                      /*  if (mPlayer != null) {
                            mHandler.sendMessageDelayed(mHandler.obtainMessage(SERVER_DIED), 2000);
                        }*/
                        return true;
                    default:
                        Log.d("MultiPlayer", "Error: " + what + "," + extra);
                        break;
                }
                return false;
            }
        };

        public boolean isComplete() {
            return mIsComplete;
        }

        public long duration() {
            return mCurrentMediaPlayer.getDuration();
        }

        public long position() {
            return mCurrentMediaPlayer.getCurrentPosition();
        }

        public long seek(long whereto) {
            mCurrentMediaPlayer.seekTo((int) whereto);
            return whereto;
        }

        public void setVolume(float vol) {
            mCurrentMediaPlayer.setVolume(vol, vol);
        }

        public void setAudioSessionId(int sessionId) {
            mCurrentMediaPlayer.setAudioSessionId(sessionId);
        }

        public int getAudioSessionId() {
            return mCurrentMediaPlayer.getAudioSessionId();
        }
    }

    static class CompatMediaPlayer extends MediaPlayer implements MediaPlayer.OnCompletionListener {

        private boolean mCompatMode = true;
        private MediaPlayer mNextPlayer;
        private OnCompletionListener mCompletion;

        public CompatMediaPlayer() {
            try {
                MediaPlayer.class.getMethod("setNextMediaPlayer", MediaPlayer.class);
                mCompatMode = false;
            } catch (NoSuchMethodException e) {
                mCompatMode = true;
                super.setOnCompletionListener(this);
            }
        }

        public void setNextMediaPlayer(MediaPlayer next) {
            if (mCompatMode) {
                mNextPlayer = next;
            } else {
                super.setNextMediaPlayer(next);
            }
        }

        @Override
        public void setOnCompletionListener(OnCompletionListener listener) {
            if (mCompatMode) {
                mCompletion = listener;
            } else {
                super.setOnCompletionListener(listener);
            }
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mNextPlayer != null) {
                // as it turns out, starting a new MediaPlayer on the completion
                // of a previous player ends up slightly overlapping the two
                // playbacks, so slightly delaying the start of the next player
                // gives a better user experience
                SystemClock.sleep(50);
                mNextPlayer.start();
            }
            mCompletion.onCompletion(this);
        }
    }
}
