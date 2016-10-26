package com.faseapp.faseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Sonu on 26-Oct-16.
 */

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.splash_screen);

        Thread myThread=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    startActivity(new Intent(getApplicationContext(),UserEntry.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
        super.onCreate(savedInstanceState);
    }
}
