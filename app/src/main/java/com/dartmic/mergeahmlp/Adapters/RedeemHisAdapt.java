package com.dartmic.mergeahmlp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dartmic.mergeahmlp.Constants.RedeemHisBean;
import com.dartmic.mergeahmlp.R;

import java.util.ArrayList;


/**
 * Created by Agrata Arya on 10/6/2017.
 */

public class RedeemHisAdapt extends RecyclerView.Adapter<RedeemHisAdapt.Items> {
    Context context;
    ArrayList<RedeemHisBean> data;

    public RedeemHisAdapt(Context context, ArrayList<RedeemHisBean> data) {
        Log.e("Context", context + "");
        Log.e("Data", data.size() + "");
        this.context = context;
        this.data = data;
    }

    @Override
    public RedeemHisAdapt.Items onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.redeem_card, parent, false);
        return new RedeemHisAdapt.Items(v);
    }

    @Override
    public void onBindViewHolder(RedeemHisAdapt.Items holder, int position) {
        holder.tv_Total_Points.setText(data.get(position).getTotal_Points());
        holder.tv_Redeem_Points.setText(data.get(position).getRedeem_Points());
        holder.tv_Created.setText(data.get(position).getCreated());
       Log.e( "!!!!!!!",data.get(position).getMech_Id());

    }

    @Override
    public int getItemCount() {
        Log.e("Size:::::::", data.size() + "");
        return data.size();
    }

    public class Items extends RecyclerView.ViewHolder {

        TextView tv_Total_Points,tv_Redeem_Points,tv_Created;

        public Items(View itemView) {
            super(itemView);
            tv_Total_Points = (TextView) itemView.findViewById(R.id.tv_Total_Points);
            tv_Redeem_Points = (TextView) itemView.findViewById(R.id.tv_Redeem_Points);
            tv_Created = (TextView) itemView.findViewById(R.id.tv_Created);

        }
    }
}
