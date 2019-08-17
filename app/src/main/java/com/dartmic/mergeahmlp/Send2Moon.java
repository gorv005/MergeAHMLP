package com.dartmic.mergeahmlp;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.dartmic.mergeahmlp.Constants.FixedData;
import com.dartmic.mergeahmlp.SharedPref.MyPref;

import java.util.HashMap;
import java.util.Map;


public class Send2Moon {

    public static final String URL_SAVE_NAME = FixedData.baseURL + "rlp/api/hey.php";
    public static final String URL_SAVE_TRACK = FixedData.baseURL + "rlp/api/offline_lme_track.php";
    public static final String URL_START_DAY = FixedData.baseURL + "rlp/api/startButtonOffline.php";
    public static final String URL_STOP_DAY = FixedData.baseURL + "rlp/api/end_lme_day_offline.php";


    public static void saveNameToServer(final Context context, final String name, Response.Listener listener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_NAME,
                listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("remark", name);
                Log.e("uid", MyPref.getUserId(context));
                Log.e("role", MyPref.getRole(context));
                params.put("uid", MyPref.getUserId(context));
                params.put("role", MyPref.getRole(context));
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    public static void saveTrackToServer(final Context context, final String name, final String lati, final String longi, final String domino, Response.Listener listener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_TRACK,
                listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id", MyPref.getUserId(context));
                params.put("role", MyPref.getRole(context));
                params.put("lati", lati);
                params.put("longi", longi);
                params.put("date", domino);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    public static void saveStartDayToServer(final Context context, final String name, final String lati, final String longi, final String dominoo, Response.Listener listener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_START_DAY,
                listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.e("role", MyPref.getRole(context));

                params.put("p_id", MyPref.getUserId(context));
                params.put("role", MyPref.getRole(context));
                params.put("mkt_id", MyPref.getMarket_id(context));
                params.put("date", dominoo);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    public static void saveStopDayToServer(final Context context, final String name, final String lati, final String longi, final String dominoo, Response.Listener listener, Response.ErrorListener errorListener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_STOP_DAY,
                listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.e("role", MyPref.getRole(context));

                params.put("p_id", MyPref.getUserId(context));
                params.put("role", MyPref.getRole(context));
                params.put("mkt_id", MyPref.getMarket_id(context));
                params.put("date", dominoo);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


}
