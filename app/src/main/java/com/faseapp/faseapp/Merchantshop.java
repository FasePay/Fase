package com.faseapp.faseapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import Utils.MyDebugClass;
import model.MySingleton;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by amit on 25/10/16.
 */

public class Merchantshop extends Fragment implements OnMapReadyCallback{
    private static final int MY_PERMISSION_REQUEST_FINE = 1;
    private static final int MY_NETWORK_STATE = 2;
    private static final int MY_MAP_NETWORK_STATE = 3;
    MapView mMapView;
    private GoogleMap mMap=null;
    public EditText editText;
    Marker marker,marker1;
    Bundle arguments;
    double latitude,longitude;
    String server_url;
    ListView listView;
    private final String ARRAY_STRING="predictions";
    private final String OBEJCT_STRING="description";
    ArrayAdapter<String> arrayAdapter;
    CardView cardView;
    boolean flag=false;
    private Handler handler;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        if (isInternetAvailable(1)) {
            mMapView.getMapAsync(this);
        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        // setContentView(R.layout.merchantshop);

        final View view = inflater.inflate(R.layout.merchantshop, container, false);
        server_url=getPlaceAutoCompleteUrl("Raj");
        cardView= (CardView) view.findViewById(R.id.map_Card_view);
        mMapView = (MapView) view.findViewById(R.id.map);
        listView= (ListView) view.findViewById(R.id.searchResultLV);
        mMapView.onCreate(savedInstanceState);
        String myTag = getTag();
        ((MainActivity)getActivity()).setTabFragmentB(myTag);
        mMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
            }
        });
        editText = (EditText) view.findViewById(R.id.TFaddress);
         arguments = getArguments();
        ImageButton btn = (ImageButton) view.findViewById(R.id.button);
        final Button btn3 = (Button) view.findViewById(R.id.zoomin);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                flag=true;
                editText.setText(adapterView.getItemAtPosition(i).toString());
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
        final Button btn4 = (Button) view.findViewById(R.id.zoomout);
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
                    hideKeyboard(view);
                }
                if (isInternetAvailable(2)) {
                    doWork();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                }

            }

        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(flag)
                {
                    Log.v("FLAG","FLAG");
                    flag=false;
                    hideKeyboard(getView());
                    doWork();
                }
               else if((editText.getText().toString() != null &&editText.getText().length()>0 ))
                {
                    Runnable run = new Runnable() {


                        @Override
                        public void run() {

                            // cancel all the previous requests in the queue to optimise your network calls during autocomplete search
                            MySingleton.getInstance(getApplicationContext()).cancelAllRequests("timepass");

                            //build Get url of Place Autocomplete and hit the url to fetch result.
                            server_url=getPlaceAutoCompleteUrl(editText.getText().toString());
                            StringRequest stringRequest=new StringRequest(Request.Method.POST,server_url,new Response.Listener<String>(){
                                @Override
                                public void onResponse(String response) {
                                    ArrayList<String> arrayList=new ArrayList<String>();
                                    arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrayList);
                                    try {
                                        JSONObject jsonObject=new JSONObject(response);
                                        JSONArray jsonArray=new JSONArray();
                                        jsonArray=jsonObject.getJSONArray(ARRAY_STRING);
                                        mMapView.setVisibility(View.GONE);
                                        cardView.setVisibility(View.GONE);
                                        listView.setVisibility(View.VISIBLE);
                                        listView.setAdapter(arrayAdapter);
                                        for(int i=0;i<jsonArray.length();i++)
                                        {
                                            JSONObject jsonObject1=new JSONObject();
                                            jsonObject1=jsonArray.getJSONObject(i);
                                            arrayList.add(jsonObject1.getString(OBEJCT_STRING));
                                            arrayAdapter.notifyDataSetChanged();
                                        }
                                        mMapView.setVisibility(View.GONE);
                                        cardView.setVisibility(View.GONE);
                                        listView.setVisibility(View.VISIBLE);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },new Response.ErrorListener()
                            {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    editText.setText(error.getMessage());
                                }
                            });
                            MySingleton.getInstance(getApplicationContext()).addtoRequestqueue(stringRequest);

                        }

                    };

                    // only canceling the network calls will not help, you need to remove all callbacks as well
                    // otherwise the pending callbacks and messages will again invoke the handler and will send the request
                    if (handler != null) {
                        handler.removeCallbacksAndMessages(null);
                    } else {
                        handler = new Handler();
                    }
                    handler.postDelayed(run, 1000);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        // some code depending on keyboard visiblity status
                        if(!isOpen)
                        {
                            listView.setVisibility(View.GONE);
                            mMapView.setVisibility(View.VISIBLE);
                            cardView.setVisibility(View.VISIBLE);
                        }
                    }
                });
        return view;
    }

    public void setfavshop(String name){
        List<Address> addressList = null;
        if (name != null && name.length() > 0) {
            //  if (marker != null)
            //  marker.remove();
            Log.d("Nme",name);
            Geocoder geocoder = new Geocoder(getActivity());
            try {
                addressList = geocoder.getFromLocationName(name, 1);
                if (addressList == null || addressList.size()==0) {
                    Toast.makeText(getActivity().getApplicationContext(),"ADDRESS NOT FOUND",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (addressList.size() > 0) {
                    Address address = addressList.get(0);
                    if(address!=null) {
                        Log.d("Nme",name);
                        Toast.makeText(getActivity(),name+" added successfully",Toast.LENGTH_LONG).show();
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        marker1 = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                    else
                    {

                        Toast.makeText(getActivity().getApplicationContext(), "PLACE NOT FOUND", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(),"PLACE NOT FOUND",Toast.LENGTH_SHORT).show();
        }
    }


    public void doWork()
    {
        listView.setVisibility(View.GONE);
        mMapView.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.VISIBLE);
        String location = editText.getText().toString();
        List<Address> addressList = null;
        if (location != null && location.length() > 0) {
            if (marker != null)
                marker.remove();
            Geocoder geocoder = new Geocoder(getActivity());
            try {
                addressList = geocoder.getFromLocationName(location, 5);
                if (addressList == null || addressList.size()==0) {
                   Toast.makeText(getActivity().getApplicationContext(),"ADDRESS NOT FOUND",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (addressList.size() > 0) {
                    Address address = addressList.get(0);
                    if(address!=null) {

                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                    else
                    {

                        Toast.makeText(getActivity().getApplicationContext(), "PLACE NOT FOUND", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(),"PLACE NOT FOUND",Toast.LENGTH_SHORT).show();
        }
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
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                hideKeyboard(getView());
            }
        });
        MyDebugClass.showLog("tag","on Map ready");
        checkPerm();

    }

    // has not been used
    private void checkPerm() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_FINE);

            }
            else {
                setMapCamera();
              /*  if(arguments!=null) {
                    value = getArguments().getString("favshop");

                    setfavshop(value);
                }*/

            }
        } else {
            setMapCamera();
            mMap.setMyLocationEnabled(true);
           /* if(arguments!=null) {
                value = getArguments().getString("favshop");

                setfavshop(value);
            }*/


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
        else if(requestCode==MY_NETWORK_STATE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              doWork();
            } else {
                MyDebugClass.showToast(getApplicationContext(), "Please check internet permission");

            }
        }
        else if(requestCode==MY_MAP_NETWORK_STATE)
        {
            mMapView.getMapAsync(this);
        }
        else
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


           longitude = location.getLongitude();
            latitude = location.getLatitude();
            LatLng latlng = new LatLng(latitude, longitude);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,14));
            marker = mMap.addMarker(new MarkerOptions().position(latlng).title("Marker"));

        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(),"ADDRESS NOT FOUND",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isInternetAvailable(int i) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                if(i==2)
                requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE},MY_NETWORK_STATE);
                else
                    requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE},MY_MAP_NETWORK_STATE);
            }
            else
            {
                return connectionResult();
            }
        }
        else
        {
            return connectionResult();
        }

        return connectionResult();
    }

    public boolean connectionResult()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public String getPlaceAutoCompleteUrl(String input) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/place/autocomplete/json");
        urlString.append("?input=");
        try {
            urlString.append(URLEncoder.encode(input, "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        urlString.append("&location=");
        urlString.append(latitude + "," + longitude); // append lat long of current location to show nearby results.
        urlString.append("&radius=2500&language=en");
        urlString.append("&key=" + "AIzaSyCKATo4rHZuFg3hk3lNOeoCMwoOK6CvY3c");

        Log.d("FINAL URL:::   ", urlString.toString());
        return urlString.toString();
    }

}
