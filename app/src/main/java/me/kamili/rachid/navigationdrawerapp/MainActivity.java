package me.kamili.rachid.navigationdrawerapp;

import android.os.Bundle;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer(this,R.id.nav_home);
        System.out.println(this);
    }

}
