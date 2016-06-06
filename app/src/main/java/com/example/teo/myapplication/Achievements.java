package com.example.teo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.teo.myapplication.Models.AchievementModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Teo on 2016-05-08.
 */
public class Achievements extends Activity implements Connection.OnTaskCompleted{

    String Username;
    Connection con = new Connection(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievements_view);

        Username = new Session(getBaseContext()).getusername();
        con.delegate = this;
        String url = "AchievementsJson?Username=" + Username;
        con.execute(url);


        ImageButton btnBack = (ImageButton)findViewById(R.id.imageButtonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Achievements.this,MainActivity.class));
                finish();
            }
        });


    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(Achievements.this,MainActivity.class));
        finish();
    }

    @Override
    public void processFinish(JSONObject output) {
        try {
            JSONArray jsonArray = output.getJSONArray("Table");
            List<AchievementModel> AchList = new ArrayList();
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                AchList.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), AchievementModel.class));
            }
            ((ListView) findViewById(R.id.listViewId)).setAdapter(new AchievementAdapter(getApplicationContext(), R.layout.achievement_row, AchList));
        } catch (Exception e) {
            Toast.makeText(this, "There was a problem, please try again later", Toast.LENGTH_LONG).show();
        }
    }
}
