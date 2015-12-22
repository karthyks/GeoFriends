package com.karthyk.geofriends.Activities.Main;

import android.app.Fragment;

/**
 * Created by karthik on 24/11/15.
 */
public interface MainInteractor {
  Fragment getFragment(int pos);

  boolean isUserInDB();
}
