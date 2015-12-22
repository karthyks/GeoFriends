package com.karthyk.geofriends.Activities.Login;

public interface LoginView {

  void NoNetwork(String error);

  void Connected(String userName);

  void OnDisconnected();

  void setDisplayPicture(String url);
}
