package com.karthyk.geofriends.Login;

import android.content.Intent;

import java.util.HashMap;

/**
 * Created by karthik on 3/11/15.
 */
public interface LoginPresenter {
    void OnSignInClicked();
    void OnLogOutClicked();
    void OnActivityStart();
    void OnActivityStop();
    void OnActivityResult(int requestCode, int resultCode, Intent data);
    void OnCreateBundleVariables(boolean[] bundleVariables);
    boolean isGoogleAPIConnected();
    HashMap<String, Boolean> OnSaveInstance();
}
