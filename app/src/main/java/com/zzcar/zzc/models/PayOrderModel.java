package com.zzcar.zzc.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/13 15:06
 **/
public class PayOrderModel {
    public String order_no;
    public String pay_code;

    public PayOrderModel(String order_no, String pay_code) {
        this.order_no = order_no;
        this.pay_code = pay_code;
    }
}
