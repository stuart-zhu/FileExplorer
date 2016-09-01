package com.stuart.fileexplorer.entitiy;

import java.io.File;

/**
 * Created by lenovo on 2016/8/30.
 */
public class FileInfo {

    private Category category = Category.OTHER;

    private File mFile;

    private FileInfo() {
    }

    public FileInfo(Category category, File mFile) {
        this.category = category;
        this.mFile = mFile;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public File getFile() {
        return mFile;
    }

    public void setFile(File mFile) {
        this.mFile = mFile;
    }

    @Override
    public String toString() {
        return "{FileInfo" +
                "category=" + category +
                ", mFile=" + mFile +
                '}';
    }


    public enum Category {
        MUSIC, DOCUMENT, PICTURE, VIDEO, OTHER, APK, DOWNLOAD
    }
}
