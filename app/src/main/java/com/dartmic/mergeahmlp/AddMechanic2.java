package com.dartmic.mergeahmlp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.SharedPref.MyPref;
import com.dartmic.mergeahmlp.api.UpdateMech;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddMechanic2 extends BaseAgBc {

    EditText  et_Account, et_IFSC, et_BAccount;
    private static ArrayList<String> banks = new ArrayList<>();
    RadioGroup radioGroup;
    Context context;
    RadioButton rb_Current, rb_Saving;
    static String type = "", mid;
    AutoCompleteTextView et_Bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mechanic2);
        context = this;
        et_Account = (EditText) findViewById(R.id.et_Account);

        et_Bank = (AutoCompleteTextView) findViewById(R.id.et_Bank);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.bank_list_items, banks);
        et_Bank.setAdapter(adapter);
        et_Bank.setThreshold(2);

        et_IFSC = (EditText) findViewById(R.id.et_IFSC);
        et_BAccount = (EditText) findViewById(R.id.et_BAccount);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        rb_Saving = (RadioButton) findViewById(R.id.rb_Saving);
        rb_Current = (RadioButton) findViewById(R.id.rb_Current);
        mid = getIntent().getStringExtra("of");
        initViews();
    }

    public void initViews() {
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
        findViewById(R.id.btn_Submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_Saving) {
                    type = rb_Saving.getText().toString();
                } else {
                    type = rb_Current.getText().toString();
                }
                updateMech();
            }
        });

        findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AddMechanic3.class).putExtra("of", mid));
                //startActivity(new Intent(context, MLPDashboard.class).putExtra("of", mid));
                finish();
            }
        });

    }

    private void updateMech() {

        Bundle b = new Bundle();
        b.putString("mid", mid);
        b.putString("Acc_Type", type);
        b.putString("Ifsc", et_IFSC.getText().toString());
        b.putString("Acc_No", et_Account.getText().toString());
        b.putString("Bank_name", et_Bank.getText().toString());
        showProgressDialog();

        UpdateMech.updateMechanic(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("My Respo:", response + "");
                try {
                    JSONObject respo = new JSONObject(response + "");
                    if (respo.getInt("status") == 1) {
                        startActivity(new Intent(context, AddMechanic3.class).putExtra("of", mid));
                       // startActivity(new Intent(context, MLPDashboard.class).putExtra("of", mid));
                        finish();
                    } else {
                        Toast.makeText(context, "Error...", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("Send Me", e.getMessage());
                }
                hideProgressDialog();
            }

            @Override
            public void onError(ANError anError) {
                hideProgressDialog();
            }
        }, context, b);

    }
}
