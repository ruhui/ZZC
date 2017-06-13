package com.zzcar.zzc.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/13 10:32
 **/
public class CheckoutcartModel {
    public int product_id;
    public int quantity;

    public CheckoutcartModel(int product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }
}
//"product_id": 1,
//        "quantity": 2