package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.OnlineOperation.Networking;
import com.dartmic.mergeahmlp.SharedPref.MyPref;
import com.dartmic.mergeahmlp.api.CreatMech;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class AddMechanic extends BaseAgBc implements View.OnClickListener {

    EditText et_Mobile, et_Passbook, et_Retailer,et_Name;

    static String distr = "NA";

    private static ArrayList<String> dis_list = new ArrayList<>();
    String mobile, passbook;
    Networking networking;
    Context context;
    AutoCompleteTextView et_Distributor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mechanic);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        networking = new Networking(context);
        initViews();
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

    public void initViews() {
        Log.e("Banks Names", MyPref.storePrefs(context).getBANKS());


        Log.e("DIS Names", MyPref.storePrefs(context).getDIS());
        try {
            JSONArray array = new JSONArray(MyPref.storePrefs(context).getDIS());
            dis_list.clear();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Log.e("D_id", obj.getString("d_id"));
                dis_list.add(obj.getString("name"));
            }
        } catch (JSONException e) {
            Log.e("hahahahahahahah:::::", e.toString());
        }


        et_Mobile = (EditText) findViewById(R.id.et_Mobile);
        et_Passbook = (EditText) findViewById(R.id.et_Passbook);
        et_Name = (EditText) findViewById(R.id.et_Name);

//        et_Passbook.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                Toast.makeText(AddMechanic.this, "Life has been changed..." + b, Toast.LENGTH_SHORT).show();
//            }
//        });


        //et_Retailer = (EditText) findViewById(R.id.et_Retailer);


        findViewById(R.id.btn_Submit1).setOnClickListener(this);

        passbook = et_Passbook.getText().toString();
        mobile = et_Mobile.getText().toString();


        et_Distributor = (AutoCompleteTextView) findViewById(R.id.et_Distributor);
        ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(), R.layout.dis_list_items, dis_list);
        et_Distributor.setAdapter(adapter1);
        et_Distributor.setThreshold(-1);


        et_Distributor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_Distributor.showDropDown();
                et_Distributor.requestFocus();
                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {

        if (!et_Distributor.getText().toString().isEmpty()) {
            int index = dis_list.indexOf(et_Distributor.getText().toString().trim());
            if (index >= 0) {
                try {
                    JSONArray array = new JSONArray(MyPref.storePrefs(context).getDIS());
                    JSONObject obj = array.getJSONObject(index);
                    distr = obj.getString("d_id");
                } catch (JSONException e) {
                    distr = "NA";
                    Log.e("hahahahahah:::::", e.toString());
                }
            }
        }

        if (et_Passbook.getText().toString().isEmpty()) {
            et_Passbook.setError("Fields cannot be blank");

        } else if (et_Name.getText().toString().isEmpty()) {
            et_Name.setError("Fields cannot be blank");

        }else if (distr.equals("NA")) {
            et_Distributor.setError("Select Distributor");

        } else if (!isValidMobile(et_Mobile.getText().toString())) {
            et_Mobile.setError("please enter valid number");

        } else {
            sendData();
        }
    }

    public static boolean isValidMobile(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            if (phoneNumber.toString().contains(" ")
                    || (phoneNumber.charAt(0) == '0')
                    || (!phoneNumber.toString().matches("[0-9]+"))
                    || (phoneNumber.length() != 10)) {
                return false;
            } else {
                return Patterns.PHONE.matcher(phoneNumber).matches();
            }
        }
        return false;
    }

    private void sendSMS(String phone, String sms) {
        String url = "http://www.onextel.in/shn/api/pushsms.php?usr=pawanotp2017&pwd=TSUWQEJUrt5&sndr=LUMANX&ph=" + phone + "&text=" + sms + "&fl=0&gwid=2";
        AndroidNetworking.initialize(context);
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .setTag("...")
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

    void sendData() {
        Bundle b = new Bundle();
        b.putString("PassbookNo", et_Passbook.getText().toString());
        b.putString("et_Mobile", et_Mobile.getText().toString());
        b.putString("et_Name", et_Name.getText().toString());
        b.putString("Dis", distr);
        showProgressDialog();
        CreatMech.newMechanic(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("My Respo:", response + "");
                try {
                    JSONObject respo = new JSONObject(response + "");
                    if (respo.getInt("status") == 1) {
                        Toast.makeText(context, "Mechanic Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, MLPDashboard.class).putExtra("of", respo.getString("mid")));
                        finish();
                    } else {
                        Toast.makeText(context, "Network issue...", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("Send Me", e.getMessage());
                }
                hideProgressDialog();
            }

            @Override
            public void onError(ANError anError) {
                hideProgressDialog();
                Log.e("Send Me", anError.getMessage());
            }
        }, context, b);
    }


}

