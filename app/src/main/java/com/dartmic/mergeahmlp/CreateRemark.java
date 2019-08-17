package com.dartmic.mergeahmlp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONObject;

public class CreateRemark extends BaseAgBc {

    EditText remark;
    EditText total_counter;
    EditText total_enroll;
    EditText total_mechanic;
    EditText total_point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_remark);

        total_mechanic = (EditText) findViewById(R.id.total_mechanic);
        total_counter = (EditText) findViewById(R.id.no_couters);
        total_point = (EditText) findViewById(R.id.total_points);
        total_enroll = (EditText) findViewById(R.id.new_enrollment);
        remark = (EditText) findViewById(R.id.remark);
    }

    public void submitRemark(View view) {

        if (total_mechanic.getText().toString().trim().isEmpty()) {
            total_mechanic.setError("Feild can't be blank...");
        } else if (total_counter.getText().toString().trim().isEmpty()) {
            total_counter.setError("Feild can't be blank...");
        } else if (total_point.getText().toString().trim().isEmpty()) {
            total_point.setError("Feild can't be blank...");
        } else if (total_enroll.getText().toString().trim().isEmpty()) {
            total_enroll.setError("Feild can't be blank...");
        } else if (remark.getText().toString().trim().isEmpty()) {
            remark.setError("Feild can't be blank...");
        } else {
            showProgressDialog();
            AndroidNetworking.initialize(this);
            AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/add_lme_remark.php").addBodyParameter("by_id", MyPref.getLmeId()).addBodyParameter("visited_mechanic", this.total_mechanic.getText().toString()).addBodyParameter("no_of_counter", this.total_counter.getText().toString()).addBodyParameter("total_points", this.total_point.getText().toString()).addBodyParameter("new_enroll", this.total_enroll.getText().toString()).addBodyParameter("remark", this.remark.getText().toString()).setPriority(Priority.MEDIUM).build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getInt("status") == 1) {
                                    Toast.makeText(CreateRemark.this, "Remark Submitted...", Toast.LENGTH_SHORT).show();
                                    CreateRemark.this.startActivity(new Intent(CreateRemark.this, ViewRemark.class));
                                    CreateRemark.this.finish();
                                } else {
                                    Toast.makeText(CreateRemark.this, "Error...", Toast.LENGTH_SHORT ).show();
                                }
                            } catch (Exception jSONObject2) {
                                jSONObject2.printStackTrace();
                            }
                            CreateRemark.this.hideProgressDialog();
                        }

                        @Override
                        public void onError(ANError anError) {
                            CreateRemark.this.hideProgressDialog();
                            Log.e("jj", anError.getErrorBody());
                        }
                    });
        }
    }
}
