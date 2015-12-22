package com.karthyk.geofriends.Activities.Fragments.Profile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.karthyk.geofriends.Utility.DbBitMapUtility;

public class ProfilePresenterImpl implements ProfilePresenter {
  public static final String TAG = ProfilePresenterImpl.class.getSimpleName();
  ProfileView mProfileView;
  ProfileInteractor mProfileInteractor;
  Activity mActivity;
  Context mContext;


  byte[] userBmp;
  String userName;
  String livesIn;
  String distance;
  double mLatitude;
  double mLongitude;

  public ProfilePresenterImpl(ProfileView profileView, Activity activity, Context context,
                              Bundle bundle) {

    mProfileView = profileView;
    mActivity = activity;
    mContext = context;
    mProfileInteractor = new ProfileInteractorImpl(mActivity, mContext);
    userName = bundle.getString(ProfileConstants.USER_NAME);
    livesIn = bundle.getString(ProfileConstants.LIVING);
    distance = bundle.getString(ProfileConstants.DISTANCE);
    mLatitude = bundle.getDouble(ProfileConstants.LATITUDE);
    mLongitude = bundle.getDouble(ProfileConstants.LONGITUDE);
    userBmp = bundle.getByteArray(ProfileConstants.USER_DP);
    mProfileView.setUserData(userName, livesIn, distance, DbBitMapUtility.getImage(userBmp));
  }

  @Override public void locateInMap() {
    mProfileInteractor.locateInMap(mLatitude, mLongitude);
  }
}
