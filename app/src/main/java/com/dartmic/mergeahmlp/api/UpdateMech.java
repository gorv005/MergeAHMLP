package com.dartmic.mergeahmlp.api;

import android.content.Context;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

public class UpdateMech {

    public static void updateMechanic(JSONObjectRequestListener jl, Context context, Bundle b) {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/Mech_Reg_Bank.php")
                .setPriority(Priority.HIGH)
                .setTag("AJAJ")
                .addBodyParameter("Acc_No", b.getString("Acc_No"))
                .addBodyParameter("Acc_Type", b.getString("Acc_Type"))
                .addBodyParameter("Ifsc", b.getString("Ifsc"))
                .addBodyParameter("Bank_name", b.getString("Bank_name"))
                .addBodyParameter("mid", b.getString("mid"))
                .doNotCacheResponse()
                .build()
                .getAsJSONObject(jl);
    }

}
