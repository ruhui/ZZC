package com.zzcar.zzc.models;

import java.io.Serializable;
import java.util.List;

/**
 * 创建时间： 2017/6/10.
 * 作者：黄如辉
 * 功能描述：
 */

public class BlandModle implements Serializable{
    private int id;
    private List<SeriesItemsModel> series_items;
    private String name;

    public BlandModle(int id, List<SeriesItemsModel> series_items, String name) {
        this.id = id;
        this.series_items = series_items;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SeriesItemsModel> getSeries_items() {
        return series_items;
    }

    public void setSeries_items(List<SeriesItemsModel> series_items) {
        this.series_items = series_items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
// "id": 1,
//         "name": "sample string 2"
