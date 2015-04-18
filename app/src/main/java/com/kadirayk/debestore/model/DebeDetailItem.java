package com.kadirayk.debestore.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kadiray on 13.04.2015.
 */
public class DebeDetailItem implements Parcelable{

    private long id;
    private long parentId;
    private int place;
    private String title;
    private String author;
    private String url;
    private String content;
    private String date;

    DebeDetailItem(Parcel in){
        this.id = in.readLong();
        this.parentId = in.readLong();
        this.place = in.readInt();
        this.title = in.readString();
        this.author = in.readString();
        this.url = in.readString();
        this.content = in.readString();
        this.date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(parentId);
        dest.writeInt(place);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(url);
        dest.writeString(content);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DebeDetailItem> CREATOR = new Creator<DebeDetailItem>(){
        @Override
        public DebeDetailItem createFromParcel(Parcel in) {
            return new DebeDetailItem(in);
        }

        @Override
        public DebeDetailItem[] newArray(int size) {
            return new DebeDetailItem[size];
        }

    };

    public DebeDetailItem(long parentId, int place, String title, String author, String url, String content, String date) {
//        this.id = id;
        this.parentId = parentId;
        this.place = place;
        this.title = title;
        this.author = author;
        this.url = url;
        this.content = content;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
