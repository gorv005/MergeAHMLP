package com.dartmic.mergeahmlp.OnlineOperation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Networking {

    private Context context;

    public Networking(Context context) {
        this.context = context;
    }

    public void login(JSONObjectRequestListener listener, String username, String pass,String lat, String longi) {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(FixedData.baseURL + "rlp/apiMLP/loginlme.php")
                .addBodyParameter("username", username)
                .addBodyParameter("password", pass)
                .addBodyParameter("latitude", lat)
                .addBodyParameter("longitude", longi)
                .setPriority(Priority.HIGH).setTag("Login").build().getAsJSONObject(listener);
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void fetchingBankData() {
        AndroidNetworking.initialize(context);
        AndroidNetworking.get("http://www.dartmic.com/BNKs/getBankNames")
                .setPriority(Priority.HIGH)
                .setTag("Banks").doNotCacheResponse()
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("bank");
                    MyPref.storePrefs(context).setBANKS(array.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

    public void fetchGiftsData() {
        AndroidNetworking.initialize(context);
        AndroidNetworking.get(FixedData.baseURL + "rlp/apiMLP/mlp_gifts")
                .setPriority(Priority.HIGH)
                .setTag("Gifts").doNotCacheResponse()
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("result");
                    Log.e("ARRAYYY:::::", array.toString());
                    MyPref.storePrefs(context).setGIFTS(array.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

    public void fetchDisList() {
        AndroidNetworking.initialize(context);
        AndroidNetworking.get(FixedData.baseURL + "rlp/apiMLP/new_dist_list.php?lme=" + MyPref.getLmeId())
                .setPriority(Priority.HIGH)
                .setTag("Dis").doNotCacheResponse()
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("result");
                    MyPref.storePrefs(context).setDIS(array.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

}
