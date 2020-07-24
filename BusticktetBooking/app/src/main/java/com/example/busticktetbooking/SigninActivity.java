package com.example.busticktetbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SigninActivity extends AppCompatActivity {
    EditText Email, Password;
    Button LogInButton, RegisterButton;
    String email, password;
    ProgressDialog dialog;
    public static final String userEmail="";
    public static final String TAG="LOGIN";

    String URL_SHOW_U= "http://192.168.0.104/BusBooking/signin.php";
    JSONParser jsonParser=new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        LogInButton = (Button) findViewById(R.id.buttonLogin);
        RegisterButton = (Button) findViewById(R.id.buttonRegister);
        Email = (EditText) findViewById(R.id.editEmail);
        Password = (EditText) findViewById(R.id.editPassword);
        LogInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new AttemptLogin().execute();
            }
        });


    }

    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override

        protected void onPreExecute() {
            email = Email.getText().toString().trim();
            password = Password.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(SigninActivity.this, "Enter the correct Email", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(SigninActivity.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
                return;
            }

            super.onPreExecute();

        }

        @Override

        protected JSONObject doInBackground(String... args) {

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));


            JSONObject json = jsonParser.makeHttpRequest(URL_SHOW_U, "POST", params);


            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            try {
                int success = result.getInt("success");

                if (success == 1) {
                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SigninActivity.this,MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }


}