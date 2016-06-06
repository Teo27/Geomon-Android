package com.example.teo.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by Ieva on 18/05/2016.
 */
public class Session {

    SharedPreferences sharedPrefs;

    public static final String MyPreferences = "myPrefs" ;
    public static final String Username = "usernameKey";
    private static final String IS_LOGIN = "IsLoggedIn";

    int PRIVATE_MODE = 0;
    Context cont;

    public Session(Context context) {
        // TODO Auto-generated constructor stub

        this.cont = context;
        sharedPrefs = cont.getSharedPreferences(MyPreferences, PRIVATE_MODE);
    }

    public void login(String username) {

        sharedPrefs.edit().putBoolean(IS_LOGIN, true).commit();
        sharedPrefs.edit().putString(Username, username).commit();
        //No idea why the following is there:
        // prefsCommit();
    }

    public String getusername() {
        String username = sharedPrefs.getString(Username,"Nothing Found");
        return username;
    }

    public boolean isLoggedIn(){
        return sharedPrefs.getBoolean(IS_LOGIN, false);
    }

    public void logout(){
        sharedPrefs.edit().clear().commit();
    }
}

