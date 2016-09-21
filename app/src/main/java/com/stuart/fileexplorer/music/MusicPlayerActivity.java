package com.stuart.fileexplorer.music;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

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
}
