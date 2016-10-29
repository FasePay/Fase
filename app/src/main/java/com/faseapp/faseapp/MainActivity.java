package com.faseapp.faseapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import Utils.MyDebugClass;
import navigation.CardActivity;
import navigation.CardAdd;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private AHBottomNavigation bottomNavigation;
    private final String FRAGMENT_TAG = "FTAG";
    private int MY_PERMISSION_REQUEST_CAMERA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottomNavigation);
        setUpBottomNavigation();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (findViewById(R.id.fragment) != null) {
            if (savedInstanceState != null) {
                return;
            }
            InstaPay1 fragment = new InstaPay1();
            fragment.setArguments(getIntent().getExtras());


            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, fragment).commit();
        }

    }

    private void setUpBottomNavigation() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Insta pay", R.drawable.ic_menu_camera);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Transfer & refill", R.drawable.ic_exit_to_app_black_48px);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Fav shops", R.drawable.ic_favorite_black_24dp);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Merchant's shop", R.drawable.ic_store_mall_directory_black_24dp);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setAccentColor(Color.parseColor("#e86d66"));
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#344a5c"));
        bottomNavigation.setInactiveColor(Color.parseColor("#f8eeef"));
        bottomtab();
    }

    public void bottomtab() {
        // final FragmentManager fragmentManager = getSupportFragmentManager();
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                changeView(position);
                return true;
            }
        });
    }

    private void changeView(int position) {
        switch (position){

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            Intent intent = new Intent(MainActivity.this, CardActivity.class);
            intent.putExtra("FLAG", false);
            startActivity(intent);
        } else if (id == R.id.nav_merchant_transaction) {
            Intent intent = new Intent(MainActivity.this, CardActivity.class);
            intent.putExtra("FLAG", true);
            startActivity(intent);
        }
        else if (id==R.id.nav_logOut)
        {
            startActivity(new Intent(MainActivity.this,ProfileActivity.class));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class InstaPay1 extends android.support.v4.app.Fragment {
        TextView textView;
        Button button;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_insta_pay1, container, false);
            button = (Button) view.findViewById(R.id.buttonBalance);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "hua ya nahi hua", Toast.LENGTH_LONG).show();
                }
            });
            textView = (TextView) view.findViewById(R.id.textViewScanQrCode);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    InstaPay2 su = new InstaPay2();
                    FragmentManager fragmentManager1 = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                    fragmentTransaction1.replace(R.id.fragment, su);
                    fragmentTransaction1.commit();
                }
            });
            return view;
        }

        public class InstaPay2 extends Fragment {
            TextView textView;
            ImageView imageView;

            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_insta_pay2, container, false);
                textView = (TextView) view.findViewById(R.id.textViewBack);
                imageView = (ImageView) view.findViewById(R.id.imageViewQr);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);
                                return;
                            }
                        }
                        Intent intent = new Intent(getContext(), QrCodeScanner.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }
                });
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InstaPay1 pay1 = new MainActivity.InstaPay1();
                        FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.fragment, pay1);
                        fragmentTransaction1.commit();
                    }
                });
                return view;
            }
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==MY_PERMISSION_REQUEST_CAMERA){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(getApplicationContext(),QrCodeScanner.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
            else {
                MyDebugClass.showToast(getApplicationContext(),"Please check permission to use camera");

            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}