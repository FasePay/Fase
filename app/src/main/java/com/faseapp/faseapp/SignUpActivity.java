package com.faseapp.faseapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    private EditText otp;
    private Button button,buttonOtp;
    private EditText username,firstname,lastname,email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
    }

    public class SignUp1 extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.signup1,container,false);
            username= (EditText) view.findViewById(R.id.editTextUsername);
            firstname= (EditText) view.findViewById(R.id.editTextfirstname);
            lastname= (EditText) view.findViewById(R.id.editTextlastname);
            email= (EditText) view.findViewById(R.id.editTextemail);
            password= (EditText) view.findViewById(R.id.editTextpassword);
            button= (Button) view.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
            return view;
        }
    }
    public class OtpFragment extends Fragment{


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_otp,container,false);
            //citrusClient.enableAutoOtpReading(true);

            //String string=citrusClient.getAUTO_load_type().toString();
            otp= (EditText) view.findViewById(R.id.editTextOtpClientFrag);
            buttonOtp= (Button) view.findViewById(R.id.buttonOtpActivity);
            buttonOtp.setText("CONTINUE");
            buttonOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonOtp.setClickable(false);
                    
                    setOtpAndContinue();
                }
            });
            return view;
        }
    }

    private void setOtpAndContinue() {
    }
}
