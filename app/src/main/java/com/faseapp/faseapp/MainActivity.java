package com.faseapp.faseapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Fragment.FavShops_Fragment;
import Fragment.TransferAndRefill_Fragment;
import Utils.MyDebugClass;
import navigation.CardActivity;
import navigation.CardAdd;
import navigation.User_transaction;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private int MY_PERMISSION_REQUEST_CAMERA = 100;
    private TextView textView;
    private final String FRAGMENT_TAG = "FTAG";
    String TabFragmentB;

    public void setTabFragmentB(String t){
        TabFragmentB = t;
    }

    public String getTabFragmentB(){
        return TabFragmentB;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutActivity);
        viewPager = (ViewPager) findViewById(R.id.viewPagerActivity);
        setupViewPager(viewPager);
       /* pagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new InstaPay1();
                    case 1:
                        return new TransferAndRefill_Fragment();
                    case 2:
                        return new FavShops_Fragment();
                    case 3:
                        if (isGoogleMapsInstalled())
                        {
                            return new Merchantshop();
                        }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Install Google Play Services First",Toast.LENGTH_LONG).show();
                        return new InstaPay1();
                    }
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return super.getPageTitle(position);
            }
        };*/
       // viewPager.setAdapter(pagerAdapter);
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
    public void bottomtab() {
        // final FragmentManager fragmentManager = getSupportFragmentManager();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               // changeView(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void changeView(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new InstaPay1(), FRAGMENT_TAG).addToBackStack(null).commit();
                break;
            case 1:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new TransferAndRefill_Fragment(), FRAGMENT_TAG).addToBackStack(null).commit();
                break;
            case 2:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new FavShops_Fragment(), FRAGMENT_TAG).addToBackStack(null).commit();
                break;
            case 3:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Merchantshop(), FRAGMENT_TAG).addToBackStack(null).commit();
                break;
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
            startActivity(new Intent(getApplicationContext(), UserEntry.class));
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

                }
            });
            textView = (TextView) view.findViewById(R.id.textViewScanQrCode);
            textView.setOnClickListener(new View.OnClickListener() {
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
            return view;
        }
    }

    public boolean isGoogleMapsInstalled()
    {

        try
        {
            ApplicationInfo info = getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0 );
            return true;
        }
        catch(PackageManager.NameNotFoundException e)
        {
            return false;
        }
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


}