package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
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
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MarketMLPList extends BaseAgBc {

    ListView list_view;
    Context context;
    static String lme;
    static String mkt_id;
    List<MarketPojo> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_mlplist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        list_view = (ListView) findViewById(R.id.lvMarket);

        fetchingMarketData();

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (isMyLocationOn()) {


                    if (isNetworkConnected()) {

                        mkt_id = data.get(position).getMkt_id();
                        MyPref.setMarket_id(context, mkt_id);

                        sendBeatReport();


                    } else {

                        Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
//                        long idd = db.startDay(MyPref.getUserId(context), MyPref.getRole(context) + "", MyPref.getMarket_id(context) + "", 0);
//                        startActivity(new Intent(MarketMLPList.this, MLPDashboard.class));
//                        finish();
                    }


                } else {
                    Toast.makeText(context, "Check GPS / LOCATION...", Toast.LENGTH_SHORT).show();
                }
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

    private void fetchingMarketData() {
        showProgressDialog();
        data.clear();
        AndroidNetworking.initialize(context);
        AndroidNetworking.get(FixedData.baseURL + "rlp/api/get_market.php?id=" + MyPref.getLmeId() + "&role=LME")
                .setTag("LMERemark")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("AA BEEE::::::::::", response.toString());

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
                .addBodyParameter("mkt_id", mkt_id).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("status") == 1) {
                        requestLocationUpdates(LocationServices.getFusedLocationProviderClient(getApplicationContext()));
                        Toast.makeText(context, "Beat Started...", Toast.LENGTH_SHORT).show();
                        MyPref.setBeatIn(context, response.getInt("token"));
                        startActivity(new Intent(MarketMLPList.this, MLPDashboard.class));
                        finish();
                    } else
                        Toast.makeText(context, "Report inserted failed...", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

    private boolean isMyLocationOn() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        return gps_enabled;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(context, Login.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
        finish();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}


