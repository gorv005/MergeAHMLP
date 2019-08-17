package com.dartmic.mergeahmlp.Constants;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Agrata Arya on 10/5/2017.
 */

public class CouponHisBean {

    String part_no;
    String mlp_points;
    String created;
    String m_id;

    public String getPart_no() {
        return part_no;
    }
    ArrayList<CouponHisBean> arrayList;
    Context context;

    public CouponHisBean(ArrayList<CouponHisBean> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void setPart_no(String part_no) {
        this.part_no = part_no;
    }

    public String getMlp_points() {
        return mlp_points;
    }

    public void setMlp_points(String mlp_points) {
        this.mlp_points = mlp_points;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }
}
