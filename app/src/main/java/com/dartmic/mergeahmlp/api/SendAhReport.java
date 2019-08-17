package com.dartmic.mergeahmlp.api;

import android.content.Context;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

public class SendAhReport {
    public static void sendReport(JSONObjectRequestListener jSONObjectRequestListener, Context context, String str, String str2, String str3, String str4, String str5, String str6) {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/ah_new_remark.php").addBodyParameter("remark", str).addBodyParameter("role", str2).addBodyParameter("did", str3).addBodyParameter("by_id", MyPref.getAh(context)).addBodyParameter("pb", str4).addBodyParameter("rc_visit", str5).addBodyParameter("rc_convert", str6).setTag((Object) "").setPriority(Priority.HIGH).build().getAsJSONObject(jSONObjectRequestListener);
    }
}