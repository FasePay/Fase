package com.faseapp.faseapp;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtpClient extends AppCompatActivity {
    Button button;
    @BindView(R.id.editTextotp)
    TextInputLayout otp;
    @BindView(R.id.editTextOtpClient)
    EditText otptext;
    TextWatcher watch=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.toString().trim().length()>0){
                otp.setError(null);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_client);
        ButterKnife.bind(this);
        otptext.addTextChangedListener(watch);
        button= (Button) findViewById(R.id.buttonOtpActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otptext.getText().toString().trim().length()!=6)
                    otp.setError("Invalid otp should be exact six digits");
                else
                    startActivity(new Intent(OtpClient.this,MainActivity.class));
            }
        });
    }
}
