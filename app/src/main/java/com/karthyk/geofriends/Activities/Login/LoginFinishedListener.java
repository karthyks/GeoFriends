package com.karthyk.geofriends.Activities.Login;

import com.google.android.gms.plus.model.people.Person;

public interface LoginFinishedListener {
  void onSuccess(String userName);

  void onFailure(String error);

  void onDisconnected();

  void PersonInfo(Person person);

  void DisplayPicture(String url);
}
