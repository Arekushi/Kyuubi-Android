package br.com.arekushi.kyuubi;

import android.annotation.SuppressLint;
import android.content.Context;

public class SharedPreferences {
    private static SharedPreferences instance;
    private android.content.SharedPreferences preferences;
    private android.content.SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SharedPreferences(Context context) {
        String namePreferences = context.getString(R.string.sharedPreferencesName);
        preferences = context.getSharedPreferences(namePreferences, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void addString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String default_) {
        return preferences.getString(key, default_);
    }

    public void addBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean default_) {
        return preferences.getBoolean(key, default_);
    }

    public void removeKey(String key) {
        editor.remove(key);
        editor.apply();
    }

    public boolean verificKey(String key) {
        return preferences.contains(key);
    }

    public static SharedPreferences getInstance(Context context) {
        if(instance == null)
            instance = new SharedPreferences(context);

        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
