package com.karthyk.geofriends.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

public class DataProvider extends ContentProvider {

  private final static String TAG = DataProvider.class.getSimpleName();
  private final static String PROVIDER = "com.karthyk.geofriends.Database" + "." +
      DataBaseHelper.DATABASE_NAME;
  private static HashMap<String, String> PROJECTION_MAP;

  SQLiteDatabase mDB;
  public static Uri mURI;
  static UriMatcher uriMatcher;

  static {
    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(PROVIDER, Table.NAME, 1);
    uriMatcher.addURI(PROVIDER, Table.NAME + "/#", 2);
  }

  public void BuildURI() {
    mURI = new Uri.Builder()
        .scheme("content")
        .authority(PROVIDER)
        .appendPath(Table.NAME).build();
  }

  @Override public boolean onCreate() {
    BuildURI();
    DataBaseHelper dbHelper = new DataBaseHelper(getContext());
    mDB = dbHelper.getWritableDatabase();
    return (mDB != null);
  }

  @Override public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
    sqLiteQueryBuilder.setTables(Table.NAME);

    switch (uriMatcher.match(uri)) {
      case 1:
        sqLiteQueryBuilder.setProjectionMap(PROJECTION_MAP);
        break;
      case 2:
        sqLiteQueryBuilder.appendWhere("id = " + uri.getPathSegments().get(1));
        break;
      default:
        throw new IllegalArgumentException("Unknown URI " + uri);
    }

    if (sortOrder == null || sortOrder == "") {
      /**
       * By default sort on id
       */
      sortOrder = "id";
    }
    Cursor c = sqLiteQueryBuilder.query(mDB, projection, selection, selectionArgs, null, null, sortOrder);
    c.setNotificationUri(getContext().getContentResolver(), uri);
    return c;
  }

  @Override public String getType(Uri uri) {
    return null;
  }

  @Override public Uri insert(Uri uri, ContentValues values) {
    long insertID = mDB.insert(Table.NAME, null, values);
    if (insertID > 0) {
      Uri _uri = ContentUris.withAppendedId(mURI, insertID);
      getContext().getContentResolver().notifyChange(_uri, null);
      Log.d(TAG, "Inserted into " + _uri.toString());
      return _uri;
    }
    throw new SQLException("Failed to add a record into " + uri);
  }

  @Override public int delete(Uri uri, String selection, String[] selectionArgs) {
    int count;

    switch (uriMatcher.match(uri)) {
      case 1:
        count = mDB.delete(Table.NAME, selection, selectionArgs);
        break;

      case 2:
        String id = uri.getPathSegments().get(1);
        count = mDB.delete(Table.NAME, "id = " + id +
            (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
        break;

      default:
        throw new IllegalArgumentException("Unknown URI " + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);
    return count;
  }

  @Override public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    int count;

    switch (uriMatcher.match(uri)) {
      case 1:
        count = mDB.update(Table.NAME, values, selection, selectionArgs);
        break;

      case 2:
        count = mDB.update(Table.NAME, values, "id = " + uri.getPathSegments().get(1) +
            (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
        break;

      default:
        throw new IllegalArgumentException("Unknown URI " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return count;
  }
}
