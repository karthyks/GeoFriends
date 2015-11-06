package com.karthyk.geofriends.Login;

import com.google.android.gms.plus.model.people.Person;

public interface LoginView {
  void NoNetwork(String error);

  void Connected(String userName);

  void OnDisconnected();

  void getPersonInfo(Person person);

  void setDisplayPicture(String url);

  void NavigateToHome();
}
