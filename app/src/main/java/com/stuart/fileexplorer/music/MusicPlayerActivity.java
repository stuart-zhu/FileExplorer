package com.stuart.fileexplorer.music;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.stuart.fileexplorer.R;
import com.stuart.fileexplorer.entitiy.FileInfo;
import com.stuart.fileexplorer.entitiy.MusicFile;
import com.stuart.fileexplorer.utils.FileUtils;
import com.stuart.fileexplorer.widget.CircleImageView;
import com.stuart.fileexplorer.widget.TitleView;

import java.util.List;

/**
 * Created by stuart on 2016/9/21.
 */
@ContentView(R.layout.activity_music)
public class MusicPlayerActivity extends Activity {

    private static final String TAG = "MusicPlayerActivity";

    private List<FileInfo> mFiles;
    private MusicFile mCurrentMusic;

    @ViewInject(R.id.album_icon)
    private CircleImageView mAlbumIcon;

    @ViewInject(R.id.title_view)
    private TitleView mTitleView;

    @ViewInject(R.id.hand)
    private RelativeLayout mHand;

    private int state; // 1 播放 0， 暂停

    @ViewInject(R.id.play)
    private ImageView mPlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        if (getIntent() == null) {
            Log.i(TAG, "intent is null , finish");
            finish();
            return;
        } else {
            Bundle music = getIntent().getBundleExtra("music");
            if (music == null) {
                Log.i(TAG, "has not data, finish");
                finish();
                return;
            } else {
                mFiles = music.getParcelableArrayList("list");
                FileInfo fileInfo = music.getParcelable("current");
                mCurrentMusic = FileUtils.getMusicFile(this, fileInfo.getId());
            }
        }
        //Bundle ss = savedInstanceState.getBundle("ss");
//        ss.getin

        setAlbum();
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState();
            }
        });
    }

    private void setAlbum() {
        String albumPath = FileUtils.getAlbumPath(this, mCurrentMusic.getUri());
        mTitleView.setTitle(mCurrentMusic.getTitle(), mCurrentMusic.getAlbum());
        if (TextUtils.isEmpty(albumPath)) {
           // mAlbumIcon.setImageResource(ResourceUtil.getDefalutImageRes(mCurrentFileInfo.getCategory()));
            return;
        }
        final BitmapUtils bu = new BitmapUtils(this);
//        bu.configDefaultLoadingImage(ResourceUtil.getDefalutImageRes(mCurrentFileInfo.getCategory()));
//        bu.configDefaultLoadFailedImage(ResourceUtil.getDefalutImageRes(mCurrentFileInfo.getCategory()));
        bu.display(mAlbumIcon, albumPath);

    }

    @Override
    protected void onResume() {
        super.onResume();
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                s();
            }
        }, 1000);*/
    }


    private void changeState() {

        if (state == 1) {
            state = 0;
        } else {
            state = 1;
        }
        checkState();
    }

    private void checkState() {
        Animation handAnima = null;

        if (state == 0) {
            handAnima = AnimationUtils.loadAnimation(this, R.anim.music_hand_down_anim);
            mPlay.setImageResource(R.drawable.bottom_pause_selector);
        } else if (state == 1) {
            handAnima = AnimationUtils.loadAnimation(this, R.anim.music_hand_up_anim);
            mPlay.setImageResource(R.drawable.bottom_play_selector);
        }
        if (handAnima != null) {
            handAnima.setFillAfter(true);
            mHand.startAnimation(handAnima);
        }
    }

    private void s() {
        AnimatorSet set = new AnimatorSet() ;
       /* ObjectAnimator anim = ObjectAnimator .ofFloat(mHand, "rotationX", 0f, 180f);
        anim.setDuration(2000);
        ObjectAnimator anim2 = ObjectAnimator .ofFloat(mHand, "rotationX", 180f, 0f);
        anim2.setDuration(2000);
        ObjectAnimator anim3 = ObjectAnimator .ofFloat(mHand, "rotationY", 0f, 180f);
        anim3.setDuration(2000);
        ObjectAnimator anim4 = ObjectAnimator .ofFloat(mHand, "rotationY", 180f, 0f);
        anim4.setDuration(2000);

        set.play(anim).before(anim2);
        set.play(anim3).before(anim4) ;
        set.start();*/

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.music_hand_up_anim);
        animation.setFillAfter(true);
        mHand.startAnimation(animation);
    }
}
