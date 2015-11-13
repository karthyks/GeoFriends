package com.karthyk.geofriends.Login;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.karthyk.geofriends.Database.DataBaseHelper;
import com.karthyk.geofriends.Database.DataProvider;
import com.karthyk.geofriends.Database.Table;

import java.util.HashMap;

public class LoginInteractorImpl implements LoginInteractor, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {

  private static final String TAG = LoginInteractorImpl.class.getSimpleName();

  /* RequestCode for resolutions involving sign-in */
  private static final int RC_SIGN_IN = 1;

  /* RequestCode for resolutions to get GET_ACCOUNTS permission on M */
  private static final int RC_PERM_GET_ACCOUNTS = 2;

  /* Keys for persisting instance variables in savedInstanceState */
  private static final String KEY_IS_RESOLVING = "is_resolving";
  private static final String KEY_SHOULD_RESOLVE = "should_resolve";

  /* Client for accessing Google APIs */
  private final GoogleApiClient mGoogleApiClient;


  /* Is there a ConnectionResult resolution in progress? */
  private boolean mIsResolving = false;

  /* Should we automatically resolve ConnectionResults when possible? */
  private boolean mShouldResolve = false;


  Context mContext;
  Activity mActivity;
  String mUserName;
  final LoginFinishedListener mLoginFinishedListener;

  DataBaseHelper mDBHelper;

  public LoginInteractorImpl(Activity activity, Context context,
                             final LoginFinishedListener loginFinishedListener) {
    mContext = context;
    mActivity = activity;
    mLoginFinishedListener = loginFinishedListener;
    mGoogleApiClient = new GoogleApiClient.Builder(mContext)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API)
        .addScope(new Scope(Scopes.PROFILE))
        .addScope(new Scope(Scopes.EMAIL))
        .build();
    mDBHelper = new DataBaseHelper(mContext);
  }

  @Override
  public void onConnected(Bundle bundle) {
    Log.d(TAG, "Google API Connected");
    mShouldResolve = false;

    Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
    if (currentPerson != null) {
      addUserToDatabase(currentPerson);
      mUserName = currentPerson.getDisplayName(); //BY THIS CODE YOU CAN GET CURRENT LOGIN USER ID
      mLoginFinishedListener.onSuccess(mUserName);
      mLoginFinishedListener.PersonInfo(currentPerson);
      if (currentPerson.hasImage()) {
        mLoginFinishedListener.DisplayPicture(currentPerson.getImage().getUrl() + "&sz=200");
      }
    } else {
      Log.e(TAG, "Current Person is null");
    }
  }

  @Override
  public void onConnectionSuspended(int i) {
    Log.e(TAG, "Error connection " + i);
  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {
    if (connectionResult.getErrorMessage() != null)
      mLoginFinishedListener.onFailure(connectionResult.getErrorMessage());
    Log.e(TAG, "Error connection - " + connectionResult.getErrorCode());

    if (!mIsResolving && mShouldResolve) {
      if (connectionResult.hasResolution()) {
        try {
          connectionResult.startResolutionForResult(mActivity, RC_SIGN_IN);
          mIsResolving = true;
        } catch (IntentSender.SendIntentException e) {
          Log.e(TAG, "Could not resolve ConnectionResult.", e);
          mIsResolving = false;
          mGoogleApiClient.connect();
        }
      } else {
        // Could not resolve the connection result, show the user an
        // error dialog.
        Log.e(TAG, "Error Dialogue : Could not resolve");
        showErrorDialog(connectionResult);
      }
    } else {
      // Show the signed-out UI
      Log.e(TAG, "Signed out");
    }
  }

  private void showErrorDialog(ConnectionResult connectionResult) {
    GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
    int resultCode = apiAvailability.isGooglePlayServicesAvailable(mContext);

    if (resultCode != ConnectionResult.SUCCESS) {
      if (apiAvailability.isUserResolvableError(resultCode)) {
        apiAvailability.getErrorDialog(mActivity, resultCode, RC_SIGN_IN,
            new DialogInterface.OnCancelListener() {
              @Override
              public void onCancel(DialogInterface dialog) {
                mShouldResolve = false;
                Log.e(TAG, "SignedOut");
              }
            }).show();
      } else {
        Log.w(TAG, "Google Play Services Error:" + connectionResult);
        String errorString = apiAvailability.getErrorString(resultCode);
        Log.e(TAG, errorString);
        mShouldResolve = false;
        Log.e(TAG, "SignedOut");
      }
    }
  }

  @Override
  public void ConnectGoogleAPI() {
    Log.d("LoginInteractorImpl", "ConnectGoogleAPI " + mGoogleApiClient.isConnected());
    mShouldResolve = true;

    mGoogleApiClient.connect();
  }

  @Override
  public void DisconnectGoogleAPI() {
    if (mGoogleApiClient.isConnected()) {
      mGoogleApiClient.disconnect();
    }
  }

  @Override
  public void LogOutGoogle() {
    if (mGoogleApiClient.isConnected()) {
      Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
      mGoogleApiClient.disconnect();
    }
  }

  @Override
  public boolean isGoogleAPIConnected() {
    return mGoogleApiClient.isConnected();
  }

  @Override
  public void OnActivityResult(int requestCode, int resultCode, Intent data) {
    Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

    if (requestCode == RC_SIGN_IN) {
      // If the error resolution was not successful we should not resolve further.
      if (resultCode != 3) {
        mShouldResolve = false;
      }

      mIsResolving = false;
      mGoogleApiClient.connect();
    }
  }

  @Override
  public HashMap<String, Boolean> getSaveInstanceBundleHashMap() {
    HashMap<String, Boolean> saveInstanceBundleVariables = new HashMap<>();
    saveInstanceBundleVariables.put(KEY_IS_RESOLVING, mIsResolving);
    saveInstanceBundleVariables.put(KEY_SHOULD_RESOLVE, mShouldResolve);
    return saveInstanceBundleVariables;
  }

  @Override
  public void setSaveInstanceBundleHashMap(boolean[] saveInstanceBundleVariables) {
    if (saveInstanceBundleVariables != null) {
      mIsResolving = saveInstanceBundleVariables[0];
      mShouldResolve = saveInstanceBundleVariables[1];
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    Log.d(TAG, "onRequestPermissionsResult:" + requestCode);
    if (requestCode == RC_PERM_GET_ACCOUNTS) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        Log.d(TAG, "GET_ACCOUNTS Permission Granted");
      } else {
        Log.d(TAG, "GET_ACCOUNTS Permission Denied.");
      }
    }
  }

  public void addUserToDatabase(Person person) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(Table.COLUMN_USERID, person.getId());
    contentValues.put(Table.COLUMN_USER_NAME, person.getDisplayName());
    contentValues.put(Table.COLUMN_FIRST_NAME, person.getDisplayName());
    contentValues.put(Table.COLUMN_LAST_NAME, person.getDisplayName());
    contentValues.put(Table.COLUMN_AGE, 10);
    contentValues.put(Table.COLUMN_DOB, person.getBirthday());
    /* Bitmap userDP = DownloadUtility.getBitmapFromURL(person.getImage().getUrl());
    if(userDP != null) {
      contentValues.put(Table.COLUMN_USER_DP, DbBitMapUtility.getBytes(userDP));
    }
    Bitmap userCover = DownloadUtility.getBitmapFromURL(person.getCover().getCoverPhoto().getUrl());
    if(userCover != null) {
      contentValues.put(Table.COLUMN_USER_COVER, DbBitMapUtility.getBytes(userCover));
    } */
    contentValues.put(Table.COLUMN_CURRENT_CITY, "CITY");
    contentValues.put(Table.COLUMN_CURRENT_LATITUDE, 1.0000);
    contentValues.put(Table.COLUMN_CURRENT_LONGITUDE, 1.0000);
    contentValues.put(Table.COLUMN_CURRENT_COUNTRY, "Country");
    mContext.getContentResolver().insert(DataProvider.mURI, contentValues);
  }
}
