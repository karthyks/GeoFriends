package com.karthyk.geofriends.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DbBitMapUtility {

  // convert from bitmap to byte array
  public static byte[] getBytes(Bitmap bitmap) {
    byte[] byteImage;
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
    byteImage = stream.toByteArray();
    try {
      stream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return byteImage;
  }

  // convert from byte array to bitmap
  public static Bitmap getImage(byte[] image) {
    return BitmapFactory.decodeByteArray(image, 0, image.length);
  }
}
