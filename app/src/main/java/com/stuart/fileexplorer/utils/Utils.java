package com.stuart.fileexplorer.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by stuart on 2016/9/1.
 */
public class Utils {

    private static final String TAG = "stuart - FM";
    private static final int MEMORY_UNIT_CONVERSION = 1024;

    /**
     *
     */
    public static String formatFileSize(long fileSize) {
        DecimalFormat sizeFormat = new DecimalFormat("#.00");
        StringBuffer strBuffer = new StringBuffer();

        if (fileSize < MEMORY_UNIT_CONVERSION) {
            strBuffer.append(fileSize);
            strBuffer.append(" B");
        } else if (fileSize < MEMORY_UNIT_CONVERSION * MEMORY_UNIT_CONVERSION) {
            strBuffer.append(sizeFormat.format((double) fileSize
                    / MEMORY_UNIT_CONVERSION));
            strBuffer.append(" K");
        } else if (fileSize < Math.pow(MEMORY_UNIT_CONVERSION, 3)) {
            strBuffer.append(sizeFormat.format((double) fileSize
                    / Math.pow(MEMORY_UNIT_CONVERSION, 2)));
            strBuffer.append(" M");
        } else {
            strBuffer.append(sizeFormat.format((double) fileSize
                    / Math.pow(MEMORY_UNIT_CONVERSION, 3)));
            strBuffer.append(" G");
        }
        sizeFormat = null;
        return strBuffer.toString();
    }

    public static String formatDate(long time) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy.MM.dd  HH:mm");
        if (time > 0) {
            Date date = new Date(time);
            return fmt.format(date);
        }
        return "未知";

    }


    public static Drawable getApkIcon(Context context, String apkPath) {
        PackageManager pm;
        pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return appInfo.loadIcon(pm);
            } catch (OutOfMemoryError e) {
                Log.e(TAG, e.toString());
            }
        }
        return null;
    }
}
