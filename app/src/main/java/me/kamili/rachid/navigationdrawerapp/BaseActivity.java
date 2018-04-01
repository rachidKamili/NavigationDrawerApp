package me.kamili.rachid.navigationdrawerapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
    protected DrawerLayout mDrawerLayout;
    protected ActionBar actionBar;
    protected NavigationView navigationView;
    private int menuItem;
    private int title;
    //private ActionBarDrawerToggle drawerToggle;

    protected void onCreateDrawer(final Context currentContext, int idItem, int titleId) {

        mDrawerLayout = findViewById(R.id.drawer_layout);
        menuItem = idItem;
        title = titleId;
        initiateToolbar();
        configActionBar();

        navigationView = findViewById(R.id.nav_view);
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

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(menuItem);
    }

    private void initiateToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
    }

    protected void configActionBar() {

        //show a back arrow instead of home
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
        upArrow.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
        actionBar.setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
