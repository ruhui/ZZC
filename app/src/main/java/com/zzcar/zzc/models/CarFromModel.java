package com.zzcar.zzc.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/1 9:42
 **/
public class CarFromModel {
    public int type = 1;//表车源
    public String product_id;
    public String price;

    public CarFromModel(String product_id, String price) {
        this.product_id = product_id;
        this.price = price;
    }
}
