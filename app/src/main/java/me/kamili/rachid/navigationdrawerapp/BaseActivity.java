package me.kamili.rachid.navigationdrawerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Admin on 4/1/2018.
 */

public class BaseActivity extends AppCompatActivity {
    protected DrawerLayout mDrawerLayout;
    protected ActionBar actionBar;
    protected NavigationView navigationView;
    private int menuItem;
    private int title;
    protected Toolbar mToolbar;
    private Context mContext;

    protected void onCreateDrawer(Context currentContext, int idItem, int titleId) {

        mContext = currentContext;
        mDrawerLayout = findViewById(R.id.drawer_layout);
        menuItem = idItem;
        title = titleId;

        initiateToolbar();
        configActionBar();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(menuItem);
        navigationView.setNavigationItemSelectedListener(
                new MyNavigationItemSelectedListener(mContext, mDrawerLayout)
        );

    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(menuItem);
    }

    private void initiateToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.about_dialog:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(R.string.about_message)
                        .setTitle(R.string.about_title);
                builder.create().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
