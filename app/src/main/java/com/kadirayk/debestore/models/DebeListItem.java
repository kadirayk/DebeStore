package com.kadirayk.debestore.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kadiray on 12.04.2015.
 */
public class DebeListItem implements Parcelable{

    private long id;
    private int place;
    private String title;
    private String author;
    private String url;
    private String date;


    DebeListItem(Parcel in) {
        this.id = in.readLong();
        this.place = in.readInt();
        this.title = in.readString();
        this.author = in.readString();
        this.url = in.readString();
        this.date = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(place);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(url);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DebeListItem> CREATOR = new Creator<DebeListItem>(){
        @Override
        public DebeListItem createFromParcel(Parcel in) {
            return new DebeListItem(in);
        }

        @Override
        public DebeListItem[] newArray(int size) {
            return new DebeListItem[size];
        }

    };

    public DebeListItem(int place, String title, String author, String url, String date) {
//        this.id = id;
        this.place = place;
        this.title = title;
        this.author = author;
        this.url = url;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
