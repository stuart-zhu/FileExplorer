package com.stuart.fileexplorer.entitiy;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * Created by stuart on 2016/8/30.
 */
public class FileInfo implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(category.value);
        parcel.writeSerializable(mFile);

    }

    public enum Category {
        MUSIC(1), DOCUMENT(2), PICTURE(3), VIDEO(4), DOWNLOAD(6), APK(7), OTHER(8),;

        public int value;

        Category(int i) {

            this.value = i;
        }

    }

    public static Category getCategory(int i) {
        switch (i) {
            case 1:
                return Category.MUSIC;
            case 2:
                return Category.DOCUMENT;
            case 3:
                return Category.PICTURE;
            case 4:
                return Category.VIDEO;
   /*         case 5:
                return Category.MUSIC;*/
            case 6:
                return Category.DOWNLOAD;
            case 7:
                return Category.APK;
            case 8:
                return Category.OTHER;

        }
        return Category.OTHER;
    }

    public static final Creator<FileInfo> CREATOR = new Creator<FileInfo>() {
        @Override
        public FileInfo createFromParcel(Parcel parcel) {
            int i = parcel.readInt();

            Category category = getCategory(i);
            File file = (File) parcel.readSerializable();
            return new FileInfo(category, file);
        }

        @Override
        public FileInfo[] newArray(int i) {
            return new FileInfo[0];
        }
    };


}
