package com.karthyk.geofriends.Activities.Fragments.Profile;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.karthyk.geofriends.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements ProfileView, View.OnClickListener {
  public static final String TAG = ProfileFragment.class.getSimpleName();

  CircleImageView mUserDP;
  TextView mUserName;
  TextView mLivesIn;
  TextView mDistance;

  Button mLocate;
  ProfilePresenter mProfilePresenter;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_profile, null);
    injectViews(view);
    mProfilePresenter = new ProfilePresenterImpl(this, getActivity(), getActivity(),
        getArguments());
    mLocate.setOnClickListener(this);
    return view;
  }

  private void injectViews(View view) {
    mUserDP = (CircleImageView) view.findViewById(R.id.user_dp);
    mLocate = (Button) view.findViewById(R.id.locate_btn);
    mUserName = (TextView) view.findViewById(R.id.user_name);
    mLivesIn = (TextView) view.findViewById(R.id.lives_in);
    mDistance = (TextView) view.findViewById(R.id.distance);
  }

  @Override public void setUserData(String name, String living, String dist, Bitmap bmp) {
    mUserName.setText(name);
    mLivesIn.setText(living);
    mDistance.setText(dist);
    mUserDP.setImageBitmap(bmp);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.locate_btn:
        mProfilePresenter.locateInMap();
        break;
    }
  }
}
