package com.faseapp.faseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginSignup extends AppCompatActivity {
    TextView textViewGoogle,textViewFb;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        textViewGoogle= (TextView) findViewById(R.id.textViewGoogle);
        textViewFb= (TextView) findViewById(R.id.textViewFb);
        button= (Button) findViewById(R.id.buttonSignup);
        textViewGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSignup.this,Password_activity.class));
            }
        });
        textViewFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSignup.this,Password_activity.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSignup.this,SignUpClient.class));
            }
        });

    }
}
