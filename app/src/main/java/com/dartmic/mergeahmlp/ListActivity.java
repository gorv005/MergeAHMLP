package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Adapters.DistributorAdapt;
import com.dartmic.mergeahmlp.Adapters.MechAdapt;
import com.dartmic.mergeahmlp.Adapters.RetailerAdapt;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.Constants.ListBean;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.dartmic.mergeahmlp.Constants.FixedData.fixedRole;

public class ListActivity extends BaseAgBc {

    RecyclerView recycler;
    static ArrayList<ListBean> arrayList, data;
    RecyclerView.LayoutManager manager;
    MechAdapt adapter1;
    DistributorAdapt adapter2;
    RetailerAdapt adapter3;
    Context context;
    EditText showlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        context = this;
        init();
        if (fixedRole.equals("retailer")) {
            getSupportActionBar().setTitle("Select Retailer");
            fetchListR();
        }
        if (fixedRole.equals("distributor")) {
            getSupportActionBar().setTitle("Select Distributor");
            fetchListD();
        }
        if (fixedRole.equals("mech")) {
            getSupportActionBar().setTitle("Select Mechanic");
            fetchListM();
        }
    }

    public void init() {
        recycler = (RecyclerView) findViewById(R.id.recycler);
        arrayList = new ArrayList<>();
        data = new ArrayList<>();
        showlist = (EditText) findViewById(R.id.showlist);
        manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        showlist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                data.clear();
                for (int p = 0; p < arrayList.size(); p++) {
                    if (arrayList.size() > 0 && fixedRole.equals("distributor")) {
                        if (arrayList.get(p).getName().toLowerCase().contains(charSequence.toString().toLowerCase()) || arrayList.get(p).getId().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            data.add(arrayList.get(p));
                        }
                    } else if (arrayList.size() > 0 && arrayList.get(p).getPassbook().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || arrayList.get(p).getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        data.add(arrayList.get(p));
                    }
                }
                filterBc(data);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

    void filterBc(ArrayList<ListBean> bc) {
        Log.e("Calling", bc.size() + "");
        recycler.setLayoutManager(manager);
        adapter2 = new DistributorAdapt(context, bc);
        recycler.setAdapter(adapter2);

        if (fixedRole.equals("mech")) {
            adapter1 = new MechAdapt(context, bc);
            recycler.setAdapter(adapter1);
        }
        if (fixedRole.equals("distributor")) {
            adapter2 = new DistributorAdapt(context, bc);
            recycler.setAdapter(adapter2);
        }
        if (fixedRole.equals("retailer")) {
            adapter3 = new RetailerAdapt(context, bc);
            recycler.setAdapter(adapter3);
        }
    }

    public void fetchListM() {
        showProgressDialog();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/mech_list.php")
                .addBodyParameter("id", MyPref.getAh(context))
                .setTag("Asj")
                .setPriority(Priority.HIGH)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                Log.e("Data", response.toString());
                try {
                    if (response.getInt("status") == 2) {
                        JSONArray array = response.getJSONArray("result");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            ListBean bean = new ListBean();
                            bean.setName(object.getString("Name"));
                            bean.setId(object.getString("M_id"));
                            bean.setPassbook(object.getString("PassbookNo"));
                            bean.setShopname(object.getString("WorkshopeName"));
                            bean.setAddress(object.getString("AddOnId"));
                            bean.setCity(object.getString("City"));
                            bean.setPhone(object.getString("Phone"));
                            bean.setPoints(object.getString("points"));
                            arrayList.add(bean);
                        }
                    }
                    Log.e("array list007", arrayList.size() + "");
                } catch (JSONException e) {
                    Log.e("James Bond 007", e + "");
                }

                filterBc(arrayList);
            }

            @Override
            public void onError(ANError anError) {
                hideProgressDialog();
                Log.e("Error", anError.toString());
            }
        });
    }

    public void fetchListR() {
        showProgressDialog();
        AndroidNetworking.initialize(context);
        http://dartmic.com/lumanRLP/apiAH/ret_list.php
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiAH/ret_list.php")
                .addBodyParameter("id", MyPref.getAh(context))
                .setTag("As")
                .setPriority(Priority.HIGH)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                try {
                    if (response.getInt("status") == 2) {
                        JSONArray array = response.getJSONArray("result");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            ListBean bean = new ListBean();
                            bean.setName(object.getString("name"));
                            bean.setId(object.getString("r_id"));
                            bean.setR_pts(object.getString("points"));
                            bean.setPassbook(object.getString("passbook"));
                            bean.setShopname(object.getString("shop_name"));
                            bean.setAddress(object.getString("address"));
                            bean.setCity(object.getString("city"));
                            bean.setPhone(object.getString("phone"));
                            arrayList.add(bean);
                        }
                    }
                    Log.e("array list007", arrayList.size() + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgressDialog();
                }

                filterBc(arrayList);

            }

            @Override
            public void onError(ANError anError) {
                hideProgressDialog();
                Log.e("Error", anError.toString());
            }
        });
    }

    public void fetchListD() {
        showProgressDialog();
        AndroidNetworking.initialize(context);

        http://dartmic.com/lumanRLP/apiAH/dist_list.php

        AndroidNetworking.post(FixedData.baseURL + "rlp/apiAH/dist_list.php")
                .addBodyParameter("id",MyPref.getAh(context))
                .setTag("..")
                .setPriority(Priority.HIGH)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                try {
                    if (response.getInt("status") == 2) {
                        JSONArray array = response.getJSONArray("result");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            ListBean bean = new ListBean();
                            bean.setName(object.getString("name"));
                            bean.setId(object.getString("d_id"));
                            bean.setEmail(object.getString("lgn_id"));
                            bean.setPhone(object.getString("d_num"));
                            arrayList.add(bean);
                        }
                    }
                    Log.e("array list007", arrayList.size() + "");
                } catch (JSONException e) {
                    hideProgressDialog();
                    e.printStackTrace();
                }

                filterBc(arrayList);

            }

            @Override
            public void onError(ANError anError) {
                hideProgressDialog();
                Log.e("Error", anError.toString());
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context, AHDashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

}
