package com.dartmic.mergeahmlp.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.Constants.MechListBean;
import com.dartmic.mergeahmlp.Fragments.HomeFrag;
import com.dartmic.mergeahmlp.R;
import com.dartmic.mergeahmlp.SharedPref.MyPref;
import com.dartmic.mergeahmlp.edit_mech;

import java.util.ArrayList;


public class MechanicListAdapt extends RecyclerView.Adapter<MechanicListAdapt.Items> {
    Context context;
    ArrayList<MechListBean> data;

    public MechanicListAdapt(Context context, ArrayList<MechListBean> data) {
        Log.e("Context", context + "");
        Log.e("Data", data.size() + "");
        this.context = context;
        this.data = data;
    }

    @Override
    public MechanicListAdapt.Items onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mech_list, parent, false);
        return new MechanicListAdapt.Items(v);
    }

    @Override
    public void onBindViewHolder(MechanicListAdapt.Items holder, final int position) {
        holder.tv_Shop_Name.setText(data.get(position).getShopName());
        holder.tv_Passbook.setText(data.get(position).getPassbook_no());
        holder.tv_Name.setText(data.get(position).getMech_Name());

        if (data.get(position).getCity().contains("null") || data.get(position).getCity().isEmpty()) {
            holder.tv_City.setText("0");

        } else {
            holder.tv_City.setText(data.get(position).getCity());
        }


        holder.keepedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyPref.setmy_mec_id(context, "" + data.get(position).getMech_Id());
                MyPref.storePrefs(context).setMechName(data.get(position).getMech_Name());
                context.startActivity(new Intent(context, edit_mech.class));
                ((Activity) context).finish();
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("POS::::::::::::::::::", data.get(position).getMech_Id());
                MyPref.storePrefs(context).setShopName(data.get(position).getShopName());
                MyPref.storePrefs(context).setPoints(data.get(position).getCity());
                MyPref.storePrefs(context).setMechName(data.get(position).getMech_Name());
                MyPref.storePrefs(context).setMecId(data.get(position).getMech_Id());
                MyPref.storePrefs(context).setPassbook(data.get(position).getPassbook_no());
                MyPref.storePrefs(context).setMOBILE(data.get(position).getMobile());

                MyPref.storePrefs(context).setSelectedStatus(true);
                FixedData.lastUpdate = data.get(position).getCity();
                Fragment fragment = new HomeFrag();
                FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();

            }
        });
    }


    @Override
    public int getItemCount() {
        Log.e("Size:::::::", data.size() + "");
        return data.size();
    }

    public class Items extends RecyclerView.ViewHolder {

        TextView tv_Shop_Name, tv_Passbook, tv_City, tv_Name;
        LinearLayout keepedit;

        public Items(View itemView) {
            super(itemView);

            keepedit = itemView.findViewById(R.id.keepedit);
            tv_Shop_Name = (TextView) itemView.findViewById(R.id.tv_Shop_Name);
            tv_Passbook = (TextView) itemView.findViewById(R.id.tv_Passbook);
            tv_City = (TextView) itemView.findViewById(R.id.tv_City);
            tv_Name = (TextView) itemView.findViewById(R.id.tv_Name);
        }
    }
}
