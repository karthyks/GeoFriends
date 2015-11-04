package com.karthyk.geofriends.Login;

/**
 * Created by karthik on 3/11/15.
 */
public interface LoginFinishedListener {
    void onSuccess(String userName);
    void onFailure(String error);
}
