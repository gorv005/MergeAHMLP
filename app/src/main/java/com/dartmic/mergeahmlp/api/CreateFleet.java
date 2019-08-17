package com.dartmic.mergeahmlp.api;

import android.content.Context;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.StringRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

public class CreateFleet {

    public static void newFleet(StringRequestListener jl, Context context, Bundle b) {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/Fleet_Reg.php")
                .setPriority(Priority.HIGH)
                .setTag("AjjAJ")
                .addBodyParameter("LME_Id", MyPref.getLmeId())
                .addBodyParameter("gst", "ji")
                .addBodyParameter("phone", b.getString("phone"))
                .addBodyParameter("name", b.getString("et_Name"))
                .addBodyParameter("shop", b.getString("shop"))
                .addBodyParameter("add", b.getString("add"))
                .addBodyParameter("pin", b.getString("pin"))
                .addBodyParameter("state", b.getString("state"))
                .addBodyParameter("mech", b.getString("mech"))
                .addBodyParameter("ret", b.getString("ret"))
                .addBodyParameter("id", b.getString("id"))
                .addBodyParameter("id_pic", b.getString("id_pic"))
                .doNotCacheResponse()
                .build()
                .getAsString(jl);
    }

}
