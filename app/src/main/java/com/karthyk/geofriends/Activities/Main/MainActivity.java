package com.karthyk.geofriends.Activities.Main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.karthyk.geofriends.R;

public class MainActivity extends AppCompatActivity implements MainView {

  private String[] mDrawerItems;
  private DrawerLayout mDrawerLayout;
  private ListView mDrawerList;

  ActionBarDrawerToggle mDrawerToggle;

  private CharSequence mDrawerTitle;
  private CharSequence mTitle;

  private MainPresenter mainPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mainPresenter = new MainPresenterImpl(this, this);
    setContentView(R.layout.activity_main);
    mDrawerTitle = getResources().getString(R.string.drawer_title);
    mTitle = getResources().getString(R.string.app_name);
    injectViews();
    drawerOpenClose();
  }

  private void injectViews() {
    mDrawerItems = getResources().getStringArray(R.array.drawer_main_items_array);
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_main_layout);
    mDrawerList = (ListView) findViewById(R.id.drawer_main_list);

    ObjectDrawerItem[] objectDrawerItems = new ObjectDrawerItem[5];
    objectDrawerItems[0] = new ObjectDrawerItem(R.drawable.ic_home, mDrawerItems[0]);
    objectDrawerItems[1] = new ObjectDrawerItem(R.drawable.icon_profile, mDrawerItems[1]);
    objectDrawerItems[2] = new ObjectDrawerItem(R.drawable.ic_friends, mDrawerItems[2]);
    objectDrawerItems[3] = new ObjectDrawerItem(R.drawable.ic_friends, mDrawerItems[3]);
    objectDrawerItems[4] = new ObjectDrawerItem(R.drawable.ic_log_off, mDrawerItems[4]);

    DrawerItemCustomAdapter mDrawerItemCustomAdapter = new DrawerItemCustomAdapter(this, R.layout
        .drawer_main_list_row,
        objectDrawerItems);
    mDrawerList.setAdapter(mDrawerItemCustomAdapter);
    mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
  }

  private void drawerOpenClose() {
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_main_layout);
    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, R.string.drawer_open, R
        .string.drawer_close) {
      @Override public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        getSupportActionBar().setTitle(mDrawerTitle);
      }

      @Override public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        getSupportActionBar().setTitle(mTitle);
      }
    };
//    mDrawerToggle = new ActionBarDrawerToggle(
//        this,
//        mDrawerLayout,
//        R.drawable.ic_list,
//        R.string.drawer_open,
//        R.string.drawer_close
//    ) {
//
//      /** Called when a drawer has settled in a completely closed state. */
//      public void onDrawerClosed(View view) {
//        super.onDrawerClosed(view);
//        //getActionBar().setTitle(mTitle);
//      }
//
//      /** Called when a drawer has settled in a completely open state. */
//      public void onDrawerOpened(View drawerView) {
//        super.onDrawerOpened(drawerView);
//        //getActionBar().setTitle(mDrawerTitle);
//      }
//    };

    mDrawerLayout.setDrawerListener(mDrawerToggle);
//

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (mDrawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void setTitle(CharSequence title) {
    mTitle = title;
    //getActionBar().setTitle(mTitle);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    mDrawerToggle.syncState();
  }

  private void selectDrawerItem(int pos) {
    setTitle(mDrawerItems[pos]);
    mainPresenter.onClickDrawerItem(pos);

    mDrawerList.setItemChecked(pos, true);
    mDrawerList.setSelection(pos);
    //getActionBar().setTitle(mDrawerItems[pos]);
    mDrawerLayout.closeDrawer(mDrawerList);
  }

  @Override public void setFragment(Fragment fragment) {
    if (fragment != null) {
      FragmentManager fragmentManager = getFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id
          .content_frame, fragment);
      fragmentTransaction.commit();
    } else {
      logOut();
    }
  }

  private void logOut() {
    Snackbar.make(findViewById(android.R.id.content), "Are you sure want to log out?",
        Snackbar.LENGTH_LONG)
        .setAction("Yes", new View.OnClickListener() {
          @Override public void onClick(View v) {
          }
        })
        .setActionTextColor(Color.RED)
        .show();
  }

  private class DrawerItemCustomAdapter extends ArrayAdapter<ObjectDrawerItem> {

    private Context mContext;
    int layoutResourceId;
    ObjectDrawerItem data[] = null;

    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, ObjectDrawerItem[]
        data) {

      super(mContext, layoutResourceId, data);
      this.layoutResourceId = layoutResourceId;
      this.mContext = mContext;
      this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

      View listItem = convertView;
      DrawerItemViewHolder viewHolder;
      if (convertView == null) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);
        viewHolder = new DrawerItemViewHolder(listItem);
        listItem.setTag(viewHolder);
      } else {
        viewHolder = (DrawerItemViewHolder) listItem.getTag();
      }

      ObjectDrawerItem folder = data[position];
      viewHolder.imageViewIcon.setImageResource(folder.icon);
      viewHolder.textViewName.setText(folder.name);

      return listItem;
    }

    private class DrawerItemViewHolder {
      ImageView imageViewIcon;
      TextView textViewName;

      public DrawerItemViewHolder(View base) {
        imageViewIcon = (ImageView) base.findViewById(R.id.icon_row);
        textViewName = (TextView) base.findViewById(R.id.text_row);
      }
    }
  }


  private class DrawerItemClickListener implements ListView.OnItemClickListener {

    @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      selectDrawerItem(position);
    }
  }
}
