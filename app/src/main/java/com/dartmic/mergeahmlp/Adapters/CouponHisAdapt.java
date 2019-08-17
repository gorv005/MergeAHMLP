package com.dartmic.mergeahmlp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dartmic.mergeahmlp.Constants.CouponHisBean;
import com.dartmic.mergeahmlp.R;

import java.util.ArrayList;

/**
 * Created by Agrata Arya on 10/5/2017.
 */

public class CouponHisAdapt extends RecyclerView.Adapter<CouponHisAdapt.Items> {

    Context context;
    ArrayList<CouponHisBean> data;

    public CouponHisAdapt(Context context, ArrayList<CouponHisBean> data) {
        Log.e("Context", context + "");
        Log.e("Data", data.size() + "");
        this.context = context;
        this.data = data;
    }

    @Override
    public CouponHisAdapt.Items onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.coupon_cardview, parent, false);
        return new CouponHisAdapt.Items(v);

    }

    @Override
    public void onBindViewHolder(CouponHisAdapt.Items holder, int position) {
        holder.tv_Part_No.setText(data.get(position).getPart_no());
        holder.tv_Mlp_points_His.setText(data.get(position).getMlp_points());
        holder.tv_Created.setText(data.get(position).getCreated());

    }

    @Override
    public int getItemCount() {
        Log.e("Size:::::::", data.size() + "");
        return data.size();
    }

    public class Items extends RecyclerView.ViewHolder {
        TextView tv_Part_No,tv_Mlp_points_His,tv_Created;

        public Items(View itemView) {
            super(itemView);
            tv_Part_No = (TextView) itemView.findViewById(R.id.tv_Part_No);
            tv_Mlp_points_His = (TextView) itemView.findViewById(R.id.tv_Mlp_points_His);
            tv_Created = (TextView) itemView.findViewById(R.id.tv_Created);

        }
    }
}
