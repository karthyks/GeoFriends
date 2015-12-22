package com.karthyk.geofriends.database;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.karthyk.geofriends.Model.UserModel;

import java.util.ArrayList;

public class DataResolver {

  Context mContext;

  public DataResolver(Context context) {
    mContext = context;
  }

  public ArrayList<UserModel> getUserLists() {
    ArrayList<UserModel> userModelArrayList = new ArrayList<>();
    String[] projection = new String[]{
        "id",
        Table.COLUMN_USERID,
        Table.COLUMN_USER_NAME,
        Table.COLUMN_FIRST_NAME,
        Table.COLUMN_LAST_NAME,
        Table.COLUMN_USER_DP,
        Table.COLUMN_USER_COVER,
        Table.COLUMN_AGE,
        Table.COLUMN_DOB,
        Table.COLUMN_CURRENT_CITY,
        Table.COLUMN_CURRENT_LATITUDE,
        Table.COLUMN_CURRENT_LONGITUDE,
        Table.COLUMN_CURRENT_COUNTRY};

    Cursor cursor = mContext.getContentResolver().query(DataProvider.mURI, projection
        , null, null, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      userModelArrayList.add(new UserModel(cursor));
      cursor.moveToNext();
    }
    cursor.close();
    return userModelArrayList;
  }

  public UserModel getUser(String userID) {
    UserModel userModel = null;
    String[] projection = new String[]{
        "id",
        Table.COLUMN_USERID,
        Table.COLUMN_USER_NAME,
        Table.COLUMN_FIRST_NAME,
        Table.COLUMN_LAST_NAME,
        Table.COLUMN_USER_DP,
        Table.COLUMN_USER_COVER,
        Table.COLUMN_AGE,
        Table.COLUMN_DOB,
        Table.COLUMN_CURRENT_CITY,
        Table.COLUMN_CURRENT_LATITUDE,
        Table.COLUMN_CURRENT_LONGITUDE,
        Table.COLUMN_CURRENT_COUNTRY};

    Cursor cursor = mContext.getContentResolver().query(DataProvider.mURI, projection,
        Table.COLUMN_USERID + " = " + userID,
        null, null);
    if (cursor != null) {
      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
        userModel = new UserModel(cursor);
        Log.d("Resolver", userModel.getmFirstName());
        break;
      }
      cursor.close();
    }
    return userModel;
  }
}
