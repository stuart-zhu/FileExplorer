package com.stuart.fileexplorer.utils;

import java.text.DecimalFormat;

/**
 * Created by lenovo on 2016/9/1.
 */
public class Utils {

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
}
