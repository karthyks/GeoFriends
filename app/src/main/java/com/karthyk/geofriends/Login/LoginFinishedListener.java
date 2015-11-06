package com.karthyk.geofriends.Login;

import com.google.android.gms.plus.model.people.Person;

public interface LoginFinishedListener {
  void onSuccess(String userName);

  void onFailure(String error);

  void PersonInfo(Person person);

  void DisplayPicture(String url);
}
