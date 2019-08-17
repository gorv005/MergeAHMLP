package com.dartmic.mergeahmlp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONException;
import org.json.JSONObject;

public class AHLogin extends BaseAgBc implements View.OnClickListener {
    EditText et_Username, et_Pass;
    String name, pass;
    Context context;

    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_LOCATION = 3;
    public static final int REQUEST_CODE_EXTERNAL_STORAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahlogin);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        context = this;

        checkPermission(Manifest.permission.CAMERA);

        initViews();
    }

    public void initViews() {
        et_Username = (EditText) findViewById(R.id.et_Username);
        et_Pass = (EditText) findViewById(R.id.et_Pass);
        findViewById(R.id.btn_Login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        name = et_Username.getText().toString();
        pass = et_Pass.getText().toString();
        gotoNext();
    }

    private void gotoNext() {
        showProgressDialog();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/ah_login.php")
                .addBodyParameter("email", name)
                .addBodyParameter("password", pass)
                .setTag("...")
                .setPriority(Priority.HIGH)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                Log.e("RESPONSE:::", response.toString());
                try {
                    if (response.getInt("status") == 1) {
                        Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();
                        MyPref.setAh_Name(context, response.getJSONObject("info").getString("name"));
                        MyPref.setAhId(context, response.getJSONObject("info").getString("email"));
                        MyPref.setEId(context, response.getJSONObject("info").getString("email_real"));
                        MyPref.setAh(context,response.getJSONObject("info").getString("id"));
                        MyPref.setPhone(context,response.getJSONObject("info").getString("phone"));

                        MyPref.setUserId(context,response.getJSONObject("info").getString("id"));
                        Intent intent = new Intent(context, AHDashboard.class);
                        startActivity(intent);
                        finish();

                    } else
                        Toast.makeText(context, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    hideProgressDialog();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.e("Error::::::::", anError.toString());
                hideProgressDialog();
            }
        });

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
                    Toast.makeText(AHLogin.this, "Allow App to access camera !!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AHLogin.this, "Allow App to access External Storage !!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(AHLogin.this, "Allow App to access location !!", Toast.LENGTH_SHORT).show();
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

}

