package com.example.karim.anta5a.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.karim.anta5a.models.User;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "storage_anta5a";
    private static SharedPrefManager manager;
    private Context context;

    private SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (manager == null)
            manager = new SharedPrefManager(context);
        return manager;
    }

    public void saveUser(User user) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("id", user.getId());
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.putString("phonenumber", user.getPhoneNumber());
        editor.putString("address", user.getAddress());
        editor.putString("gender", user.getGender());
        editor.putString("image", user.getImage());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return (preferences.getLong("id", -1) != -1);
    }

    public User getUser() {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                preferences.getLong("id", -1),
                preferences.getString("name", null),
                preferences.getString("email", null),
                preferences.getString("password", null),
                preferences.getString("phonenumber", null),
                preferences.getString("address", null),
                preferences.getString("gender", null),
                preferences.getString("image", null)
        );
    }

    public void logOut() {

        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("id", -1);
        editor.putString("name", "-1");
        editor.putString("email", "-1");
        editor.putString("password", "-1");
        editor.putString("phonenumber", "-1");
        editor.putString("address", "-1");
        editor.putString("gender", "-1");
        editor.putString("image", "-1");
        editor.apply();
    }

    public void clear() {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}