package com.example.teo.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Random;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;





public class Maps extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    Connection con;
    private double curLan;
    private double curLgt;
    private DatabaseHelper dbh;
    double eggLat;
    double eggLng;
    private boolean isDrawed;
    private boolean isLocationKnown;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private Marker m1;
    private Marker m2;
    private GoogleMap mMap;
    private Polyline pl;
    boolean searchActive;

    /* renamed from: com.example.teo.myapplication.Maps.1 */
    class C03581 implements View.OnClickListener {
        C03581() {
        }

        public void onClick(View v) {
            Maps.this.startActivity(new Intent(Maps.this, MainActivity.class));
            Maps.this.finish();
        }
    }

    /* renamed from: com.example.teo.myapplication.Maps.2 */
    class C03592 implements LocationListener {
        C03592() {
        }

        public void onLocationChanged(Location location) {
            Maps.this.curLan = location.getLatitude();
            Maps.this.curLgt = location.getLongitude();
            Maps.this.isLocationKnown = true;
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    }

    /* renamed from: com.example.teo.myapplication.Maps.3 */
    class C03603 implements LocationListener {
        C03603() {
        }

        public void onLocationChanged(Location location) {
            Maps.this.curLan = location.getLatitude();
            Maps.this.curLgt = location.getLongitude();
            Maps.this.isClose();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    }

    public Maps() {
        this.con = new Connection(getBaseContext());
        this.isDrawed = false;
        this.searchActive = false;
        this.dbh = new DatabaseHelper(this, null, null, 1);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        ((ImageButton) findViewById(R.id.imageButtonBack)).setOnClickListener(new C03581());
        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        this.locationListener = new C03592();
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.locationManager.requestLocationUpdates("network", 0, 0.0f, this.locationListener);
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.mMap.setMyLocationEnabled(true);
            this.mMap.setOnMarkerClickListener(this);
        }
    }

    private void isClose() {
        double shortCurLan = Math.floor(this.curLan * 10000.0d) / 10000.0d;
        double shortCurLgt = Math.floor(this.curLgt * 10000.0d) / 10000.0d;
        Location eggLoc = new Location(BuildConfig.FLAVOR);
        eggLoc.setLatitude(this.eggLat);
        eggLoc.setLongitude(this.eggLng);
        Location curLoc = new Location(BuildConfig.FLAVOR);
        curLoc.setLatitude(shortCurLan);
        curLoc.setLongitude(shortCurLgt);
        double shortEggLat = Math.floor(this.eggLat * 10000.0d) / 10000.0d;
        double shortEggLng = Math.floor(this.eggLng * 10000.0d) / 10000.0d;
        double lat = Math.abs(shortCurLan - shortEggLat);
        lat = Math.floor(10000.0d * lat) / 10000.0d;
        double lng = Math.floor(10000.0d * Math.abs(shortCurLgt - shortEggLng)) / 10000.0d;
        float distanceInMeters = curLoc.distanceTo(eggLoc);
        if (distanceInMeters < 6000.0f) {
            Toast.makeText(this, ((int) distanceInMeters) + " meters away " + this.curLan + " " + this.curLgt, Toast.LENGTH_LONG).show();
        }
        if (lat < 1.0E-4d && lng < 1.0E-4d) {
            int id = this.dbh.addRandomGeomon();
            Toast.makeText(this, "CLOSE ", Toast.LENGTH_LONG).show();
            clearMap();
            if (id == 1) {
                Toast.makeText(getBaseContext(), "You have captured them all", Toast.LENGTH_LONG).show();
            } else {
                unlockGeomon(id);
            }
        }
    }

    public boolean onMarkerClick(Marker marker) {
        if (this.pl != null) {
            this.pl.remove();
        }
        if (this.m2 != null) {
            this.m2.setIcon(BitmapDescriptorFactory.defaultMarker(0.0f));
        }
        this.m2 = marker;
        this.m2.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        gLocate();
        goToLoc(this.curLan, this.curLgt);
        isClose();
        return true;
    }

    public void gLocate() {
        if (this.m2.getTitle() != null) {
            this.eggLat = 0.0d;
            this.eggLng = 0.0d;
            return;
        }
        LatLng eggLatLng = this.m2.getPosition();
        this.eggLat = eggLatLng.latitude;
        this.eggLng = eggLatLng.longitude;
    }

    private void goToLoc(double curLan, double curLgt) {
        if (this.isDrawed) {
            this.isDrawed = false;
        }
        LatLng curMarker = new LatLng(curLan, curLgt);
        if (this.m1 != null) {
            this.m1.remove();
            this.m1 = null;
        }
        this.m1 = this.mMap.addMarker(new MarkerOptions().position(curMarker).title("You"));
        if (this.m1 != null && this.m2 != null && !this.isDrawed) {
            drawLine();
            this.isDrawed = true;
        }
    }

    public void spawnEgg(LatLng rLoc) {
        Marker marker = this.mMap.addMarker(new MarkerOptions().position(rLoc));
    }

    public void randomLoc() {
        Random r = new Random();
        double ra = (double) (r.nextInt(3) + 2);
        double rb = (double) (r.nextInt(3) + 2);
        double rLat = 0.0d;
        double rLng = 0.0d;
        int rs = r.nextInt(4);
        if (this.isLocationKnown) {
            switch (rs) {
                case 0 /*0*/:
                    rLat = this.curLan + (ra / 1000.0d);
                    rLng = this.curLgt + (rb / 1000.0d);
                    break;
                case 1 /*1*/:
                    rLat = this.curLan + (ra / 1000.0d);
                    rLng = this.curLgt - (rb / 1000.0d);
                    break;
                case 2 /*2*/:
                    rLat = this.curLan - (ra / 1000.0d);
                    rLng = this.curLgt - (rb / 1000.0d);
                    break;
                case 3 /*3*/:
                    rLat = this.curLan - (ra / 1000.0d);
                    rLng = this.curLgt + (rb / 1000.0d);
                    break;
            }
            spawnEgg(new LatLng(rLat, rLng));
        }
    }

    private void clearMap() {
        this.mMap.clear();
        this.m1.remove();
        this.m1 = null;
        this.m2.remove();
        this.m2 = null;
        this.pl.remove();
        this.pl = null;
    }

    public void geoLocate(View view) throws IOException {
        if (!this.searchActive) {
            for (int i = 0; i <= 9; i++) {
                randomLoc();
            }
        }
        this.searchActive = true;
    }

    private void drawLine() {
        if (this.m1 != null && this.m2 != null) {
            this.pl = this.mMap.addPolyline(new PolylineOptions().add(this.m1.getPosition()).add(this.m2.getPosition()));
        }
    }

    public void onPause() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.locationManager.removeUpdates(this.locationListener);
            super.onPause();
        }
    }

    public void onResume() {
        super.onResume();
        this.locationListener = new C03603();
    }

    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }

    public void unlockGeomon(int id) {
        String url = "InsertCollection?Username=" + new Session(getBaseContext()).getusername() + "&Id=10" + id;
        this.con.execute(new String[]{url});
    }
}
