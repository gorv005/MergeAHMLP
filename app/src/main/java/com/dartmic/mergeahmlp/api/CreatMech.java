package com.dartmic.mergeahmlp.api;

import android.content.Context;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

public class CreatMech {

    public static void newMechanic(JSONObjectRequestListener jl, Context context, Bundle b) {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/Mech_Reg.php")
                .setPriority(Priority.HIGH)
                .setTag("AJAJ")
                .addBodyParameter("LME_Id", MyPref.storePrefs(context).getLmeId())
                .addBodyParameter("PassbookNo", b.getString("PassbookNo"))
                .addBodyParameter("Phone", b.getString("et_Mobile"))
                .addBodyParameter("name", b.getString("et_Name"))
                .addBodyParameter("d_id", b.getString("Dis"))
                .doNotCacheResponse()
                .build()
                .getAsJSONObject(jl);
    }

}
