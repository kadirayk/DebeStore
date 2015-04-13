package com.kadirayk.debestore.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kadiray on 13.04.2015.
 */
public class DebeDetailListItem implements Parcelable{

    private long id;
    private long parentId;
    private int place;
    private String title;
    private String author;
    private String url;
    private String date;

    DebeDetailListItem(Parcel in){
        this.id = in.readLong();
        this.parentId = in.readLong();
        this.place = in.readInt();
        this.title = in.readString();
        this.author = in.readString();
        this.url = in.readString();
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
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DebeDetailListItem> CREATOR = new Creator<DebeDetailListItem>(){
        @Override
        public DebeDetailListItem createFromParcel(Parcel in) {
            return new DebeDetailListItem(in);
        }

        @Override
        public DebeDetailListItem[] newArray(int size) {
            return new DebeDetailListItem[size];
        }

    };

    public DebeDetailListItem(long parentId, int place, String title, String author, String url, String date) {
//        this.id = id;
        this.parentId = parentId;
        this.place = place;
        this.title = title;
        this.author = author;
        this.url = url;
        this.date = date;
    }

}
