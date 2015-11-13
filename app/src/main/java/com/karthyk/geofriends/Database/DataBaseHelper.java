package com.karthyk.geofriends.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.karthyk.geofriends.Utility.DbCommandLineUtility;

import java.util.HashMap;

public class DataBaseHelper extends SQLiteOpenHelper {
  private final static String TAG = DataBaseHelper.class.getSimpleName();
  public final static String DATABASE_NAME = "geoFriendsDB";
  public static String TABLE_CMD_LINE = "";
  private Context mContext;

  public DataBaseHelper(Context context) {
    super(context, DATABASE_NAME, null, 1);
    mContext = context;
  }

  public void createTable() {
    HashMap<String, String> columns = new HashMap<>();
    columns.put(Table.COLUMN_USERID, "INTEGER");
    columns.put(Table.COLUMN_USER_NAME, "TEXT");
    columns.put(Table.COLUMN_FIRST_NAME, "TEXT");
    columns.put(Table.COLUMN_LAST_NAME, "TEXT");
    columns.put(Table.COLUMN_USER_DP, "BLOB");
    columns.put(Table.COLUMN_USER_COVER, "BLOB");
    columns.put(Table.COLUMN_AGE, "INTEGER");
    columns.put(Table.COLUMN_DOB, "TEXT");
    columns.put(Table.COLUMN_CURRENT_CITY, "TEXT");
    columns.put(Table.COLUMN_CURRENT_LATITUDE, "INTEGER");
    columns.put(Table.COLUMN_CURRENT_LONGITUDE, "INTEGER");
    columns.put(Table.COLUMN_CURRENT_COUNTRY, "TEXT");
    TABLE_CMD_LINE = DbCommandLineUtility.createTable(Table.NAME, columns);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    createTable();
    db.execSQL(TABLE_CMD_LINE);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + Table.NAME);
    onCreate(db);
  }

  public Cursor getUserData(int userID) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor res = db.rawQuery("select * from " + Table.NAME + " where UserID=" + userID + "", null);
    return res;
  }
}
