package com.dartmic.mergeahmlp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.dartmic.mergeahmlp.GetHistoryDist;
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

public class DistributorAdapt extends RecyclerView.Adapter<DistributorAdapt.Items> {

    Context context;
    ArrayList<ListBean> data;
    LayoutInflater inflater = null;
    AlertDialog dialog;
    EditText ahRemark;
    static String id = "";

    public DistributorAdapt(Context context, ArrayList<ListBean> data) {
        Log.e("Context", context + "");
        Log.e("Data", data.size() + "");
        this.context = context;
        this.data = data;
    }

    @Override
    public DistributorAdapt.Items onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.distributor_visit, parent, false);
        return new DistributorAdapt.Items(v);
    }

    @Override
    public void onBindViewHolder(DistributorAdapt.Items holder, final int position) {
        holder.d_name.setText(data.get(position).getName());
        holder.d_id.setText(data.get(position).getEmail());
        holder.d_number.setText(data.get(position).getPhone());
        holder.tv_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, GetHistoryDist.class).putExtra("d_id", data.get(position).getId()));
                ((Activity) context).finish();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = data.get(position).getId();
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
        TextView d_name, d_id, d_number, tv_History;

        public Items(View itemView) {
            super(itemView);
            d_name = (TextView) itemView.findViewById(R.id.d_name);
            d_id = (TextView) itemView.findViewById(R.id.d_id);
            d_number = (TextView) itemView.findViewById(R.id.d_number);
            tv_History = (TextView) itemView.findViewById(R.id.tv_History);
        }
    }

    public void sendReport() {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiAH/ah_new_remark.php")
                .addBodyParameter("remark", ahRemark.getText().toString())
                .addBodyParameter("role", fixedRole)
                .addBodyParameter("id", id)
                .addBodyParameter("reported_by", MyPref.getAh(context))
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

                    String DistributorRemark = ahRemark.getText().toString();
                    Log.e("DistributorRemark:::::", DistributorRemark);
                } catch (JSONException e) {
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
// .addBodyParameter("role",role)
//         .addBodyParameter("role", ((Activity)context).getIntent().getStringExtra("role"))