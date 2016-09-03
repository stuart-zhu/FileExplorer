package com.stuart.fileexplorer.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.stuart.fileexplorer.entitiy.FileInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stuart on 2016/8/30.
 */
public class FileUtils {

    private static final Uri PICTURE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    private static final Uri VIDEO_URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

    private static final Uri AUDIO_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    private static final Uri AUDIO_ALBUM_URI = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

    public static List<FileInfo> getAllFile(Context context, FileInfo.Category category) {

        Uri uri = null;
        switch (category) {

            case MUSIC:
                uri = AUDIO_URI;
                break;

            case DOCUMENT:
                break;
            case PICTURE:
                uri = PICTURE_URI;
                break;
            case VIDEO:
                uri = VIDEO_URI;
                break;
            case OTHER:
                break;
        }

        if (uri == null) {
            Log.i("stuart", "没有uri 没法得到数据");
            return null;
        }
        Cursor c = null;
        try {

            c = context.getContentResolver().query(
                    uri, null, null, null, null
            );
            List<FileInfo> list = new ArrayList<>();
            while (c != null && c.moveToNext()) {
                list.add(new FileInfo(category, new File(c.getString(c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)))));
            }
            return list;
        } finally {
            if (c != null) {
                c.close();
            }
        }

    }


    private static final String[] AUDIO_SELECTION = new String[]{MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID};

    public static String getAlbumPath(Context context, String path) {
        Cursor c = null;
        try {
            c = context.getContentResolver().query(AUDIO_URI, AUDIO_SELECTION, MediaStore.Audio.Media.DATA + "=?", new String[]{path}, null);
            if (c != null && c.moveToNext()) {
                int album_id = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                c = context.getContentResolver().query(AUDIO_ALBUM_URI, null, MediaStore.Audio.Albums._ID + "=" + album_id, null, null);
                if (c != null && c.moveToNext()) {
                    path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ALBUM_ART));
                    if (!TextUtils.isEmpty(path)) {
                        return path;
                    }
                }
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return null;
    }


    public static String getVideoPath(Context c, String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            Bitmap bitmap = retriever.getFrameAtTime();
            return saveBitmap(c, bitmap);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String saveBitmap(Context context, Bitmap bitmap) throws IOException {
        File f = new File(context.getCacheDir(), bitmap.hashCode() + "");
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f.getAbsolutePath();
    }
}
