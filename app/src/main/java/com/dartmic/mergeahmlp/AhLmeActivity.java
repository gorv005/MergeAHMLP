package com.dartmic.mergeahmlp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AhLmeActivity extends BaseAgBc {

    static String did = null;
    static String lmeok = "";
    Context context;
    AutoCompleteTextView et_LME;
    List<String> lme = new ArrayList();
    List<String> lmeId = new ArrayList();
    EditText remark;
    EditText total_counter;
    EditText total_enroll;
    EditText total_mechanic;
    EditText total_point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ah_lme);
        this.context = this;
        this.et_LME = (AutoCompleteTextView) findViewById(R.id.lme_all);
        this.total_mechanic = (EditText) findViewById(R.id.total_mechanic);
        this.total_counter = (EditText) findViewById(R.id.no_couters);
        this.total_point = (EditText) findViewById(R.id.total_points);
        this.total_enroll = (EditText) findViewById(R.id.new_enrollment);
        this.remark = (EditText) findViewById(R.id.remark);
        did = getIntent().getStringExtra("did");

        this.et_LME.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                AhLmeActivity.this.et_LME.showDropDown();
                AhLmeActivity.this.et_LME.requestFocus();
                return false;
            }
        });

        this.et_LME.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int adapterView = AhLmeActivity.this.lme.indexOf((String) parent.getItemAtPosition(position));
                if (adapterView >= 0) {
                    AhLmeActivity.lmeok = (String) AhLmeActivity.this.lmeId.get(adapterView);
                }
            }
        });

        fetchLME();
    }

    private void fetchLME() {
        showProgressDialog();
        this.lme.clear();
        this.lmeId.clear();
        AndroidNetworking.initialize(this.context);
        AndroidNetworking.get(FixedData.baseURL + "rlp/apiMLP/lmelist.php").build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("status") == 1) {
                                JSONArray jSONObject = response.getJSONArray("details");
                                for (int i = 0; i < jSONObject.length(); i++) {
                                    JSONObject jSONObject2 = jSONObject.getJSONObject(i);
                                    AhLmeActivity.this.lme.add(jSONObject2.getString("Lme_name"));
                                    AhLmeActivity.this.lmeId.add(jSONObject2.getString("LME_id"));
                                }
                                AhLmeActivity.this.et_LME.setAdapter(new ArrayAdapter(AhLmeActivity.this.getApplicationContext(), R.layout.dis_list_items, AhLmeActivity.this.lme));
                                AhLmeActivity.this.et_LME.setThreshold(-1);
                            }
                        } catch (Exception jSONObject3) {
                            jSONObject3.printStackTrace();
                        }
                        AhLmeActivity.this.hideProgressDialog();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("er", anError.getErrorBody());
                        AhLmeActivity.this.hideProgressDialog();
                    }
                });
    }

    public void submitRemark(View view) {
        if (lmeok.equals("")) {
            this.et_LME.setError("Select LME");
        } else if (this.total_mechanic.getText().toString().trim().isEmpty()) {
            this.total_mechanic.setError("Field can't be blank...");
        } else if (this.total_counter.getText().toString().trim().isEmpty()) {
            this.total_counter.setError("Field can't be blank...");
        } else if (this.total_point.getText().toString().trim().isEmpty()) {
            this.total_point.setError("Field can't be blank...");
        } else if (this.total_enroll.getText().toString().trim().isEmpty()) {
            this.total_enroll.setError("Field can't be blank...");
        } else if (this.remark.getText().toString().trim().isEmpty()) {
            this.remark.setError("Field can't be blank...");
        } else {
            showProgressDialog();
            AndroidNetworking.initialize(this.context);
            AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/ah_new_remark.php").addBodyParameter("visited_mechanic", this.total_mechanic.getText().toString()).addBodyParameter("no_of_counter", this.total_counter.getText().toString()).addBodyParameter("total_points", this.total_point.getText().toString()).addBodyParameter("new_enroll", this.total_enroll.getText().toString()).addBodyParameter("remark", this.remark.getText().toString()).addBodyParameter("by_id", MyPref.getAh(this.context)).addBodyParameter("lme", lmeok).addBodyParameter("role", "lme").addBodyParameter("did", did).setTag((Object) "j").setPriority(Priority.HIGH).build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(AhLmeActivity.this.context, "Remark Submitted...", Toast.LENGTH_SHORT).show();
                            AhLmeActivity.this.finish();
                            AhLmeActivity.this.hideProgressDialog();
                        }

                        @Override
                        public void onError(ANError anError) {
                            Log.e("Ts", anError.getErrorBody());
                            AhLmeActivity.this.hideProgressDialog();
                        }
                    });
        }
    }
}
