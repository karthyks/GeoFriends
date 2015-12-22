package com.karthyk.geofriends.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

public class DownloadUtility extends AsyncTask<String, Void, Bitmap> {

  @Override protected Bitmap doInBackground(String... params) {
    try {
      URL url = new URL(params[0]);
      InputStream in = url.openStream();
      return BitmapFactory.decodeStream(in);
    } catch (Exception e) {
      Log.d("Download Image", e.toString());
    }
    return null;
  }

  @Override protected void onPostExecute(Bitmap bitmap) {
    // Do after returning the Bitmap
  }
}
