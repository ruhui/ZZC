package com.zzcar.zzc.models;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/17 19:51
 **/
public class ShouzhiDetailModel {

    private String date;
    private String total;
    private List<ShouzhiItem> items;

    public ShouzhiDetailModel(String date, String total, List<ShouzhiItem> items) {
        this.date = date;
        this.total = total;
        this.items = items;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ShouzhiItem> getItems() {
        return items;
    }

    public void setItems(List<ShouzhiItem> items) {
        this.items = items;
    }
}

//"date": "sample string 1",
//        "total": 2.0,
//        "items":