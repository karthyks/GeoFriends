package com.karthyk.geofriends.Activities.Fragments.circles;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karthyk.geofriends.Model.GoogleInfo;
import com.karthyk.geofriends.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CircleFragment extends Fragment {

  ListView mListViewCircles;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_circles, null);
    injectViews(view);
    return view;
  }

  private void injectViews(View view) {
    mListViewCircles = (ListView) view.findViewById(R.id.list_circles);

    CircleListAdapter listAdapter = new CircleListAdapter(getActivity(),
        GoogleInfo.getInstance().getUserLists());
    mListViewCircles.setAdapter(listAdapter);
  }

  private class CircleListAdapter extends ArrayAdapter<UserList> {

    ArrayList<UserList> userLists;

    public CircleListAdapter(Context context, ArrayList<UserList> users) {
      super(context, 0, users);
      userLists = users;
    }

    @Override public UserList getItem(int position) {
      return userLists.get(position);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
      View v = convertView;
      UserList user = getItem(position);
      final CircleListViewHolder viewHolder;
      if (convertView == null) {
        LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context
            .LAYOUT_INFLATER_SERVICE);
        v = li.inflate(R.layout.circle_list_row, null);
        viewHolder = new CircleListViewHolder(v);
        v.setTag(viewHolder);
      } else {
        viewHolder = (CircleListViewHolder) v.getTag();
      }
      viewHolder.mTextViewUserName.setText(user.name);
      Picasso.with(getActivity())
          .load(user.imageUrl)
          .resize(50, 50)
          .into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
              viewHolder.mImageViewUserDP.setImageBitmap(bitmap);
            }

            @Override public void onBitmapFailed(Drawable errorDrawable) {
              viewHolder.mImageViewUserDP.setImageDrawable(getResources().getDrawable(R.drawable
                  .ic_default_user));
            }

            @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
              viewHolder.mImageViewUserDP.setImageDrawable(getResources().getDrawable(R.drawable
                  .ic_default_user));
            }
          });
      return v;
    }
  }

  private class CircleListViewHolder {
    TextView mTextViewUserName;
    CircleImageView mImageViewUserDP;

    public CircleListViewHolder(View v) {
      mTextViewUserName = (TextView) v.findViewById(R.id.user_name);
      mImageViewUserDP = (CircleImageView) v.findViewById(R.id.user_dp);
    }
  }
}
