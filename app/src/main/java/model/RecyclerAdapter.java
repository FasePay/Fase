package model;

/**
 * Created by shashank on 10/23/2016.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faseapp.faseapp.R;

import java.util.List;

/**
 * Created by shashank on 10/21/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<CardModel> cardModelList;
    boolean flag;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,amount,time;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.textView12);
            amount = (TextView) view.findViewById(R.id.textView18);
            time = (TextView) view.findViewById(R.id.textView25);

        }


    }
    public RecyclerAdapter(Context mContext, List<CardModel> cardList,boolean flag) {
        this.mContext = mContext;
        this.cardModelList = cardList;
        this.flag=flag;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(!flag) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_card_view, parent, false);
        }
        else
        {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_card_view, parent, false);
        }
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CardModel cardModel = cardModelList.get(position);
        holder.name.setText(cardModel.getMerchantName());
        holder.amount.setText(" \u20B9 "+cardModel.getAmount());
        holder.time.setText(cardModel.getDates());
        // loading album cover using Glide library

    }

    @Override
    public int getItemCount() {
        return cardModelList.size();
    }
}
