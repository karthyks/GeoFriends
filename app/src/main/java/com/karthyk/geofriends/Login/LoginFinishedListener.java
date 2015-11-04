package com.karthyk.geofriends.Login;

import android.graphics.Bitmap;

import com.google.android.gms.plus.model.people.Person;

/**
 * Created by karthik on 3/11/15.
 */
public interface LoginFinishedListener {
    void onSuccess(String userName);
    void onFailure(String error);
    void PersonInfo(Person person);
    void DisplayPicture(Bitmap resultBitmap);
}
