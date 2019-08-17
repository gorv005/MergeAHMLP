package com.dartmic.mergeahmlp.Constants;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.dartmic.mergeahmlp.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Agrata Arya on 9/19/2017.
 */

public class FixedData {

    public static String baseURL = "http://35.237.224.131/";
    public static AlertDialog.Builder builder;
    public static LayoutInflater inflater;
    public static final String QR_RESULT_KEY = "QR_CODE_RESULT";
    public static final String LME_ID = "lme_ID";
    public static final String MEC_ID = "mec_ID";
    public static String lastUpdate = "0";
    public static int ifyes = 0, tp = 0;
    public static int po = 0;
    public static ArrayList<MechListBean> mData =new ArrayList<>();

    static AlertDialog.Builder adb;

    public static String fixedRole="default";
    public static String ROLE="default";

    public static String fixedId="id";
    public static int fixedIntent=0;

    public static boolean isValidPass(CharSequence password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6;

    }


    public static float dp2px(Context context, int hi){
        Resources r = context.getResources();
         return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, hi, r.getDisplayMetrics());
    }

    public static boolean isValid(String str) {
        return !TextUtils.isEmpty(str) && str.length() >= 5;
    }

    public static String encodeImage2String(Bitmap thumbnail) {
        thumbnail = Bitmap.createScaledBitmap(thumbnail, 500, 500, true);
        ByteArrayOutputStream blob1 = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 70, blob1);
        byte[] bitmapdata1 = blob1.toByteArray();
        return Base64.encodeToString(bitmapdata1, Base64.DEFAULT);
    }

    public static Bitmap encodeString2Image(String thumbnail) {
        byte[] decodedBytes = Base64.decode(thumbnail, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

//    public static void changePassDialog(Context context) {
//        builder = new AlertDialog.Builder(context);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View v = inflater.inflate(R.layout.change_pass_dialog, null);
//        builder.setView(v);
//        EditText et_Old_Pass = (EditText) v.findViewById(R.id.et_Old_Pass);
//        EditText et_New_Pass = (EditText) v.findViewById(R.id.et_New_Pass);
//        EditText et_Con_Pass = (EditText) v.findViewById(R.id.et_Con_Pass);
//
//        v.findViewById(R.id.btn_Sub_Pass);
//        builder.setCancelable(true);
//        final AlertDialog dialog = builder.create();
//        dialog.show();
//    }

    public static String getCurrentDate() {
        //Log.e("New Date", new Date() + "");
        String currentDate = null;
        try {
            currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    .format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentDate + "";
    }

    public static boolean isValidPassword(CharSequence password) {
        return !TextUtils.isEmpty(password) && password.equals("oldp");
    }

    public static void dBox(Context context) {
        adb = new AlertDialog.Builder(context, R.style.SyncT);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.progress, null);
        adb.setView(view);
        showProg();

    }

    public static void showProg() {
        Dialog prog = adb.create();
        prog.setCancelable(false);
        prog.show();
    }

    public static void dismissProg() {
        Dialog prog = adb.create();
        prog.setCancelable(true);
        prog.dismiss();
    }
}