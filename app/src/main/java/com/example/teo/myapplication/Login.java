package com.example.teo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.teo.myapplication.Models.GeomonId;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ieva on 09/05/2016.
 */
public class Login extends Activity implements Connection.OnTaskCompleted{

    Connection con = new Connection(this);
    Connection con2 = new Connection(this);
    DatabaseHelper dbh;
    private String textPassword;
    private String textUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_menu);
        con.delegate = this;
        con2.delegate = this;


    Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
    Button buttonLogin = (Button) findViewById(R.id.buttonLogin);

    buttonRegister.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            startActivity(new Intent(Login.this, Register.class));
            finish();
        }
    });

    buttonLogin.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
            textUsername = ((EditText) findViewById(R.id.editTextUsername)).getText().toString().trim();
            textPassword = editTextPassword.getText().toString().trim();
            if (textPassword.isEmpty() && textUsername.isEmpty()) {
                Toast.makeText(Login.this, "All fields are required", Toast.LENGTH_LONG).show();
            }
            String url = "Login?Username=" + textUsername + "&Password=" + Login.this.textPassword;
            con.execute(url);
        }
    });




    }

    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    @Override
    public void processFinish(JSONObject output) {
        try {
            int i = Integer.parseInt(output.getString("LoginResult"));
            if (i == 0) {
                new Session(getBaseContext()).login(this.textUsername);
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_LONG).show();
                updateGeomonTable();
                finish();
            } else if (i == -1) {
                Toast.makeText(getBaseContext(), "Username or password is incorrect.", Toast.LENGTH_LONG).show();
            } else if (i == -2) {
                Toast.makeText(getBaseContext(), "Username or password is incorrect.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "There was a problem with the server. Please try again later", Toast.LENGTH_LONG).show();
            }
            recreate();
        } catch (JSONException e) {
            try {
                JSONArray jsonArray = output.getJSONArray("Table");
                List<Integer> geoAvList = new ArrayList();
                Gson gson = new Gson();
                for (int g = 0; g < jsonArray.length(); g++) {
                    geoAvList.add(Integer.valueOf(((GeomonId) gson.fromJson(jsonArray.getJSONObject(g).toString(), GeomonId.class)).getId()));
                }
                dbh = new DatabaseHelper(this, null, null, 1);
                dbh.updateAvailability(geoAvList);
            } catch (Exception e2) {
            }
        }
    }

    public void updateGeomonTable() {
        String url = "LocalCollection?Username=" + this.textUsername;
        con2.execute(url);
    }
}
