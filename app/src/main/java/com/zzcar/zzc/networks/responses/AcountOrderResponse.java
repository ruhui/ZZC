package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.MemberModel;
import com.zzcar.zzc.models.OrderitemsModel;
import com.zzcar.zzc.models.PayInfoModel;
import com.zzcar.zzc.models.ShoppingModel;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/28 16:06
 **/
public class AcountOrderResponse {
    private String id;
    private String order_no;
    private String user_id;
    private String buy_id;
    private String type;
    private String order_time;
    private String status;
    private String pay_id;
    private double order_amount;
    private String sale_name;
    private double coupon_amount;
    private double freight;
    private double pay_amount;
    private double fee_amount;
    private double amount;
    private double shipping_type;
    private MemberModel buyer_info;
    private PayInfoModel pay_info;
    private ShoppingModel shipping;
    private List<OrderitemsModel> order_items;

    public AcountOrderResponse(String id, String order_no, String user_id, String buy_id, String type, String order_time, String status, String pay_id, double order_amount, String sale_name, double coupon_amount, double freight, double pay_amount, double fee_amount, double amount, double shipping_type, MemberModel buyer_info, PayInfoModel pay_info, ShoppingModel shipping, List<OrderitemsModel> order_items) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBuy_id() {
        return buy_id;
    }

    public void setBuy_id(String buy_id) {
        this.buy_id = buy_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
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

    public double getShipping_type() {
        return shipping_type;
    }

    public void setShipping_type(double shipping_type) {
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

    public ShoppingModel getShipping() {
        return shipping;
    }

    public void setShipping(ShoppingModel shipping) {
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
//        "order_time": "2017-06-28 16:04:39",
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
