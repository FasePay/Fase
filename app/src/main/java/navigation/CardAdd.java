package navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.faseapp.faseapp.MainActivity;
import com.faseapp.faseapp.R;

/**
 * Created by shashank on 10/22/2016.
 */

public class CardAdd extends Fragment {

    TextView textView;
    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // super.onCreate(savedInstanceState);
        View view=inflater.inflate(R.layout.add_card,container,false);
        //setContentView(R.layout.add_card);
        textView= (TextView) view.findViewById(R.id.textView7);
        button= (Button)view. findViewById(R.id.button);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity().getApplicationContext(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity().getApplicationContext(),MainActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }
}