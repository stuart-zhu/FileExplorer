package com.stuart.fileexplorer.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.stuart.fileexplorer.entitiy.FileInfo;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/30.
 */
public class FileUtils {

    private static final Uri PICTURE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    private static final Uri VIDEO_URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

    private static final Uri MUSIC_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    public static List<FileInfo> getAllFile(Context context, FileInfo.Category category) {

        Uri uri = null;
        switch (category) {

            case MUSIC:
                uri = MUSIC_URI;
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
        try{

            c = context.getContentResolver().query(
                    uri,null,null,null, null
            );
            List<FileInfo> list = new ArrayList<>();
            while(c != null && c.moveToNext()) {
                list.add(new FileInfo(category, new File(c.getString(c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)))));
            }
            return list;
        } finally {
            if (c != null) {
                c.close();
            }
        }

    }


}
