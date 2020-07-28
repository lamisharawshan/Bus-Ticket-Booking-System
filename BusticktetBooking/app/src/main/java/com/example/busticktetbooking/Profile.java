package com.example.busticktetbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profile extends AppCompatActivity {


        TextView textViewId, textViewUsername, textViewEmail, textViewGender;
        Button editbutton;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);
            editbutton=(Button) findViewById(R.id.btnSave);

            init();
            editbutton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    // creating new product in background thread
                    startActivity(new Intent(getApplicationContext(), EditProfile.class));

                }
            });
        }
        void init(){
            textViewId = findViewById(R.id.textViewId);
            textViewUsername = findViewById(R.id.textViewUsername);
            textViewEmail = findViewById(R.id.textViewEmail);

            //getting the current user
            User user = SharedPrefManager.getInstance(this).getUser();

            //setting the values to the textviews
            textViewId.setText(user.getPhone());
            textViewUsername.setText(user.getUsername());
            textViewEmail.setText(user.getEmail());

            //when the user presses logout button calling the logout method

        }
    }
