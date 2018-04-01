package me.kamili.rachid.navigationdrawerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.concurrent.Executors;

/**
 * Created by Admin on 4/1/2018.
 */

public class BaseActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    //private ActionBarDrawerToggle drawerToggle;

    protected void onCreateDrawer(final Context currentContext, int menuItem) {

        mDrawerLayout = findViewById(R.id.drawer_layout);

        initiateToolbarAndActionBar();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(menuItem);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

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
                                        Intent intent = new Intent(currentContext, finalActivityClass);
                                        //intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                });
                            }

                            menuItem.setChecked(true);
                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    protected void initiateToolbarAndActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
