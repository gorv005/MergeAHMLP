package com.dartmic.mergeahmlp.Constants;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Agrata Arya on 9/28/2017.
 */

public class MechListBean {
    String shopName;
    String city;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    String mobile;
    String passbook_no;
    String Mech_Name;

    public String getMech_Id() {
        return Mech_Id;
    }

    public void setMech_Id(String mech_Id) {
        Mech_Id = mech_Id;
    }

    String Mech_Id;

    public String getMech_Name() {
        return Mech_Name;
    }

    public void setMech_Name(String mech_Name) {
        Mech_Name = mech_Name;
    }

    ArrayList<MechListBean> arrayList;
    Context context;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        Log.e("SHOPNAME:::::::", shopName);
        this.shopName = shopName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        Log.e("CITY:::::::", city);
        this.city = city;
    }

    public String getPassbook_no() {
        return passbook_no;
    }

    public void setPassbook_no(String passbook_no) {
        Log.e("PASSBOOKKKK:::::::", passbook_no);
        this.passbook_no = passbook_no;
    }

}

