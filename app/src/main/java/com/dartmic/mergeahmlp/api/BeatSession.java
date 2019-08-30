package com.dartmic.mergeahmlp.api;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;

public class BeatSession {

    public static void getSession(JSONObjectRequestListener jl, Context context, String id, String m_id, String type, int re,String lat, String longi) {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/api/plan_session.php")
                .addBodyParameter("id",id)
                .addBodyParameter("role","LME")
                .addBodyParameter("mkt",m_id)
                .addBodyParameter("type",type)
                .addBodyParameter("token",re+"")
                .addBodyParameter("latitude",lat+"")
                .addBodyParameter("longitude",longi+"")
                .setTag("Beat Session")
                .build()
                .getAsJSONObject(jl);
    }

}
