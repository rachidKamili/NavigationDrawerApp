package me.kamili.rachid.navigationdrawerapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer(this,R.id.nav_home,R.string.home_title);

    }

    @Override
    protected void configActionBar() {
        //show home button
        actionBar.setDisplayHomeAsUpEnabled(true);
        Drawable homeMenu = getResources().getDrawable(R.drawable.ic_menu);
        actionBar.setHomeAsUpIndicator(homeMenu);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(R.string.home_title);
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
