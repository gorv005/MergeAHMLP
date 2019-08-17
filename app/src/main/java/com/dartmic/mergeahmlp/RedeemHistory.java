package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Adapters.RedeemHisAdapt;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.Constants.RedeemHisBean;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RedeemHistory extends BaseAgBc {
    Context context;
    ArrayList<RedeemHisBean> arrayList;
    RedeemHisAdapt adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        getSupportActionBar().setTitle(MyPref.storePrefs(context).getMOBILE()+"(" + MyPref.storePrefs(context).getPassbook() + ")");
        RedeemHistory();
    }

    public void RedeemHistory() {
        showProgressDialog();
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/Redeeme_history.php")
                .addBodyParameter("m_id", MyPref.storePrefs(context).getMecId())
                .setTag("ASDF")
                .setPriority(Priority.MEDIUM).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("aaeye",response.toString());

                hideProgressDialog();
                // Toast.makeText(context, "No History Found!!!!", Toast.LENGTH_SHORT).show();
                try {
                    if (response.getInt("status") == 1) {

                        JSONArray array = response.getJSONArray("msg");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            RedeemHisBean redeemHisBean = new RedeemHisBean(arrayList, context);
//                            redeemHisBean.setRedeem_Points(object.getString("rpoints"));
//                            redeemHisBean.setTotal_Points(object.getString("apoints"));
//                            redeemHisBean.setCreated(object.getString("created"));
//                            redeemHisBean.setReamrk(object.getString("remark"));
//                            redeemHisBean.setMech_Id(object.getString("mech_id"));

                            redeemHisBean.setRedeem_Points(object.getString("scn_pts"));
                            redeemHisBean.setTotal_Points(object.getString("total_pts"));
                            redeemHisBean.setCreated(object.getString("date"));
                            redeemHisBean.setReamrk(object.getString("remark"));
                            redeemHisBean.setMech_Id(object.getString("m_id"));
                            arrayList.add(redeemHisBean);

                        }
                    }else{
                        Toast.makeText(context, "No record available", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("array list111", arrayList.size() + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new RedeemHisAdapt(context, arrayList);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
                if(arrayList.size()>0)findViewById(R.id.txt_no).setVisibility(View.GONE);
            }

            @Override
            public void onError(ANError anError) {
                hideProgressDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,MLPDashboard.class));
        finish();
    }

}
