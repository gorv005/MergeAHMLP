package com.dartmic.mergeahmlp;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.OnlineOperation.Networking;
import com.dartmic.mergeahmlp.SharedPref.MyPref;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends BaseAgBc implements View.OnClickListener {
    ActionBar actionBar;
    EditText et_Username, et_Pass;
    Context context;
    Dialog dialog;

    Networking networking;
    String name1, pass;

    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_LOCATION = 3;
    public static final int REQUEST_CODE_EXTERNAL_STORAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        checkPermission(Manifest.permission.CAMERA);
        requestLocationUpdates(LocationServices.getFusedLocationProviderClient(getApplicationContext()));
        networking = new Networking(context);
        setContentView(R.layout.activity_login);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MyPref.storePrefs(context).setSelectedStatus(false);
        actionBar = getSupportActionBar();
        actionBar.hide();

        if (MyPref.storePrefs(context).getIsLoggedIn()) {
            startActivity(new Intent(context, MLPDashboard.class));
            finish();
        } else
            initViews();
    }

    public void initViews() {
        et_Username = (EditText) findViewById(R.id.et_Username);
        et_Pass = (EditText) findViewById(R.id.et_Pass);
        findViewById(R.id.btn_Login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        name1 = et_Username.getText().toString();
        pass = et_Pass.getText().toString();
        if (!Networking.isInternetAvailable(context))
            Toast.makeText(context, "Check Your internet connection!!!", Toast.LENGTH_SHORT).show();
        else {
            if (et_Username.getText().toString().isEmpty()) {
                et_Username.setError("Fields cannot be blank");

            } else if (et_Pass.getText().toString().isEmpty()) {
                et_Pass.setError("Fields cannot be blank");
            } else {
                gotoNext();
            }
        }
    }

    private void gotoNext() {
        showProgressDialog();
        networking.login(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                Log.e("RESPONSE::::", response.toString());
                try {
                    if (response.getInt("status") == 1) {
                        JSONObject obj = response.getJSONObject("detail");
                        String ver = obj.getString("version");
                        if (!ver.equals("24")) {
                            dBox();
                        } else {
                            String mech_num = response.getString("mech_num");
                            FixedData.lastUpdate = "0";
                            String LME_id = obj.getString("LME_id");

                            Log.e("LME_ID::::::", LME_id);
                            String Lme_name = obj.getString("Lme_name");
                            String AHname = obj.getString("Ahs_id");
                            String Email = obj.getString("Email");
                            String D_Id = obj.getString("D_Id");
                            String Password = obj.getString("Password");
                            MyPref.storePrefs(context).setPassword(et_Pass.getText().toString());
                            String name = obj.getString("name");
                            String AHphone = "";
                            MyPref.storePrefs(context).setIsLoggedIn(true);
                            MyPref.storePrefs(context).setAreaHeadAlloted(AHname);
                            MyPref.storePrefs(context).setAreaHeadPhone(AHphone);
                            MyPref.storePrefs(context).setEmail(name1);
                            MyPref.storePrefs(context).setLmeName(Lme_name);
                            MyPref.storePrefs(context).setTotalMechanics(mech_num);

                            MyPref.storePrefs(context).setShopName(name1);
                            MyPref.storePrefs(context).setPoints("0");
                            MyPref.storePrefs(context).setMechName("Luman MLP");
                            MyPref.storePrefs(context).setLmeId(LME_id);
                            networking.fetchDisList();
                            MyPref.storePrefs(context).setUserId(context, LME_id);
                            Toast.makeText(context, "Logged In", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MarketMLPList.class);
                            startActivity(intent);
                            finish();
                        }
                    } else
                        Toast.makeText(context, "Invalid username or password!!!!", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    Log.e("Error", e + "");
                }
            }

            @Override
            public void onError(ANError anError) {

            }
        }, name1, pass);
    }

    private void dBox() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        View view = getLayoutInflater().inflate(R.layout.version_dialog, null);
        final Button bt1month = (Button) view.findViewById(R.id.bt_update);
        bt1month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    dialog.dismiss();
                    finish();
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                    dialog.dismiss();
                    finish();
                }
            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(context, AsWhatLogin.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
        finish();
    }

    public void checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permission.equalsIgnoreCase(Manifest.permission.CAMERA)) {
                if (checkSelfPermission(permission)
                        != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{permission}, REQUEST_CODE_CAMERA);

                    return;
                } else {
                    checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

                }
            } else if (permission.equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (checkSelfPermission(permission)
                        != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{permission}, REQUEST_CODE_CAMERA);

                    return;
                } else {
                    checkPermission(Manifest.permission.ACCESS_FINE_LOCATION);

                }
            } else if (permission.equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (checkSelfPermission(permission)
                        != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{permission}, REQUEST_CODE_LOCATION);

                    return;
                }

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {
                    Toast.makeText(Login.this, "Allow App to access camera !!", Toast.LENGTH_SHORT).show();
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case REQUEST_CODE_EXTERNAL_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {
                    Toast.makeText(Login.this, "Allow App to access External Storage !!", Toast.LENGTH_SHORT).show();
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case REQUEST_CODE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {
                    Toast.makeText(Login.this, "Allow App to access location !!", Toast.LENGTH_SHORT).show();
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

}

