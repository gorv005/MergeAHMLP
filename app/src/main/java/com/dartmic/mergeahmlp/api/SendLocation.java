package com.dartmic.mergeahmlp.api;

import android.content.Context;
import android.location.Location;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

public class SendLocation {

    public static void sendLoc(JSONObjectRequestListener listener, Context context, Location location, String addo) {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/api/ah_lme_track.php")
                .setPriority(Priority.MEDIUM)
                .setTag("AsLoc")
                .addBodyParameter("id", MyPref.getUserId(context))
                .addBodyParameter("lati", location.getLatitude() + "")
                .addBodyParameter("longi", location.getLongitude() + "")
                .addBodyParameter("addo", addo)
                .addBodyParameter("role", MyPref.getRole(context))
                .build().getAsJSONObject(listener);
    }

}
