package me.kamili.rachid.navigationdrawerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MusicActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        super.onCreateDrawer(this,R.id.nav_music,R.string.music_title);

    }
}
