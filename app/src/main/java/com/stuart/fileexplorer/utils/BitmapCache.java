/*
 * Copyright(c) 2015 Yangzhou New Telecom Science & Technology Co., Ltd. All rights reserved.
 */
package com.stuart.fileexplorer.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BitmapCache {

    private static final int BITMAP_QUAILITY = 80;

    private static BitmapCache mInstance;
    private HashMap<String, SoftReference<Bitmap>> mCache;
    private static Context mContext;

    private BitmapCache () {
        mCache = new HashMap<String, SoftReference<Bitmap>>();
    }

    public static BitmapCache getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BitmapCache();
            mContext = context;
        }
        return mInstance;
    }

    private void saveCacheToSdcard(String path, Bitmap bm) {
        File cache = new File(mContext.getExternalCacheDir() +"/" + path.hashCode());
        if (!cache.exists()) {
            try {
                FileOutputStream op = new FileOutputStream(cache);
                bm.compress(Bitmap.CompressFormat.JPEG, BITMAP_QUAILITY, op);
                op.close();
            } catch (Exception e) {
                Log.e("FileExplorer ", "putCache Error", e);
            }
        }
    }

    public Bitmap getCache(String path) {
        Bitmap bm;
        SoftReference<Bitmap> soft;
        if ((soft = mCache.get(path)) != null) {
            return soft.get();
        }
        File cache = new File(mContext.getExternalCacheDir() +"/" + path.hashCode());
        if (cache.exists()) {
            try {
                bm = BitmapFactory.decodeFile(cache.getAbsolutePath());
                mCache.put(path, new SoftReference<Bitmap>(bm));
                return bm;
            } catch (Exception e) {
                Log.e("FileExplorer ", "getCache Error", e);
            }
        }
        return null;
    }

    public void putBitmapCache(String path, Bitmap bm, boolean saveToSdcard) {
        if (path == null || bm == null) return;
        synchronized (mCache) {
            mCache.put(path, new SoftReference<Bitmap>(bm));
        }
        if (saveToSdcard) {
            saveCacheToSdcard(path, bm);
        }
    }

}
