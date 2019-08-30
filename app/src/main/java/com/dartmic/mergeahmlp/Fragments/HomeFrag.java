package com.dartmic.mergeahmlp.Fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.Coupon;
import com.dartmic.mergeahmlp.MLPDashboard;
import com.dartmic.mergeahmlp.R;
import com.dartmic.mergeahmlp.RedeemHistory;
import com.dartmic.mergeahmlp.RedeemRequest;
import com.dartmic.mergeahmlp.ScanQRCode;
import com.dartmic.mergeahmlp.SharedPref.MyPref;
import com.dartmic.mergeahmlp.edit_mech;
import com.dartmic.mergeahmlp.room.database.AppDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFrag extends Fragment {
    TextView tv_Mlp_Points,tv_today_Mlp_Points;
    Button tv_Coupon_History;
    TextView tv_Navigation;
    Context context;
    boolean flag = true;
    ImageView iv_QrCode, img_menu, iv_Redeem, iv_Redeem_His,iv_qr_number,iv_qr_points;
    String updatedPoints = "0";
    Button btn_Finalle;
    static String allFinal;
    static String url = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_frag, container, false);
        updatedPoints = "0";
        context = getActivity();
        tv_Mlp_Points = (TextView) v.findViewById(R.id.tv_Mlp_Points);
        totalPoints();

        initializeViews(v);
        Log.e("UP Points>>>>>>", updatedPoints);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.iv_QrCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyPref.storePrefs(context).getSelectedStatus()) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED &&
                            ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(context, ScanQRCode.class);
                        startActivity(intent);
                      //  getActivity().finish();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
                    }
                } else Toast.makeText(context, "Please Select Mechanic", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initializeViews(View v) {
//        v.findViewById(R.id.keepedit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (MyPref.getUserId(context) == "" || MyPref.getUserId(context) == null || MyPref.getUserId(context) == "1") {
//                    Toast.makeText(context, "value = "+MyPref.getUserId(context), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(context, "Please Select Mechanic...", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    startActivity(new Intent(context, edit_mech.class));
//                    getActivity().finish();
//                }
//            }
//        });


        iv_QrCode = (ImageView) v.findViewById(R.id.iv_QrCode);
        img_menu = (ImageView) v.findViewById(R.id.img_menu);
        iv_Redeem = (ImageView) v.findViewById(R.id.iv_Redeem);
        btn_Finalle = (Button) v.findViewById(R.id.btn_Finalle);
        iv_Redeem_His = (ImageView) v.findViewById(R.id.iv_Redeem_His);
        iv_qr_number = (ImageView) v.findViewById(R.id.iv_qr_number);
        iv_qr_points = (ImageView) v.findViewById(R.id.iv_qr_points);

        tv_Navigation = (TextView) v.findViewById(R.id.tv_Navigation);
        tv_today_Mlp_Points = (TextView) v.findViewById(R.id.tv_today_Mlp_Points);

        tv_Navigation.setText(MyPref.storePrefs(context).getEmail());
        if (MyPref.storePrefs(context).getSelectedStatus())
            tv_Navigation.setText(MyPref.storePrefs(context).getMechName() + "(" + MyPref.storePrefs(context).getPassbook() + ")");

        tv_Coupon_History = (Button) v.findViewById(R.id.tv_Coupon_History);


        Log.e("KEY::::::::::", MyPref.storePrefs(context).getPoints().trim());
//        tv_Mlp_Points.setText(MyPref.storePrefs(context).getPoints().trim());

//
//        if (FixedData.lastUpdate.contains("null") || FixedData.lastUpdate.isEmpty() ){
//            tv_Mlp_Points.setText("0");
//
//        } else {
//            tv_Mlp_Points.setText(FixedData.lastUpdate);
//        }


//        tv_Mlp_Points.setText(FixedData.lastUpdate);

        if (FixedData.ifyes == 1) {
            FixedData.ifyes = 0;
            v.findViewById(R.id.btn_Finalle).setVisibility(View.VISIBLE);
        } else {
            v.findViewById(R.id.btn_Finalle).setVisibility(View.GONE);
        }


        v.findViewById(R.id.tv_Navigation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        v.findViewById(R.id.img_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MLPDashboard.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        v.findViewById(R.id.tv_Coupon_History).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyPref.storePrefs(context).getSelectedStatus()) {
                    Intent intent = new Intent(getActivity(), Coupon.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                } else
                    Toast.makeText(context, "Please Select Mechanic", Toast.LENGTH_SHORT).show();
            }
        });
        v.findViewById(R.id.iv_Redeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyPref.storePrefs(context).getSelectedStatus()) {
                    Intent intent = new Intent(getActivity(), RedeemRequest.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                } else Toast.makeText(context, "Please Select Mechanic", Toast.LENGTH_SHORT).show();
            }
        });
        v.findViewById(R.id.iv_Redeem_His).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyPref.storePrefs(context).getSelectedStatus()) {
                    Intent intent = new Intent(getActivity(), RedeemHistory.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                } else Toast.makeText(context, "Please Select Mechanic", Toast.LENGTH_SHORT).show();
            }
        });
        v.findViewById(R.id.btn_Finalle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Clicked::::", "CLICKED ::::");
                finalle(context);
//                btn_Finalle.setText(allFinal);
            }
        });

        v.findViewById(R.id.iv_qr_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyPref.storePrefs(context).getSelectedStatus()) {
                   enterQRNumberDialog();
                } else Toast.makeText(context, "Please Select Mechanic", Toast.LENGTH_SHORT).show();
            }
        });
        v.findViewById(R.id.iv_qr_points).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyPref.storePrefs(context).getSelectedStatus()) {
                    enterQRPointsDialog();
                } else Toast.makeText(context, "Please Select Mechanic", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void finalle(final Context context) {
        ((MLPDashboard) context).showProgressDialog();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/gettotal.php")
                .addBodyParameter("m_id", MyPref.storePrefs(context).getMecId())
                .setPriority(Priority.MEDIUM)
                .setTag("faad")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("FinalRes:::", response.toString());
                try {
                    if (response.getInt("status") == 1) {
                        allFinal = response.getString("msg");
                        Log.e("allFinal<><><><><><><>", allFinal);
                        btn_Finalle.setVisibility(View.GONE);

                        url = "Hi " + MyPref.storePrefs(context).getMechName() + ",\n" +
                                "\n" + "You had added " + FixedData.po + " points successfully." + "\n" +
                                "\n" +
                                "Your new total MLP point is : " + FixedData.lastUpdate + ".";

                        try {
                            url = java.net.URLEncoder.encode(url, "UTF-8");
                        } catch (Exception e) {

                            Log.e(e + "", url);
                        }

                        sendSMS(MyPref.storePrefs(context).getMOBILE(), url);
                        ((MLPDashboard) context).hideProgressDialog();

                        FixedData.po = 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.e("ANError::::", anError.toString());
            }
        });
    }

    private void sendSMS(String phone, String sms) {
        String url = "http://www.onextel.in/shn/api/pushsms.php?usr=pawanotp2017&pwd=TSUWQEJUrt5&sndr=LUMANX&ph="
                + phone + "&text=" + sms + "&fl=0&gwid=2";

        AndroidNetworking.initialize(context);
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .setTag("...")
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Points Added Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(ANError anError) {
            }
        });
    }


    void enterQRNumberDialog(){
        LayoutInflater layoutinflater = LayoutInflater.from(context);
        View promptUserView = layoutinflater.inflate(R.layout.qr_code_dialog_layout, null);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(promptUserView)
                .setTitle("QR Number")
                .setCancelable(false)
                .setPositiveButton("Submit", null) //Set to null. We override the onclick
                .setNegativeButton("Cancel", null)
                .create();
        final EditText userAnswer = (EditText) promptUserView.findViewById(R.id.et_dr_number);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow( final DialogInterface dialog) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something

                        if (userAnswer.getText().toString().length() > 0) {
                            redeemQRPoints(userAnswer.getText().toString());
                            dialog.dismiss();
                        }
                        else {
                            userAnswer.requestFocus();
                            userAnswer.setError("Please enter your QR number");
                        }
                    }
                });

            }
        });
        dialog.show();
    }
    void enterQRPointsDialog(){
        LayoutInflater layoutinflater = LayoutInflater.from(context);
        View promptUserView = layoutinflater.inflate(R.layout.qr_code_dialog_layout, null);
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(promptUserView)
                .setTitle("QR Points")
                .setCancelable(false)
                .setPositiveButton("Submit", null) //Set to null. We override the onclick
                .setNegativeButton("Cancel", null)
                .create();
        final EditText userAnswer = (EditText) promptUserView.findViewById(R.id.et_dr_number);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow( final DialogInterface dialog) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something

                        if (userAnswer.getText().toString().length() > 0) {
                            redeemQRScanPoints(userAnswer.getText().toString());
                            dialog.dismiss();
                        }
                        else {
                            userAnswer.requestFocus();
                            userAnswer.setError("Please enter your QR number");
                        }
                    }
                });

            }
        });
        dialog.show();
    }

    private void totalPoints(){
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMech/ttl_points.php")
                .addBodyParameter("M_id", MyPref.storePrefs(context).getMecId())
                .setTag("total Points")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("status")==1) {
                                tv_Mlp_Points.setText("" + response.getString("total"));
                                tv_today_Mlp_Points.setText("" + response.getString("todays_point"));
                                if (MyPref.storePrefs(context).getSelectedStatus()) {
                                    AppDatabase.getAppDatabase(getActivity().getApplicationContext()).getMechDataDao().updateMechPoints(MyPref.storePrefs(context).getMecId(),  response.getString("total"),response.getInt("todays_point"));
                                }
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


    public void redeemQRPoints(String qrNumber) {
        ((MLPDashboard)getActivity()).showProgressDialog();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMech/post_qrcode3.php")
                .addBodyParameter("m_id", MyPref.storePrefs(context).getMecId())
                .addBodyParameter("L_part_num", qrNumber)
                .addBodyParameter("scan_pts", "0")
                .setPriority(Priority.MEDIUM)
                .setTag("As")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                ((MLPDashboard)getActivity()).hideProgressDialog();
                Log.e("aja re RES:::::::::::", response.toString());
                try {
                    if (response.getInt("status") == 1) {

                        totalPoints();

                    }
                   else if (response.getInt("status") == -1) {

                        Toast.makeText(context, response.getString("msg"), Toast.LENGTH_SHORT).show();

                    }
                    else if (response.getInt("status") == 0) {
                        Toast.makeText(context, "QR Code already used!!!", Toast.LENGTH_SHORT).show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                ((MLPDashboard)getActivity()).hideProgressDialog();
            }
        });
    }
    public void redeemQRScanPoints(String points) {
        ((MLPDashboard)getActivity()).showProgressDialog();
        AndroidNetworking.initialize(context);
        String s=MyPref.storePrefs(context).getMecId();
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMech/post_qrcode3.php")
                .addBodyParameter("m_id", s)
                .addBodyParameter("L_part_num", "")
                .addBodyParameter("scan_pts", points)
                .setPriority(Priority.MEDIUM)
                .setTag("As")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                ((MLPDashboard)getActivity()).hideProgressDialog();
                Log.e("aja re RES:::::::::::", response.toString());
                try {
                    if (response.getInt("status") == 1) {

                        totalPoints();

                    }
                    else if (response.getInt("status") == -1) {

                        Toast.makeText(context, response.getString("msg"), Toast.LENGTH_SHORT).show();

                    }
                    else if (response.getInt("status") == 0) {
                        Toast.makeText(context, "QR Code already used!!!", Toast.LENGTH_SHORT).show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                ((MLPDashboard)getActivity()).hideProgressDialog();
            }
        });
    }

}