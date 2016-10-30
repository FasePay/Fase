package model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faseapp.faseapp.R;

import java.util.ArrayList;

/**
 * Created by amit on 30/10/16.
 */

public class Adapterr  extends RecyclerView.Adapter<Adapterr.MyViewHolder>{

    private ArrayList<Utdatamodel> dataset;
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textviewdr;
        TextView textviewwr;
        TextView textviewn;
        //ImageView imageicon;
        public MyViewHolder(View itemview){
            super(itemview);
            this.textviewdr=(TextView)itemview.findViewById(R.id.txtsn);
            this.textviewwr=(TextView)itemview.findViewById(R.id.txta);
            this.textviewn=(TextView)itemview.findViewById(R.id.txtr);
        }
    }
    public Adapterr(ArrayList<Utdatamodel> data){
        this.dataset=data;
    }
    @Override
    public int getItemCount(){
        return dataset.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.usrtrancrd,parent,false);
        MyViewHolder myviewholder=new MyViewHolder(view);
        return myviewholder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder arg, int position){
        TextView name=arg.textviewdr;
        TextView addr=arg.textviewwr;
        TextView prc=arg.textviewn;
        // ImageView img=arg.imageicon;
        name.setText(dataset.get(position).getName());
        addr.setText(dataset.get(position).getDate());
        prc.setText(dataset.get(position).getPrc());
        // img.setImageResource(dataset.get(position).getImage());
    }
}
