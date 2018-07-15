package com.example.nenad.businessideacreator;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "idea_table")
public class Idea implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int mId;
    private String mTitle;
    private String mProblems;

    private int mNeed;
    private int mEntry;
    private int mControl;
    private int mScale;
    private int mTime;
    private int mDifficulty;
    private int mPotential;

    private Boolean mNew;
    private Boolean mEditable;

    public Idea(@NonNull String title){
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getProblems() {
        return mProblems;
    }

    public void setProblems(String problems) {
        mProblems = problems;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getNeed() {
        return mNeed;
    }

    public void setNeed(int need) {
        mNeed = need;
    }

    public int getEntry() {
        return mEntry;
    }

    public void setEntry(int entry) {
        mEntry = entry;
    }

    public int getControl() {
        return mControl;
    }

    public void setControl(int control) {
        mControl = control;
    }

    public int getScale() {
        return mScale;
    }

    public void setScale(int scale) {
        mScale = scale;
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public Boolean getNew() {
        return mNew;
    }

    public void setNew(Boolean aNew) {
        mNew = aNew;
    }

    public int getDifficulty() {
        return mDifficulty;
    }

    public void setDifficulty(int difficulty) {
        mDifficulty = difficulty;
    }

    public int getPotential() {
        return mPotential;
    }

    public void setPotential(int potential) {
        mPotential = potential;
    }

    public Boolean getEditable() {
        return mEditable;
    }

    public void setEditable(Boolean editable) {
        mEditable = editable;
    }

    public Idea(Parcel parcel){
         mTitle = parcel.readString();
         mProblems = parcel.readString();
         mNeed = parcel.readInt();
         mEntry = parcel.readInt();
         mControl = parcel.readInt();
         mScale = parcel.readInt();
         mTime = parcel.readInt();
         mDifficulty = parcel.readInt();
         mPotential = parcel.readInt();
         mNew = parcel.readByte() != 0;
         mEditable = parcel.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
       parcel.writeString(mTitle);
       parcel.writeString(mProblems);
       parcel.writeInt(mNeed);
       parcel.writeInt(mEntry);
       parcel.writeInt(mControl);
       parcel.writeInt(mScale);
       parcel.writeInt(mTime);
       parcel.writeInt(mDifficulty);
       parcel.writeInt(mPotential);
       parcel.writeByte((byte) (mNew ? 1 : 0));
       parcel.writeByte((byte) (mEditable ? 1 : 0));
    }

    public static final Parcelable.Creator<Idea> CREATOR
            = new Parcelable.Creator<Idea>(){
        @Override
        public Idea createFromParcel(Parcel parcel) {
            return new Idea(parcel);
        }

        @Override
        public Idea[] newArray(int size) {
            return new Idea[0];
        }
    };

}