package com.example.busticketbookingadminpanel;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowUserList extends ListActivity {
    String name, ID, Email;
    private static String url_show_all_user = "http://192.168.0.104/BusBooking/Admin/get_all_users.php";
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> usersList;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_users = "users";
    ArrayList<HashMap<String, String>> userList;
    JSONArray users = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_list);
        usersList = new ArrayList<HashMap<String, String>>();
        Intent i = getIntent();
        new showAllUsers().execute();
        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // getting values from selected ListItem
////                String rent = ((TextView) view.findViewById(R.id.rent)).getText().toString();
////                String routeid = ((TextView) view.findViewById(R.id.route_id)).getText().toString();
////
////                // Starting new intent
////                Intent in = new Intent(getApplicationContext(),
////                        edit.class);
////                // sending pid to next activity
////                in.putExtra("route_ID", routeid);
////                in.putExtra("source", Source);
////                in.putExtra("Destination", Destination);
////                in.putExtra("arrivaltime", arrivaltime);
////                in.putExtra("departuretime", departuretime);
////                in.putExtra("available_seat", available_seat);
////                in.putExtra("Day", Day);
////                in.putExtra("rent", rent);
////                in.putExtra("name", name);
////                // starting new activity and expecting some response back
////                //startActivityForResult(in, 100);
////                startActivity(in);
////            }
////        });
//
   }

    class showAllUsers extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ShowUserList.this);
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

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jParser.makeHttpRequest(url_show_all_user,
                    "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // successfully created product
                    users = json.getJSONArray(TAG_users);
                    String[] countries = new String[users.length()];
                    // looping through All places
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject c = users.getJSONObject(i);
                        // Storing each json item in variable
                        ID = c.getString("id");
                        Email = c.getString("email");
                        name = c.getString("name");

                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put("Name", name);
                        map.put("Email",Email );
                        map.put("ID",ID );
                        // adding HashList to ArrayList
                        usersList.add(map);
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
                                ShowUserList.this, usersList, R.layout.activity_list_users
                                , new String[]{"Name", "Email", "ID"},
                                new int[]{R.id.Name, R.id.Email, R.id.ID});

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