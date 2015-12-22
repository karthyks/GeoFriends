package com.karthyk.geofriends.Activities.Login;

import android.content.Intent;

public interface LoginInteractor {

  void onSignInResult(int requestCode, Intent data);

  void setUserDP(byte[] userDP);

  boolean isUserInDB();
}
