package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.MemberModel;
import com.zzcar.zzc.models.OrderitemsModel;
import com.zzcar.zzc.models.PayInfoModel;
import com.zzcar.zzc.models.ShippingModel;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/28 10:59
 **/
public class ReceiptDetailResponse {
    private long id;
    private String order_no;
    private int user_id;
    private int buy_id;
    private int type;
    private String order_time;
    private int status;
    private int pay_id;
    private double order_amount;
    private String sale_name;
    private double coupon_amount;
    private double freight;
    private double pay_amount;
    private double fee_amount;
    private double amount;
    private int shipping_type;
    private MemberModel buyer_info;
    private PayInfoModel pay_info;
    private ShippingModel shipping;
    private List<OrderitemsModel> order_items;

    public ReceiptDetailResponse(long id, String order_no, int user_id, int buy_id, int type, String order_time, int status, int pay_id, double order_amount, String sale_name, double coupon_amount, double freight, double pay_amount, double fee_amount, double amount, int shipping_type, MemberModel buyer_info, PayInfoModel pay_info, ShippingModel shipping, List<OrderitemsModel> order_items) {
        this.id = id;
        this.order_no = order_no;
        this.user_id = user_id;
        this.buy_id = buy_id;
        this.type = type;
        this.order_time = order_time;
        this.status = status;
        this.pay_id = pay_id;
        this.order_amount = order_amount;
        this.sale_name = sale_name;
        this.coupon_amount = coupon_amount;
        this.freight = freight;
        this.pay_amount = pay_amount;
        this.fee_amount = fee_amount;
        this.amount = amount;
        this.shipping_type = shipping_type;
        this.buyer_info = buyer_info;
        this.pay_info = pay_info;
        this.shipping = shipping;
        this.order_items = order_items;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBuy_id() {
        return buy_id;
    }

    public void setBuy_id(int buy_id) {
        this.buy_id = buy_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPay_id() {
        return pay_id;
    }

    public void setPay_id(int pay_id) {
        this.pay_id = pay_id;
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

    public double getCoupon_amount() {
        return coupon_amount;
    }

    public void setCoupon_amount(double coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(double pay_amount) {
        this.pay_amount = pay_amount;
    }

    public double getFee_amount() {
        return fee_amount;
    }

    public void setFee_amount(double fee_amount) {
        this.fee_amount = fee_amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getShipping_type() {
        return shipping_type;
    }

    public void setShipping_type(int shipping_type) {
        this.shipping_type = shipping_type;
    }

    public MemberModel getBuyer_info() {
        return buyer_info;
    }

    public void setBuyer_info(MemberModel buyer_info) {
        this.buyer_info = buyer_info;
    }

    public PayInfoModel getPay_info() {
        return pay_info;
    }

    public void setPay_info(PayInfoModel pay_info) {
        this.pay_info = pay_info;
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
//"id": 1,
//        "order_no": "sample string 2",
//        "user_id": 3,
//        "buy_id": 1,
//        "type": 1,
//        "order_time": "2017-06-28 10:59:43",
//        "status": 0,
//        "pay_id": 1,
//        "order_amount": 5.0,
//        "sale_name": "sample string 6",
//        "coupon_amount": 7.0,
//        "freight": 8.0,
//        "pay_amount": 9.0,
//        "fee_amount": 10.0,
//        "amount": 11.0,
//        "shipping_type": 1,
//