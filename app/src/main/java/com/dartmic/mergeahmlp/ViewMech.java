package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewMech extends BaseAgBc {

    TextView tv_Mech_Name, tv_Gar, tv_Add, tv_City, tv_Pin, tv_Pos, tv_Mob,tv_State,
            tv_IdType, tv_Passbook, tv_Pur, tv_AccType, tv_AccNo, tv_Bnk, tv_Ifsc, tv_PhotoId,tv_Distributor;
    ImageView iv_IdPhoto, iv_Mech_Photo, iv_Photo3, iv_Photo4;

    Button btn_Can, btn_Sub;
    static String fromAll = "";
    Context context;
    static Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mech);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        init();
        Intent i = getIntent();
        b = i.getBundleExtra("detail");
        tv_Mech_Name.setText(b.getString("Name"));
        tv_Gar.setText(b.getString("WorkshopeName"));
        tv_Add.setText(b.getString("Address"));
        tv_City.setText(b.getString("Distt"));
        tv_Pin.setText(b.getString("Pincode"));
        tv_Pos.setText(b.getString("AddOnId"));
        tv_Mob.setText(b.getString("Phone"));
        tv_Distributor.setText(b.getString("Dis"));
        tv_State.setText(b.getString("State"));
//        tv_IdType.setText(b.getString("idtype"));
        tv_Passbook.setText(b.getString("PassbookNo"));
        tv_AccType.setText(b.getString("Acc_Type"));
        tv_AccNo.setText(b.getString("Acc_No"));
        tv_Bnk.setText(b.getString("Bank_name"));
        tv_Ifsc.setText(b.getString("Ifsc"));
        tv_PhotoId.setText(b.getString("idtype"));


        btn_Can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_Sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mechOnline();
            }
        });
    }

    void mechOnline() {
        showProgressDialog();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/addmech.php")
                .setPriority(Priority.HIGH)
                .setTag("AJA")
                .addBodyParameter("LME_Id", MyPref.storePrefs(context).getLmeId())
                .addBodyParameter("PassbookNo", b.getString("PassbookNo"))
                .addBodyParameter("Name", b.getString("Name"))
                .addBodyParameter("WorkshopeName", b.getString("WorkshopeName"))
                .addBodyParameter("Address", b.getString("Address"))
                .addBodyParameter("Country", b.getString("Country"))
                .addBodyParameter("State", b.getString("State"))
                .addBodyParameter("Distt", b.getString("Distt"))
                .addBodyParameter("Pincode", b.getString("Pincode"))
                .addBodyParameter("Phone", b.getString("Phone"))
                .addBodyParameter("City", b.getString("City"))
                .addBodyParameter("Bank_name", b.getString("Bank_name"))
                .addBodyParameter("d_id", b.getString("Dis"))
                .addBodyParameter("Acc_No", b.getString("Acc_No"))
                .addBodyParameter("Acc_Type", b.getString("Acc_Type"))
                .addBodyParameter("Ifsc", b.getString("Ifsc"))
                .addBodyParameter("AddOnId", b.getString("AddOnId"))
                .addBodyParameter("idtype", b.getString("idtype"))
                .addBodyParameter("BuyFrmRetail", b.getString("BuyFrmRetail"))
                .addBodyParameter("id_picture", b.getString("id_picture"))
                .addBodyParameter("bill_picture", b.getString("bill_picture"))
                .addBodyParameter("Picture", b.getString("Picture"))
                .addBodyParameter("mrp_pict", b.getString("mrp_pict"))
                .doNotCacheResponse()
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.e("My Respo:", response);
                try {
                    JSONObject respo = new JSONObject(response);
                    if (respo.getInt("status") == 1) {
                        hideProgressDialog();
                        String url = "Hi " + b.getString("Name") + ",\n" +
                                "\n" +
                                "You had successfully registered yourself in LUMAN SBKI JEET MECHANIC LOYALTY PROGRAM.\n" +
                                "\n" +
                                "Your registered LME is :  " + MyPref.storePrefs(context).getLmeName() + "\n" +
                                "Your Passbook Num is : " + b.getString("PassbookNo") + "\n" +
                                "Shop name  is :" + b.getString("WorkshopeName");

                        try {
                            url = java.net.URLEncoder.encode(url, "UTF-8");
                        } catch (Exception e) {
                        }
                        sendSMS(b.getString("Phone"), url);

                        Toast.makeText(context, "" + "Mechanic Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, MLPDashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                    }
                } catch (Exception e) {
                    Log.e("MyRespo:", e.toString());
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.e("MyRespo2:", anError.toString());
            }
        });

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

    void init() {

        tv_Mech_Name = findViewById(R.id.tv_Mech_Name);
        tv_Gar = findViewById(R.id.tv_Gar);
        tv_Add = findViewById(R.id.tv_Add);
        tv_City = findViewById(R.id.tv_City);
        tv_Pin = findViewById(R.id.tv_Pin);
        tv_Pos = findViewById(R.id.tv_Pos);
        tv_Mob = findViewById(R.id.tv_Mob);
        tv_State = findViewById(R.id.tv_State);
        tv_Distributor = findViewById(R.id.tv_Distributor);
//        tv_IdType = findViewById(R.id.tv_IdType);
        tv_Passbook = findViewById(R.id.tv_Passbook);
        tv_Pur = findViewById(R.id.tv_Pur);
        tv_AccType = findViewById(R.id.tv_AccType);
        tv_AccNo = findViewById(R.id.tv_AccNo);
        tv_Bnk = findViewById(R.id.tv_Bnk);
        tv_Ifsc = findViewById(R.id.tv_Ifsc);
        tv_PhotoId = findViewById(R.id.tv_PhotoId);
        btn_Can = findViewById(R.id.btn_Can);
        btn_Sub = findViewById(R.id.btn_Sub);
        iv_Mech_Photo = findViewById(R.id.iv_Mech_Photo);
        iv_IdPhoto = findViewById(R.id.iv_IdPhoto);

    }
}

