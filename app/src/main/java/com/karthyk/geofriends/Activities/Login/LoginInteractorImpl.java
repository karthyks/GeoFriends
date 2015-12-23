package com.karthyk.geofriends.Activities.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.karthyk.geofriends.Activities.AppGlobal;
import com.karthyk.geofriends.Model.GoogleInfo;
import com.karthyk.geofriends.database.DataResolver;
import com.karthyk.geofriends.services.GooglePlusIntentService;

import java.util.ArrayList;
import java.util.List;

public class LoginInteractorImpl implements LoginInteractor,
    GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

  private static final String TAG = LoginInteractorImpl.class.getSimpleName();

  /* Client for accessing Google APIs */
  private final GoogleApiClient mGoogleApiClient;

  Context mContext;
  Activity mActivity;
  String mUserName;

  final LoginFinishedListener mLoginFinishedListener;

  GoogleSignInAccount googleSignInAccount;
  Person mPerson;

  GoogleInfo mGoogleInfo;

  public LoginInteractorImpl(Activity activity, Context context,
                             final LoginFinishedListener loginFinishedListener) {
    mContext = context;
    mActivity = activity;

    mLoginFinishedListener = loginFinishedListener;
    mGoogleInfo = AppGlobal.getGoogleInfo();
    mGoogleInfo.setGoogleClient(mContext, this);
    mGoogleApiClient = mGoogleInfo.getGoogleApiClient();
  }

  @Override
  public void onSignInResult(int requestCode, Intent data) {
    if (requestCode == GoogleInfo.RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      handleSignInResult(result);
    }
  }

  private void handleSignInResult(GoogleSignInResult result) {
    Log.d(TAG, "handleSignInResult:" + result.isSuccess());
    if (result.isSuccess()) {
      mGoogleApiClient.connect(GoogleApiClient.SIGN_IN_MODE_OPTIONAL);
      googleSignInAccount = result.getSignInAccount();
      mGoogleInfo.setGoogleSignInAccount(googleSignInAccount);
      updateUI(true);
    } else {
      updateUI(false);
    }
  }

  @Override public void setUserDP(byte[] userDP) {
    Log.d(TAG, "setting user dp");
    if (mPerson != null && mGoogleInfo.getGoogleApiClient().isConnected()) {
      addUserToDatabase(mPerson, userDP);
    } else {
      Log.d(TAG, "Null");
    }
  }

  @Override public boolean isUserInDB() {
    return !(new DataResolver(mContext).getUserLists().isEmpty());
  }

  public void addUserToDatabase(Person person, byte[] userDp) {
    Log.d(TAG, "Adding user to database");
    Intent plusService = new Intent(mContext, GooglePlusIntentService.class);
    plusService.putExtra("Person", (Parcelable) person);
    plusService.putExtra("USER_DP", userDp);
    mContext.startService(plusService);
  }

  @Override public void onConnectionFailed(ConnectionResult connectionResult) {
    Log.d(TAG, "Connection Failed" + connectionResult.getErrorCode());
  }

  private void updateUI(boolean isConnected) {
    Log.d(TAG, "updateUI called" + isConnected);
    if (googleSignInAccount != null && isConnected) {
      mUserName = googleSignInAccount.getDisplayName();
      if (googleSignInAccount.getPhotoUrl() != null) {
        mLoginFinishedListener.DisplayPicture(googleSignInAccount.getPhotoUrl().toString());
      }
      mLoginFinishedListener.onSuccess(mUserName);
    } else {
      mLoginFinishedListener.onDisconnected();
    }
  }

  @Override public void onConnected(Bundle bundle) {
    Log.d(TAG, "Api is connected");
    if(mGoogleApiClient.hasConnectedApi(Plus.API)) {
      mGoogleInfo.setPerson(Plus.PeopleApi.getCurrentPerson(mGoogleApiClient));
      mPerson = mGoogleInfo.getPerson();
      mGoogleInfo.getCircles();
    }
  }

  @Override public void onConnectionSuspended(int i) {

  }
}
