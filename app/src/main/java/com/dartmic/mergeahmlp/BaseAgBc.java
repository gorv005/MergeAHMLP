package com.dartmic.mergeahmlp;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;

public class BaseAgBc extends AppCompatActivity {

    protected Dialog mProgressDialog;

    String TAG = BaseAgBc.class.getSimpleName();

    public static
    Location location = new Location("GPS");
    public static LocationRequest mLocationRequest;
    public static final long MAX_WAIT_TIME = 60000 * 5; // Every 5 minutes.
    public static boolean haveLocation = false;

    public void showProgressDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this, R.style.SyncT);
        final View view = getLayoutInflater().inflate(R.layout.progress, null);
        adb.setView(view);
        mProgressDialog = adb.create();
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void requestLocationUpdates(FusedLocationProviderClient mFusedLocationClient) {
        mLocationRequest = new LocationRequest().setInterval(60000).setFastestInterval(30000).setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY).setMaxWaitTime(MAX_WAIT_TIME);
        try {
            Log.i(TAG, "Starting location updates");
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, getPendingIntent());
        } catch (SecurityException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void removeLocationUpdates(FusedLocationProviderClient mFusedLocationClient) {
        Log.i(TAG, "Removing location updates");
        mFusedLocationClient.removeLocationUpdates(getPendingIntent());
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, LocationReceiver.class);
        intent.setAction(LocationReceiver.ACTION_PROCESS_UPDATES);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


}
