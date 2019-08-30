package com.dartmic.mergeahmlp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.Constants.MechListBean;
import com.dartmic.mergeahmlp.Fragments.FleetListFrag;
import com.dartmic.mergeahmlp.Fragments.HomeFrag;
import com.dartmic.mergeahmlp.Fragments.MechListFrag;
import com.dartmic.mergeahmlp.SharedPref.MyPref;
import com.dartmic.mergeahmlp.Utils.SharedPreference;
import com.dartmic.mergeahmlp.api.BeatSession;
import com.dartmic.mergeahmlp.room.database.AppDatabase;
import com.dartmic.mergeahmlp.room.entity.MechData;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MLPDashboard extends BaseAgBc implements View.OnClickListener {
    ActionBar actionBar;
    public static DrawerLayout drawerLayout;
    static boolean myhm = true;
    FrameLayout frameLayout;
    Fragment profile;
    Fragment home;
    Fragment fleetListFrag;

    Fragment logout;
    Fragment information;
    Fragment about_us, design, Achievement, plants;
    MechListFrag mechanic_list;
    FragmentManager manager;
    Context context;
    ArrayList<MechListBean> arrayList;
    static TextView tv_Mech_Name;
    AlertDialog dialog = null;
    static boolean doubleBackToExit = false;
    List<MechData> mechDataWithZeroPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlpdashboard);
        context = getApplicationContext();
        arrayList = new ArrayList<>();

        initViews();
    }

    public void initViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        TextView tv_Home = (TextView) findViewById(R.id.tv_Home);
        TextView tv_Mechanic_List = (TextView) findViewById(R.id.tv_Mechanic_List);
        TextView tv_Profile = (TextView) findViewById(R.id.tv_Profile);
        TextView tv_Info = (TextView) findViewById(R.id.tv_Info);
        TextView tv_About = (TextView) findViewById(R.id.tv_About);
        TextView tv_Beat = (TextView) findViewById(R.id.tv_Beat);
        TextView tv_Logout = (TextView) findViewById(R.id.tv_Logout);
        tv_Mech_Name = (TextView) findViewById(R.id.tv_Mech_Name);
        Button fleet = findViewById(R.id.tv_Fleet);
        tv_Home.setOnClickListener(this);
        tv_Mechanic_List.setOnClickListener(this);
        tv_Profile.setOnClickListener(this);
        tv_Info.setOnClickListener(this);
        tv_Logout.setOnClickListener(this);
        tv_About.setOnClickListener(this);
        tv_Beat.setOnClickListener(this);
        fleet.setOnClickListener(this);


        findViewById(R.id.tv_SendReport).setOnClickListener(this);
        findViewById(R.id.tv_Design).setOnClickListener(this);
        findViewById(R.id.tv_Verify).setOnClickListener(this);
        findViewById(R.id.tv_Remark).setOnClickListener(this);

        manager = getFragmentManager();
        Log.e("kya hua:::::::::::::", MyPref.storePrefs(context).getMechName());
//     tv_Mech_Name.setText(MyPref.storePrefs(context).getMechName());

        //comment today
//        tv_Mech_Name.setText("Luman MLP");
//        Log.e("MechName::::", (MyPref.storePrefs(context).getMechName()));

//        drawerLayout.setDrawerShadow(R.drawable.et_back_drawable, GravityCompat.START);
        manager = getFragmentManager();

        try {
            if (getIntent().getStringExtra("from").equalsIgnoreCase("0"))
                manager.beginTransaction().replace(R.id.frameLayout, new HomeFrag()).commit();

        } catch (Exception e) {
            home = new HomeFrag();
            manager.beginTransaction().replace(R.id.frameLayout, home).commit();
        }
    }

    @Override
    public void onClick(View view) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        switch (view.getId()) {
            case R.id.tv_Home:
                myhm = true;
                home = new HomeFrag();
                manager.beginTransaction().replace(R.id.frameLayout, home).commit();
                drawerLayout.closeDrawers();
                break;

            case R.id.tv_Mechanic_List:
                myhm = false;
                mechanic_list = new MechListFrag();
                manager.beginTransaction().replace(R.id.frameLayout, mechanic_list).commit();
                drawerLayout.closeDrawers();
                break;

            case R.id.tv_Remark:
                myhm = false;
                startActivity(new Intent(context, ViewRemark.class));
                finish();
                break;
            case R.id.tv_Fleet:
                myhm = false;
                fleetListFrag = new FleetListFrag();
                manager.beginTransaction().replace(R.id.frameLayout, fleetListFrag).commit();
                drawerLayout.closeDrawers();
                break;

            case R.id.tv_Profile:
                myhm = false;
                startActivity(new Intent(context, MProfile.class));
                finish();
                break;

            case R.id.tv_SendReport:
                myhm = false;
                sendReport();
//                drawerLayout.closeDrawers();
                break;

            case R.id.tv_Info:
                startActivity(new Intent(context, MInfoActivity.class));
                finish();
                break;

            case R.id.tv_Beat:
                // Toast.makeText(context, "Under development...", Toast.LENGTH_SHORT).show();
//                myhm = false;
//                Intent intent1=new Intent(context,BeatMLP.class);
//                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                drawerLayout.closeDrawers();
//                startActivity(intent1);
                break;

            case R.id.tv_Verify:
                myhm = false;
                if (MyPref.storePrefs(context).getSelectedStatus()) {
                    startActivity(new Intent(MLPDashboard.this, VerifyActivity.class));
                    finish();
                } else
                    Toast.makeText(context, "Please Select Mechanic...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_About:
                myhm = false;
                startActivity(new Intent(context, MAbout_us.class));
                finish();
                break;

            case R.id.tv_Logout:

                drawerLayout.closeDrawers();
                logoutClick();
                break;
        }
    }

    void  logoutClick(){
        mechDataWithZeroPoints=AppDatabase.getAppDatabase(getApplicationContext()).getMechDataDao().getMechDataWithLessThenRequiredPoints(300,"");
        if(mechDataWithZeroPoints!=null && mechDataWithZeroPoints.size()>0){
            showAlertForMechWithNoPoints(mechDataWithZeroPoints);
        }
        else {
            sendReports();

        }
    }

    void showAlertForMechWithNoPoints(List<MechData> mechData){
        enterRemarks(mechData.get(0));
    }
    private void logout() {

        showProgressDialog();
        BeatSession.getSession(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("status") == 1) {
                        myhm = false;
                        AppDatabase.getAppDatabase(MLPDashboard.this).getMechDataDao().delete();
                        removeLocationUpdates(LocationServices.getFusedLocationProviderClient(getApplicationContext()));
                        MyPref.setmy_mec_id(context, "");
                        MyPref.setMarket_id(context, "");
                        MyPref.storePrefs(context).setIsLoggedIn(false);
                        MyPref.storePrefs(context).setSelectedStatus(false);
                        Intent intent = new Intent(context, AsWhatLogin.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(context, "Error...", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideProgressDialog();
            }

            @Override
            public void onError(ANError anError) {
                hideProgressDialog();
            }
        }, context, MyPref.getUserId(context), "", "1", MyPref.isBeatIn(context),
                SharedPreference.getInstance(context).getString("lat"),
                SharedPreference.getInstance(context).getString("longi")
                );
    }

    private void sendReport() {

        if (MyPref.storePrefs(context).getSelectedStatus()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view1 = getLayoutInflater().inflate(R.layout.remark, null);
            final EditText ahRemark = (EditText) view1.findViewById(R.id.ahRemark);
            final Button remarkSubmit = (Button) view1.findViewById(R.id.btn_Submit);

            remarkSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String URL = FixedData.baseURL + "rlp/apiMLP/mlp_dsr_remark.php";
                    AndroidNetworking.initialize(context);
                    AndroidNetworking.post(URL).
                            addBodyParameter("mech_id", MyPref.storePrefs(context).getLmeId()).
                            addBodyParameter("category", "default").
                            addBodyParameter("comment", ahRemark.getText().toString()).setTag("Report").
                            build().getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            dialog.dismiss();
                            try {
                                if (response.getInt("status") == 1) {
                                    Toast.makeText(context, "Remark submitted successfully", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.e("exeption", e.toString());
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            dialog.dismiss();
                            Toast.makeText(context, "Report sending Failed...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            builder.setView(view1);
            dialog = builder.create();
            dialog.show();
        } else Toast.makeText(context, "Please Select Mechanic", Toast.LENGTH_SHORT).show();
    }

    public static void setname(Context context) {
//        tv_Mech_Name.setText(MyPref.storePrefs(context).getMechName());
        tv_Mech_Name.setText("Luman MLP");
    }

    @Override
    public void onBackPressed() {
        if (myhm) {
            if (doubleBackToExit) {
                finish();
            } else {
                doubleBackToExit = true;
                Toast.makeText(context, "Press again to exit.", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExit = false;
                    }
                }, 2000);
            }
        } else {
            home = new HomeFrag();
            myhm = true;
            manager.beginTransaction().replace(R.id.frameLayout, home).commit();
            drawerLayout.closeDrawers();
        }
    }
    void enterRemarks(final MechData mechData){
      /*  LayoutInflater layoutinflater = LayoutInflater.from(this);
        View promptUserView = layoutinflater.inflate(R.layout.mech_remarks, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(promptUserView);

        final EditText et_remarks = (EditText) promptUserView.findViewById(R.id.et_remarks);
        final TextView title = (TextView) promptUserView.findViewById(R.id.title);

        title.setText("You have got 0 points from "+mechData.getM_name()+" Please enter reason for that.");

        // prompt for username
        alertDialogBuilder.setPositiveButton("Submit",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (et_remarks.getText().toString().length() > 0) {
                   int val= AppDatabase.getAppDatabase(getApplicationContext()).getMechDataDao().updateMechRemarks(mechData.getM_id(),
                            et_remarks.getText().toString());
                       logoutClick();
                    dialog.dismiss();
                }
                else {
                    et_remarks.requestFocus();
                    et_remarks.setError("Please enter your comment");
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // all set and time to build and show up!
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();*/


        LayoutInflater layoutinflater = LayoutInflater.from(MLPDashboard.this);
        View promptUserView = layoutinflater.inflate(R.layout.mech_remarks, null);
        final AlertDialog dialog = new AlertDialog.Builder(MLPDashboard.this)
                .setView(promptUserView)
                .setCancelable(false)
                .setPositiveButton("Submit", null) //Set to null. We override the onclick
                .setNegativeButton("Cancel", null)
                .create();
        final EditText et_remarks = (EditText) promptUserView.findViewById(R.id.et_remarks);
        final TextView title = (TextView) promptUserView.findViewById(R.id.title);
        title.setText("You have got less then 300 points from "+mechData.getM_name()+" Please enter reason for that.");

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow( final DialogInterface dialog) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something

                        if (et_remarks.getText().toString().length() > 0) {
                            int val= AppDatabase.getAppDatabase(getApplicationContext()).getMechDataDao().updateMechRemarks(mechData.getM_id(),
                                    et_remarks.getText().toString());
                            logoutClick();
                            dialog.dismiss();
                        }
                        else {
                            et_remarks.requestFocus();
                            et_remarks.setError("Please enter your comment");
                        }
                    }
                });

            }
        });
        dialog.show();

    }
    private void sendReports(){
        showProgressDialog();

        Gson gson = new Gson();
        String json = gson.toJson(AppDatabase.getAppDatabase(getApplicationContext()).
                getMechDataDao().getMechForReport());

        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMech/user_point_details.php")
                .addBodyParameter("user_id",MyPref.getLmeId())
                .addBodyParameter("market_id",MyPref.getMarket_id(this))
                .addBodyParameter("m_array",json)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            hideProgressDialog();

                            if (response.getInt("status")==1) {
                               Toast.makeText(MLPDashboard.this,"Report sent successfully",Toast.LENGTH_LONG).show();

                            }
                            MyPref.storePrefs(context).setMecId("");
                            logout();
                        } catch (JSONException e) {
                            hideProgressDialog();
                            MyPref.storePrefs(context).setMecId("");
                            logout();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hideProgressDialog();
                        MyPref.storePrefs(context).setMecId("");
                        logout();
                    }
                });
    }

}
