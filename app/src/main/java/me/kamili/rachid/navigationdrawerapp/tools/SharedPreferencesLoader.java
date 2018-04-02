package me.kamili.rachid.navigationdrawerapp.tools;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;

import me.kamili.rachid.navigationdrawerapp.R;

/**
 * Created by Admin on 4/2/2018.
 */

public class SharedPreferencesLoader  extends AsyncTaskLoader<SharedPreferences>
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences prefs = null;
    private Context mContext;

    public SharedPreferencesLoader(Context context) {
        super(context);
        mContext = context;
    }

    // Load the data asynchronously
    @Override
    public SharedPreferences loadInBackground() {
        prefs = mContext.getSharedPreferences(mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        prefs.registerOnSharedPreferenceChangeListener(this);
        return (prefs);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (prefs != null) {
            deliverResult(prefs);
        }

        if (takeContentChanged() || prefs == null) {
            forceLoad();
        }
    }

    public static void persist(final SharedPreferences.Editor editor) {
        editor.apply();
    }

}