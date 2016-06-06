package com.example.teo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        session = new Session(MainActivity.this);


        ImageButton btn_catch = (ImageButton) findViewById(R.id.btn_catch);
        ImageButton btn_achieve = (ImageButton) findViewById(R.id.btn_achieve);
        ImageButton btn_collect = (ImageButton) findViewById(R.id.btn_collect);
        ImageButton btn_hatch = (ImageButton) findViewById(R.id.btn_hatch);
        ImageButton btn_help = (ImageButton) findViewById(R.id.btn_help);
        ImageButton btn_score = (ImageButton) findViewById(R.id.btn_score);
        ImageButton btn_sign = (ImageButton) findViewById(R.id.btn_sign);

        btn_catch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Maps.class));
                finish();
            }
        });

        btn_achieve.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Achievements.class));
                finish();
            }
        });

        btn_collect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Collection.class));
                finish();
            }
        });

        btn_hatch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Coming soon",Toast.LENGTH_LONG).show();
            }
        });

        btn_help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Help.class));
                finish();
            }
        });

        btn_score.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HighScore.class));
                finish();
            }
        });

        btn_sign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                session.logout();
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
            }
        });





    }



    @Override
    public void onBackPressed(){

    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
