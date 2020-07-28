package com.example.busticktetbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class BookingRouteActivity extends AppCompatActivity {
String Source,Destination,Day,route_ID, arrivaltime,departuretime,available_seat,rent,name,price, selectedItemText;
    TextView form, to, day, rent_text, name_text, arrival_time_text,seat_available,route_id, cost;
    Button payment;
    User user; int id;
    AutoCompleteTextView editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_route);
        Intent i = getIntent();
        Source = i.getStringExtra("source");
        Destination = i.getStringExtra("Destination");
        Day = i.getStringExtra("Day");
        route_ID = i.getStringExtra("route_ID");
        arrivaltime = i.getStringExtra("arrivaltime");
        departuretime = i.getStringExtra("departuretime");
        available_seat = i.getStringExtra("available_seat");
        rent = i.getStringExtra("rent");
        name = i.getStringExtra("name");
        //editText = findViewById(R.id.autoCompleteTextView3);
         form = (TextView) findViewById(R.id.form);
         to = (TextView) findViewById(R.id.Source);
         day = (TextView) findViewById(R.id.day);
         rent_text = (TextView) findViewById(R.id.rent);
         name_text = (TextView) findViewById(R.id.name);
         arrival_time_text = (TextView) findViewById(R.id.Source);
         seat_available = (TextView) findViewById(R.id.seat_available);
         route_id = (TextView) findViewById(R.id.route_id);
         cost = (TextView) findViewById(R.id.totalcost);
         payment = (Button) findViewById(R.id.payment);
         form.setText(Source);
         to.setText(Destination);
         day.setText(Day);
         rent_text.setText(rent);
         name_text.setText(name);
         arrival_time_text.setText(arrivaltime);
         seat_available.setText(available_seat);
         route_id.setText(route_ID);
        String[] personNumber = {"1", "2", "3", "4"};
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, personNumber);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
                i.putExtra("routeId",route_ID);
                i.putExtra("cost",price);
                i.putExtra("source", Source);
                i.putExtra("Destination", Destination);
                i.putExtra("arrival_time", arrivaltime);
                i.putExtra("departure_time", departuretime);
                i.putExtra("available_seat", available_seat);
                i.putExtra("Day", Day);
                i.putExtra("rent", rent);
                i.putExtra("name", name);
                i.putExtra("ticket_number",selectedItemText);

                startActivity(i);
                // creating new product in background thread
            }
        });

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                // do something upon option selection
                selectedItemText = (String) parent.getItemAtPosition(position);
             //  cost.setText(selectedItemText);
               String rent = rent_text.getText().toString();
               int Total_cost=Integer.parseInt(rent)*Integer.parseInt(selectedItemText);
               price= String.valueOf(Total_cost);
               cost.setText(price);
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // can leave this empty
            }
        });



    }


}
