package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONException;
import org.json.JSONObject;

public class QRCodePoints extends BaseAgBc {

    TextView tv_Redeem_Coupon;
    Button btnRedeem;
    Context context;
    static String part, point, qrString;
    JSONObject obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_points);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();

        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redeemQRPoints();
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
        startActivity(new Intent(context, Dashboard.class));
        finish();
    }

    public void initializeViews() {
        tv_Redeem_Coupon = (TextView) findViewById(R.id.tv_Redeem_Coupon);
        btnRedeem = (Button) findViewById(R.id.btn_Redeem);
        handleQrCode();
    }

    private void handleQrCode() {

        try {

            qrString = getIntent().getStringExtra("qr");
            if (qrString.contains(",MLP")) {
                qrString = formattedString(qrString);
            }
            obj = new JSONObject(qrString);

            point = obj.getString("mlppoints");
            FixedData.po = FixedData.po + Integer.valueOf(point);
            Log.e("POINTS::::::::::", obj.getString("mlppoints"));
            part = obj.getString("lumanpart");
            tv_Redeem_Coupon.setText(obj.getString("mlppoints"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String formattedString(String qr) {

        qr = qr.replace("{\"", "").replace("\"}", "")
                .replace("\"\"", "_").replace("\",", ",")
                .replace("MLP", "").replace(":", "");
        String[] dt = qr.split(",");
        JSONObject qrda = new JSONObject();
        try {
            qrda.put("lumanpart", dt[0].trim());
            qrda.put("mlppoints", dt[1].trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return qrda.toString();

    }


//    public void redeemQRPoints() {
//        showProgressDialog();
//        AndroidNetworking.initialize(context);
//        AndroidNetworking.post("http://dartmic.com/luman/MLP/point_store.php")
//                .addBodyParameter("m_id", MyPref.storePrefs(context).getMecId())
//                .addBodyParameter("part_number", part)
//                .addBodyParameter("mlp_points", point.replaceAll("”", ""))
//                .setPriority(Priority.MEDIUM)
//                .setTag("As")
//                .build().getAsJSONObject(new JSONObjectRequestListener() {
//            @Override
//            public void onResponse(JSONObject response) {
//                hideProgressDialog();
//                Log.e("aja re RES:::::::::::", response.toString());
//
//                try {
//                    if (response.getInt("status") == 1) {
//                        FixedData.ifyes = 1;
//                        Log.e("Nice", response.getString("points"));
//                        FixedData.lastUpdate = response.getString("points");
//                        startActivity(new Intent(context, Dashboard.class));
//                        finish();
//                    } else if (response.getInt("status") == 0) {
//                        Toast.makeText(context, response.getString("msg"), Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(context, Dashboard.class));
//                        finish();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(ANError anError) {
//                hideProgressDialog();
//                startActivity(new Intent(context, Dashboard.class));
//                finish();
//                Log.e("ERRRROORRR", anError.toString());
//
//            }
//        });
//    }

    public void redeemQRPoints() {
        showProgressDialog();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMech/post_qrcode3.php")
                .addBodyParameter("m_id", MyPref.storePrefs(context).getMecId())
                .addBodyParameter("L_part_num", part)
                .addBodyParameter("scan_pts", point.replaceAll("”", ""))
                .setPriority(Priority.MEDIUM)
                .setTag("As")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                Log.e("aja re RES:::::::::::", response.toString());
                try {
                    if (response.getInt("status") == 1) {

                        startActivity(new Intent(context, MLPDashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }

                    else if (response.getInt("status") == -1) {

                        Toast.makeText(context, response.getString("msg"), Toast.LENGTH_SHORT).show();

                    }
                    else if (response.getInt("status") == 0) {
                        Toast.makeText(context, "QR Code already used!!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, MLPDashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                hideProgressDialog();
                startActivity(new Intent(context, MLPDashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                Log.e("ERRRROORRR", anError.toString());

            }
        });
    }
}

