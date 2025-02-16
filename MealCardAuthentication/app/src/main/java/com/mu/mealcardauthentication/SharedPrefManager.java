package com.mu.mealcardauthentication;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static final String KEY_USER = "name";
    private static final String KEY_IMAGE = "profile_picture";

    private static SharedPrefManager instance;
    private final SharedPreferences sharedPreferences;

    private SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public void saveUserData(String name, String user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER, name);
        editor.putString(KEY_IMAGE, user);
        editor.apply();
    }

    // Retrieve user data
    public String getUser() {
        return sharedPreferences.getString(KEY_USER, null);
    }

    public String getImage() {
        return sharedPreferences.getString(KEY_IMAGE, null);
    }

    public void clearUserData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}