package com.example.busticketbookingadminpanel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AddingRouteTime extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText arrivalTime,departureTime,Rent;
    AutoCompleteTextView editText, editText1;
    String arrival_Time, departure_Time, Rent_price, daydate;
    Button pick_button;
    TextView set_picked;
    int day, month, year;
    int dayFinal, monthFinal, yearFinal;
    String SelectedBus, SelectedRoute;
    JSONParser jParser = new JSONParser();
    List<String> li = new ArrayList<>();
    List<String> Ro = new ArrayList<>();
    ArrayList<HashMap<String, String>> placesList;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_places = "places";
    private static final String TAG_route = "route";
    private static final String TAG_placeID = "placeID";
    private static final String TAG_NAME = "placename";
    JSONArray places = null;
    JSONArray routes = null;
    private static String url_all_places = "http://192.168.0.104/BusBooking/Admin/get_all_buses.php";
    private static String url_all_routes = "http://192.168.0.104/BusBooking/Admin/get_all_routes.php";
    private static String url_save_routes = "http://192.168.0.104/BusBooking/Admin/save_routes.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_route_time);
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);
        pick_button = (Button) findViewById(R.id.pick_button);
        set_picked = (TextView) findViewById(R.id.set_picked);
        editText = findViewById(R.id.actv);
        editText1 = findViewById(R.id.actv1);
        new loaddAllBus().execute();
        new loadAllRoute().execute();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, li);
        editText1.setAdapter(adapter1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Ro);
        editText.setAdapter(adapter);

        pick_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                day = c.get(Calendar.DAY_OF_MONTH);
                month = c.get(Calendar.MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddingRouteTime.this, AddingRouteTime.this, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                arrivalTime = (EditText)findViewById(R.id.ArrivalTime);
                departureTime = (EditText)findViewById(R.id.inputPrice);
                Rent = (EditText)findViewById(R.id.Rent);
                arrival_Time = arrivalTime.getText().toString().trim();
                departure_Time = departureTime.getText().toString().trim();
                Rent_price = Rent.getText().toString().trim();
                daydate=set_picked.getText().toString().trim();
                SelectedRoute=editText.getText().toString();
                SelectedBus=editText1.getText().toString();
                new SavingNewRoute().execute();
            }
        });



    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1 + 1;
        dayFinal = i2;
        set_picked.setText(dayFinal + "/" + monthFinal + "/" + yearFinal);
    }

    class loaddAllBus extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * getting All places from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_places, "POST", params);

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // places found
                    // Getting Array of places
                    places = json.getJSONArray(TAG_places);
                    String[] countries = new String[places.length()];
                    // looping through All places
                    for (int i = 0; i < places.length(); i++) {
                        JSONObject c = places.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString("bid");
                        String name = c.getString("name");
                        li.add(id);

                    }
                } else {
                    // no places found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            BusSearchingActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {

        }

    }

    class SavingNewRoute extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * getting All places from url
         */
        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("arrivalTime", arrival_Time));
            params.add(new BasicNameValuePair("departureTime", departure_Time));
            params.add(new BasicNameValuePair("rent", Rent_price));
            params.add(new BasicNameValuePair("day", daydate));
            params.add(new BasicNameValuePair("busid", SelectedBus));
            params.add(new BasicNameValuePair("Route", SelectedRoute));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jParser.makeHttpRequest(url_save_routes,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
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
         **/
        protected void onPostExecute(String file_url) {

        }

    }
    class loadAllRoute extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * getting All places from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_routes, "POST", params);

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // places found
                    // Getting Array of places
                    routes = json.getJSONArray(TAG_route);
                    String[] countries = new String[routes.length()];
                    // looping through All places
                    for (int i = 0; i < routes.length(); i++) {
                        JSONObject c = routes.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString("rid");
                        Ro.add(id);

                    }
                } else {
                    // no places found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            BusSearchingActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {

        }

    }



}