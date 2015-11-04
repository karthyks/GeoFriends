package com.karthyk.geofriends.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by karthik on 3/11/15.
 */
public class LoginPresenterImpl implements LoginPresenter, LoginFinishedListener {
    LoginView mLoginView;
    LoginInteractor mLoginInteractor;
    public LoginPresenterImpl(LoginView loginView, Activity activity, Context context) {
        mLoginView = loginView;
        mLoginInteractor = new LoginInteractorImpl(activity, context, this);
    }

    @Override
    public void OnSignInClicked() {
        Log.d("LoginPresenterImpl", "OnSignInClicked");
        mLoginInteractor.ConnectGoogleAPI();
    }

    @Override
    public void OnLogOutClicked() {
        mLoginInteractor.LogOutGoogle();
        mLoginView.OnDisconnected();
    }

    @Override
    public void OnActivityStart() {
        mLoginInteractor.ConnectGoogleAPI();
    }

    @Override
    public void OnActivityStop() {
        mLoginInteractor.DisconnectGoogleAPI();
    }

    @Override
    public void OnActivityResult(int requestCode, int resultCode, Intent data) {
        mLoginInteractor.OnActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void OnCreateBundleVariables(boolean[] bundleVariables) {
        mLoginInteractor.setSaveInstanceBundleHashMap(bundleVariables);
    }

    @Override
    public boolean isGoogleAPIConnected() {
        return mLoginInteractor.isGoogleAPIConnected();
    }

    @Override
    public HashMap<String, Boolean> OnSaveInstance() {
        return mLoginInteractor.getSaveInstanceBundleHashMap();
    }

    @Override
    public void onSuccess(String userName) {
        mLoginView.Connected(userName);
    }

    @Override
    public void onFailure(String error) {
        mLoginView.NoNetwork(error);
    }
}
