package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dartmic.mergeahmlp.OnlineOperation.Networking;
import com.dartmic.mergeahmlp.SharedPref.MyPref;
import com.google.android.gms.location.LocationServices;

public class welcome extends BaseAgBc {

    ImageView iv_Luman;
    Context context;
    Networking networking;
    Handler handler;
    AlertDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        iv_Luman = findViewById(R.id.iv_Luman);
        setContentView(R.layout.activity_welcome);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;

        MyPref.setmy_mec_id(context, "");
        MyPref.setTotaaly(context,"");
        MyPref.storePrefs(context).setMecId("");

        networking = new Networking(context);
        networking.fetchingBankData();
        networking.fetchGiftsData();
        requestLocationUpdates(LocationServices.getFusedLocationProviderClient(getApplicationContext()));
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, AsWhatLogin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }, 3500);
    }

//    private void showLoginInfo() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        View view = getLayoutInflater().inflate(R.layout.session_box, null);
//
//        //((TextView) view.findViewById(R.id.tvInfoTxt)).setText("Distributor : " + MyPref.getDistId(context));
//        ((TextView) view.findViewById(R.id.tvInfo2)).setText("LME Id: " + MyPref.getLmeId());
//
//        view.findViewById(R.id.btn_continue).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, MLPDashboard.class);
//                intent.putExtra("FLAG_ACTIVITY_FROM", "ACTIVITY_LOGIN");
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        view.findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showProgressDialog();
//
//
//
//                BeatSession.getSession(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            if (response.getInt("status") == 1) {
//
//                                Intent intent = new Intent(context, Login.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
//                                finish();
//
//                            } else {
//                                Toast.makeText(context, "Error...", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        hideProgressDialog();
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        hideProgressDialog();
//                        Log.e("Ex", anError.getErrorBody() + "");
//                    }
//                }, context, MyPref.getLmeId(), " ", "1", "";
//            }
//        });
//
//        builder.setView(view);
//        dialog = builder.create();
//        dialog.setCancelable(false);
//        dialog.show();
//    }

}