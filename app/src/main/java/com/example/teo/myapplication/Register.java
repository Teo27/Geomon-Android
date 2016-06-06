package com.example.teo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Button;

import com.example.teo.myapplication.Models.LocalGeomonModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by Ieva on 09/05/2016.
 */
public class Register extends Activity implements Connection.OnTaskCompleted {

    Connection con = new Connection(this);
    Connection con2 = new Connection(this);
    private DatabaseHelper dbh;
    private EditText emailValue;
    private EditText pass2Value;
    private EditText passValue;
    private EditText scrNameValue;
    private EditText userNameValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        this.con.delegate = this;
        this.con2.delegate = this;
        this.scrNameValue = (EditText) findViewById(R.id.editTextScreenName);
        this.userNameValue = (EditText) findViewById(R.id.editTextUsername);
        this.passValue = (EditText) findViewById(R.id.editTextPassword);
        this.pass2Value = (EditText) findViewById(R.id.editTextRepeatPassword);
        this.emailValue = (EditText) findViewById(R.id.editTextEmail);


        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String scrName = Register.this.scrNameValue.getText().toString().trim();
                String userName = Register.this.userNameValue.getText().toString().trim();
                String pass = Register.this.passValue.getText().toString().trim();
                String pass2 = Register.this.pass2Value.getText().toString().trim();
                String email = Register.this.emailValue.getText().toString().trim();

                if (scrName.isEmpty() && userName.isEmpty() && pass.isEmpty() && email.isEmpty()) {
                    Toast.makeText(Register.this, "All fields are required", Toast.LENGTH_LONG).show();
                } else if (!email.matches(emailPattern)) {
                  Toast.makeText(Register.this, "Email is invalid",Toast.LENGTH_LONG).show();
                } else if (pass.matches(pass2)) {
                    String url = "CreateCustomer?ScrName=" + scrName + "&Username=" + userName + "&Password=" + pass + "&Email=" + email + "&DateOfCreation=" + new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                    Register.this.con.execute(new String[]{url});
                } else {
                    Toast.makeText(Register.this, "Passwords don't match", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });


    }

    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    @Override
    public void processFinish(JSONObject output) {
        try {
            int result = Integer.parseInt(output.getString("value").replace("\n", BuildConfig.FLAVOR));
            if (result == 0) {
                startActivity(new Intent(this, Login.class));
                Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_LONG).show();
                createDB();
            } else if (result == -1) {
                Toast.makeText(this, "Email is already taken", Toast.LENGTH_LONG).show();
            } else if (result == -2) {
                Toast.makeText(this, "Screen name is already taken", Toast.LENGTH_LONG).show();
            } else if (result == -3) {
                Toast.makeText(this, "Username is already taken", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "There was a problem, please try again later", Toast.LENGTH_LONG).show();
            }
            recreate();
        } catch (JSONException e) {
            try {
                JSONArray jsonArray = output.getJSONArray("Table");
                Gson gson = new Gson();
                for (int i = 0; i < jsonArray.length(); i++) {
                    LocalGeomonModel geoModel = (LocalGeomonModel) gson.fromJson(jsonArray.getJSONObject(i).toString(), LocalGeomonModel.class);
                    this.dbh.addGeomon(geoModel.getId(), geoModel.getName(), geoModel.getRarity());
                }
                finish();
            } catch (Exception e2) {
            }
        }
    }
    public void createDB() {
        this.dbh = new DatabaseHelper(this, null, null, 1);
        try {
            this.con2.execute(new String[]{"LocalGeomon"});
        } catch (Exception e) {
            Log.i("exxxx", e.toString());
        }
    }
}
