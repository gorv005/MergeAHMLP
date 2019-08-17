package com.dartmic.mergeahmlp.Constants;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Agrata Arya on 10/6/2017.
 */

public class RedeemHisBean {

    String Mech_Id;
    String Redeem_Points;
    String Total_Points;
    String Reamrk;
    String Created;
    String hit__button;

    public String getMech_Id() {
        return Mech_Id;
    }

    public void setMech_Id(String mech_Id) {
        Mech_Id = mech_Id;
    }

    ArrayList<RedeemHisBean> arrayList;
    Context context;

    public RedeemHisBean(ArrayList<RedeemHisBean> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public String getRedeem_Points() {
        return Redeem_Points;
    }

    public void setRedeem_Points(String redeem_Points) {
        Redeem_Points = redeem_Points;
    }



    public void setTotal_Points(String total_Points) {
        Total_Points = total_Points;
    }

    public String getTotal_Points() {
        return Total_Points;
    }

    public void setHit__button(String hit__button) {
        hit__button = hit__button;
    }


    public String getHit__button() {
        return hit__button;
    }

    public String getReamrk() {
        return Reamrk;
    }

    public void setReamrk(String reamrk) {
        Reamrk = reamrk;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }
}
