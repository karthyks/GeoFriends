package com.karthyk.geofriends.Activities;

import android.app.Application;
import android.content.Context;

import com.karthyk.geofriends.Model.GoogleInfo;

public class AppGlobal extends Application {
  public static final String TAG = AppGlobal.class.getSimpleName();
  private static Context context;
  private static GoogleInfo mGoogleInfo;

  @Override public void onCreate() {
    context = getApplicationContext();
    mGoogleInfo = GoogleInfo.getInstance();
    super.onCreate();
  }

  public static Context getContext() {
    return context;
  }

  public static GoogleInfo getGoogleInfo() {
    return mGoogleInfo;
  }
}
