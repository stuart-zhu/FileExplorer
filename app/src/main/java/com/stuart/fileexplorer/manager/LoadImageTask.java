package com.stuart.fileexplorer.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.stuart.fileexplorer.entitiy.FileInfo;
import com.stuart.fileexplorer.utils.BitmapCache;
import com.stuart.fileexplorer.utils.FileUtils;
import com.stuart.fileexplorer.utils.ResourceUtil;

import java.io.File;

/**
 * Created by stuart on 2016/9/2.
 */
public class LoadImageTask extends AsyncTask {

    private Context mContext;
    private ImageView iv;
    private FileInfo.Category mCategory;
    private File file;
    private BitmapCache mCache;

    private boolean cancle;

    private boolean isUsed;

    public interface onLoadImageListener {
        public void onLoadStart();

        public void onLoading();

        public void onLoadFinish();

    }

    private onLoadImageListener l;

    public void startOnLoadImageListener(onLoadImageListener l) {
        this.l = l;
    }

    public LoadImageTask(Context context, ImageView iv, File f, FileInfo.Category c) {
        mContext = context;
        this.iv = iv;
        this.mCategory = c;
        this.file = f;
        mCache = BitmapCache.getInstance(context);
    }

    @Override
    protected void onPreExecute() {
        isUsed = true;
        if (l != null) l.onLoadStart();
        super.onPreExecute();
        iv.setImageResource(ResourceUtil.getDefalutImageRes(mCategory));
    }

    @Override
    protected Bitmap doInBackground(Object[] objects) {
        if (l != null) l.onLoading();
        String path = null;
        switch (mCategory) {
            case MUSIC:
                path = FileUtils.getAlbumPath(mContext, file.getAbsolutePath());
                break;
            case DOCUMENT:
                break;
            case PICTURE:

                path =  file.getAbsolutePath();
                break;
            case VIDEO:
                path =  FileUtils.getVideoPath(mContext, file.getAbsolutePath());
                break;
            case OTHER:
                break;
            case APK:
                break;
            case DOWNLOAD:
                break;
        }
        if (path != null) {
            Bitmap bm =  BitmapFactory.decodeFile(path);
            mCache.putBitmapCache(file.getAbsolutePath(), bm , true);
            return bm;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        if (l != null) l.onLoadFinish();
        super.onPostExecute(o);

        if (cancle) return;
        if (o == null) {
            Log.i("stuart", "hh path is null");
            return;
        }
       iv.setImageBitmap((Bitmap) o);

    }

    public void cancle() {
        cancle = true;
    }

    public boolean isUsed() {
        return isUsed;
    }

    /*@Override
    public String toString() {
        return "LoadImageTask{" +
                "mContext=" + mContext +
                ", iv=" + iv +
                ", mCategory=" + mCategory +
                ", file=" + file +
                ", isRunning=" + isRunning +
                ", cancle=" + cancle +
                ", l=" + l +
                '}';
    }*/
}
