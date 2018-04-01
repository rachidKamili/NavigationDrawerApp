package me.kamili.rachid.navigationdrawerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import java.util.concurrent.Executors;

/**
 * Created by Admin on 4/1/2018.
 */

public class MyNavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

    Context mContext;
    DrawerLayout mDrawerLayout;

    public MyNavigationItemSelectedListener(Context mContext, DrawerLayout mDrawerLayout) {
        this.mContext = mContext;
        this.mDrawerLayout = mDrawerLayout;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (!menuItem.isChecked()) {

            Class<? extends Activity> activityClass = null;
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    activityClass = MainActivity.class;
                    break;
                case R.id.nav_search:
                    activityClass = SearchActivity.class;
                    break;
            }

            if (activityClass != null) {
                final Class<?> finalActivityClass = activityClass;
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(mContext, finalActivityClass);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mContext.startActivity(intent);
                    }
                });
            }

            menuItem.setChecked(true);
        }

        mDrawerLayout.closeDrawers();
        return true;
    }
}
