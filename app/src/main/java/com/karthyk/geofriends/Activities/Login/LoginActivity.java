package com.karthyk.geofriends.Activities.Login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.karthyk.geofriends.Activities.Main.MainActivity;
import com.karthyk.geofriends.Model.GoogleInfo;
import com.karthyk.geofriends.R;
import com.karthyk.geofriends.Utility.DbBitMapUtility;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

  SignInButton signInButton;
  ProgressBar progressBar;
  LoginPresenter mLoginPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    mLoginPresenter = new LoginPresenterImpl(this, this, this.getApplicationContext());
    injectViews();
    checkLogIn();
  }

  private void injectViews() {
    signInButton = (SignInButton) findViewById(R.id.sign_in_button);
    signInButton.setOnClickListener(this);
    signInButton.setSize(SignInButton.SIZE_WIDE);
    signInButton.setScopes(GoogleInfo.getInstance().getGSO().getScopeArray());
    progressBar = (ProgressBar) findViewById(R.id.progressBar);
    progressBar.setVisibility(View.INVISIBLE);
    getSupportActionBar().hide();
  }

  private void checkLogIn() {
    if (mLoginPresenter.isUserSignedIn()) {
      Log.d("LoginAct", "User is Signed In already");
      signInButton.setVisibility(View.GONE);
      progressBar.setVisibility(View.VISIBLE);
      GoogleInfo.getInstance().silentSignIn(this);
    }
  }

  private void showSignIn() {
    signInButton.setVisibility(View.VISIBLE);
    progressBar.setVisibility(View.INVISIBLE);
  }

  @Override
  public void NoNetwork(String error) {
    Toast.makeText(this, getString(R.string.NoNetworkError), Toast.LENGTH_SHORT).show();
    signInButton.setVisibility(View.VISIBLE);
  }

  @Override
  public void Connected(String userName) {
    signInButton.setVisibility(View.INVISIBLE);
  }

  @Override
  public void OnDisconnected() {
    signInButton.setVisibility(View.VISIBLE);
    progressBar.setVisibility(View.INVISIBLE);
  }

  @Override
  public void setDisplayPicture(String url) {
    Picasso.with(this)
        .load(url)
        .resize(200, 200)
        .into(new Target() {
          @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mLoginPresenter.sendBackUserDp(DbBitMapUtility.getBytes(bitmap));
            progressBar.setVisibility(View.INVISIBLE);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
          }

          @Override public void onBitmapFailed(Drawable errorDrawable) {

          }

          @Override public void onPrepareLoad(Drawable placeHolderDrawable) {

          }
        });
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.sign_in_button:
        Log.d("LoginActivity", "Clicked");
        GoogleInfo.getInstance().signIn(this);
        progressBar.setVisibility(View.VISIBLE);
        break;
    }
  }

  @Override protected void onResume() {
    super.onResume();
    if(mLoginPresenter.isUserSignedIn())
      GoogleInfo.getInstance().silentSignIn(this);
    LocalBroadcastManager.getInstance(this).registerReceiver(silentSignInReceiver, new
        IntentFilter(GoogleInfo.class.getSimpleName()));
  }

  @Override protected void onPause() {
    super.onPause();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(silentSignInReceiver);
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    mLoginPresenter.OnActivityResult(requestCode, data);
  }

  private BroadcastReceiver silentSignInReceiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      boolean result = intent.getBooleanExtra("Result", false);
      if (result) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
      } else {
        showSignIn();
      }
    }
  };
}




