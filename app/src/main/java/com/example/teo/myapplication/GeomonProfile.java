package com.example.teo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teo.myapplication.Models.GeomonModel;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by Teo on 2016-06-05.
 */
public class GeomonProfile extends Activity implements Connection.OnTaskCompleted {
    String GeoName;
    String Lvl;
    Connection con = new Connection(this);

    /* renamed from: com.example.teo.myapplication.GeomonProfile.1 */
    class C03451 implements View.OnClickListener {
        C03451() {
        }

        public void onClick(View v) {
            GeomonProfile.this.finish();
        }
    }

    /* renamed from: com.example.teo.myapplication.GeomonProfile.2 */
    class C03462 implements View.OnClickListener {
        C03462() {
        }

        public void onClick(View v) {
            Intent intent = new Intent(GeomonProfile.this, GeomonLevel.class);
            intent.putExtra("Geo_Name", GeomonProfile.this.GeoName);
            intent.putExtra("Geo_Lvl", GeomonProfile.this.Lvl);
            GeomonProfile.this.startActivity(intent);
        }
    }

    public GeomonProfile() {
        this.GeoName = BuildConfig.FLAVOR;
        this.Lvl = BuildConfig.FLAVOR;
        this.con = new Connection(this);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.geomon_view);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                this.GeoName = null;
            } else {
                this.GeoName = extras.getString("Geo_Name");
            }
        } else {
            this.GeoName = (String) savedInstanceState.getSerializable("Geo_Name");
        }
        ((ImageButton) findViewById(R.id.imageButtonBack)).setOnClickListener(new C03451());
        ((ImageButton) findViewById(R.id.geomon_btn_level)).setOnClickListener(new C03462());
        this.con.delegate = this;
        String url = "GeomonJson?Name=" + this.GeoName;
        this.con.execute(url);
    }

    public void processFinish(JSONObject output) {
        try {
            GeomonModel geomon = (GeomonModel) new Gson().fromJson(output.getJSONArray("Table").getJSONObject(0).toString(), GeomonModel.class);
            ImageView image = (ImageView) findViewById(R.id.geomon_image);
            TextView name = (TextView) findViewById(R.id.geomon_name);
            TextView id = (TextView) findViewById(R.id.geomon_number);
            TextView level = (TextView) findViewById(R.id.geomon_level);
            TextView type = (TextView) findViewById(R.id.geomon_type);
            RatingBar rarity = (RatingBar) findViewById(R.id.geomon_ratingBar);
            rarity.setFocusable(false);
            image.setImageResource(getResources().getIdentifier(geomon.getName().toLowerCase(), "drawable", getPackageName()));
            name.setText(geomon.getName());
            id.setText("#" + geomon.getId());
            level.setText("12");
            type.setText(geomon.getType());
            rarity.setRating((float) geomon.getRarity());
            this.Lvl = level.getText().toString();
        } catch (Exception e) {
            Toast.makeText(this, "There was a problem, please try again later", Toast.LENGTH_LONG).show();
        }
    }
}
