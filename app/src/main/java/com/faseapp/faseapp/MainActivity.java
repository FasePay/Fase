package com.faseapp.faseapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.citrus.sdk.Callback;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.response.CitrusError;
import com.citrus.sdk.response.CitrusResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Fragment.FavShops_Fragment;
import Fragment.TransferAndRefill_Fragment;
import Utils.CitrusPay;
import Utils.MyDebugClass;
import navigation.CardActivity;
import navigation.CardAdd;
import navigation.User_transaction;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    boolean doubleBackToExitPressedOnce = false;
    private CitrusClient citrusClient;
    private int MY_PERMISSION_REQUEST_CAMERA = 100;
    private TextView textView2;
    private final String FRAGMENT_TAG = "FTAG";
    String TabFragmentB;
    private CitrusPay citrusPay;
    private String rs;
    public EditText payTo,mobileNo,amount;
    public void setTabFragmentB(String t){
        TabFragmentB = t;
    }

    public String getTabFragmentB(){
        return TabFragmentB;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        citrusPay=new CitrusPay(getApplicationContext());
        citrusClient=citrusPay.getCitrusClient();
        citrusClient = CitrusClient.getInstance(getApplicationContext());
        if(!isUserSignedIn()){
            startActivity(new Intent(getApplicationContext(),UserEntry.class));
            MyDebugClass.showToast(getApplicationContext(),"Please sign in");
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutActivity);
        viewPager = (ViewPager) findViewById(R.id.viewPagerActivity);
        setupViewPager(viewPager);


        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
       // viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private boolean isUserSignedIn() {
        final boolean[] b = {false};
        citrusClient.isUserSignedIn(new com.citrus.sdk.Callback<Boolean>() {
            @Override
            public void success(Boolean loggedIn) {
                MyDebugClass.showLog("user",loggedIn.toString());
                b[0] = Boolean.parseBoolean(loggedIn.toString());
            }

            @Override
            public void error(CitrusError error) {
                MyDebugClass.showLog("user","user logged Out");
                MyDebugClass.showToast(getApplicationContext(),"Check internet connection or try again");
            }
        });
        return b[0];
    }


    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Insta pay");
        tabOne.setSelected(true);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon1, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Transfer & refill");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon2, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Fav shops");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon3, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("Shops");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon4, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);
         //  bottomtab();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new InstaPay1(), "Insta pay");
        adapter.addFrag(new TransferAndRefill_Fragment(), "Transfer & refill");
        adapter.addFrag(new FavShops_Fragment(), "Fav shops");
        adapter.addFrag(new Merchantshop(), "Shops");
        viewPager.setAdapter(adapter);
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           // startActivity(new Intent(getApplicationContext(), Home_Page.class));
            return true;
        } else if (id == R.id.action_profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
//
        if (id == R.id.nav_addCard) {
            startActivity(new Intent(MainActivity.this, CardAdd.class));
        } else if (id == R.id.nav_user_transaction) {
            startActivity(new Intent(MainActivity.this, User_transaction.class));
        } else if (id == R.id.nav_merchant_transaction) {

            Intent intent = new Intent(MainActivity.this, CardActivity.class);
            intent.putExtra("FLAG", true);
            startActivity(intent);
        } else if (id == R.id.nav_logOut) {
            citrusClient.signOut(new Callback<CitrusResponse>() {

                @Override
                public void success(CitrusResponse citrusResponse) {
                    startActivity(new Intent(getApplicationContext(), UserEntry.class));
                    MyDebugClass.showLog("sucess","otp"+citrusResponse.toString());
                    MyDebugClass.showToast(getApplicationContext(),citrusResponse.getMessage());
                }

                @Override
                public void error(CitrusError error) {
                    MyDebugClass.showToast(getApplicationContext(),error.getMessage()+"Or retry");
                }
            });


        } else if (id == R.id.nav_share) {
            ApplicationInfo app = getApplicationInfo();
            String filePath = app.sourceDir;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.setType("application/vnd.android.package-archive");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
            startActivity(Intent.createChooser(intent, "Share app"));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSION_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(getApplicationContext(), QrCodeScanner.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            } else {
                MyDebugClass.showToast(getApplicationContext(), "Please check permission to use camera");

            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public class InstaPay1 extends Fragment{

        public InstaPay1(){

        }
        Button buttonBalance;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_insta_pay1, container, false);
            Button button = (Button) view.findViewById(R.id.buttonBalance);
            payTo= (EditText) view.findViewById(R.id.editTextPayTo);
            mobileNo= (EditText) view.findViewById(R.id.editTextPayeeNumber);
            amount= (EditText) view.findViewById(R.id.editTextAmount);
            button.setSoundEffectsEnabled(false);
            Button buttonPayNow= (Button) view.findViewById(R.id.buttonPayNow);
            buttonBalance= (Button) view.findViewById(R.id.buttonBalance);
            getBal();
            buttonPayNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rs=amount.getText().toString();
                    alertDialogForPayMoneyOption();
                    //startActivity(new Intent(getContext(),CardPay.class));
                }
            });
            buttonBalance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   getBal();
                }
            });

            return view;
        }

        private void getBal(){
            citrusClient.getBalance(new com.citrus.sdk.Callback<Amount>() {
                @Override
                public void success(Amount amount) {
                    String s1="Balance =Rs ";
                    String s2=String.valueOf(amount.getValueAsDouble());
                    String s3=s1.concat(s2);
                    buttonBalance.setText(s3);
                    MyDebugClass.showLog("getWallet",s3+"12");
                }

                @Override
                public void error(CitrusError error) {
                    MyDebugClass.showLog("balance",error.getMessage());
                }
            });
        }
    }
    public void alertDialogForPayMoneyOption(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose)
                .setItems(R.array.paymentOption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       switch (which){
                           case 0:

                               boolean b=citrusPay.payFromWallet(amount.getText().toString(),mobileNo.getText().toString());
                               MyDebugClass.showLog("citsonu", String.valueOf(b));
                               if(b){
                                   payTo.setText("");
                                   mobileNo.setText("");
                                   amount.setText("");
                               }
                               break;
                           case 1:
                               alertDialogForBankName();
                               break;
                           case 2:
                               startActivity(new Intent(getApplicationContext(),CardPay.class).putExtra("cardType","DC").putExtra("amount",rs));
                               break;
                           case 3:
                               startActivity(new Intent(getApplicationContext(),CardPay.class).putExtra("cardType","CC").putExtra("amount",rs));
                               break;
                       }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        builder.create();

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void alertDialogForBankName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose)
                .setItems(R.array.bankList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        citrusPay.loadMoneyFromNetBanking(getResources().getStringArray(R.array.bankList)[which],getResources().getStringArray(R.array.bankCID)[which],"10");
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MyDebugClass.showToast(getApplicationContext(),"Please choose a mode to load balance");        // User cancelled the dialog
                    }
                });
        builder.create();

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}