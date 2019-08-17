package com.dartmic.mergeahmlp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.dartmic.mergeahmlp.Constants.FixedData.fixedRole;

public class AHDashboard extends BaseAgBc implements View.OnClickListener {

    static String did = "";
    CardView LMEReview;
    CardView NewOther;
    CardView RetailerVisit;
    EditText ahRemark;
    Context context;
    ArrayList<String> d1 = new ArrayList();
    ArrayList<String> d2 = new ArrayList();
    AlertDialog dialog;
    Dialog dialog1;
    AutoCompleteTextView et_dist;
    LayoutInflater inflater = null;
    Button tv_About;
    Button tv_Achieve;
    Button tv_Beat;
    Button tv_Design;
    Button tv_Home;
    Button tv_Info;
    Button tv_Logout;
    Button tv_Mail_Report;
    Button tv_Pass;
    Button tv_Plants;
    Button tv_Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahdashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.context = this;
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        this.et_dist = (AutoCompleteTextView) findViewById(R.id.at_dist);

        this.et_dist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                AHDashboard.this.et_dist.showDropDown();
                AHDashboard.this.et_dist.requestFocus();
                return false;
            }
        });

        this.et_dist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = d1.indexOf((parent.getItemAtPosition(position)));
                if (pos >= 0) {
                    AHDashboard.did = (String) AHDashboard.this.d2.get(pos);
                }
            }
        });
        this.et_dist.setThreshold(-1);
        init();
    }

    void init() {
        this.tv_Home = (Button) findViewById(R.id.tv_Home);
        this.tv_Profile = (Button) findViewById(R.id.tv_Profile);
        this.tv_Info = (Button) findViewById(R.id.tv_Info);
        this.tv_About = (Button) findViewById(R.id.tv_About);
        this.tv_Logout = (Button) findViewById(R.id.tv_Logout);
        this.tv_Pass = (Button) findViewById(R.id.tv_Pass);
        this.tv_Beat = (Button) findViewById(R.id.tv_Beat);
        this.tv_Mail_Report = (Button) findViewById(R.id.tv_Mail_Report);
        this.RetailerVisit = (CardView) findViewById(R.id.RetailerVisit);
        this.NewOther = (CardView) findViewById(R.id.other);
        this.LMEReview = (CardView) findViewById(R.id.lme_review);
        listener();
    }

    public void listener() {
        this.tv_Home.setOnClickListener(this);
        this.tv_Profile.setOnClickListener(this);
        this.tv_Info.setOnClickListener(this);
        this.tv_About.setOnClickListener(this);
        this.tv_Logout.setOnClickListener(this);
        this.tv_Pass.setOnClickListener(this);
        this.tv_Beat.setOnClickListener(this);
        this.RetailerVisit.setOnClickListener(this);
        this.NewOther.setOnClickListener(this);
        this.LMEReview.setOnClickListener(this);
        this.tv_Mail_Report.setOnClickListener(this);
        getAllDist();
    }

    private void getAllDist() {
        showProgressDialog();
        AndroidNetworking.initialize(this.context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/api/get_ah_dist.php").addBodyParameter("ah_id", MyPref.getAh(this.context)).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TY", response.toString());
                        try {
                            if (response.getInt("status") == 1) {
                                JSONArray jSONObject = response.getJSONArray("details");
                                for (int i = 0; i < jSONObject.length(); i++) {
                                    JSONObject jSONObject2 = jSONObject.getJSONObject(i);
                                    AHDashboard.this.d1.add(jSONObject2.getString(DatabaseHelper.COLUMN_NAME));
                                    AHDashboard.this.d2.add(jSONObject2.getString("d_id"));
                                }
                                AHDashboard.this.et_dist.setAdapter(new ArrayAdapter(AHDashboard.this.getApplicationContext(), R.layout.dis_list_items, AHDashboard.this.d1));
                            }
                        } catch (Exception jSONObject3) {
                            jSONObject3.printStackTrace();
                        }
                        AHDashboard.this.hideProgressDialog();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    void close() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RetailerVisit:
                if (did.isEmpty()) {
                    Toast.makeText(this.context, "Select Distributor...", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("CLicked::::::", "MechanicVisit");
                FixedData.fixedRole = "retailer";
                startActivity(new Intent(this.context, AHRemarkActivity.class).putExtra("did", did));
                return;
            case R.id.lme_review:
                if (did.isEmpty()) {
                    Toast.makeText(this.context, "Select Distributor...", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("CLicked::::::", "MechanicVisit");
                FixedData.fixedRole = "retailer";
               Intent view = new Intent(this.context, AhLmeActivity.class);
                view.putExtra("role", "retailer");
                view.putExtra("did", did);
                startActivity(view);
                return;
            case R.id.other:
                if (did.isEmpty()) {
                    Toast.makeText(this.context, "Select Distributor...", Toast.LENGTH_SHORT).show();
                    return;
                }
                FixedData.fixedRole = "other";
                showDialog();
                return;
            case R.id.tv_About:
                startActivity(new Intent(this.context, About_us.class));
                finish();
                return;
            case R.id.tv_Beat:
                Toast.makeText(this.context, "Under development...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_Home:
                break;
            case R.id.tv_Info:
                startActivity(new Intent(this.context, InfoActivity.class));
                finish();
                return;
            case R.id.tv_Logout:
                Intent hi = new Intent(this.context, AsWhatLogin.class);
                hi.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(hi);
                finish();
                return;
            case R.id.tv_Mail_Report:
                sendMail();
                return;
            case R.id.tv_Pass:
                updatePassword();
                return;
            case R.id.tv_Profile:
                startActivity(new Intent(this.context, ProfileActivity.class));
                finish();
                return;
            default:
                return;
        }
        close();
    }

    private void sendMail() {
        http:
//dartmic.com/luman/MLP/send_final_report.php
        close();
        showProgressDialog();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiAH/send_final_report.php")
                .addBodyParameter("id", MyPref.getAh(context))
                .setTag("Asj")
                .setPriority(Priority.HIGH)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.e("RESSSSSSSSSS", response.toString());
                hideProgressDialog();
                Toast.makeText(context, "Report Send...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(ANError anError) {
                hideProgressDialog();
                Log.e("Errorgjhgjhjhjhjh", anError.toString());
            }
        });
    }

//    private void showLogoutDialog() {
//        close();
//        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
//        View v = LayoutInflater.from(context).inflate(R.layout.plz_logout, null);
//        builder.setView(v);
//        builder.setCancelable(false);
//        final android.app.AlertDialog dialog = builder.create();
//
//        v.findViewById(R.id.btn_Cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                close();
//                dialog.dismiss();
//            }
//        });
//
//        v.findViewById(R.id.btn_Ok).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//                Intent intent = new Intent(context, AsWhatLogin.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        dialog.show();
//    }

    void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View v1 = inflater.inflate(R.layout.remark, null);
        ahRemark = (EditText) v1.findViewById(R.id.ahRemark);
        v1.findViewById(R.id.btn_Submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReport();
            }
        });
        builder.setView(v1);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();

    }

    public void sendReport() {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/ah_new_remark.php")
                .addBodyParameter("remark", ahRemark.getText().toString())
                .addBodyParameter("role", fixedRole)
                .addBodyParameter("id", "None")
                .addBodyParameter("reported_by", MyPref.getAh(context))
                .setTag("")
                .setPriority(Priority.HIGH)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("RES", response.toString());
                try {
                    if (response.getInt("status") == 1)
                        Toast.makeText(context, "Sent", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.e("ERROR", anError.toString());
            }
        });
    }

    public void updatePassword() {
        close();
        dialog1 = new Dialog(context);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view1 = getLayoutInflater().inflate(R.layout.change_pass_dialog, null);
        final EditText etOldPassword = (EditText) view1.findViewById(R.id.et_Old_Pass);
        final EditText etNewPassword = (EditText) view1.findViewById(R.id.et_New_Pass);
        final EditText etConfirmPassword = (EditText) view1.findViewById(R.id.et_Con_Pass);
        final Button btProceed = (Button) view1.findViewById(R.id.btn_Sub_Pass);
        btProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FixedData.isValidPass(etOldPassword.getText().toString())) {
                    etOldPassword.setError("Enter valid password");
                } else if (!FixedData.isValidPass(etNewPassword.getText().toString())) {
                    etNewPassword.setError("Enter valid password");
                } else if (etOldPassword.getText().toString().equals(etNewPassword.getText().toString())) {
                    etNewPassword.setError("New Password same as old Password");
                } else if (!etConfirmPassword.getText().toString().equals(etNewPassword.getText().toString())) {
                    etConfirmPassword.setError("Password does not match");
                } else {
                    if (!FixedData.isValidPass(etOldPassword.getText().toString())) {
                        etOldPassword.setError("Enter valid password");
                    } else if (!FixedData.isValidPass(etNewPassword.getText().toString())) {
                        etNewPassword.setError("Enter valid password");
                    } else if (etOldPassword.getText().toString().equals(etNewPassword.getText().toString())) {
                        etNewPassword.setError("New Password same as old Password");
                    } else if (!etConfirmPassword.getText().toString().equals(etNewPassword.getText().toString())) {
                        etConfirmPassword.setError("Password does not match");
                    } else {
                        showProgressDialog();
                        AndroidNetworking.initialize(context);
                        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/changeAhPass.php")
                                .addBodyParameter("opass", etOldPassword.getText().toString())
                                .addBodyParameter("npass", etNewPassword.getText().toString())
                                .addBodyParameter("id", MyPref.getAh(context))
                                .setTag("ppp")
                                .setPriority(Priority.HIGH)
                                .build().getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                hideProgressDialog();
                                Log.e("repo", response.toString());
                                try {
                                    dialog1.dismiss();
                                    Toast.makeText(context, response.getString("msg") + "", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    Toast.makeText(context, "Error!!!", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                hideProgressDialog();
                            }
                        });
                    }
                }
            }
        });
        builder.setView(view1);
        dialog1 = builder.create();
        dialog1.show();
    }

//    public void changeProfile(final Context context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
//        View v = inflater.inflate(R.layout.profile_layout, null);
//        builder.setView(v);
//        builder.setCancelable(true);
//        dialog = builder.create();
//        dialog.show();
//    }

}
