package com.karthyk.geofriends.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.karthyk.geofriends.Activities.Fragments.circles.UserList;
import com.karthyk.geofriends.services.GoogleSignInIntentService;

import java.util.ArrayList;

public class GoogleInfo implements ResultCallback<People.LoadPeopleResult> {
  public static final String SERVER_CLIENT_ID = "493986055362-6nqt3427d63cm5apb1m9beue7q3qjja2" +
      ".apps.googleusercontent.com";
  public static final int RC_SIGN_IN = 9001;
  public static final String TAG = GoogleInfo.class.getSimpleName();

  private GoogleApiClient mGoogleApiClient;
  private GoogleSignInAccount mGoogleSignInAccount;
  private GoogleSignInOptions mGSO;
  private Person mPerson;

  private static GoogleInfo mInstance;
  private ArrayList<UserList> userLists;

  public static GoogleInfo getInstance() {
    if (mInstance == null) {
      mInstance = new GoogleInfo();
    }
    return mInstance;
  }

  public GoogleInfo() {
    setGSO();
    userLists = new ArrayList<>();
  }

  private void setGSO() {
    mGSO = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestProfile()
        .requestScopes(Plus.SCOPE_PLUS_LOGIN, Plus.SCOPE_PLUS_PROFILE)
        .build();
  }

  public void setGoogleClient(Context context, GoogleApiClient.ConnectionCallbacks callbacks) {
    mGoogleApiClient = new GoogleApiClient.Builder(context)
        .addApi(Auth.GOOGLE_SIGN_IN_API, mGSO)
        .addApi(Plus.API)
        .addScope(new Scope(Scopes.PLUS_LOGIN))
        .addScope(new Scope(Scopes.PROFILE))
        .addConnectionCallbacks(callbacks)
        .build();
  }

  public void setPerson(Person person) {
    mPerson = person;
  }

  public void setGoogleSignInAccount(GoogleSignInAccount googleSignInAccount) {
    mGoogleSignInAccount = googleSignInAccount;
  }

  public GoogleApiClient getGoogleApiClient() {
    return mGoogleApiClient;
  }

  public GoogleSignInOptions getGSO() {
    return mGSO;
  }

  public Person getPerson() {
    return mPerson;
  }

  public GoogleSignInAccount getGoogleSignInAccount() {
    return mGoogleSignInAccount;
  }

  public void signIn(Activity activity) {
    Intent signInIntent = Auth.GoogleSignInApi
        .getSignInIntent(GoogleInfo.getInstance().getGoogleApiClient());
    activity.startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  public void signOut(ResultCallback<Status> callback) {
    Auth.GoogleSignInApi.signOut(GoogleInfo.getInstance().getGoogleApiClient()).setResultCallback
        (callback);
  }

  public void silentSignIn(Context context) {
    context.startService(new Intent(context, GoogleSignInIntentService.class));
  }

  public void revokeAccess(ResultCallback<Status> callback) {
    Auth.GoogleSignInApi.revokeAccess(GoogleInfo.getInstance().getGoogleApiClient())
        .setResultCallback(callback);
  }

  public void getCircles() {
    Plus.PeopleApi.loadVisible(getGoogleApiClient(), null).setResultCallback(this);
  }

  public ArrayList<UserList> getUserLists() {
    return userLists;
  }

  @Override
  public void onResult(People.LoadPeopleResult peopleData) {
    if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
      PersonBuffer personBuffer = peopleData.getPersonBuffer();
      try {
        int count = personBuffer.getCount();
        Log.d(TAG, count + " : Count");
        for (int i = 0; i < count; i++) {
          Log.d(TAG, "Display name: " + personBuffer.get(i).getDisplayName());
          userLists.add(new UserList(personBuffer.get(i).getDisplayName(),
              personBuffer.get(i).getImage().getUrl()));
        }
      } finally {
        personBuffer.release();
      }
    } else {
      Log.e(TAG, "Error requesting people data: " + peopleData.getStatus());
    }
  }
}
