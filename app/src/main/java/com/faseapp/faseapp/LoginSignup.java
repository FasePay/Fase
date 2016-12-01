package com.faseapp.faseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


public class LoginSignup extends AppCompatActivity {

    ProgressBar pbar;

    Button button,mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_signup);

        button= (Button) findViewById(R.id.buttonSignup);
        mButton= (Button) findViewById(R.id.login_button);

        pbar= (ProgressBar) findViewById(R.id.progressBar1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
                finish();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginSignup.this,login.class);
                intent.putExtra("SIGNIN","SIGNIN");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),UserEntry.class));
    }
}