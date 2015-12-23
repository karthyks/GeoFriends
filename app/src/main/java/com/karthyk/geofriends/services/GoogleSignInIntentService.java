package com.karthyk.geofriends.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.karthyk.geofriends.Model.GoogleInfo;

public class GoogleSignInIntentService extends IntentService {

  public static final String TAG = GoogleSignInIntentService.class.getSimpleName();

  private GoogleInfo mGoogleInfo;

  public GoogleSignInIntentService() {
    super(TAG);
  }

  @Override protected void onHandleIntent(Intent intent) {
    mGoogleInfo = GoogleInfo.getInstance();
    OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi
        .silentSignIn(mGoogleInfo.getGoogleApiClient());
    mGoogleInfo.getGoogleApiClient().connect(GoogleApiClient.SIGN_IN_MODE_OPTIONAL);
    if (opr.isDone()) {
      Log.d(TAG, "Got cached sign-in");
      GoogleSignInResult result = opr.get();
      handleSignInResult(result);
    } else {
      opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
        @Override
        public void onResult(GoogleSignInResult googleSignInResult) {
          handleSignInResult(googleSignInResult);
        }
      });
    }
  }

  private void handleSignInResult(GoogleSignInResult result) {
    Log.d(TAG, "handleSignInResult:" + result.isSuccess());
    if (result.isSuccess()) {
      mGoogleInfo.setGoogleSignInAccount(result.getSignInAccount());
      broadcastResult(true);
    } else {
      Log.d(TAG, "Error handling Sign in !");
      broadcastResult(false);
    }
  }

  private void broadcastResult(boolean result) {
    Intent intent = new Intent(GoogleInfo.class.getSimpleName());
    intent.putExtra("Result", result);
    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
  }
}
