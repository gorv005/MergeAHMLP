package com.dartmic.mergeahmlp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Adapters.MyAdapter;
import com.dartmic.mergeahmlp.Constants.DataBean;
import com.dartmic.mergeahmlp.Constants.FixedData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetHistoryDist extends BaseAgBc {


    ArrayList<DataBean> data = new ArrayList<>();
    MyAdapter adapter;
    Context context;
    RecyclerView recyclerView;
    static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_history_dist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;


        try {
            Intent intent = getIntent();
            name = intent.getStringExtra("d_id");


        } catch (Exception e) {
            Log.e("", e.toString());
        }


        gettData();
        recyclerView = findViewById(R.id.rv1);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
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
        startActivity(new Intent(context, ListActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
    private void gettData() {
        showProgressDialog();
//        Toast.makeText(context, "list" + name, Toast.LENGTH_SHORT).show();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/api/get_history_distributor.php")

                .addBodyParameter("d_id", name)
                .setTag("history distributor")
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();

                try {
                    if (response.getInt("status") == 1) {

                        JSONArray array = response.getJSONArray("info");
                        for (int i = 0; i < array.length(); i++) {

                            if (i == 0) {

                            }

                            JSONObject object = array.getJSONObject(i);
                            DataBean bean = new DataBean();

                            bean.setyear(object.getString("year"));
                            bean.settotal_pts(object.getString("Sum"));


                            if (object.getString("month").equals("1")) {
                                bean.setMonth("January");
                            } else if (object.getString("month").equals("2")) {
                                bean.setMonth("February");
                            } else if (object.getString("month").equals("3")) {
                                bean.setMonth("March");
                            } else if (object.getString("month").equals("4")) {
                                bean.setMonth("April");
                            } else if (object.getString("month").equals("5")) {
                                bean.setMonth("May");
                            } else if (object.getString("month").equals("6")) {
                                bean.setMonth("June");
                            } else if (object.getString("month").equals("7")) {
                                bean.setMonth("July");
                            } else if (object.getString("month").equals("8")) {
                                bean.setMonth("August");
                            } else if (object.getString("month").equals("9")) {
                                bean.setMonth("September");
                            } else if (object.getString("month").equals("10")) {
                                bean.setMonth("October");
                            } else if (object.getString("month").equals("11")) {
                                bean.setMonth("November");
                            } else if (object.getString("month").equals("12")) {
                                bean.setMonth("December");
                            } else
                                bean.setMonth(object.getString("month"));

                            data.add(bean);

                        }
                    }
                    else Toast.makeText(context, "No Record Found!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("Tag", e.toString());
                }

                setAdaptor(data);
                hideProgressDialog();

            }

            @Override
            public void onError(ANError anError) {

            }
        });

    }


    void setAdaptor(ArrayList<DataBean> Data) {

        adapter = new MyAdapter(context, Data);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

}

