package com.dartmic.mergeahmlp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dartmic.mergeahmlp.Constants.DataBean;
import com.dartmic.mergeahmlp.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Items> {

    Context context;
    ArrayList<DataBean> data;


    public MyAdapter(Context context, ArrayList<DataBean> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public Items onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.history_dist, parent, false);
        return new Items(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Items holder, int position) {
        holder.serial.setText(position + 1 + ".");
        holder.month.setText(data.get(position).getMonth());
        holder.year.setText(data.get(position).getYear());
        holder.tp.setText(data.get(position).getTotal_pts());

        if (position==0){
            holder.ll.setBackgroundResource(R.color.colorPrimary);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Items extends RecyclerView.ViewHolder {
        TextView month, year, serial, tp;
        LinearLayout ll;

        public Items(View itemView) {
            super(itemView);

            ll = itemView.findViewById(R.id.ll);
            serial = itemView.findViewById(R.id.serial_id);
            month = itemView.findViewById(R.id.month);
            year = itemView.findViewById(R.id.year);
            tp = itemView.findViewById(R.id.tp);

        }
    }
}
