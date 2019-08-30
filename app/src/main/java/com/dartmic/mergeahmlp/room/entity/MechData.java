package com.dartmic.mergeahmlp.room.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "mechdata")
public class MechData implements Serializable {
    private String m_name;
    @PrimaryKey
    @NonNull
    private String m_id;

    @NonNull
    private int m_point;
    private String total_point;

    private String phoneNumber;
    private String m_remark;
    String shopName;

    String passbook_no;

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    @NonNull
    public String getM_id() {
        return m_id;
    }

    public void setM_id(@NonNull String m_id) {
        this.m_id = m_id;
    }

    @NonNull
    public int getM_point() {
        return m_point;
    }

    public void setM_point( @NonNull int m_point) {
        this.m_point = m_point;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getM_remark() {
        return m_remark;
    }

    public void setM_remark(String m_remark) {
        this.m_remark = m_remark;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPassbook_no() {
        return passbook_no;
    }

    public void setPassbook_no(String passbook_no) {
        this.passbook_no = passbook_no;
    }

    public String getTotal_point() {
        return total_point;
    }

    public void setTotal_point(String total_point) {
        this.total_point = total_point;
    }
}
