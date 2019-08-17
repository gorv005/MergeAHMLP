package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONObject;

public class VerifyActivity extends BaseAgBc {

    Context context;
    ImageView img;
    EditText ob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        img = findViewById(R.id.img_ID);
        ob = findViewById(R.id.et_OpeningBalance);
        getDetails();
    }

    private void getDetails() {
        showProgressDialog();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/passbookdet.php")
                .addBodyParameter("passbookno", MyPref.storePrefs(context).getPassbook())
                .setTag("f")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                try {
                    if (response.getInt("status") == 1) {
                        img.setImageBitmap(FixedData.encodeString2Image(response.getString("bill_picture_record")));
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ANError anError) {
                hideProgressDialog();
                Toast.makeText(context, "" + anError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void verifyOb(View view) {
        if (ob.getText().toString().isEmpty())
            Toast.makeText(context, "Enter Opening balance.", Toast.LENGTH_SHORT).show();
        else {
            showProgressDialog();
            AndroidNetworking.initialize(context);
            AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/updatebal.php")
                    .addBodyParameter("passbookno", MyPref.storePrefs(context).getPassbook())
                    .addBodyParameter("obal", ob.getText().toString())
                    .setTag("OB")
                    .build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    hideProgressDialog();
                    try {
                        if (response.getInt("status") == 1) {

                            String url = "Hi " + MyPref.storePrefs(context).getMechName() + ",\n" +
                                    "\n" + "Thank you for joining LUMAN SABKI JEET MECHANIC LOYALTY PROGRAM." + "\n" +
                                    "You new opening balance is:" +(ob.getText().toString() ) + "\n" +
                                    "You will get update once it will be verified." + "\n" + "Team LUMAN";


                            try {
                                url = java.net.URLEncoder.encode(url, "UTF-8");
                            } catch (Exception e) {
                            }

                            sendSMS(MyPref.storePrefs(context).getMOBILE(), url);

                            MyPref.storePrefs(context).setPoints(ob.getText().toString());
                            FixedData.lastUpdate = ob.getText().toString();
                            Toast.makeText(context, "Opening Balance updated Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MLPDashboard.class);
                            startActivity(intent);
                            finish();
                            onBackPressed();
                        } else
                            Toast.makeText(context, "Opening balance already exists", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MLPDashboard.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onError(ANError anError) {
                    hideProgressDialog();
                }
            });
        }
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

    private void sendSMS(String phone, String sms) {
        String url = "http://www.onextel.in/shn/api/pushsms.php?usr=pawanotp2017&pwd=TSUWQEJUrt5&sndr=LUMANX&ph=" + phone + "&text=" + sms + "&fl=0&gwid=2";
        AndroidNetworking.initialize(context);
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .setTag("...")
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

}
