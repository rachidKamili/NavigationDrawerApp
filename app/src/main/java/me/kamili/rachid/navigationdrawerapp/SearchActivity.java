package me.kamili.rachid.navigationdrawerapp;

import android.os.Bundle;

public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        super.onCreateDrawer(this,R.id.nav_search,R.string.search_title);
    }
}
