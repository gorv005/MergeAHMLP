package com.dartmic.mergeahmlp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

public class ProfileActivity extends BaseAgBc  {
    Context context;
    CardView change_pass;
    Dialog dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        init();
    }

    private void init() {
        ((TextView) findViewById(R.id.txt_Id)).setText( MyPref.getAhId(context));
        ((TextView) findViewById(R.id.txt_Name)).setText( MyPref.getAh_Name(context));
        ((TextView) findViewById(R.id.txt_Email)).setText( MyPref.getEId(context));
        ((TextView) findViewById(R.id.txt_Phone)).setText( MyPref.getPhone(context));
        change_pass=findViewById(R.id.change_pass);
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              updatePassword();
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context, AHDashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }


    public void updatePassword() {

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
}
