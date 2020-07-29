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
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {

    Button buy;
    TextView cardnumber,cvc,name,surname,costInt;
    String route_id,price,selectedItemText;
    int id;
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_buses = "products";
    private static String url_save_bus_info = "http://192.168.0.104/BusBooking/save_booking_information.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        User user = SharedPrefManager.getInstance(this).getUser();
        id=user.getId();
        buy = findViewById(R.id.PayButton);
        cardnumber = findViewById(R.id.PaymentCardNumber);
        cvc= findViewById(R.id.PaymentCVC);
        surname = findViewById(R.id.PaymentSurname);
        name = findViewById(R.id.PaymentName);
        costInt = findViewById(R.id.ActivityPayment);
        Intent i = getIntent();
        route_id = i.getStringExtra("routeId");
        price = i.getStringExtra("cost");
        selectedItemText = i.getStringExtra("ticket_number");
        costInt.setText(price);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new confirmBooking().execute();

            }
        });

    }

    class confirmBooking extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PaymentActivity.this);
            pDialog.setMessage("Saving payment record");
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
            params.add(new BasicNameValuePair("routeId", route_id));
            params.add(new BasicNameValuePair("cost", price));
            params.add(new BasicNameValuePair("person_number", selectedItemText));
            params.add(new BasicNameValuePair("card_number", cardnumber.getText().toString()));
            params.add(new BasicNameValuePair("cvc", cvc.getText().toString()));
            params.add(new BasicNameValuePair("surname", surname.getText().toString()));
            params.add(new BasicNameValuePair("name", name.getText().toString()));
            params.add(new BasicNameValuePair("user_id", String.valueOf(id)));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jParser.makeHttpRequest(url_save_bus_info,
                    "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
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
            Toast.makeText(getApplicationContext(),"Payment Succesfull!",Toast.LENGTH_LONG).show();
            startActivity(new Intent(PaymentActivity.this,MainActivity.class));
            //finish();

        }

    }
}