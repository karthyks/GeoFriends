package com.karthyk.geofriends.Activities.Main;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import com.karthyk.geofriends.Activities.Fragments.Profile.ProfileConstants;
import com.karthyk.geofriends.Activities.Fragments.Profile.ProfileFragment;
import com.karthyk.geofriends.Activities.Fragments.circles.CircleFragment;
import com.karthyk.geofriends.Model.GoogleInfo;
import com.karthyk.geofriends.Model.UserModel;
import com.karthyk.geofriends.database.DataResolver;


public class MainInteractorImpl implements MainInteractor {

  Context mContext;

  public MainInteractorImpl(Context context) {
    mContext = context;
  }

  @Override public Fragment getFragment(int pos) {
    Fragment fragment = null;
    switch (pos) {
      case 0:
        break;
      case 1:
        DataResolver dataResolver = new DataResolver(mContext);
        UserModel userModel = dataResolver.getUser(GoogleInfo.getInstance().getPerson().getId());
        fragment = new ProfileFragment();
        if (userModel != null) {
          Bundle bundle = new Bundle();
          bundle.putString(ProfileConstants.USER_NAME, userModel.getmFirstName());
          bundle.putString(ProfileConstants.LIVING, userModel.getmCurrentCity());
          bundle.putString(ProfileConstants.DISTANCE, "0");
          bundle.putDouble(ProfileConstants.LATITUDE, userModel.getmCurrentLatitude());
          bundle.putDouble(ProfileConstants.LONGITUDE, userModel.getmCurrentLongitude());
          bundle.putByteArray(ProfileConstants.USER_DP, userModel.getmUserDP());
          fragment.setArguments(bundle);
        }
        break;
      case 2:
        break;
      case 3:
        fragment = new CircleFragment();
        break;
      case 4:
        break;
    }
    return fragment;
  }

  @Override
  public boolean isUserInDB() {
    DataResolver dataResolver = new DataResolver(mContext);
    return dataResolver.getUserLists().size() > 0;
  }

  private byte[] getUserDP() {
    DataResolver dataResolver = new DataResolver(mContext);
    UserModel userModel = dataResolver.getUser(GoogleInfo.getInstance().getPerson().getId());
    byte[] img;
    if (userModel != null) {
      img = userModel.getmUserDP();
      return img;
    }
    return null;
  }
}
