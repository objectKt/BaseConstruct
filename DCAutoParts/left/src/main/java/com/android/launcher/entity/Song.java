package com.android.launcher.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

@Entity
public class Song implements Serializable{

    public static final long serialVersionUID = 1L ;

    @Id(autoincrement = true)
    public Long id ;
    /**
     * 歌手
     */
    private String artist;
    /**
     * 歌曲名
     */
    private String title;
    /**
     * 图片
     */
    private byte[] image;
    /**
     * 歌曲的地址
     */
    private String path;

    private String album;
    /**
     * 歌曲长度
     */
    private int duration;
    /**
     * 歌曲的大小
     */
    private long size;

    @Generated(hash = 1356921839)
    public Song(Long id, String artist, String title, byte[] image, String path,
            String album, int duration, long size) {
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.image = image;
        this.path = path;
        this.album = album;
        this.duration = duration;
        this.size = size;
    }

    @Generated(hash = 87031450)
    public Song() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Song song = (Song) o;
        return path.equals(song.path);
    }

    @Override
    public String toString() {
        return "Song{" +
                "artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", album='" + album + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                '}';
    }

    public String getAlbum() {
        if(album != null) {
            return album;
        }
        return "未知";
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        if(artist != null) {
            return artist;
        }
        return "未知";
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        if (title != null) {
            return title;
        }
        return "未知";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Deprecated
    public byte[] getImage() {
        return image;
    }

    public Bitmap getBitmap() {
        if (image != null) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        return null;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
