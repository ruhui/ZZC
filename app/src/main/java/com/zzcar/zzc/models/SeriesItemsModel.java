package com.zzcar.zzc.models;

/**
 * 创建时间： 2017/6/10.
 * 作者：黄如辉
 * 功能描述：
 */

public class SeriesItemsModel {
    private int id;
    private String name;

    public SeriesItemsModel(int id, String name) {
        this.id = id;
        this.name = name;
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
}
//"id": 1,
//        "name": "sample string 2"
