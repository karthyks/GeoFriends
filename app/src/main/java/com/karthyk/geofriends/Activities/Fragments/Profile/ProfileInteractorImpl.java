package com.karthyk.geofriends.Activities.Fragments.Profile;

import android.app.Activity;
import android.content.Context;

/**
 * Created by karthik on 18/11/15.
 */
public class ProfileInteractorImpl implements ProfileInteractor {
  Activity mActivity;
  Context mContext;

  public ProfileInteractorImpl(Activity activity, Context context) {
    mActivity = activity;
    mContext = context;
  }

  @Override public void locateInMap(double lat, double lon) {

  }
}
