package com.dartmic.mergeahmlp.Adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.Constants.ListBean;
import com.dartmic.mergeahmlp.R;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.dartmic.mergeahmlp.Constants.FixedData.fixedRole;


/**
 * Created by Agrata Arya on 11/10/2017.
 */

public class RetailerAdapt extends RecyclerView.Adapter<RetailerAdapt.Items>{

    Context context;
    ArrayList<ListBean> data;
    static  String id="";
    AlertDialog dialog;
    EditText ahRemark;
    LayoutInflater inflater = null;

    public RetailerAdapt(Context context, ArrayList<ListBean> data) {
        Log.e("Context", context + "");
        Log.e("Data", data.size() + "");
        this.context = context;
        this.data = data;
    }

    @Override
    public RetailerAdapt.Items onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.retailer_visit, parent, false);
        return new RetailerAdapt.Items(v);
    }

    @Override
    public void onBindViewHolder(RetailerAdapt.Items holder, final int position) {

        holder.r_passbook.setText("Passbook Number :" + data.get(position).getPassbook());
        holder.r_shop.setText("Shop Name :" + data.get(position).getShopname());
        holder.tv_points.setText(data.get(position).getR_pts());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=data.get(position).getId();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View v1 = inflater.inflate(R.layout.remark, null);
                ahRemark = (EditText) v1.findViewById(R.id.ahRemark);
                v1.findViewById(R.id.btn_Submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       sendReport();

                    }
                });
                builder.setView(v1);
                builder.setCancelable(true);
                dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Items extends RecyclerView.ViewHolder {
        TextView r_name,r_passbook,r_shop,city,r_phone,r_address,tv_points;

        public Items(View itemView) {
            super(itemView);
            tv_points = (TextView) itemView.findViewById(R.id.tv_points);
            r_passbook = (TextView) itemView.findViewById(R.id.r_passbook);
            r_shop = (TextView) itemView.findViewById(R.id.r_shop);


        }
    }
    private void sendReport() {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiAH/ah_new_remark.php")
                .addBodyParameter("remark", ahRemark.getText().toString())
                .addBodyParameter("role", fixedRole)
                .addBodyParameter("reported_by", MyPref.getAh(context))
                .addBodyParameter("id", id)
                .setTag("")
                .setPriority(Priority.HIGH)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("RES", response.toString());
                try {
                    if (response.getInt("status") == 1)
                        Toast.makeText(context, "Sent", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    String DistributorRemark=ahRemark.getText().toString();
                    Log.e("RetailerRemark:::::",DistributorRemark);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.e("ERROR", anError.toString());
            }
        });
    }
}
