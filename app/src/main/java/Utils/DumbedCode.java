package Utils;

/**
 * Created by shashank on 11/29/2016.
 */

public class DumbedCode {
}
/*
implements  GoogleApiClient.OnConnectionFailedListener
GoogleApiClient mGoogleApiClient;
 private static final int RC_SIGN_IN = 9001;
   private LoginButton loginButton;
    private SignInButton signIn;
    private CallbackManager callbackManager;



    FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton)findViewById(R.id.login_button);
        signIn= (SignInButton) findViewById(R.id.sign_in_button);
         signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbar.setVisibility(View.VISIBLE);
                signIn();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


         loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                startActivity(new Intent(LoginSignup.this,MainActivity.class));
                finish();
            }

            @Override
            public void onCancel() {
            Toast.makeText(getApplicationContext(),"Login Cancelled",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
            }
        });


        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        pbar.setVisibility(View.GONE);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        pbar.setVisibility(View.GONE);
        if (result.isSuccess()) {
            GoogleSignInAccount googleSignInAccount=result.getSignInAccount();
            String idToken=googleSignInAccount.getIdToken();
            if(idToken!=null)
                Log.v("ID RECIEVED ",idToken);
            else
            Log.v("ID FAILED","NULL RECIEVED");
            // Signed in successfully, show authenticated UI.
            startActivity(new Intent(this,MainActivity.class));
            finish();
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();
        }
    }

       private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);
       // finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();
    }


       <com.google.android.gms.common.SignInButton
        android:id="@+id/sign_in_button"
        android:elevation="@dimen/cardview_default_elevation"
        android:background="@color/white"
        android:gravity="center"
        android:layout_marginLeft="@dimen/size_60dp"
        android:layout_marginRight="@dimen/size_60dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="159dp"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true" />
*/
