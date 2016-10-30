package navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.faseapp.faseapp.R;

import java.util.ArrayList;

import model.Adapterr;
import model.Utdatamodel;

/**
 * Created by amit on 25/10/16.
 */

public class User_transaction extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;
    private static RecyclerView recyclerview;
    private static ArrayList<Utdatamodel> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.usrtransaction,container,false);
        data = new ArrayList<Utdatamodel>();
        recyclerview = (RecyclerView)view. findViewById(R.id.recyclerview);
        layout = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layout);
        adapter = new Adapterr(data);
        recyclerview.setAdapter(adapter);
        Data();
        return view;
    }
    private void Data(){
        Utdatamodel dat=new Utdatamodel("Apple","Chandni Chowk","150");
        data.add(dat);
    }
}
