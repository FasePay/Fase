package navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.faseapp.faseapp.R;

import java.util.ArrayList;

import model.Adapterr;
import model.Utdatamodel;

/**
 * Created by amit on 25/10/16.
 */

public class User_transaction extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;
    private static RecyclerView recyclerview;
    private static ArrayList<Utdatamodel> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usrtransaction);
        data = new ArrayList<Utdatamodel>();
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        layout = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layout);
        adapter = new Adapterr(data);
        recyclerview.setAdapter(adapter);
        Data();
    }

    private void Data(){
        Utdatamodel dat=new Utdatamodel("Apple","Chandni Chowk","150");
        data.add(dat);
    }
}
