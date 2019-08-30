package com.dartmic.mergeahmlp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.dartmic.mergeahmlp.SharedPref.MyPref;
import com.dartmic.mergeahmlp.Utils.LocationUtil;
import com.dartmic.mergeahmlp.Utils.SharedPreference;
import com.dartmic.mergeahmlp.api.SendLocation;
import com.google.android.gms.location.LocationResult;

import org.json.JSONObject;

import java.util.List;

public class LocationReceiver extends BroadcastReceiver {

    private static final String TAG = LocationReceiver.class.getSimpleName();
    static final String ACTION_PROCESS_UPDATES = "com.dartmic.mergeahmlp.action" + ".PROCESS_UPDATES";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATES.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null) {
                    List<Location> locations = result.getLocations();
                    BaseAgBc.haveLocation = true;
                    for (Location location : locations) {
                        BaseAgBc.location = location;
                        if (MyPref.isBeatIn(context) > 0) {
                            sendLocation(location, context);
                        }
                    }
                    Log.e(TAG, BaseAgBc.location.getTime() + "");
                }
            }
        }
    }

    private void sendLocation(Location location, Context context) {
        String addo = LocationUtil.getAddress(context, location);
        SharedPreference.getInstance(context).setString("lat",""+location.getLatitude());
        SharedPreference.getInstance(context).setString("longi",""+location.getLongitude());

        SendLocation.sendLoc(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("RESPONSE", response.toString());
            }

            @Override
            public void onError(ANError anError) {
                Log.e("ERROR::::::", anError.toString());

            }
        },context, location, addo);
    }

}
