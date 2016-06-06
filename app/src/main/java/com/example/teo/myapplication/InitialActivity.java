package com.example.teo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Teo on 2016-06-05.
 */
public class InitialActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        Intent intent;
        super.onCreate(savedInstanceState);
        if (new Session(this).isLoggedIn()) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, Login.class);
        }
        startActivity(intent);
        finish();
    }
}
