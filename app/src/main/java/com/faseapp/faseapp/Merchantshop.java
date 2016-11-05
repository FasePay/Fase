package com.faseapp.faseapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import Utils.MyDebugClass;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by amit on 25/10/16.
 */

public class Merchantshop extends Fragment implements OnMapReadyCallback {
    private static final int MY_PERMISSION_REQUEST_FINE = 1;
    private static final int MY_PERMISSION_REQUEST_COARSE = 2;
    MapView mMapView;
    CameraUpdate camera;
    private GoogleMap mMap;
    public EditText editText;
    Marker marker;
//    private final String LOG_TAG = "FTAG";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        // setContentView(R.layout.merchantshop);
        View view = inflater.inflate(R.layout.merchantshop, container, false);
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        editText = (EditText) view.findViewById(R.id.TFaddress);
        ImageButton btn = (ImageButton) view.findViewById(R.id.button);
        Button btn3 = (Button) view.findViewById(R.id.zoomin);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
        Button btn4 = (Button) view.findViewById(R.id.zoomout);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                String location = editText.getText().toString();
                List<Address> addressList = null;
                if (location != null && location.length() > 0) {
                    if (marker != null)
                        marker.remove();
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        addressList = geocoder.getFromLocationName(location, 5);
                        if (addressList == null) {
                            Log.v("ADDRESS NULL", "ADDRESS NULL");
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addressList.size() > 0) {
                        Address address = addressList.get(0);
                        Log.v("ADDRESS", address.getLocality());
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                }
            }


        });

        mMapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null)
            return; // Google Maps not available

        mMap = googleMap;
        MyDebugClass.showLog("on Map ready");
        checkPerm();

    }

    // has not been used
    private void checkPerm() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_FINE);

            }
            setMapCamera();
        } else {
                setMapCamera();
                mMap.setMyLocationEnabled(true);

            }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSION_REQUEST_FINE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setMapCamera();

            } else {
                MyDebugClass.showToast(getApplicationContext(), "Please check permission to use GPS");

            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void setMapCamera() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
        Location location = null;

        for (int i = providers.size() - 1; i >= 0; i--) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            location = lm.getLastKnownLocation(providers.get(i));
            if (location != null)
                break;

        }
        if (location != null) {


            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            LatLng latlng = new LatLng(latitude, longitude);
            MyDebugClass.showLog(latlng.latitude + " " + latlng.longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,14));
            marker = mMap.addMarker(new MarkerOptions().position(latlng).title("Marker"));

        }
    }
}
