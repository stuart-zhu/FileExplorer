package com.stuart.fileexplorer.entitiy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by stuart on 2016/9/21.
 */
public class MusicFile implements Parcelable{

    private int id;

    /**
     * 歌曲名
     */
    private String title;

    /**
     * 专辑名
     */
    private String album;

    /**
     * 歌手名
     */
    private String artist;

    /**
     * 歌曲路径
     */
    private String uri;

    /**
     * 歌曲时长
     */
    private long duration;

    /**
     * 文件大小
     */
    private long size;

    public MusicFile(int id, String title, String album, String artist, String uri, long duration, long size) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.uri = uri;
        this.duration = duration;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "MusicFile{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", uri='" + uri + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(album);
        parcel.writeString(artist);
        parcel.writeString(uri);
        parcel.writeLong(duration);
        parcel.writeLong(size);
    }

    public static Creator<MusicFile> CREATOR = new Creator<MusicFile>() {
        @Override
        public MusicFile createFromParcel(Parcel parcel) {
            int id = parcel.readInt();
            String title = parcel.readString();
            String album = parcel.readString();
            String artist = parcel.readString();
            String uri = parcel.readString();
            long duration = parcel.readLong();
            long size = parcel.readLong();
            return new MusicFile(id, title, album,artist,uri,duration,size);
        }

        @Override
        public MusicFile[] newArray(int i) {
            return new MusicFile[0];
        }
    };
}
