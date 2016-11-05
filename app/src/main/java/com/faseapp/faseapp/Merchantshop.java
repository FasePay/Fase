package com.faseapp.faseapp;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by amit on 25/10/16.
 */

public class Merchantshop extends Fragment implements OnMapReadyCallback {
    MapView mMapView;
    private GoogleMap mMap;
    public EditText editText;
//    private final String LOG_TAG = "FTAG";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        // setContentView(R.layout.merchantshop);
        View view = inflater.inflate(R.layout.merchantshop, container, false);
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        editText=(EditText) view.findViewById(R.id.TFaddress);
        ImageButton btn=(ImageButton) view.findViewById(R.id.button);
        Button btn3=(Button)view.findViewById(R.id.zoomin);
        Button btn4=(Button)view.findViewById(R.id.zoomout);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String location=editText.getText().toString();
                List<Address> addressList=null;
                if(location!=null || !location.equals(""))
                {
                    Geocoder geocoder=new Geocoder(getApplicationContext());
                    try {
                        addressList= geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address=addressList.get(0);
                    LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng)) ;
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
        // try {
        MapsInitializer.initialize(getActivity());
      /*  }
        catch (GooglePlayServicesNotAvailableException e) {
            Log.e(LOG_TAG, "Have GoogleMap but then error", e);
            return;
        }*/

    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            return;
        }

    }
}
