package com.karthyk.geofriends.Login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.model.people.Person;
import com.karthyk.geofriends.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    SignInButton signInButton;
    LoginPresenter mLoginPresenter;
    Button signOutButton;
    TextView mUserName;

    ImageView displayPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signOutButton = (Button) findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(this);
        signOutButton.setVisibility(View.INVISIBLE);

        displayPicture = (ImageView) findViewById(R.id.displayPicture);
        mUserName = (TextView) findViewById(R.id.user_name);

        mLoginPresenter = new LoginPresenterImpl(this, this, this.getApplicationContext());

        if (savedInstanceState != null) {
            int index = 0;
            boolean[] bundleVariables = new boolean[2];
            for (Map.Entry entries : mLoginPresenter.OnSaveInstance().entrySet()) {
                bundleVariables[index] = Boolean.parseBoolean(entries.getValue().toString());
                index++;
            }
            mLoginPresenter.OnCreateBundleVariables(bundleVariables);
        }
    }

    @Override
    public void NoNetwork(String error) {
        Toast.makeText(this, getString(R.string.NoNetworkError), Toast.LENGTH_SHORT).show();
        mUserName.setText(error);
        signInButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void Connected(String userName) {
        Toast.makeText(this, getString(R.string.Connected), Toast.LENGTH_SHORT).show();
        mUserName.setText(userName);
        signInButton.setVisibility(View.INVISIBLE);
        signOutButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnDisconnected() {
        mUserName.setText("Logged Out");
        signInButton.setVisibility(View.VISIBLE);
        signOutButton.setVisibility(View.INVISIBLE);
        displayPicture.setImageResource(R.drawable.default_profile_img);
    }

    @Override
    public void getPersonInfo(Person person) {

    }

    @Override
    public void setDisplayPicture(Bitmap result) {
        displayPicture.setImageBitmap(result);
    }

    @Override
    public void NavigateToHome() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                Log.d("LoginActivity", "Clicked");
                mLoginPresenter.OnSignInClicked();
                break;
            case R.id.sign_out_button:
                mLoginPresenter.OnLogOutClicked();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLoginPresenter.OnActivityStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLoginPresenter.OnActivityStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginPresenter.OnActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        HashMap<String, Boolean> bundleVariables = mLoginPresenter.OnSaveInstance();

        if (bundleVariables != null) {
            for (Map.Entry entries : bundleVariables.entrySet()) {
                outState.putBoolean(entries.getKey().toString(),
                        Boolean.parseBoolean(entries.getValue().toString()));
            }
        }
    }
}




