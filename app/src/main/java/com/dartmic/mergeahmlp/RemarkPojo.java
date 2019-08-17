package com.dartmic.mergeahmlp;

public class RemarkPojo {
    private String date;
    private String new_enroll;
    private String no_of_counter;
    private String remark;
    private String total_points;
    private String visited_mechanic;

    public String getVisited_mechanic() {
        return this.visited_mechanic;
    }

    public void setVisited_mechanic(String str) {
        this.visited_mechanic = str;
    }

    public String getNo_of_counter() {
        return this.no_of_counter;
    }

    public void setNo_of_counter(String str) {
        this.no_of_counter = str;
    }

    public String getTotal_points() {
        return this.total_points;
    }

    public void setTotal_points(String str) {
        this.total_points = str;
    }

    public String getNew_enroll() {
        return this.new_enroll;
    }

    public void setNew_enroll(String str) {
        this.new_enroll = str;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String str) {
        this.remark = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }
}
