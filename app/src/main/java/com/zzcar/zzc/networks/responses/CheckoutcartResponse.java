package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.OrderitemsModel;
import com.zzcar.zzc.models.ShippingModel;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/13 10:38
 **/
public class CheckoutcartResponse implements Serializable{
    private String order_no;
    private double order_amount;
    private String sale_name;
    private String coupon_id;
    private String coupon_amount;
    private String freight;
    private String pay_amount;
    private String shipping_type;
    private String pay_type;
    private ShippingModel shipping;
    private List<OrderitemsModel> order_items;

    public CheckoutcartResponse(String order_no, double order_amount, String sale_name, String coupon_id, String coupon_amount, String freight, String pay_amount, String shipping_type, String pay_type, ShippingModel shipping, List<OrderitemsModel> order_items) {
        this.order_no = order_no;
        this.order_amount = order_amount;
        this.sale_name = sale_name;
        this.coupon_id = coupon_id;
        this.coupon_amount = coupon_amount;
        this.freight = freight;
        this.pay_amount = pay_amount;
        this.shipping_type = shipping_type;
        this.pay_type = pay_type;
        this.shipping = shipping;
        this.order_items = order_items;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(double order_amount) {
        this.order_amount = order_amount;
    }

    public String getSale_name() {
        return sale_name;
    }

    public void setSale_name(String sale_name) {
        this.sale_name = sale_name;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(String coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getShipping_type() {
        return shipping_type;
    }

    public void setShipping_type(String shipping_type) {
        this.shipping_type = shipping_type;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public ShippingModel getShipping() {
        return shipping;
    }

    public void setShipping(ShippingModel shipping) {
        this.shipping = shipping;
    }

    public List<OrderitemsModel> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(List<OrderitemsModel> order_items) {
        this.order_items = order_items;
    }
}

//"order_no": "sample string 1",
//        "order_amount": 3.0,
//        "sale_name": "sample string 4",
//        "coupon_id": 1,
//        "coupon_amount": 5.0,
//        "freight": 6.0,
//        "pay_amount": 7.0,
//        "shipping_type": 1,