package com.karthyk.geofriends.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationUpdateService extends Service implements LocationListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

  int mStartMode;
  boolean mAllowRebind;

  Context mContext;
  GoogleApiClient mGoogleApiClient;

  double mLatitude = 0;
  double mLongitude = 0;

  BindLocationServices mBinder;

  @Override public void onCreate() {
    mGoogleApiClient = new GoogleApiClient.Builder(mContext)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .addScope(new Scope(Scopes.PROFILE))
        .addScope(new Scope(Scopes.EMAIL))
        .build();
    super.onCreate();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    mGoogleApiClient.connect();
    return mStartMode;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    stopLocationUpdates();
  }

  @Override public IBinder onBind(Intent intent) {
    return mBinder;
  }

  @Override public void onRebind(Intent intent) {
    super.onRebind(intent);
  }

  @Override public boolean onUnbind(Intent intent) {
    return mAllowRebind;
  }

  @Override public void onLocationChanged(Location location) {
    mLatitude = location.getLatitude();
    mLongitude = location.getLongitude();
  }

  @Override public void onConnected(Bundle bundle) {
    mBinder = new BindLocationServices();
  }

  @Override public void onConnectionSuspended(int i) {

  }

  @Override public void onConnectionFailed(ConnectionResult connectionResult) {

  }

  protected LocationRequest createLocationRequest() {
    LocationRequest mLocationRequest = new LocationRequest();
    mLocationRequest.setInterval(10000);
    mLocationRequest.setFastestInterval(5000);
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    return mLocationRequest;
  }

  protected void startLocationUpdates() {
    LocationServices.FusedLocationApi.requestLocationUpdates(
        mGoogleApiClient, createLocationRequest(), this);
  }

  protected void stopLocationUpdates() {
    LocationServices.FusedLocationApi.removeLocationUpdates(
        mGoogleApiClient, this);
    mGoogleApiClient.disconnect();
  }

  private class BindLocationServices extends Binder {
    public BindLocationServices() {
      if (mGoogleApiClient.isConnected()) {
        startLocationUpdates();
      } else {
        mGoogleApiClient.connect();
      }
    }

    public double LastKnownLatitude() {
      return (mLatitude != 0) ? mLatitude : 0;
    }

    public double LastKnownLongitude() {
      return (mLongitude != 0) ? mLongitude : 0;
    }
  }
}
