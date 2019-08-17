package com.dartmic.mergeahmlp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dartmic.mergeahmlp.R;
import com.dartmic.mergeahmlp.beans.FleetBean;

import java.util.ArrayList;

public class FleetListAdapt extends RecyclerView.Adapter<FleetListAdapt.Items> {
    Context context;
    ArrayList<FleetBean> data;

    public FleetListAdapt(Context context, ArrayList<FleetBean> data) {
        Log.e("Context", context + "");
        Log.e("Data", data.size() + "");
        this.context = context;
        this.data = data;
    }

    @Override
    public FleetListAdapt.Items onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fleet_list, parent, false);
        return new FleetListAdapt.Items(v);
    }

    @Override
    public void onBindViewHolder(FleetListAdapt.Items holder, final int position) {

        holder.tv_Shop_Name.setText(data.get(position).getFirmName());
        holder.tv_Passbook.setText(data.get(position).getContact());

    }


    @Override
    public int getItemCount() {
        Log.e("Size:::::::", data.size() + "");
        return data.size();
    }

    public class Items extends RecyclerView.ViewHolder {

        TextView tv_Shop_Name, tv_Passbook;


        public Items(View itemView) {
            super(itemView);

            tv_Shop_Name = (TextView) itemView.findViewById(R.id.tv_Name);
            tv_Passbook = (TextView) itemView.findViewById(R.id.tv_Phone);

        }
    }
}
