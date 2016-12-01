package com.faseapp.faseapp;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.citrus.sdk.Callback;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.Environment;
import com.citrus.sdk.classes.LinkBindUserResponse;
import com.citrus.sdk.classes.LinkUserExtendedResponse;
import com.citrus.sdk.classes.LinkUserPasswordType;
import com.citrus.sdk.response.CitrusError;
import com.citrus.sdk.response.CitrusResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Utils.MyDebugClass;
import model.MySingleton;

public class login extends AppCompatActivity {

    private CitrusClient citrusClient=null;
    private int COUNTER=0;
    private TextView email,phoneNo;
    private EditText otp;
    private Button button,buttonOtp;
    private ProgressBar progressBar;
    private LinkUserExtendedResponse linkUserExtended;
    private LinkBindUserResponse linkBindUser = null;
    boolean doubleBackToExitPressedOnce = false;
    private String SERVER_URL="https://fase.herokuapp.com/register";
    private String PREF_NAME="my_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar= (ProgressBar) findViewById(R.id.progressBar_login);
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

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameSignIn, new LogInFragment()).commit();

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
                progressBar.setVisibility(View.GONE);
                button.setClickable(true);
                MyDebugClass.showLog("firstclasssonu","success ho gya"+linkUserExtendedResponse.toString());
                linkUserExtended=linkUserExtendedResponse;
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentManager.popBackStack("null", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentTransaction.replace(R.id.frameSignIn, new OtpFragment());
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
            View view=inflater.inflate(R.layout.fragment_login,container,false);
            phoneNo= (TextView) view.findViewById(R.id.editTextPhoneNo_signIn);
            email= (TextView) view.findViewById(R.id.editTextEmail_signIn);
            button= (Button) view.findViewById(R.id.buttonContinue);
            button.setOnClickListener(this);
            return view;
        }

        @Override
        public void onClick(View v) {
            button.setClickable(false);
            progressBar.setVisibility(View.VISIBLE);
                Log.d("Safedex",email.getText().toString()+phoneNo.getText().toString());
         sendJsonToServer();

        }
    }



    public class LogInFragment extends Fragment  {

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.activity_sign_in,container,false);
            final EditText username= (EditText) view.findViewById(R.id.edit_username_signIn);
            final EditText password= (EditText) view.findViewById(R.id.edit_password_signIn);
            Button log_button= (Button) view.findViewById(R.id.button_login);
            log_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   sendToserver(username.getText().toString(),password.getText().toString());
                }
            });
            return view;
        }


    }

    void sendToserver(String a,String b)
    {
        Map<String,String> jsonObject=new HashMap<String,String>();
        jsonObject.put("username",a);
        jsonObject.put("password",b);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, SERVER_URL, new JSONObject(jsonObject), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response!=null)
                {
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.v("JSON RESPONSE","NULL STRING");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"NOT SUCCESFULL",Toast.LENGTH_SHORT).show();
                //  Log.v("JSON RESPONSE",error.getLocalizedMessage());
            }


        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Secret-Key","dev.fasepay@123");
                headers.put("Encrypted-Key","FasePay:FNMq60RPlQYrx8Qqkogf07EdqKk");
                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.v("JSON RESPONSE",response.toString());
                return super.parseNetworkResponse(response); }
        };
        MySingleton.getInstance(getApplicationContext()).addtoRequestqueue(jsonObjectRequest);
    }

    void sendJsonToServer()
    {
        Map<String,String> jsonObject=new HashMap<String,String>();
            jsonObject.put("username","fakeuser");
            jsonObject.put("first_name","Fakename");
            jsonObject.put("last_name","Fakelast");
            jsonObject.put("email","fake@gmail.com");
            jsonObject.put("password","fakepass");
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, SERVER_URL, new JSONObject(jsonObject), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
             if(response!=null)
             {
                 try {
                     if(response.getString("Response").equals("Registration Successful"))
                     {
                         SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
                         editor.putString("TOKEN", response.getString("Token"));
                         editor.commit();
                         setCitrusClient(email.getText().toString(),phoneNo.getText().toString());
                     }
                     else
                         Toast.makeText(getApplicationContext(),"SIGN FAILURE",Toast.LENGTH_SHORT).show();
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
                else
             {
                 Log.v("JSON RESPONSE","NULL STRING");
             }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"NOT SUCCESFULL",Toast.LENGTH_SHORT).show();
            //  Log.v("JSON RESPONSE",error.getLocalizedMessage());
            }


        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Secret-Key","dev.fasepay@123");
                headers.put("Encrypted-Key","FasePay:FNMq60RPlQYrx8Qqkogf07EdqKk");
                return headers;
            }

           @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
               Log.v("JSON RESPONSE",response.toString());
                return super.parseNetworkResponse(response); }
            };
        MySingleton.getInstance(getApplicationContext()).addtoRequestqueue(jsonObjectRequest);
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

                progressBar.setVisibility(View.GONE);
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

        public final OtpFragment newInstance(){
            OtpFragment otpFragment=new OtpFragment();
            return otpFragment;
        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_otp,container,false);
            //citrusClient.enableAutoOtpReading(true);

            //String string=citrusClient.getAUTO_load_type().toString();
            otp= (EditText) view.findViewById(R.id.editTextOtpClientFrag);
            buttonOtp= (Button) view.findViewById(R.id.buttonOtpActivity);
            buttonOtp.setText("SIGN IN");
            buttonOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonOtp.setClickable(false);
                    progressBar.setVisibility(View.VISIBLE);
                    setOtpAndContinue();
                }
            });
            return view;
        }
    }

    }



