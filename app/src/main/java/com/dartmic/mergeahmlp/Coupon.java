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
import com.dartmic.mergeahmlp.Adapters.CouponHisAdapt;
import com.dartmic.mergeahmlp.Constants.CouponHisBean;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Coupon extends BaseAgBc implements View.OnClickListener {
    Context context;
    ArrayList<CouponHisBean> arrayList;
    CouponHisAdapt adapter;
    RecyclerView recycler;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        arrayList = new ArrayList<>();
        recycler = (RecyclerView) findViewById(R.id.recycler);
        manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        getSupportActionBar().setTitle(MyPref.storePrefs(context).getMOBILE()+"(" + MyPref.storePrefs(context).getPassbook() + ")");
        couponHistory();
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
        startActivity(new Intent(context,MLPDashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    public void couponHistory() {
        showProgressDialog();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/points_history.php")
        //AndroidNetworking.post("http://35.237.64.197/rlp/apiMech/points_history.php")
                .addBodyParameter("m_id", MyPref.storePrefs(context).getMecId())
                .setPriority(Priority.MEDIUM)
                .setTag("AJA")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                // Toast.makeText(context, "No History Found!!!!", Toast.LENGTH_SHORT).show();
                Log.e("Aaooooo:::::::::::", response.toString());

                try {
                    Log.e("m_id>>>>>>>>>>>>>>>", MyPref.storePrefs(context).getMecId());
                    if (response.getInt("status") == 1) {
                        JSONArray array = response.getJSONArray("msg");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            obj.getString("m_id");
                            obj.getString("part_num");
                            obj.getString("scn_pts");
                            obj.getString("date");
                            CouponHisBean couponHisBean = new CouponHisBean(arrayList, context);
                            couponHisBean.setPart_no(obj.getString("part_num"));
                            couponHisBean.setCreated(obj.getString("scn_pts"));
                            couponHisBean.setMlp_points(obj.getString("date"));
                            couponHisBean.setM_id(obj.getString("m_id"));
                            arrayList.add(couponHisBean);
                        }
                    }
                    else Toast.makeText(context, "No record available", Toast.LENGTH_SHORT).show();
                    Log.e("array list123", arrayList.size() + "");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new CouponHisAdapt(context, arrayList);
                recycler.setLayoutManager(manager);
                recycler.setAdapter(adapter);
                if(arrayList.size()>0)findViewById(R.id.txt_no).setVisibility(View.GONE);
            }

            @Override
            public void onError(ANError anError) {
                hideProgressDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

}
