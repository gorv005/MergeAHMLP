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
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.api.SendAhReport;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AHRemarkActivity extends BaseAgBc {

    static int count;
    static String did;
    ArrayList<String> array = new ArrayList();
    Context context;
    EditText converted_counter;
    AutoCompleteTextView et_pb;
    EditText pb;
    ArrayList<String> pbList = new ArrayList();
    EditText red_counter;
    EditText remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahremark);
        this.context = this;
        this.red_counter = (EditText) findViewById(R.id.red_counter);
        this.converted_counter = (EditText) findViewById(R.id.converted_counters);
        this.pb = (EditText) findViewById(R.id.pb);
        this.remark = (EditText) findViewById(R.id.remark);
        did = getIntent().getStringExtra("did");
        this.et_pb = (AutoCompleteTextView) findViewById(R.id.pb_no);
        this.et_pb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                AHRemarkActivity.this.et_pb.showDropDown();
                AHRemarkActivity.this.et_pb.requestFocus();
                return false;
            }
        });

        this.et_pb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) parent.getItemAtPosition(position);
                if (AHRemarkActivity.this.pbList.lastIndexOf(str) == -1) {
                    AHRemarkActivity.this.pbList.add(str);
                    AHRemarkActivity.this.updateList();
                } else {
                    Toast.makeText(AHRemarkActivity.this.context, "Already Added...", Toast.LENGTH_SHORT).show();
                }
                AHRemarkActivity.this.et_pb.setText("");
            }
        });

        this.et_pb.setThreshold(-1);
        findViewById(R.id.minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int view = AHRemarkActivity.this.pbList.size();
                if (view > 0) {
                    AHRemarkActivity.this.pbList.remove(view - 1);
                    AHRemarkActivity.this.updateList();
                }
            }
        });
        getAllPb();
    }

    private void updateList() {
        if (this.pbList.size() > 0) {
            this.pb.setText(this.pbList.toString());
        } else {
            this.pb.setText("");
        }
    }

    private void getAllPb() {
        showProgressDialog();
        AndroidNetworking.initialize(this.context);
        AndroidNetworking.get(FixedData.baseURL + "rlp/api/get_pb_all.php").build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("status") == 1) {
                                AHRemarkActivity.this.array.clear();
                              JSONArray jSONObject = response.getJSONArray("details");
                                for (int i = 0; i < jSONObject.length(); i++) {
                                    AHRemarkActivity.this.array.add(jSONObject.getString(i));
                                }
                                AHRemarkActivity.this.et_pb.setAdapter(new ArrayAdapter(AHRemarkActivity.this.getApplicationContext(), R.layout.dis_list_items, AHRemarkActivity.this.array));
                            }
                        } catch (Exception jSONObject2) {
                            jSONObject2.printStackTrace();
                        }
                        AHRemarkActivity.this.hideProgressDialog();
                    }

                    @Override
                    public void onError(ANError anError) {
                        AHRemarkActivity.this.hideProgressDialog();
                        Log.e("Error", anError.getErrorBody());
                    }
                });
    }

    public void submitRemark(View view) {
        count = 0;
        try {
            count = Integer.parseInt(this.converted_counter.getText().toString().trim());
        } catch (Exception e) {
            Log.e("caskj", e.getMessage());
        }
        if (count != this.pbList.size()) {
            Toast.makeText(this.context, "Add All Red counter converted Passbook", Toast.LENGTH_SHORT).show();
        } else if (this.red_counter.getText().toString().trim().isEmpty()) {
            this.red_counter.setError("Feild can't be blank...");
        } else if (this.converted_counter.getText().toString().trim().isEmpty()) {
            this.converted_counter.setError("Feild can't be blank...");
        } else if (this.pb.getText().toString().trim().isEmpty()) {
            this.pb.setError("Feild can't be blank...");
        } else if (this.remark.getText().toString().trim().isEmpty()) {
            this.remark.setError("Feild can't be blank...");
        } else {
            showProgressDialog();
            SendAhReport.sendReport(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Respo", response.toString());
                    Toast.makeText(AHRemarkActivity.this.context, "Remark Submitted...", Toast.LENGTH_SHORT).show();
                    AHRemarkActivity.this.finish();
                }

                @Override
                public void onError(ANError anError) {
                    Log.e("tad", anError.getErrorBody());
                }
            }, this.context, this.remark.getText().toString(), "retailer", did, this.pb.getText().toString(), this.red_counter.getText().toString(), this.converted_counter.getText().toString());
        }
    }
}
