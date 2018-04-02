package me.kamili.rachid.navigationdrawerapp;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MusicActivity extends BaseActivity {

    private ListView musicList;
    private MediaPlayer mMediaPlayer;
    private int mLength = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        super.onCreateDrawer(this, R.id.nav_music, R.string.music_title);

        musicList = findViewById(R.id.music_list);

        //get all the ids+names form raw folder
        List<String> values = new ArrayList<>();
        Field[] fields = R.raw.class.getFields();
        for (Field field : fields) {
            //fields[count].getInt(fields[count])
            values.add((field.getName()).substring(0, 1).toUpperCase() + (field.getName()).substring(1));
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        musicList.setAdapter(adapter);

        final Activity activity = this;
        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    int rawId = (R.raw.class.getFields())[position].getInt((R.raw.class.getFields())[position]);
                    releaseMediaPlayer();
                    mMediaPlayer = MediaPlayer.create(activity, rawId);
                    mMediaPlayer.start();

                    int count = parent.getChildCount();
                    for (int i = 0; i < count; i++) {
                        parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                    view.setBackgroundColor(Color.LTGRAY);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.music_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.play_music:
                playMusic();
                return true;
            case R.id.pause_music:
                pauseMusic();
                return true;
            case R.id.restart_music:
                restartMusic();
                return true;
            case R.id.stop_music:
                stopMusic();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void stopMusic() {
        releaseMediaPlayer();
        int count = musicList.getChildCount();
        for (int i = 0; i < count; i++) {
            musicList.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void restartMusic() {
        if(mMediaPlayer != null){
            mMediaPlayer.seekTo(0);
            mMediaPlayer.start();
        }
    }

    private void pauseMusic() {
        if(mMediaPlayer != null  && mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
            mLength = mMediaPlayer.getCurrentPosition();
        }
    }

    private void playMusic() {
        if(mMediaPlayer != null && !mMediaPlayer.isPlaying()){
            mMediaPlayer.seekTo(mLength);
            mMediaPlayer.start();
        }
    }
}
