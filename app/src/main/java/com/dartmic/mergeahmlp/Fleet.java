package com.dartmic.mergeahmlp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.Constants.ListBean;
import com.dartmic.mergeahmlp.SharedPref.MyPref;
import com.dartmic.mergeahmlp.Utils.LocationUtil;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fleet extends BaseAgBc {

    Context context = this;
    EditText gst, noOfVehicle, pan, mobile, foName, foCName, add, pin, srvcByRetailer, idProof;
    FloatingActionButton sendData;
    ImageView cropImageView;
    Bitmap bitmap = null;
    boolean flagbitmap = false;
    AutoCompleteTextView et_Distributor, state;
    ArrayAdapter adapter1;
    private static ArrayList<String> mech_list = new ArrayList<>();
    private static ArrayList<String> id_list = new ArrayList<>();
    public static final int DEFAULT_MAX_RETRIES = 1;

    String stste[] = {
            "Andhra Pradesh",
            "Arunachal Pradesh",
            "Assam",
            "Bihar",
            "Chhattisgarh",
            "Chandigarh",
            "Dadra and Nagar Haveli",
            "Daman and Diu",
            "Delhi",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jammu and Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Orissa",
            "Punjab",
            "Pondicherry",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Tripura",
            "Uttar Pradesh",
            "Uttarakhand",
            "West Bengal"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet);

        gst = findViewById(R.id.gst_number);
        noOfVehicle = findViewById(R.id.no_vehicle);
        pan = findViewById(R.id.pan_number);
        mobile = findViewById(R.id.et_Mobile);
        foCName = findViewById(R.id.fleet_owner_company_name);
        foName = findViewById(R.id.fleet_owner_name);
        state = findViewById(R.id.state);
        pin = findViewById(R.id.pin);
        add = findViewById(R.id.address_1);
        srvcByRetailer = findViewById(R.id.service_by_retailer);
        cropImageView = findViewById(R.id.img);

        fetchListM();

        cropImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropMenuCropButtonIcon(R.drawable.ic_check_black_24dp)
                        .start(Fleet.this);
            }
        });
        srvcByRetailer = findViewById(R.id.service_by_retailer);
        idProof = findViewById(R.id.id_proof);
        sendData = findViewById(R.id.btn_Submit1);
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validate()) {
                    sendData();
                }
            }
        });

        et_Distributor = findViewById(R.id.service_by_mechanic);
        adapter1 = new ArrayAdapter(Fleet.this, R.layout.dis_list_items, mech_list);
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

        state = findViewById(R.id.state);
        ArrayAdapter stateAdptr = new ArrayAdapter(Fleet.this, R.layout.dis_list_items, stste);
        state.setAdapter(stateAdptr);
        state.setThreshold(-1);

        state.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                state.showDropDown();
                state.requestFocus();
                return false;
            }
        });

    }

    private boolean Validate() {
        boolean temp = true;

        if (foName.getText().toString().isEmpty()) {
            foName.setError("Enter Fleet Owner Name");
            temp = false;
        } else if (mobile.getText().toString().length() < 10) {
            mobile.setError("Enter mobile number");
            temp = false;
        } else if (foName.getText().toString().isEmpty()) {
            foName.setError("Enter Fleet Owner Name");
            temp = false;
        } else if (srvcByRetailer.getText().toString().isEmpty()) {
            srvcByRetailer.setError("Enter Retailer Name");
            temp = false;
        } else if (state.getText().toString().isEmpty()) {
            state.setError("Enter state name");
            temp = false;
        } else if (pin.getText().toString().isEmpty()) {
            pin.setError("Enter pin code");
            temp = false;
        } else if (pan.getText().toString().trim().isEmpty() && gst.getText().toString().trim().isEmpty() && idProof.getText().toString().trim().isEmpty()) {
            Toast.makeText(context, "One is compulsory in PAN/GST/ID", Toast.LENGTH_SHORT).show();
            temp = false;
        } else if (noOfVehicle.getText().toString().trim().isEmpty()) {
            noOfVehicle.setError("Enter No of Vehicle");
            temp = false;
        } else if (!flagbitmap) {
            Toast.makeText(context, "Add id Image!", Toast.LENGTH_SHORT).show();
            temp = false;
        }


        return temp;
    }


    private void sendData() {
        String mechs = "";
        final int index = mech_list.indexOf(et_Distributor.getText().toString());
        try {
            mechs = id_list.get(index);
        } catch (Exception e){
            mechs = et_Distributor.getText().toString();
        }

        //  Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();
        String URL = "http://35.237.224.131/rlp/apiMLP/Fleet_Reg.php";
        showProgressDialog();

        AndroidNetworking.initialize(context);
        AndroidNetworking.post(URL).setPriority(Priority.MEDIUM).setTag("yo")
                .addBodyParameter("LME_Id", MyPref.getLmeId())
                .addBodyParameter("phone", mobile.getText().toString().trim())
                .addBodyParameter("gst", gst.getText().toString().trim())
                .addBodyParameter("pan", pan.getText().toString().trim())
                .addBodyParameter("loc", LocationUtil.getAddress(context, location))
                .addBodyParameter("name", foName.getText().toString())
                .addBodyParameter("shop", foCName.getText().toString())
                .addBodyParameter("add", add.getText().toString())
                .addBodyParameter("pin", pin.getText().toString())
                .addBodyParameter("state", state.getText().toString())
                .addBodyParameter("mech", mechs)
                .addBodyParameter("ret", srvcByRetailer.getText().toString())
                .addBodyParameter("id", idProof.getText().toString())
                .addBodyParameter("no_vehicle", noOfVehicle.getText().toString())
                .addBodyParameter("id_pic", getStringImage(bitmap))

                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                hideProgressDialog();
                //   Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                Log.e("Search Data", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1) {
                        new AlertDialog.Builder(context)
                                .setMessage("Account Created Successfully")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        startActivity(new Intent(context, MLPDashboard.class));
                                        finish();
                                    }
                                })
                                .setNegativeButton("", null)
                                .show();
                    } else if(jsonObject.getInt("status") == 2){
                        Toast.makeText(context, "Number Registered...", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(context, "Error occurred...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, MLPDashboard.class));
                        finish();
                    }
                } catch (JSONException e) {
                    hideProgressDialog();
                    // Toast.makeText(mContext, "JSONException", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ANError anError) {
                hideProgressDialog();
                Toast.makeText(context, "Network:" + anError.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Volly ", anError.getMessage() + "");
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, out);
                    cropImageView.setImageBitmap(bitmap);
                    flagbitmap = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void fetchListM() {
        showProgressDialog();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/mechlist.php")
                .addBodyParameter("lme_id", MyPref.storePrefs(context).getLmeId())
                .setTag("Asj")
                .setPriority(Priority.HIGH)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgressDialog();
                Log.e("Data", response.toString());
                try {
                    if (response.getInt("status") == 2) {
                        JSONArray array = response.getJSONArray("result");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            ListBean bean = new ListBean();
                            bean.setName(object.getString("PassbookNo"));
                            bean.setId(object.getString("M_id"));
                            mech_list.add(object.optString("PassbookNo"));
                            Log.e("mech", object.optString("PassbookNo"));
                            id_list.add(bean.getId());
                            adapter1.notifyDataSetChanged();
                        }
                    }
                    Log.e("array list007", id_list.size() + "");
                } catch (JSONException e) {
                    Log.e("James Bond 007", e + "");
                }

            }

            @Override
            public void onError(ANError anError) {
                hideProgressDialog();
                Log.e("Error", anError.toString());
            }
        });
    }

    public String getStringImage(Bitmap bitmap) {
        String encodedImage = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception e) {
            encodedImage = "NA";
        }
        return encodedImage;
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
