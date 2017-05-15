package com.zzcar.zzc.models;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/15.
 */

public class IntegralDetail {
    private int id;
    private String name;
    private int integral;
    private String create_time;

    public IntegralDetail(int id, String name, int integral, String create_time) {
        this.id = id;
        this.name = name;
        this.integral = integral;
        this.create_time = create_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }
}
//"id": 1,
////         "name": "sample string 3",
////         "create_time": "2017-05-15 22:37:23",
////         "integral": 5