package com.zzcar.zzc.models;

import java.io.Serializable;

/**
 * 创建时间： 2017/6/10.
 * 作者：黄如辉
 * 功能描述：
 */

public class SeriesItemsModel implements Serializable{
    private long id;
    private String name;

    public SeriesItemsModel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
