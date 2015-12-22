package com.karthyk.geofriends.Activities.Main;

import android.app.Fragment;
import android.content.Context;

public class MainPresenterImpl implements MainPresenter {

  MainInteractor mainInteractor;
  MainView mainView;
  Context mContext;

  public MainPresenterImpl(MainView mView, Context context) {
    mContext = context;
    mainInteractor = new MainInteractorImpl(mContext);
    mainView = mView;
  }

  @Override public void onClickDrawerItem(int pos) {
    Fragment fragment = mainInteractor.getFragment(pos);
    mainView.setFragment(fragment);
  }

  @Override public boolean isUserSignedIn() {
    return mainInteractor.isUserInDB();
  }
}
