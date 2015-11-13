package com.karthyk.geofriends.Database;

import android.content.Context;
import android.database.Cursor;

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
    return userModelArrayList;
  }
}
