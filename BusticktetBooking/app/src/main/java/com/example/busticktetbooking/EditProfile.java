package com.example.busticktetbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditProfile extends AppCompatActivity {
    EditText txtName;
    EditText txtPhone;
    EditText txtemail;
    int userid;
    Button btnSave;
    TextView textViewId, textViewUsername, textViewEmail, textViewGender;
    String name, email, phone;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String url_update_user = "http://192.168.0.104/BusBooking/update_users_details.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    private static final String TAG_PRICE = "price";
    private static final String TAG_DESCRIPTION = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        btnSave = (Button) findViewById(R.id.update);
        init();
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // starting background task to update product
                new SaveUserDetails().execute();
            }
        });


    }
    void init(){
        textViewId = findViewById(R.id.editphone);
        textViewUsername = findViewById(R.id.editname);
        textViewEmail = findViewById(R.id.editemail);

        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
        textViewId.setText(user.getPhone());
        textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());
        userid=user.getId();

        //when the user presses logout button calling the logout method

    }
    class SaveUserDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProfile.this);
            pDialog.setMessage("Saving change ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {

            // getting updated data from EditTexts
             name = textViewUsername.getText().toString();
             email = textViewEmail.getText().toString();
             phone = textViewId.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", name));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("phone", phone));
            params.add(new BasicNameValuePair("id", String.valueOf(userid)));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_user,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated
                    User user = new User(
                            userid,
                            name,
                            email,
                            phone
                    );

                    //storing the user in shared preferences
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);


                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    // failed to update product
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
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
            startActivity(new Intent(getApplicationContext(), Profile.class));
        }
    }



}