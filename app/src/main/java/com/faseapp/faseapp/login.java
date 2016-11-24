package com.faseapp.faseapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.citrus.sdk.Callback;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.Environment;
import com.citrus.sdk.classes.LinkBindUserResponse;
import com.citrus.sdk.classes.LinkUserExtendedResponse;
import com.citrus.sdk.classes.LinkUserPasswordType;
import com.citrus.sdk.response.CitrusError;
import com.citrus.sdk.response.CitrusResponse;

import Utils.MyDebugClass;

public class login extends AppCompatActivity {

    private CitrusClient citrusClient=null;
    private int COUNTER=0;
    private TextView email,phoneNo;
    private EditText otp;
    private Button button;

    private LinkUserExtendedResponse linkUserExtended;
    private LinkBindUserResponse linkBindUser = null;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        email= (TextView) findViewById(R.id.editTextEmail_signIn);
//        phoneNo= (TextView) findViewById(R.id.editTextPhoneNo_signIn);
//        button= (Button) findViewById(R.id.buttonContinue);
        if (findViewById(R.id.frameSignIn) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            final SignInFragment fragment1 = new SignInFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            fragment1.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameSignIn, fragment1).commit();

        }


        citrusClient = CitrusClient.getInstance(getApplicationContext());
        citrusClient.enableLog(true);
        citrusClient.init(
                "test-signup",
                "c78ec84e389814a05d3ae46546d16d2e",
                "test-signin",
                "52f7e15efd4208cf5345dd554443fd99",
                "testing",
                Environment.SANDBOX );

    }

    public void setCitrusClient(String email,String phoneNo) {
        MyDebugClass.showLog("Sonu 1","enter in setcitrus");
        citrusClient.linkUserExtended(email,phoneNo , new Callback<LinkUserExtendedResponse>() {
            @Override
            public void success(LinkUserExtendedResponse linkUserExtendedResponse) {
                MyDebugClass.showLog("firstclasssonu","success ho gya"+linkUserExtendedResponse.toString());
                linkUserExtended=linkUserExtendedResponse;
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentManager.popBackStack("null", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                OtpFragment fragment = new OtpFragment();
                fragmentTransaction.replace(R.id.frameSignIn, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                COUNTER = 1;

            }

            @Override
            public void error(CitrusError error) {
                MyDebugClass.showLog("firstclasssonu","failure ho gya"+error.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(COUNTER==0){
            startActivity(new Intent(getApplicationContext(),LoginSignup.class));
            COUNTER=0;
        }
        else if(COUNTER==1){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SignInFragment fragment1 = new SignInFragment();
            fragmentTransaction.replace(R.id.frameSignIn, fragment1);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            COUNTER = 0;
        }
    }

    public class SignInFragment extends Fragment implements View.OnClickListener {

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_signin,container,false);
            phoneNo= (TextView) view.findViewById(R.id.editTextPhoneNo_signIn);
            email= (TextView) view.findViewById(R.id.editTextEmail_signIn);
            button= (Button) view.findViewById(R.id.buttonContinue);
            button.setOnClickListener(this);
            return view;
        }

        @Override
        public void onClick(View v) {
                Log.d("Safedex",email.getText().toString()+phoneNo.getText().toString());
                setCitrusClient(email.getText().toString(),phoneNo.getText().toString());
        }
    }

    private void setOtpAndContinue() {
        String linkUserPassword = null;
        String otpPassword = otp.getText().toString();
        LinkUserPasswordType linkUserPasswordType = LinkUserPasswordType.None;
        if(otpPassword.length() > 0) {
            linkUserPasswordType = LinkUserPasswordType.Otp;
            linkUserPassword = otpPassword;
        }
        citrusClient.linkUserExtendedSignIn(linkUserExtended,linkUserPasswordType,linkUserPassword, new Callback<CitrusResponse>(){
            @Override
            public void success(CitrusResponse citrusResponse) {
                // User Signed In!
                Toast.makeText(getApplicationContext(),citrusResponse.getMessage(),Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                MyDebugClass.showLog("Sucess",citrusResponse.toString());
            }
            @Override
            public void error(CitrusError error) {
                // Error case
                String errorMessasge = error.getMessage();
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                MyDebugClass.showLog("Sucess","error "+error.toString());
            }
        });
    }
    public class OtpFragment extends Fragment{
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_otp,container,false);
            citrusClient.enableAutoOtpReading(true);
            otp= (EditText) view.findViewById(R.id.editTextOtpClientFrag);
            Button button= (Button) view.findViewById(R.id.buttonOtpActivity);
            button.setText("SIGN IN");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOtpAndContinue();
                }
            });
            return view;
        }
    }

    }



