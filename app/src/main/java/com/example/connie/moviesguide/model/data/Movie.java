package com.example.connie.moviesguide.model.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.sql.Blob;
import java.util.Date;

@Entity
@TypeConverters(DateConverter.class)
public class Movie {

    @PrimaryKey(autoGenerate = true)
    public int mId;
    public String mImage;
    public String mTitle;
    public String mDetails;

    public Movie(int mId, String mImage, String mTitle, String mDetails) {
        this.mId = mId;
        this.mImage = mImage;
        this.mTitle = mTitle;
        this.mDetails = mDetails;
    }


    public Movie(String title, String imagePath, String detail) {
        this.mTitle = title;
        this.mImage = imagePath;
        this.mDetails = detail;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    public String getmDetails() {
        return mDetails;
    }

    public void setmDetails(String mDetails) {
        this.mDetails = mDetails;
    }
}
