package com.karthyk.geofriends.Utility;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class LocationGeography {
  public static final String TAG = LocationGeography.class.getSimpleName();

  public static String getLocationString(Context context, double lat, double lon) {
    Geocoder gc = new Geocoder(context);
    try {
      List<Address> addressList = gc.getFromLocation(lat, lon, 1);
      if (addressList.size() > 0) {
        Address address = addressList.get(0);
        String location = null;
        if (address.getAddressLine(0) != null) {
          location = address.getAddressLine(0);
        }
        if (address.getAddressLine(1) != null) {
          location += address.getAddressLine(1);
        }
        if (address.getAddressLine(2) != null) {
          location += address.getAddressLine(2);
        }
        return location;
      } else {
        return null;
      }
    } catch (IOException e) {
      Log.d(TAG, e.getMessage());
      return null;
    }
  }

  public static Address getAddress(Context context, double lat, double lon) {
    Geocoder gc = new Geocoder(context);
    try {
      List<Address> addressList = gc.getFromLocation(lat, lon, 1);
      if (addressList.size() > 0) {
        Address address = addressList.get(0);
        return address;
      } else {
        return null;
      }
    } catch (IOException e) {
      Log.d(TAG, e.getMessage());
      return null;
    }
  }
}
