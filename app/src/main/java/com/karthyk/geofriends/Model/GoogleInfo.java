package com.karthyk.geofriends.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.karthyk.geofriends.services.GoogleSignInIntentService;

public class GoogleInfo {

  public static final int RC_SIGN_IN = 9001;

  private GoogleApiClient mGoogleApiClient;
  private GoogleSignInAccount mGoogleSignInAccount;
  private GoogleSignInOptions mGSO;
  private Person mPerson;

  private static GoogleInfo mInstance;

  public static GoogleInfo getInstance() {
    if (mInstance == null) {
      mInstance = new GoogleInfo();
    }
    return mInstance;
  }

  public GoogleInfo() {
    setGSO();
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
}
