package com.example.teo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.teo.myapplication.Models.GeomonModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ieva on 11/05/2016.
 */
public class Collection extends Activity implements Connection.OnTaskCompleted
{
    String Username;
    Connection con = new Connection(this);
    GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_view);


        Username = new Session(getBaseContext()).getusername();
        con.delegate = this;
        String url = "CollectionJson?Username=" + Username;
        con.execute(new String[]{url});
        gridView = (GridView) findViewById(R.id.gridViewCollection);

        // Handling touch/click Event on GridView Item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                GeomonModel geomonModel = (GeomonModel) arg0.getAdapter().getItem(position);
                Intent intent = new Intent(Collection.this, GeomonProfile.class);
                intent.putExtra("Geo_Name", geomonModel.getName());
                Collection.this.startActivity(intent);
            }
        });

        ImageButton btnBack = (ImageButton)findViewById(R.id.imageButtonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Collection.this,MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void processFinish(JSONObject output) {
        try {
            JSONArray jsonArray = output.getJSONArray("Table");
            List<GeomonModel> CollectList = new ArrayList();
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                CollectList.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), GeomonModel.class));
            }
            ((GridView) findViewById(R.id.gridViewCollection)).setAdapter(new CollectionAdapter(getApplicationContext(), R.layout.collection_row, CollectList));
        } catch (Exception e) {
            Toast.makeText(this, "There was a problem, please try again later", Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }
}