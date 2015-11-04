package com.karthyk.geofriends.Login;

import android.graphics.Bitmap;

import com.google.android.gms.plus.model.people.Person;

/**
 * Created by karthik on 3/11/15.
 */
public interface LoginView {
    public void NoNetwork(String error);
    public void Connected(String userName);
    void OnDisconnected();
    void getPersonInfo(Person person);
    void setDisplayPicture(Bitmap result);
    public void NavigateToHome();
}
