package com.example.busticktetbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {


        TextView textViewId, textViewUsername, textViewEmail, textViewGender;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);

            init();
        }
        void init(){
            textViewId = findViewById(R.id.textViewId);
            textViewUsername = findViewById(R.id.textViewUsername);
            textViewEmail = findViewById(R.id.textViewEmail);

            //getting the current user
            User user = SharedPrefManager.getInstance(this).getUser();

            //setting the values to the textviews
            textViewId.setText(String.valueOf(user.getId()));
            textViewUsername.setText(user.getUsername());
            textViewEmail.setText(user.getEmail());

            //when the user presses logout button calling the logout method

        }
    }
