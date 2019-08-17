package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Adapters.RemarkListAdapter;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewRemark extends BaseAgBc {

    ListView list_view;
    Context context;
    List<RemarkPojo> data = new ArrayList();
    static int checking = 0;
    AlertDialog dialog = null;
    static String addo = "", lat = "NOT FOUND", lang = "NOT FOUND";
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_remark);
        context = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyPref.getLmeId() != null) {
                    ViewRemark.this.startActivity(new Intent(ViewRemark.this.context, CreateRemark.class));
                    ViewRemark.this.finish();

                } else {
                    Toast.makeText(context, "Select Mechanic...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        list_view = (ListView) findViewById(R.id.lvRemark);
        fetchingData();

    }

    private void fetchingData() {
        showProgressDialog();
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/lme_remark.php").setPriority(Priority.MEDIUM).addBodyParameter("by_id", MyPref.getLmeId()).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        data.clear();
                        Log.e("Respo", response.toString());
                        try {
                            if (response.getString("status").equals("1")) {
                                JSONArray jSONArray = response.getJSONArray("details");
                                for (int i = 0; i < jSONArray.length(); i++) {
                                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                    RemarkPojo remarkPojo = new RemarkPojo();
                                    remarkPojo.setDate(jSONObject2.getString("date"));
                                    remarkPojo.setNew_enroll(jSONObject2.getString("new_enroll"));
                                    remarkPojo.setNo_of_counter(jSONObject2.getString("no_of_counter"));
                                    remarkPojo.setTotal_points(jSONObject2.getString("total_points"));
                                    remarkPojo.setRemark(jSONObject2.getString("remark"));
                                    remarkPojo.setVisited_mechanic(jSONObject2.getString("visited_mechanic"));
                                    ViewRemark.this.data.add(remarkPojo);
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Ex", e.toString());
                        }
                        if (data.size() > 0) {
                            ViewRemark.this.list_view.setAdapter(new RemarkListAdapter(ViewRemark.this.context, R.layout.remark_card, ViewRemark.this.data));
                        }
                        ViewRemark.this.hideProgressDialog();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }


}
