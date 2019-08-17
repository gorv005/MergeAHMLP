package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.Fragments.HomeFrag;
import com.dartmic.mergeahmlp.OnlineOperation.Networking;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class edit_mech extends BaseAgBc {

    EditText et_Name, et_Address, et_City, et_Shop_Name, et_Pin, et_Mobile, et_Passbook, et_Distributor2,
            et_Postal, et_ID_Type, et_Retailer, et_passw, et_buyfrmR;

    static String distr = "NA";
    private static ArrayList<String> dis_list = new ArrayList<>();
    String name, address, city, shop_Name, pin, mobile, passbook;
    Networking networking;
    Context context;
    AutoCompleteTextView tv_State;

    EditText et_Account, et_IFSC, et_BAccount;
    private static ArrayList<String> banks = new ArrayList<>();
    RadioGroup radioGroup;
    RadioButton rb_Current, rb_Saving;
    String type = "";
    AutoCompleteTextView et_Bank;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mech);
        context = this;

        getSupportActionBar().setTitle(""+MyPref.storePrefs(context).getMechName());

        hello();


//
//
//
//
//        Log.e("Banks Names", MyPref.storePrefs(context).getBANKS());
//        Log.e("DIS Names", MyPref.storePrefs(context).getDIS());
//        try {
//            JSONArray array = new JSONArray(MyPref.storePrefs(context).getDIS());
//            dis_list.clear();
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject obj = array.getJSONObject(i);
//                Log.e("D_id", obj.getString("d_id"));
//                dis_list.add(obj.getString("name"));
//            }
//        } catch (JSONException e) {
//            Log.e("hahahahahahahah:::::", e.toString());
//        }
        et_BAccount = (EditText) findViewById(R.id.et_BAccount2);
        rb_Saving = findViewById(R.id.rb_Saving2);
        rb_Current = findViewById(R.id.rb_Current2);
        radioGroup = findViewById(R.id.radioGroup2);

        type = "10-Saving Account";
        et_BAccount.setText("" + type);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (rb_Current.isChecked()) {
                    type = "Current Account";
                    et_BAccount.setText("" + type);

                } else {
                    type = "10-Saving Account";
                    et_BAccount.setText("" + type);
                }
            }
        });


       // et_passw = findViewById(R.id.et_passw);
        et_Name = (EditText) findViewById(R.id.et_Name2);
        et_Address = (EditText) findViewById(R.id.et_Address2);
        et_City = (EditText) findViewById(R.id.et_City2);
        et_Shop_Name = (EditText) findViewById(R.id.et_Shop_Name2);
        et_Pin = (EditText) findViewById(R.id.et_Pin2);
        et_Mobile = (EditText) findViewById(R.id.et_Mobile2);
        et_Passbook = (EditText) findViewById(R.id.et_Passbook2);

        et_Postal = (EditText) findViewById(R.id.et_Postal2);
        et_ID_Type = (EditText) findViewById(R.id.et_ID_Type2);
        // et_Retailer = (EditText) findViewById(R.id.et_Retailer);


        name = et_Name.getText().toString();
        address = et_Address.getText().toString();
        city = et_City.getText().toString();
        shop_Name = et_Shop_Name.getText().toString();
        pin = et_Pin.getText().toString();
        passbook = et_Passbook.getText().toString();
        mobile = et_Mobile.getText().toString();

        tv_State = findViewById(R.id.tv_State2);

        et_Distributor2 = findViewById(R.id.et_Distributor2);
//        ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(), R.layout.dis_list_items, dis_list);
//        et_Distributor.setAdapter(adapter1);
//        et_Distributor.setThreshold(1);

        states();


        et_Account = (EditText) findViewById(R.id.et_Account2);

        et_Bank = (AutoCompleteTextView) findViewById(R.id.et_Bank2);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.bank_list_items, banks);
        et_Bank.setAdapter(adapter);
        et_Bank.setThreshold(2);


        etbank();

        et_IFSC = (EditText) findViewById(R.id.et_IFSC2);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);

        rb_Saving = (RadioButton) findViewById(R.id.rb_Saving2);
        rb_Current = (RadioButton) findViewById(R.id.rb_Current2);

        et_buyfrmR = findViewById(R.id.et_buyfrmR);


        findViewById(R.id.btn_Submit2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_Name.getText().toString().isEmpty()) {
                    et_Name.setError("Fields cannot be blank");
                } else if (et_Shop_Name.getText().toString().isEmpty()) {
                    et_Shop_Name.setError("Fields cannot be blank");
                } else if (et_City.getText().toString().isEmpty()) {
                    et_City.setError("Fields cannot be blank");
                } else if (et_Pin.getText().toString().isEmpty()) {
                    et_Pin.setError("Fields cannot be blank");
                } else if (et_Pin.getText().toString().length() < 6 || et_Pin.getText().toString().length() > 6) {
                    et_Pin.setError("Invalid Pincode");
                } else if (et_Postal.getText().toString().isEmpty()) {
                    et_Postal.setError("Fields cannot be blank");
                } else if (!isValidMobile(et_Mobile.getText().toString())) {
                    et_Mobile.setError("please enter valid number");
                } else if (et_Address.getText().toString().isEmpty()) {
                    et_Address.setError("Fields cannot be blank");
                } else if (et_Account.getText().toString().isEmpty()) {
                    et_Account.setError("Fields cannot be blank");
                } else if (et_Account.getText().toString().length() < 8) {
                    et_Account.setError("Invalid Account Number");
                } else if (et_IFSC.getText().toString().isEmpty()) {
                    et_IFSC.setError("Fields cannot be blank");
                } else if (et_IFSC.getText().toString().length() < 11 || et_IFSC.getText().toString().length() > 11) {
                    et_IFSC.setError("Invalid IFSC code");
                } else if (et_Bank.getText().toString().isEmpty()) {
                    et_Bank.setError("Fields cannot be blank");
                } else if (et_Bank.getText().toString().length() < 3) {
                    et_Bank.setError("Incorrect Bank Name");
                }

//                else if (et_passw.getText().toString().isEmpty()) {
//                    et_passw.setError("Fields cannot be blank");
//                } else if (et_passw.getText().toString().length() < 4) {
//                    et_passw.setError("it should more than 3 char");
//                }


                else {
                    sendData();
                }

            }
        });
    }

    private void hello() {
        showProgressDialog();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/edit_mech.php")
                .addBodyParameter("id", MyPref.getmy_mec_id(context))
                .setTag("fast")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getInt("status") == 1) {

                                JSONArray jsonArray = response.getJSONArray("msg");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    et_Name.setText("" + object.getString("Name"));
                                    et_Passbook.setText("" + object.getString("PassbookNo"));

                                    et_Address.setText("" + object.getString("Address"));
                                    et_City.setText("" + object.getString("City"));
                                    et_Shop_Name.setText("" + object.getString("WorkshopeName"));
                                    et_Pin.setText("" + object.getString("Pincode"));
                                    et_Mobile.setText("" + object.getString("Phone"));
                                    tv_State.setText("" + object.getString("State"));

                                    et_Postal.setText("" + object.getString("AddOnId"));
                                    et_ID_Type.setText("" + object.getString("idtype"));

                                    et_Account.setText("" + object.getString("Acc_No"));
                                    et_IFSC.setText("" + object.getString("Ifsc"));
                                    et_Bank.setText("" + object.getString("Bank_name"));

                                    et_Distributor2.setText("" + object.getString("d_name"));
                                  //  et_passw.setText("" + object.getString("password"));
                                    et_buyfrmR.setText("" + object.getString("BuyFrmRetail"));
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        hideProgressDialog();

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void sendData() {
        showProgressDialog();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/addmechUpdate.php")
                .addBodyParameter("id", MyPref.getmy_mec_id(context))
                .addBodyParameter("Name", et_Name.getText().toString())
                .addBodyParameter("WorkshopeName", et_Shop_Name.getText().toString())
                .addBodyParameter("Address", et_Address.getText().toString())
                .addBodyParameter("Country", "india")
                .addBodyParameter("Distt", et_City.getText().toString())
                .addBodyParameter("Pincode", et_Pin.getText().toString())
                .addBodyParameter("Phone", et_Mobile.getText().toString())
                .addBodyParameter("City", et_City.getText().toString())
                .addBodyParameter("State", tv_State.getText().toString())
                .addBodyParameter("Dis", distr)
                .addBodyParameter("AddOnId", et_Postal.getText().toString())

                .addBodyParameter("Acc_Type", et_BAccount.getText().toString())
                .addBodyParameter("Acc_No", et_Account.getText().toString())
                .addBodyParameter("Ifsc", et_IFSC.getText().toString())
                .addBodyParameter("Bank_name", et_Bank.getText().toString())
                .setTag("fast")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getInt("status") == 1) {
                                hideProgressDialog();
                                Toast.makeText(context, "Detail Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(edit_mech.this, MLPDashboard.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }


    void states() {
        LinkedList<String> categories = new LinkedList<String>();
        categories.add("Select State");
        categories.add("Andhra Pradesh");
        categories.add("Arunachal Pradesh");
        categories.add("Assam");
        categories.add("Bihar");
        categories.add("New Delhi");
        categories.add("Chhattisgarh");
        categories.add("Goa");
        categories.add("Gujarat");
        categories.add("Haryana");
        categories.add("Himachal Pradesh");
        categories.add("Jammu & Kashmir");
        categories.add("Jharkhand");
        categories.add("Karnataka");
        categories.add("Kerala");
        categories.add("Madhya Pradesh");
        categories.add("Maharashtra");
        categories.add("Manipur");
        categories.add("Meghalaya");
        categories.add("Mizoram");
        categories.add("Nagaland");
        categories.add("Orissa");
        categories.add("Punjab");
        categories.add("Rajasthan");
        categories.add("Sikkim");
        categories.add("Tamil Nadu");
        categories.add("Telangana");
        categories.add("Tripura");
        categories.add("Uttar Pradesh");
        categories.add("Uttarakhand");
        categories.add("West Bengal");

        ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(), R.layout.state_list_items, categories);
        tv_State.setAdapter(adapter1);
        tv_State.setThreshold(2);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(edit_mech.this, MLPDashboard.class));
        finish();
    }

    public void etbank() {
        try {
            JSONArray array = new JSONArray(MyPref.storePrefs(context).getBANKS());
            banks.clear();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Log.e("B_id", obj.getString("id"));
                banks.add(obj.getString("bankname"));
            }
        } catch (JSONException e) {
            Log.e("haha", e.toString());
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


}
