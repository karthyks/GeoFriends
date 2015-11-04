package com.karthyk.geofriends.Login;

/**
 * Created by karthik on 3/11/15.
 */
public interface LoginView {
    public void NoNetwork(String error);
    public void Connected(String userName);
    void OnDisconnected();
    public void NavigateToHome();
}
