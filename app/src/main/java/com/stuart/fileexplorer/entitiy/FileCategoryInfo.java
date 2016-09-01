package com.stuart.fileexplorer.entitiy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/30.
 */
public class FileCategoryInfo {

    private List<FileInfo> mFileInfo;

    private FileInfo.Category mCategory;

    private FileCategoryInfo() {
    }

    public FileCategoryInfo(List<FileInfo> infos, FileInfo.Category category) {
        setFileInfo(infos);
        setCategory(category);
    }

    public List<FileInfo> getFileInfo() {
        return mFileInfo;
    }

    public void setFileInfo(List<FileInfo> mFileInfo) {
        this.mFileInfo = mFileInfo;
    }

    public FileInfo.Category getCategory() {
        return mCategory;
    }

    public void setCategory(FileInfo.Category mCategory) {
        this.mCategory = mCategory;
    }

    public void addFileInfo(FileInfo fileInfo) {
        if (mFileInfo == null) {
            mFileInfo = new ArrayList<FileInfo>();
        }
        mFileInfo.add(fileInfo);
    }
}
