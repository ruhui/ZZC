package com.zzcar.zzc.models;

/**
 * 描述：收支明细头
 * 作者：黄如辉  时间 2017/5/20.
 */

public class MoneyDetailHeadModel {
    public String date;
    public String total;

    public MoneyDetailHeadModel(String date, String total) {
        this.date = date;
        this.total = total;
    }
}
