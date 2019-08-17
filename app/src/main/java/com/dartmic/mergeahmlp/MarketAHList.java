package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Adapters.MarketListAdapter;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.Constants.MarketPojo;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MarketAHList extends BaseAgBc {

    ListView list_view;
    Context context;
    static String lme;
    static String mkt_id;
    List<MarketPojo> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_ahlist);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
//        lme = getIntent().getStringExtra("lme");

        list_view = (ListView) findViewById(R.id.lvMarket);

        fetchingMarketAHData();

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                mkt_id = data.get(position).getMkt_id();

                Toast.makeText(context, "Beat Plan Started...", Toast.LENGTH_SHORT).show();
                finish();
                sendBeatReport();
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


    private void fetchingMarketAHData() {
        showProgressDialog();
        data.clear();
        AndroidNetworking.initialize(context);
        AndroidNetworking.get(FixedData.baseURL + "rlp/api/get_market.php?id=" + MyPref.getAh(context) + "&role=AH")
                .setTag("LMERemark")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("AA BEEE::::::::::", response.toString());
                        Log.e(" MyPref.getAh", MyPref.getAhId(context));

                        try {
                            if (response.getInt("status") == 1) {
                                JSONArray array = response.getJSONArray("info");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject obj = array.getJSONObject(i);
                                    MarketPojo marketPojo = new MarketPojo();
                                    marketPojo.setMkt_id(obj.getString("mkt_id"));
                                    marketPojo.setMkt_name(obj.getString("mkt_name"));
                                    data.add(marketPojo);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (data.size() > 0) {
                            list_view.setAdapter(new MarketListAdapter(context, R.layout.market_card, data));
                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(ANError anError) {
                        hideProgressDialog();
                        Log.e("Ex", anError + "");
                    }
                });
    }

    private void sendBeatReport() {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/api/beat_report.php")
                .addBodyParameter("p_id", MyPref.getUserId(context))
                .addBodyParameter("role", MyPref.getRole(context))
                .addBodyParameter("mkt_id", mkt_id)
                .addBodyParameter("time_start", "").build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getInt("status") == 1) {
                        MyPref.setBeatIn(context, 101);
                        Toast.makeText(context, "" + MyPref.getRole(context), Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "Report Inserted", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(context, Login.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
        finish();
    }
}