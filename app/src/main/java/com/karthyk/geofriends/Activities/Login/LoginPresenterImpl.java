package com.karthyk.geofriends.Activities.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.plus.model.people.Person;

public class LoginPresenterImpl implements LoginPresenter, LoginFinishedListener {
  LoginView mLoginView;
  LoginInteractor mLoginInteractor;

  public LoginPresenterImpl(LoginView loginView, Activity activity, Context context) {
    mLoginView = loginView;
    mLoginInteractor = new LoginInteractorImpl(activity, context, this);
  }

  @Override
  public void OnActivityResult(int requestCode, Intent data) {
    mLoginInteractor.onSignInResult(requestCode, data);
  }

  @Override public void sendBackUserDp(byte[] userDP) {
    mLoginInteractor.setUserDP(userDP);
  }

  @Override public boolean isUserSignedIn() {
    return mLoginInteractor.isUserInDB();
  }

  @Override
  public void onSuccess(String userName) {
    mLoginView.Connected(userName);
  }

  @Override
  public void onFailure(String error) {
    mLoginView.NoNetwork(error);
  }

  @Override public void onDisconnected() {
    mLoginView.OnDisconnected();
  }

  @Override
  public void PersonInfo(Person person) {
  }

  @Override
  public void DisplayPicture(String url) {
    mLoginView.setDisplayPicture(url);
  }
}
