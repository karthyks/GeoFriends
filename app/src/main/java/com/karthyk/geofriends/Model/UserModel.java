package com.karthyk.geofriends.Model;

import android.database.Cursor;

import com.karthyk.geofriends.Database.Table;

public class UserModel {
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
}
