package com.example.teo.myapplication;

/**
 * Created by Ieva on 09/05/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.teo.myapplication.Models.ScoreModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HighScore extends AppCompatActivity implements Connection.OnTaskCompleted {

    Connection con = new Connection(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_main);

        con.delegate = this;
        con.execute("HighscoreJson");


        ImageButton btnBack = (ImageButton)findViewById(R.id.imageButtonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(HighScore.this,MainActivity.class));
                finish();
            }
        });

    }


    @Override
    public void processFinish(JSONObject output) {
        try {
            JSONArray jsonArray = output.getJSONArray("Table");
            List<ScoreModel> scList = new ArrayList();
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                scList.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), ScoreModel.class));
            }
            ((ListView) findViewById(R.id.ListviewHighscore)).setAdapter(new HighscoreAdapter(getApplicationContext(), R.layout.tablerow, scList));
        } catch (Exception e) {
            Toast.makeText(this, "There was a problem, please try again later", Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }
}
