package com.example.busTicketReservation;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.myapplication.R;
import java.text.ParseException;
import java.util.Calendar;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    EditText usernameTextFake, surnameText, emailText, passwordText, dateofBirthText, ssnText, usernameText;
    Button dateButton;
    Context context = this;
    RadioButton male, female, acceptTerms;
    public static final String URL_REGISTER = "http://192.168.0.104/busticketBooking/registration.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameText = findViewById(R.id.signUpActivityNameText);
        usernameTextFake = findViewById(R.id.signUpActivityEmailText);
        surnameText = findViewById(R.id.signUpActivityActivitySurnameText);
        emailText = findViewById(R.id.signUpActivityEmailText);
        dateofBirthText = findViewById(R.id.signUpActivityDateOfBirthText);
        ssnText = findViewById(R.id.signUpActivitySSNText);
        male = findViewById(R.id.signUpAccountActivityMaleRadioButton);
        female = findViewById(R.id.signUpAccountActivityFemaleRadioButton);
        passwordText = findViewById(R.id.signUpActivityPasswordText);
        dateButton = findViewById(R.id.signUpDateButton);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar= Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog dpd= new DatePickerDialog(context,new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month += 1;

                        dateofBirthText.setText(day + "/" + month + "/" + year);

                    }

                },year,month,day);
                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE,"Select",dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE,"Cancel",dpd);
                dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                if(!((Activity) SignUpActivity.this).isFinishing())
                {
                    dpd.show();
                }
            }
        });
    }
    public void signUp(View view) {
        //creating request handler object

                //returing the responsrequestHandler = new RequestHandler();
        if(usernameText.getText().toString().isEmpty() || emailText.getText().toString().isEmpty() ){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }

        else {
            class Login extends AsyncTask<Void, Void, String> {
                ProgressDialog pdLoading = new ProgressDialog(SignUpActivity.this);

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    Log.i("hello", "in preexecute");
                    //this method will be running on UI thread
                    pdLoading.setMessage("\tLoading...");
                    pdLoading.setCancelable(false);
                    pdLoading.show();
                }

                @Override
                protected String doInBackground(Void... voids) {
                    RequestHandler requestHandler = new RequestHandler();

                    //creating request handler object
                    HashMap<String, String> params = new HashMap<>();
                    params.put("mail", emailText.getText().toString());
                    params.put("name", usernameText.getText().toString());
                    params.put("dob", dateofBirthText.getText().toString());
                    params.put("user_type", "user");
                    if (male != null) {
                        params.put("gender", "Male");
                    }
                    if ((female != null)) {
                        params.put("gender", "Female");
                    }
                    Log.i("params", params.toString());
                    //returing the response
                    return requestHandler.sendPostRequest(URL_REGISTER, params);

                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    Log.i("onPostExecute", s);
                    pdLoading.dismiss();
                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(s);

                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                        Toast.makeText(SignUpActivity.this, "Exception hi: " + e, Toast.LENGTH_LONG).show();
                    }
                }
            }

            Login login = new Login();
            login.execute();
        }
    }

    public void login(View view){
        finish();
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}