package com.example.teo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by Teo on 2016-06-05.
 */
public class GeomonLevel extends Activity implements SensorEventListener {

    private String GeoName;
    private String Lvl;
    private boolean sensorAvailable;
    private SensorManager sm;
    private TextView steps;

    /* renamed from: com.example.teo.myapplication.GeomonLevel.1 */
    class C03441 implements View.OnClickListener {
        C03441() {
        }

        public void onClick(View v) {
            GeomonLevel.this.finish();
        }
    }

    public GeomonLevel() {
        this.GeoName = BuildConfig.FLAVOR;
        this.Lvl = BuildConfig.FLAVOR;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.geomon_levelup);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                this.GeoName = "eezeltich";
                this.Lvl = "14";
            } else {
                this.GeoName = extras.getString("Geo_Name");
                this.Lvl = extras.getString("Geo_Lvl");
            }
        } else {
            this.GeoName = (String) savedInstanceState.getSerializable("Geo_Name");
            this.Lvl = (String) savedInstanceState.getSerializable("Geo_Lvl");
        }
        ((ImageButton) findViewById(R.id.levelup_btn_back)).setOnClickListener(new C03441());
        ImageView image = (ImageView) findViewById(R.id.levelup_image);
        TextView name = (TextView) findViewById(R.id.levelup_name);
        TextView level = (TextView) findViewById(R.id.levelup_lvl_value);
        this.steps = (TextView) findViewById(R.id.levelup_steps_value);
        this.sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        image.setImageResource(getResources().getIdentifier(this.GeoName.toLowerCase(), "drawable", getPackageName()));
        name.setText(this.GeoName);
        level.setText(this.Lvl);
        Sensor countSensor = this.sm.getDefaultSensor(19);
        if (countSensor != null) {
            this.sm.registerListener(this, countSensor, 2);
            this.sensorAvailable = true;
            return;
        }
        Toast.makeText(this, "Count sensor unavailable", Toast.LENGTH_LONG).show();
        this.sensorAvailable = false;
    }

    public void onSensorChanged(SensorEvent event) {
        if (this.sensorAvailable) {
            this.steps.setText(String.valueOf(event.values[0]));
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
