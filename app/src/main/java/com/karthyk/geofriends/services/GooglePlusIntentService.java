package com.karthyk.geofriends.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.plus.model.people.Person;
import com.karthyk.geofriends.Utility.DbBitMapUtility;
import com.karthyk.geofriends.Utility.DownloadUtility;
import com.karthyk.geofriends.database.DataProvider;
import com.karthyk.geofriends.database.Table;

import java.util.concurrent.ExecutionException;

public class GooglePlusIntentService extends IntentService {

  public static final String TAG = GooglePlusIntentService.class.getSimpleName();

  Person mCurrentPerson;
  byte[] mUserDP;

  public GooglePlusIntentService() {
    super(GooglePlusIntentService.class.getSimpleName());
  }

  @Override protected void onHandleIntent(Intent intent) {
    mCurrentPerson = intent.getParcelableExtra("Person");
    mUserDP = intent.getByteArrayExtra("USER_DP");
    Log.d(TAG, mCurrentPerson.getId());
    ContentValues contentValues = createContent();
    int isAlreadyPresent = 0;
    Cursor c = this.getContentResolver().query(DataProvider.mURI, null,
        Table.COLUMN_USERID, null, null);
    if (c != null) {
      isAlreadyPresent = c.getCount();
      c.close();
    }
    if (isAlreadyPresent > 0) {
      Log.d(TAG, "Updated user");
      this.getContentResolver().update(DataProvider.mURI, contentValues, Table.COLUMN_USERID, null);
    } else {
      Log.d(TAG, "Inserted user");
      this.getContentResolver().insert(DataProvider.mURI, contentValues);
    }
  }

  private ContentValues createContent() {
    ContentValues contentValues = new ContentValues();
    if (mCurrentPerson == null) {
      return null;
    }

    if (mCurrentPerson.hasId()) {
      contentValues.put(Table.COLUMN_USERID, mCurrentPerson.getId());
    } else {
      contentValues.put(Table.COLUMN_USERID, "NULL");
    }

    if (mCurrentPerson.hasDisplayName()) {
      contentValues.put(Table.COLUMN_USER_NAME, mCurrentPerson.getDisplayName());
      contentValues.put(Table.COLUMN_FIRST_NAME, mCurrentPerson.getDisplayName());
      contentValues.put(Table.COLUMN_LAST_NAME, mCurrentPerson.getDisplayName());
    } else {
      contentValues.put(Table.COLUMN_USER_NAME, "NULL");
      contentValues.put(Table.COLUMN_FIRST_NAME, "NULL");
      contentValues.put(Table.COLUMN_LAST_NAME, "NULL");
    }

    if (mCurrentPerson.hasAgeRange() && mCurrentPerson.getAgeRange().hasMax()) {
      contentValues.put(Table.COLUMN_AGE, mCurrentPerson.getAgeRange().getMax());
    } else {
      contentValues.put(Table.COLUMN_AGE, "NULL");
    }

    if (mCurrentPerson.hasBirthday()) {
      contentValues.put(Table.COLUMN_DOB, mCurrentPerson.getBirthday());
    }
    contentValues.put(Table.COLUMN_USER_DP, mUserDP);
//    if(mCurrentPerson.hasImage()) {
//      Bitmap img;
//      try {
//        img = new DownloadUtility().execute(mCurrentPerson.getImage().getUrl()).get();
//        contentValues.put(Table.COLUMN_USER_DP, DbBitMapUtility.getBytes(img));
//      } catch (InterruptedException | ExecutionException e) {
//        contentValues.put(Table.COLUMN_USER_DP, (byte[]) null);
//        e.printStackTrace();
//      }
//    }


    if (mCurrentPerson.hasCover() && mCurrentPerson.getCover().hasCoverPhoto()) {
      Bitmap img;
      try {
        img = new DownloadUtility().execute(mCurrentPerson.getCover().getCoverPhoto().getUrl())
            .get();
        contentValues.put(Table.COLUMN_USER_COVER, DbBitMapUtility.getBytes(img));
      } catch (InterruptedException | ExecutionException e) {
        contentValues.put(Table.COLUMN_USER_COVER, (byte[]) null);
        e.printStackTrace();
      }
    }
    return contentValues;
  }
}
