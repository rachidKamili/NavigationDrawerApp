package me.kamili.rachid.navigationdrawerapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.kamili.rachid.navigationdrawerapp.tools.PlayersExpandableListAdapter;
import me.kamili.rachid.navigationdrawerapp.tools.SharedPreferencesLoader;

public class MainActivity extends BaseActivity implements
        LoaderManager.LoaderCallbacks<SharedPreferences>{

    private ExpandableListView expandableListView;
    private PlayersExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    private EditText etPlayer;
    private EditText etSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer(this,R.id.nav_home,R.string.home_title);

        etPlayer = findViewById(R.id.etPlayer);
        etSport = findViewById(R.id.etSport);

        initiateList();

        getLoaderManager().initLoader(0, null, this);
    }

    private void initiateList() {
        expandableListView = findViewById(R.id.expandableListView);
        expandableListDetail = new HashMap<>();
        expandableListTitle = new ArrayList<>();
        expandableListAdapter = new PlayersExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
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

    public void savePlayer(View view) {
        SharedPreferences SaveSharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SaveSharedPref.edit();

        Map<String, Set<String>> allEntries = (Map<String, Set<String>>) SaveSharedPref.getAll();
        editor.clear();
        if (!SaveSharedPref.contains(etSport.getText().toString())){
            allEntries.put(etSport.getText().toString() ,
                    new HashSet<>( Arrays.asList(new String[] { etPlayer.getText().toString() }) ));
        }else {
            Set<String> data = allEntries.get(etSport.getText().toString());
            data.add( etPlayer.getText().toString() );
            allEntries.put( etSport.getText().toString() ,new HashSet<>(data));
        }

        for (Map.Entry<String, Set<String>> entry : allEntries.entrySet()) {
            editor.putStringSet(entry.getKey(), entry.getValue());
        }
        editor.commit();

        initiateEditText();
        getLoaderManager().initLoader(0, null, this);
    }

    private void initiateEditText() {
        etPlayer.setText("");
        etSport.setText("");
    }

    @Override
    public Loader<SharedPreferences> onCreateLoader(int i, Bundle bundle) {
        return (new SharedPreferencesLoader(this));
    }

    @Override
    public void onLoadFinished(Loader<SharedPreferences> loader, SharedPreferences prefs) {

        HashMap<String, List<String>> listDetail = new HashMap<>();
        Map<String, Set<String>> allEntries = (Map<String, Set<String>>) prefs.getAll();
        for (Map.Entry<String, Set<String>> entry : allEntries.entrySet()) {
            listDetail.put(entry.getKey(), new ArrayList(entry.getValue()));
        }

        expandableListDetail = listDetail;
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter.setNewItems(expandableListTitle,expandableListDetail);
    }

    @Override
    public void onLoaderReset(Loader<SharedPreferences> loader) {

    }
}
