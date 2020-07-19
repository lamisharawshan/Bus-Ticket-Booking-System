package com.example.busticktetbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowSuggestedBuses extends AppCompatActivity {
    String Source, Destination, Day;
    private static String url_show_buses = "http://192.168.0.104/BusBooking/get_selected_buses.php";
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    AutoCompleteTextView editText, editText2;
    private static final String TAG_SUCCESS = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_suggested_buses);
        Intent i = getIntent();
        Source = i.getStringExtra("Source");
        Destination = i.getStringExtra("Destination");
        Day = i.getStringExtra("Date");
        new showSuggestedBus().execute();
    }

    class showSuggestedBus extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(BusSearchingActivity.this);
//            pDialog.setMessage("Searching for bus");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("source", Source));
            params.add(new BasicNameValuePair("destintaion", Destination));
            params.add(new BasicNameValuePair("date", Day));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jParser.makeHttpRequest(url_show_buses,
                    "POST", params);

            // check log cat fro response
            // Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product

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