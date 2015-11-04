package com.karthyk.geofriends.Login;

import android.content.Intent;

import java.util.HashMap;

/**
 * Created by karthik on 3/11/15.
 */
public interface LoginInteractor {
    void ConnectGoogleAPI();
    void DisconnectGoogleAPI();
    void LogOutGoogle();
    boolean isGoogleAPIConnected();
    void OnActivityResult(int requestCode, int resultCode, Intent data);
    HashMap<String, Boolean> getSaveInstanceBundleHashMap();
    void setSaveInstanceBundleHashMap(boolean[] saveInstanceBundleVariables);
}
