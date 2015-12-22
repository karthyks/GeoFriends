package com.karthyk.geofriends.Activities.Login;

import android.content.Intent;

public interface LoginPresenter {

  void OnActivityResult(int requestCode, Intent data);

  void sendBackUserDp(byte[] userDP);

  boolean isUserSignedIn();
}
