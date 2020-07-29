package com.example.busticktetbooking;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {
    EditText name,email,password,phone;
    Button mRegisterbtn;
    TextView mLoginPageBack;
    String Name,Email,Password, Phone;
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    // url to create new product
    private static String url_create_user = "http://192.168.0.104/BusBooking/create_user.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name = (EditText)findViewById(R.id.editName);
        email = (EditText)findViewById(R.id.editEmail);
        password = (EditText)findViewById(R.id.editPassword);
        phone=(EditText)findViewById(R.id.editTextPhone);
        mRegisterbtn = (Button)findViewById(R.id.buttonRegister);

        mRegisterbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                Name = name.getText().toString().trim();
                Email = email.getText().toString().trim();
                Password = password.getText().toString().trim();
                Phone=  phone.getText().toString().trim();
                if (TextUtils.isEmpty(Name)){
                    Toast.makeText(RegistrationActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    name.requestFocus();

                    return;
                }else if (TextUtils.isEmpty(Email)){
                    Toast.makeText(RegistrationActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(Password)){
                    Toast.makeText(RegistrationActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                    return;
                }else if (Password.length()<6){
                    Toast.makeText(RegistrationActivity.this,"Password must be greater then 6 digit",Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(Phone)){
                    Toast.makeText(RegistrationActivity.this,"Enter Phonenumber",Toast.LENGTH_SHORT).show();
                    phone.requestFocus();
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches() ){
                    Toast.makeText(RegistrationActivity.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                    return;

                }
                else {
                    new CreateNewUser().execute();
                }
            }
        });
    }

    class CreateNewUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegistrationActivity.this);
            pDialog.setMessage("Creating User please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", Name));
            params.add(new BasicNameValuePair("email", Email));
            params.add(new BasicNameValuePair("password", Password));
            params.add(new BasicNameValuePair("phone", Phone));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_user,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), SigninActivity.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}