package com.dartmic.mergeahmlp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.dartmic.mergeahmlp.Constants.FixedData.encodeImage2String;

public class AddMechanic3 extends BaseAgBc  implements  AdapterView.OnItemSelectedListener {

    AlertDialog choose;
    Context context;
    EditText editTxt;
    Spinner edit_photo_id_type;
    LinearLayout linear;
    static int flag = 0;
    static String item = "", mid;
    static JSONObject object;
    static int a = 0, g = 0, p = 0, q = 0;
    static String pic1 = "new", pic2 = "new", pic3 = "new", pic4 = "new";

    static boolean id_pic = false, profile = false;

    ImageView iv_IdPhoto, iv_Mech_Photo;
    public static Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
    List<EditText> allEdiTxt = new ArrayList<EditText>();

    Button image_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mechanic3);
        context = this;
        linear = findViewById(R.id.linear);
        iv_Mech_Photo = (ImageView) findViewById(R.id.iv_Mech_Photo);
        iv_IdPhoto = (ImageView) findViewById(R.id.iv_IdPhoto);
        edit_photo_id_type = (Spinner) findViewById(R.id.edit_photo_id_type);
        itemSpinner();




        mid = getIntent().getStringExtra("of");
        image_submit  = findViewById(R.id.image_submit);
        image_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_photo_id_type.getSelectedItem().equals("") || edit_photo_id_type.getSelectedItem().equals("Select ID Type")){
                    Toast.makeText(context, "Select Id type", Toast.LENGTH_SHORT).show();

                } else if (!id_pic){
                    Toast.makeText(context, "Add Id Picture", Toast.LENGTH_SHORT).show();

                } else if (!profile){
                    Toast.makeText(context, "Add Profile Picture", Toast.LENGTH_SHORT).show();

                } else {
                    AddPicture();
                }
            }
        });

    }

    private void AddPicture(){
        showProgressDialog();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/Mech_Reg_Id.php")
                .addBodyParameter("mid",mid)
                .addBodyParameter("idtype", edit_photo_id_type.getSelectedItem().toString())
                .addBodyParameter("id_picture", pic1)
                .addBodyParameter("Picture", pic2)
                .setPriority(Priority.MEDIUM)
                .setTag("image")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideProgressDialog();
                        try {
                            if (response.getInt("status")>0){
                                Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddMechanic3.this, MLPDashboard.class));
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




//    object = new JSONObject();
//            try {
//        object.put("purchaseFrom" + 0, et_Retailer.getText().toString());
//        for (int i = 1; i <= allEdiTxt.size(); i++) {
//            object.put("purchaseFrom" + i, allEdiTxt.get(i - 1).getText().toString());
//        }
//    } catch (JSONException e) {
//        Log.e("Dont", "try to catch Me");
//    }


//    if (a == 0) {
//        Toast.makeText(context, "Select ID Photo", Toast.LENGTH_SHORT).show();
//    } else if (g == 0) {
//        Toast.makeText(context, "Select Mechanic Photo", Toast.LENGTH_SHORT).show();
//    } else


    public void itemSpinner() {
        edit_photo_id_type = (Spinner) findViewById(R.id.edit_photo_id_type);
        edit_photo_id_type.setOnItemSelectedListener(this);
        List<String> photoId = new ArrayList<String>();
        photoId.add("Select ID Type");
        photoId.add("Driving Licience");
        photoId.add("Adhar Card");
        photoId.add("Passport");
        photoId.add("Ration Card");
        photoId.add("VoterID Card");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spintext, photoId);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_photo_id_type.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        flag = position;
        item = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void AddEdt(View view) {
        addEdiTtext();
    }

    public void chooseImageFrom(View vv) {
        int ii = 0;
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        final View view = getLayoutInflater().inflate(R.layout.add_choose_dialog, null);
        adb.setView(view);
        choose = adb.create();
        Window window = choose.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        choose.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations_SmileWindow;
        choose.setCancelable(true);
        choose.show();

        if (vv.getId() == R.id.iv_IdPhoto)
            ii = 1001;
        if (vv.getId() == R.id.iv_Mech_Photo)
            ii = 1002;

        initDialogViews(view, ii);
    }

    private void initDialogViews(View v, final int ii) {

        v.findViewById(R.id.txt_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose.dismiss();
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, ii);
                } else {
                    ActivityCompat.requestPermissions(AddMechanic3.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
                }
            }
        });

        v.findViewById(R.id.txt_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose.dismiss();
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, ii);
                } else {
                    ActivityCompat.requestPermissions(AddMechanic3.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void addEdiTtext() {

        editTxt = new EditText(AddMechanic3.this);
        allEdiTxt.add(editTxt);

        editTxt.setBackgroundResource(R.drawable.et_back_drawable);
        editTxt.setPadding(10, 10, 10, 10);
        editTxt.setTextColor(getResources().getColor(R.color.colorAccent));
        editTxt.setHint("Retailer From Whom Purchased");
        editTxt.setCompoundDrawablePadding(10);
        editTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.retailer, 0, 0, 0);
        editTxt.setTextSize(14);
        editTxt.setTypeface(null, Typeface.BOLD);
        editTxt.setSingleLine();
        editTxt.setMaxLines(1);
        editTxt.setBackground(getResources().getDrawable(R.drawable.et_back_drawable));
        editTxt.setHintTextColor(getResources().getColor(R.color.colorlightBlack));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(12, 20, 10, 0);
        editTxt.setLayoutParams(params);
        linear.addView(editTxt);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1001:
                if (resultCode == RESULT_OK && data != null) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePath = {MediaStore.Images.Media.DATA};
                        Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePath[0]);
                        String picturePath = c.getString(columnIndex);
                        c.close();
                        File f = new File(picturePath);
                        bitmap1 = BitmapFactory.decodeFile(f.getAbsolutePath());
                    } catch (Exception e) {
                        bitmap1 = (Bitmap) data.getExtras().get("data");
                    }
                    pic1 = encodeImage2String(bitmap1);
                    a = 1;
                    iv_IdPhoto.setImageBitmap(bitmap1);
                    id_pic = true;
                }
                break;
            case 1002:
                if (resultCode == RESULT_OK && data != null) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePath = {MediaStore.Images.Media.DATA};
                        Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePath[0]);
                        String picturePath = c.getString(columnIndex);
                        c.close();
                        File f = new File(picturePath);
                        bitmap2 = BitmapFactory.decodeFile(f.getAbsolutePath());
                    } catch (Exception e) {
                        bitmap2 = (Bitmap) data.getExtras().get("data");
                    }
                    pic2 = encodeImage2String(bitmap2);
                    g = 1;
                    iv_Mech_Photo.setImageBitmap(bitmap2);
                    profile = true;
                }
                break;
            case 1003:
                if (resultCode == RESULT_OK && data != null) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePath = {MediaStore.Images.Media.DATA};
                        Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePath[0]);
                        String picturePath = c.getString(columnIndex);
                        c.close();
                        File f = new File(picturePath);
                        bitmap3 = BitmapFactory.decodeFile(f.getAbsolutePath());
                    } catch (Exception e) {
                        bitmap3 = (Bitmap) data.getExtras().get("data");
                    }
                    pic3 = encodeImage2String(bitmap3);
                    p = 1;


                }
                break;
            case 1004:
                if (resultCode == RESULT_OK && data != null) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePath = {MediaStore.Images.Media.DATA};
                        Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePath[0]);
                        String picturePath = c.getString(columnIndex);
                        c.close();
                        File f = new File(picturePath);
                        bitmap4 = BitmapFactory.decodeFile(f.getAbsolutePath());
                    } catch (Exception e) {
                        bitmap4 = (Bitmap) data.getExtras().get("data");
                    }
                    pic4 = encodeImage2String(bitmap4);
                    q = 1;


                }
                break;
            default:
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }

//    b.putString("BuyFrmRetail", object.toString());
//        b.putString("idtype", item);
//        b.putString("Acc_Type", type);
//        b.putString("Ifsc", et_IFSC.getText().toString());
//        b.putString("Acc_No", et_Account.getText().toString());
//        b.putString("Bank_name", et_Bank.getText().toString());
//        b.putString("id_picture", pic1);
//        b.putString("bill_picture", pic2);
//        b.putString("Picture", pic3);
//        b.putString("mrp_pict", pic4);

}
