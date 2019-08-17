package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.Constants.GiftBean;
import com.dartmic.mergeahmlp.OnlineOperation.Networking;
import com.dartmic.mergeahmlp.SharedPref.MyPref;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RedeemRequest extends BaseAgBc {

    EditText et_Remarks, et_Name;
    TextView tv_Points, enter_cash;
    Context context;
    Networking networking;
    Spinner sp_Redeem, spGift;
    RadioGroup rg;
    RadioButton Gift, cash;
    LinearLayout giftLayout;
    List<GiftBean> giftList;
    ArrayList<String> gifts = new ArrayList<>(), selectedGift = new ArrayList<>();
    Gson gson = new Gson();
    Type giftType = new TypeToken<ArrayList<GiftBean>>() {
    }.getType();
    String type = "", item = "", item1 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_redeem_request);
        getSupportActionBar().setTitle(MyPref.storePrefs(context).getMOBILE() + "(" + MyPref.storePrefs(context).getPassbook() + ")");
        networking = new Networking(context);
        giftList = gson.fromJson(MyPref.storePrefs(context).getGIFTS(), giftType);

        et_Name = (EditText) findViewById(R.id.et_Name);
        et_Remarks = (EditText) findViewById(R.id.et_Remarks);
        tv_Points = (TextView) findViewById(R.id.tv_Points);
        enter_cash = (TextView) findViewById(R.id.enter_cash);
        giftLayout = findViewById(R.id.ll);
        rg = findViewById(R.id.rg);
        Gift = findViewById(R.id.Gift);
        cash = findViewById(R.id.cash);


        tv_Points.setText(FixedData.lastUpdate);
        et_Name.setText(MyPref.storePrefs(context).getMOBILE());
        findViewById(R.id.btn_Submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRedeemReq();
            }
        });
        init();
    }

    void init() {
        Log.e("All Gifts", MyPref.storePrefs(context).getGIFTS());
        try {
            JSONArray array = new JSONArray(MyPref.storePrefs(context).getGIFTS());
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Log.e("GIFTSSSSSSSSSS", obj.getString("points"));
                gifts.add(obj.getString("points"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sp_Redeem = findViewById(R.id.sp_Redeem);
        spGift = findViewById(R.id.sp_Redeem_name);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.spintext, gifts);
        sp_Redeem.setAdapter(adapter);
        sp_Redeem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGift.clear();
                item = parent.getItemAtPosition(position).toString();
                Toast.makeText(context, "" + item, Toast.LENGTH_SHORT).show();
                for (int i = 0; i < giftList.size(); i++) {
                    if (giftList.get(i).getPoints().equals(item)) {
                        selectedGift.add("Please Select Gift");
                        selectedGift.add(giftList.get(i).getOption1());
                        selectedGift.add(giftList.get(i).getOption2());
                        selectedGift.add(giftList.get(i).getOption3());

//                        Toast.makeText(context, giftList.get(i).getOption1(), Toast.LENGTH_SHORT).show();
                    }
                }

                ArrayAdapter adap = new ArrayAdapter(getApplicationContext(), R.layout.spintext, selectedGift);
                spGift.setAdapter(adap);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spGift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item1 = parent.getItemAtPosition(position).toString();
                Toast.makeText(context, "" + item1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (rg.getCheckedRadioButtonId()) {
                    case R.id.cash:
                        giftLayout.setVisibility(View.GONE);
                        enter_cash.setVisibility(View.VISIBLE);
                        break;
                    case R.id.Gift:
                        giftLayout.setVisibility(View.VISIBLE);
                        enter_cash.setVisibility(View.GONE);
                        break;
                }
            }
        });

    }


    public void doRedeemReq() {
        showProgressDialog();
        AndroidNetworking.initialize(this);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/Redeemerequest.php")
                .addBodyParameter("m_id", MyPref.storePrefs(context).getMecId())
                .addBodyParameter("redeeme_points", "10")
                .addBodyParameter("remark", et_Remarks.getText().toString())
                .setPriority(Priority.MEDIUM)
                .setTag("As")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();

                try {
                    if (response.getInt("status") == 1) {
                        Log.e("final points::::", response.getString("points"));
                        FixedData.lastUpdate = response.getString("points");
                        Toast.makeText(context, "Redeem Successfully!!!!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Dashboard.class);
                        startActivity(intent);
                        finish();
                    } else if (response.getInt("status") == 2) {
                        Toast.makeText(context, "mlp points low than your request", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    hideProgressDialog();
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                hideProgressDialog();
//                Toast.makeText(context, "Error" + anError, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context, MLPDashboard.class));
        finish();
    }

}
