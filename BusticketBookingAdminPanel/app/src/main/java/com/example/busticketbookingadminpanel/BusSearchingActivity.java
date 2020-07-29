package com.example.busticketbookingadminpanel;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class BusSearchingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button pick_button;
    TextView set_picked;
    int day, month, year;
    int dayFinal, monthFinal, yearFinal;

    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    AutoCompleteTextView editText, editText2;
    ArrayList<HashMap<String, String>> placesList;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_places = "places";
    private static final String TAG_placeID = "placeID";
    private static final String TAG_NAME = "placename";
    List<String> li = new ArrayList<>();
    String[] countries;
    JSONArray places = null;
    private static String url_all_places = "http://192.168.0.104/BusBooking/get_all_places.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_searching);
        editText = findViewById(R.id.actv);
        editText2 = findViewById(R.id.actv2);
        new LoadAllroutes().execute();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, li);
        editText.setAdapter(adapter);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, li);
        editText2.setAdapter(adapter1);
        pick_button = (Button) findViewById(R.id.pick_button);
        set_picked = (TextView) findViewById(R.id.set_picked);
        Button btnCreateProduct = (Button) findViewById(R.id.search);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                String source = editText.getText().toString();
                String destintaion = editText2.getText().toString();
                String date = set_picked.getText().toString();
                Intent i = new Intent(getApplicationContext(), ShowSuggestedBuses.class);
                i.putExtra("Source",source);
                i.putExtra("Destination",destintaion);
                i.putExtra("Date", date);
                startActivity(i);
            }
        });
        pick_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                day = c.get(Calendar.DAY_OF_MONTH);
                month = c.get(Calendar.MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(BusSearchingActivity.this, BusSearchingActivity.this, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
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


    class LoadAllroutes extends AsyncTask<String, String, String> {

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
                        String id = c.getString(TAG_placeID);
                        String name = c.getString(TAG_NAME);
                        li.add(name);
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
