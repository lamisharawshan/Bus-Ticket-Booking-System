package com.example.busticktetbooking;
import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;

public class ShowSuggestedBuses extends ListActivity {
    String Source, Destination, Day;
    private static String url_show_buses = "http://192.168.0.104/BusBooking/get_selected_buses.php";
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> busList;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_buses = "products";
    private static final String TAG_busID = "busID";
    private static final String TAG_NAME = "busname";
    String id,day,rent, arrivaltime, departuretime, name, destintaion, source, available_seat;
    ArrayList<HashMap<String, String>> routeList;
    JSONArray buses = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_suggested_buses);
        busList = new ArrayList<HashMap<String, String>>();
        Intent i = getIntent();
        Source = i.getStringExtra("Source");
        Destination = i.getStringExtra("Destination");
        Day = i.getStringExtra("Date");
        TextView form = (TextView) findViewById(R.id.form);
        TextView to = (TextView) findViewById(R.id.to);
        TextView day = (TextView) findViewById(R.id.day);
        form.setText(Source);
        to.setText(Destination);
        day.setText(Day);
        new showSuggestedBus().execute();
        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String rent = ((TextView) view.findViewById(R.id.rent)).getText().toString();
             // String source = ((TextView) view.findViewById(R.id.form)).getText().toString();
              // String Destination = ((TextView) view.findViewById(R.id.to)).getText().toString();
              // String Day = ((TextView) view.findViewById(R.id.day)).getText().toString();
                String routeid = ((TextView) view.findViewById(R.id.route_id)).getText().toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        BookingRouteActivity.class);
                // sending pid to next activity
                in.putExtra("route_ID", routeid);
                in.putExtra("source", Source);
                in.putExtra("Destination", Destination);
                in.putExtra("arrivaltime", arrivaltime);
                in.putExtra("departuretime", departuretime);
                in.putExtra("available_seat", available_seat);
                in.putExtra("Day", Day);
                in.putExtra("rent", rent);
                in.putExtra("name", name);
                // starting new activity and expecting some response back
                //startActivityForResult(in, 100);
                startActivity(in);
            }
        });


    }

    class showSuggestedBus extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ShowSuggestedBuses.this);
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
            params.add(new BasicNameValuePair("source", Source));
            params.add(new BasicNameValuePair("destintaion", Destination));
            params.add(new BasicNameValuePair("date", Day));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jParser.makeHttpRequest(url_show_buses,
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
                        id = c.getString("id");
                        day = c.getString("route_date");
                        rent = c.getString("rent");
                        arrivaltime = c.getString("arrivaltime");
                        departuretime = c.getString("departuretime");
                        name = c.getString("name");
                        destintaion = c.getString("destintaion");
                        source = c.getString("source");
                        available_seat=c.getString("available_seat");
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put("name", name);
                        map.put("rent",rent );
                        map.put("arrivaltime",arrivaltime );
                        map.put("departuretime",departuretime );
                        map.put("id",id );
                        // adding HashList to ArrayList
                        busList.add(map);
                        // closing this screen
                        //finish();
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
                                ShowSuggestedBuses.this, busList, R.layout.activity_list_buses
                                , new String[]{"rent", "name", "arrivaltime", "available_seat","id"},
                                new int[]{R.id.rent, R.id.name, R.id.arrival_time, R.id.seat_available,R.id.route_id});

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