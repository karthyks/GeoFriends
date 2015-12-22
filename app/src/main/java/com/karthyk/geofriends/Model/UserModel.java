package com.karthyk.geofriends.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.karthyk.geofriends.database.Table;

public class UserModel implements Parcelable {
  private String mUserID;
  private String mUserName;
  private String mFirstName;
  private String mLastName;
  private String mAge;
  private String mDOB;
  private String mCurrentCity;
  private double mCurrentLatitude;
  private double mCurrentLongitude;
  private String mCurrentCountry;
  private byte[] mUserDP;
  private byte[] mUserCover;

  public UserModel(Cursor cursor) {
    mUserID = cursor.getString(cursor.getColumnIndex((Table.COLUMN_USERID)));
    mUserName = cursor.getString(cursor.getColumnIndex(Table.COLUMN_USER_NAME));
    mFirstName = cursor.getString(cursor.getColumnIndex(Table.COLUMN_FIRST_NAME));
    mLastName = cursor.getString(cursor.getColumnIndex(Table.COLUMN_LAST_NAME));
    mAge = cursor.getString(cursor.getColumnIndex(Table.COLUMN_AGE));
    mDOB = cursor.getString(cursor.getColumnIndex(Table.COLUMN_DOB));
    mCurrentCity = cursor.getString(cursor.getColumnIndex(Table.COLUMN_CURRENT_CITY));
    mCurrentLatitude = cursor.getDouble(cursor.getColumnIndex(Table.COLUMN_CURRENT_LATITUDE));
    mCurrentLongitude = cursor.getDouble(cursor.getColumnIndex(Table.COLUMN_CURRENT_LONGITUDE));
    mCurrentCountry = cursor.getString(cursor.getColumnIndex(Table.COLUMN_CURRENT_COUNTRY));
    mUserDP = cursor.getBlob(cursor.getColumnIndex(Table.COLUMN_USER_DP));
    mUserCover = cursor.getBlob(cursor.getColumnIndex(Table.COLUMN_USER_COVER));
  }

  protected UserModel(Parcel in) {
    mUserID = in.readString();
    mUserName = in.readString();
    mFirstName = in.readString();
    mLastName = in.readString();
    mAge = in.readString();
    mDOB = in.readString();
    mCurrentCity = in.readString();
    mCurrentLatitude = in.readDouble();
    mCurrentLongitude = in.readDouble();
    mCurrentCountry = in.readString();
    mUserDP = in.createByteArray();
    mUserCover = in.createByteArray();
  }

  public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
    @Override
    public UserModel createFromParcel(Parcel in) {
      return new UserModel(in);
    }

    @Override
    public UserModel[] newArray(int size) {
      return new UserModel[size];
    }
  };

  public String getmUserID() {
    return mUserID;
  }

  public void setmUserID(String mUserID) {
    this.mUserID = mUserID;
  }

  public String getmUserName() {
    return mUserName;
  }

  public void setmUserName(String mUserName) {
    this.mUserName = mUserName;
  }

  public String getmFirstName() {
    return mFirstName;
  }

  public void setmFirstName(String mFirstName) {
    this.mFirstName = mFirstName;
  }

  public String getmLastName() {
    return mLastName;
  }

  public void setmLastName(String mLastName) {
    this.mLastName = mLastName;
  }

  public String getmAge() {
    return mAge;
  }

  public void setmAge(String mAge) {
    this.mAge = mAge;
  }

  public String getmDOB() {
    return mDOB;
  }

  public void setmDOB(String mDOB) {
    this.mDOB = mDOB;
  }

  public String getmCurrentCity() {
    return mCurrentCity;
  }

  public void setmCurrentCity(String mCurrentCity) {
    this.mCurrentCity = mCurrentCity;
  }

  public double getmCurrentLatitude() {
    return mCurrentLatitude;
  }

  public void setmCurrentLatitude(long mCurrentLatitude) {
    this.mCurrentLatitude = mCurrentLatitude;
  }

  public double getmCurrentLongitude() {
    return mCurrentLongitude;
  }

  public void setmCurrentLongitude(long mCurrentLongitude) {
    this.mCurrentLongitude = mCurrentLongitude;
  }

  public String getmCurrentCountry() {
    return mCurrentCountry;
  }

  public void setmCurrentCountry(String mCurrentCountry) {
    this.mCurrentCountry = mCurrentCountry;
  }

  public byte[] getmUserDP() {
    return mUserDP;
  }

  public void setmUserDP(byte[] mUserDP) {
    this.mUserDP = mUserDP;
  }

  public byte[] getmUserCover() {
    return mUserCover;
  }

  public void setmUserCover(byte[] mUserCover) {
    this.mUserCover = mUserCover;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(mUserID);
    dest.writeString(mUserName);
    dest.writeString(mFirstName);
    dest.writeString(mLastName);
    dest.writeString(mAge);
    dest.writeString(mDOB);
    dest.writeString(mCurrentCity);
    dest.writeDouble(mCurrentLatitude);
    dest.writeDouble(mCurrentLongitude);
    dest.writeString(mCurrentCountry);
    dest.writeByteArray(mUserDP);
    dest.writeByteArray(mUserCover);
  }
}
