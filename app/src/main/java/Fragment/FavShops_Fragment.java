package Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.faseapp.faseapp.MainActivity;
import com.faseapp.faseapp.Merchantshop;
import com.faseapp.faseapp.QrCodeScanner;
import com.faseapp.faseapp.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sonu on 21-Oct-16.
 */
public class FavShops_Fragment extends Fragment {
    EditText shopname;
    Button save;
    String shop;
    TextView textView;
    private int MY_PERMISSION_REQUEST_CAMERA = 100;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fav_shops,container,false);
        shopname=(EditText)view.findViewById(R.id.txtfavshop);
        save=(Button)view.findViewById(R.id.btnsave);
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
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shop=shopname.getText().toString();
                List<Address> addressList = null;

                if(shop.trim().length()!=0){
                    Geocoder geocoder = new Geocoder(getActivity());
                    try{
                        addressList = geocoder.getFromLocationName(shop, 1);
                        if (addressList == null || addressList.size()==0) {
                           // Toast.makeText(getActivity().getApplicationContext(),"ADDRESS NOT FOUND",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if (addressList.size() > 0) {
                            Address address = addressList.get(0);
                            if(address!=null) {
                                Bundle args = new Bundle();
                                args.putString("favshop", shop);
                                Fragment sp=new Merchantshop();
                                String TabOfFragmentB = ((MainActivity)getActivity()).getTabFragmentB();
                                Merchantshop shopm=(Merchantshop)getActivity().getSupportFragmentManager().findFragmentByTag(TabOfFragmentB);
                                shopm.setfavshop(shop);
                                shopname.setText(" ");
                           //     sp.setArguments(args);
                                Log.d("Name",shop);
                              //  getFragmentManager().beginTransaction().add(R.id.content_frame, sp).addToBackStack(null).commit();
                            }
                            else
                            {

                                Toast.makeText(getActivity().getApplicationContext(), "PLACE NOT FOUND", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }
}
