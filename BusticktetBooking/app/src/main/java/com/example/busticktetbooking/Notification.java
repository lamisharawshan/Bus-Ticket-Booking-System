package com.example.busticktetbooking;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Notification extends ListActivity {
    String Source, Destination, Day;
    private static String url_show_notification = "http://192.168.0.104/BusBooking/get_notification.php";
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> busList;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_buses = "notifications";

    String id,day,rent, arrivaltime, departuretime, name, destintaion, source, available_seat;
    ArrayList<HashMap<String, String>> routeList;
    JSONArray buses = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        busList = new ArrayList<HashMap<String, String>>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        new loadallNotification().execute();

    }
    class loadallNotification extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Notification.this);
            pDialog.setMessage("loading Notification");
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

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jParser.makeHttpRequest(url_show_notification,
                    "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    buses = json.getJSONArray("places");
                    String[] countries = new String[buses.length()];
                    // looping through All places
                    for (int i = 0; i < buses.length(); i++) {
                        JSONObject c = buses.getJSONObject(i);
                        // Storing each json item in variable
                        source = c.getString("source");
                        destintaion = c.getString("destination");
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put("source", source);
                        map.put("destination",destintaion );
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
                                Notification.this, busList, R.layout.activity_list_notification
                                , new String[]{"source", "destination"},
                                new int[]{R.id.Destination, R.id.destin});

                        // updating listview
                        setListAdapter(adapter);
                    }
                    catch (Exception e ){
                        Log.i("lamisha","Exception: "+e);
                    }
                }
            });

        }

        // updating UI from Background Thread
        }
    }