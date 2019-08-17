package com.dartmic.mergeahmlp.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class MyPref {

    static int i = 1;
    private static String IS_LOGGED_IN = "logged_in";
    private static String USERNAME = "name";
    private static String MOBILE = "YOYO";
    private static String PASSWORD = "password";
    private static String LME_ID = "lme_id";
    private static String POINTS = "mlp_points";
    public static String MEC_ID = "mec_ID";
    public static String SHOP_NAME = "ShopName";
    public static String PASSBOOK = "passbook";
    public static String MECH_NAME = "name";
    public static String LME_NAME = "lme_name";
    public static String EMAIL = "lme_email";
    public static String AREA_HEAD_ALLOTED = "area_head";
    public static String AREA_HEAD_PHONE = "area_head_phone";
    public static String TOTAL_MECHANICS = "total_mech";
    private static String SELECTED_STATUS = "status";
    private static String PREF_BANKS = "bank";
    private static String PREF_GIFTS = "gifts";
    private static String PREF_DIS = "dis";
    private static final String PREF_KEY_IS_BEAT_RUNNING = "BEAT_in";
    private static String Ah_Id = "ah_id";
    private static String PHN = "phn";
    private static String R_Id = "r_id";
    private static String Ah_Name = "name";
    private static final String PREF_KEY_USER_USER_ID = "user_id";
    private static final String PREF_KEY_USER_USER_ROLE = "user_role";
    private static String Ah = "ah";
    private static final String PREF_KEY_LOGIN = "logged_in";

    private static final String market_id = "market";

    private static final String my_mec_id = "my_mec_id";


    private static final String current_market = "current_market";
    private static final String complete = "complete";


    private static final String totaaly  = "total_points";


    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        prefsEditor.putBoolean(key, value).commit();

    }

    public static boolean getBoolean(Context context, String key, boolean defaulValue) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(key, defaulValue);

    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        prefsEditor.putString(key, value).commit();

    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key, defaultValue);

    }

    public static int getInt(Context ctx, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs.getInt(key, 0);

    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences.Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        prefsEditor.putInt(key, value).commit();

    }

    public static void clear(Context context) {
        SharedPreferences.Editor prefsEditor = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        prefsEditor.clear();
        prefsEditor.commit();

    }

    public static boolean isLoggedIn(Context context) {
        return getBoolean(context, PREF_KEY_LOGIN, false);
    }

    public static void setLoggedIn(Context context, boolean value) {
        putBoolean(context, PREF_KEY_LOGIN, value);
    }


    public static String isComplete(Context context) {
        return getString(context, complete, "none");
    }

    public static void setComplete(Context context, String value) {
        putString(context, complete, value);
    }


    public static String getAhId(Context context) {
        return getString(context, Ah_Id, "none");
    }

    public static void setAhId(Context context, String value) {
        putString(context, Ah_Id, value);
    }

    public static String getEId(Context context) {
        return getString(context, R_Id, "none");
    }

    public static void setEId(Context context, String value) {
        putString(context, R_Id, value);
    }


    public static String getPhone(Context context) {
        return getString(context, PHN, "none");
    }

    public static void setPhone(Context context, String value) {
        putString(context, PHN, value);
    }


    public static String getMarket_id(Context context) {
        return getString(context, market_id, "none");
    }

    public static void setMarket_id(Context context, String value) {
        putString(context, market_id, value);
    }


    public static String getCurrent_market(Context context) {
        return getString(context, current_market, "none");
    }

    public static void setCurrent_market(Context context, String value) {
        putString(context, current_market, value);
    }




    public static String getTotaaly(Context context) {
        return getString(context, totaaly, "0");
    }

    public static void setTotaaly(Context context, String value) {
        putString(context, totaaly, value);
    }






    public static void setAh_Name(Context context, String value) {
        putString(context, Ah_Name, value);
    }


    // user user email
    public static String getAh_Name(Context context) {
        return getString(context, Ah_Name, null);
    }


    public static String getAh(Context context) {
        return getString(context, Ah, "none");
    }

    public static void setAh(Context context, String value) {
        putString(context, Ah, value);
    }


    public static MyPref instance;
    private Context context;
    private static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public static MyPref storePrefs(Context context) {
        if (instance == null)
            instance = new MyPref(context);
        return instance;
    }

    private MyPref(Context context) {
        this.context = context;
        String PREFS = "MyPrefs";
        sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public Boolean getIsLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void setIsLoggedIn(Boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public String getUsername() {
        return sharedPreferences.getString(USERNAME, "");
    }

    public void setUsername(String username) {
        editor.putString(USERNAME, username);
        Log.e("USERNAME:::::::::::::", username);
        editor.commit();
    }

    public String getPassword() {

        return sharedPreferences.getString(PASSWORD, "");
    }

    public void setPassword(String password) {
        editor.putString(PASSWORD, password);
        editor.commit();

    }

    public String getMOBILE() {

        return sharedPreferences.getString(MOBILE, "");
    }

    public void setMOBILE(String PHONE) {
        editor.putString(MOBILE, PHONE);
        editor.commit();

    }

    public String getBANKS() {

        return sharedPreferences.getString(PREF_BANKS, "");
    }

    public void setBANKS(String BANKS) {
        Log.e("BANKPREF", BANKS);
        editor.putString(PREF_BANKS, BANKS);
        editor.commit();

    }

    public String getGIFTS() {

        return sharedPreferences.getString(PREF_GIFTS, "");
    }

    public void setGIFTS(String GIFTS) {
        Log.e("G", GIFTS);
        editor.putString(PREF_GIFTS, GIFTS);
        editor.commit();

    }


    public String getDIS() {

        return sharedPreferences.getString(PREF_DIS, "");
    }

    public void setDIS(String DIS) {
        Log.e("D", DIS);
        editor.putString(PREF_DIS, DIS);
        editor.commit();
    }

    public static int isBeatIn(Context ctx) {
        return getInt(ctx, PREF_KEY_IS_BEAT_RUNNING);
    }

    public static void setBeatIn(Context ctx, int value) {
        putInt(ctx, PREF_KEY_IS_BEAT_RUNNING, value);
    }

    public static String getLmeId() {
        return sharedPreferences.getString(LME_ID, "");
    }

    public void setLmeId(String lmeId) {
        Log.e("LME_id::::::" + i, lmeId);
        i++;
        editor.putString(LME_ID, lmeId);
        editor.commit();
    }

    public static String getMecId() {
        return sharedPreferences.getString(MEC_ID, "");
    }

    public void setMecId(String mecId) {

        Log.e("M_ID::::", mecId);

        editor.putString(MEC_ID, mecId);
        editor.commit();
    }

    public void setPassbook(String passbook) {
        Log.e("PASSBOOK......::::", passbook);
        editor.putString(PASSBOOK, passbook);
        editor.commit();
    }

    public String getPassbook() {
        return sharedPreferences.getString(PASSBOOK, "");
    }

    public void setMechName(String mechName) {
        Log.e("MECH_NAME......::::", mechName);
        editor.putString(MECH_NAME, mechName);
        editor.commit();
    }

    public String getMechName() {
        return sharedPreferences.getString(MECH_NAME, "");
    }

    public void setShopName(String shopName) {
        Log.e("SHOP_NAME......::::", shopName);
        editor.putString(SHOP_NAME, shopName);
        editor.commit();
    }

    public String getShopName() {
        return sharedPreferences.getString(SHOP_NAME, "");

    }

    public void setPoints(String points) {
        Log.e("POINTS......::::", points);
        editor.putString(POINTS, points);
        editor.commit();
    }

    public String getPoints() {
        return sharedPreferences.getString(POINTS, "");

    }

    public void setLmeName(String lmeName) {
        Log.e("POINTS......::::", lmeName);
        editor.putString(LME_NAME, lmeName);
        editor.commit();
    }

    public String getLmeName() {
        return sharedPreferences.getString(LME_NAME, "");

    }

    public void setAreaHeadAlloted(String areaHeadAlloted) {

        Log.e("POINTS......::::", areaHeadAlloted);
        editor.putString(AREA_HEAD_ALLOTED, areaHeadAlloted);
        editor.commit();
    }

    public String getAreaHeadAlloted() {
        return sharedPreferences.getString(AREA_HEAD_ALLOTED, "");

    }

    public void setAreaHeadPhone(String areaHeadPhone) {

        Log.e("POINTS......::::", areaHeadPhone);
        editor.putString(AREA_HEAD_PHONE, areaHeadPhone);
        editor.commit();
    }

    public String getAreaHeadPhone() {
        return sharedPreferences.getString(AREA_HEAD_PHONE, "");

    }

    public void setEmail(String email) {

        Log.e("POINTS......::::", email);
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL, "");
    }

    public static String getRole(Context ctx) {
        return getString(ctx, PREF_KEY_USER_USER_ROLE, null);
    }

    public static void setRole(Context ctx, String val) {
        putString(ctx, PREF_KEY_USER_USER_ROLE, val);
    }

    public void setTotalMechanics(String totalMechanics) {

        Log.e("POINTS......::::", totalMechanics);
        editor.putString(TOTAL_MECHANICS, totalMechanics);
        editor.commit();
    }

    public String getTotalMechanics() {
        return sharedPreferences.getString(TOTAL_MECHANICS, "");

    }

    public void setSelectedStatus(Boolean is) {
        editor.putBoolean(SELECTED_STATUS, is);
        editor.commit();
    }

    public Boolean getSelectedStatus() {
        return sharedPreferences.getBoolean(SELECTED_STATUS, false);
    }

    public static String getUserId(Context ctx) {
        return getString(ctx, PREF_KEY_USER_USER_ID, null);
    }

    public static void setUserId(Context ctx, String val) {
        putString(ctx, PREF_KEY_USER_USER_ID, val);
    }

    public static String getmy_mec_id(Context ctx) {
        return getString(ctx, my_mec_id, null);
    }

    public static void setmy_mec_id(Context ctx, String val) {
        putString(ctx, my_mec_id, val);
    }

}
