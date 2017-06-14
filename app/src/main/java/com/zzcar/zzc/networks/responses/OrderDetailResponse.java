package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.OrderitemsModel;
import com.zzcar.zzc.models.PayInfoModel;
import com.zzcar.zzc.models.ShoppingModel;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/14 14:55
 **/
public class OrderDetailResponse {
    private String order_time;
    private PayInfoModel pay_info;
    private int shipping_type;
    private ShoppingModel shipping;
    private int id;
    private String order_no;
    private int status;
    private double amount;
    private boolean buyer_confirm;
    private boolean seller_confirm;
    private List<OrderitemsModel> order_items;

    public OrderDetailResponse(String order_time, PayInfoModel pay_info, int shipping_type, ShoppingModel shipping, int id, String order_no, int status, double amount, boolean buyer_confirm, boolean seller_confirm, List<OrderitemsModel> order_items) {
        this.order_time = order_time;
        this.pay_info = pay_info;
        this.shipping_type = shipping_type;
        this.shipping = shipping;
        this.id = id;
        this.order_no = order_no;
        this.status = status;
        this.amount = amount;
        this.buyer_confirm = buyer_confirm;
        this.seller_confirm = seller_confirm;
        this.order_items = order_items;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public PayInfoModel getPay_info() {
        return pay_info;
    }

    public void setPay_info(PayInfoModel pay_info) {
        this.pay_info = pay_info;
    }

    public int getShipping_type() {
        return shipping_type;
    }

    public void setShipping_type(int shipping_type) {
        this.shipping_type = shipping_type;
    }

    public ShoppingModel getShipping() {
        return shipping;
    }

    public void setShipping(ShoppingModel shipping) {
        this.shipping = shipping;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isBuyer_confirm() {
        return buyer_confirm;
    }

    public void setBuyer_confirm(boolean buyer_confirm) {
        this.buyer_confirm = buyer_confirm;
    }

    public boolean isSeller_confirm() {
        return seller_confirm;
    }

    public void setSeller_confirm(boolean seller_confirm) {
        this.seller_confirm = seller_confirm;
    }

    public List<OrderitemsModel> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(List<OrderitemsModel> order_items) {
        this.order_items = order_items;
    }
}
//"id": 2,
//        "order_no": "sample string 3",
//        "status": 0,
//        "amount": 4.0,
//        "buyer_confirm": true,
//        "seller_confirm": true,
