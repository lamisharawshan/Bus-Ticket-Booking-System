package com.example.busticktetbooking;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.ListActivity;
import android.app.ActionBar;

public class UsersBookingList extends ListActivity {
    int id;
    private static String url_show_booking = "http://192.168.0.104/BusBooking/get_booking_buses.php";
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> busList;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_buses = "products";
    private static final String TAG_busID = "busID";
    private static final String TAG_NAME = "busname";
    String day,rent, arrivaltime, departuretime, name, destintaion, source, available_seat, ticketnumber;
    ArrayList<HashMap<String, String>> routeList;
    JSONArray buses = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_users_booking_list);
        setTitle("Bus searching activity");
        busList = new ArrayList<HashMap<String, String>>();
        new showBookedBuses().execute();
        User user = SharedPrefManager.getInstance(this).getUser();
        id=user.getId();
    }
    class showBookedBuses extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UsersBookingList.this);
            pDialog.setMessage("Searching for bus");
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
            params.add(new BasicNameValuePair("id", String.valueOf(id)));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jParser.makeHttpRequest(url_show_booking,
                    "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    buses = json.getJSONArray(TAG_buses);
                    String[] countries = new String[buses.length()];
                    // looping through All places
                    for (int i = 0; i < buses.length(); i++) {
                        JSONObject c = buses.getJSONObject(i);
                        // Storing each json item in variable
                        day = c.getString("route_date");
                        rent = c.getString("rent");
                        arrivaltime = c.getString("arrival_time");
                        departuretime = c.getString("departure_time");
                        destintaion = c.getString("PDN");
                        source = c.getString("PSN");
                        available_seat=c.getString("available_seat");
                        ticketnumber=c.getString("ticket_number");
                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put("rent",rent );
                        map.put("arrival",arrivaltime );
                        map.put("departure",departuretime );
                        map.put("PSN",source );
                        map.put("PDN",destintaion );
                        map.put("ticket_number",ticketnumber );
                        map.put("route_date",day );
                        busList.add(map);
                    }
                }else{
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
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    try {
                        ListAdapter adapter = new SimpleAdapter(
                                UsersBookingList.this, busList, R.layout.activity_list_booking
                                , new String[]{"rent", "PSN", "arrival", "departure","PDN", "ticket_number", "route_date"},
                                new int[]{R.id.rent, R.id.Source, R.id.Timearrival, R.id.Destination,R.id.Departure_time, R.id.TicketNumber,R.id.Day});

                        // updating listview
                        setListAdapter(adapter);
                    }
                    catch (Exception e ){
                        Log.i("lamisha","Exception: "+e);
                    }
                }
            });

        }

    }
}