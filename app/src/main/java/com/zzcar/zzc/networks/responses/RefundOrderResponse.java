package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.OrderItemModle;

import java.util.List;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/21.
 */

public class RefundOrderResponse {
    private String order_no;
    private String amount;//实际退款金额
    private String apply_time;
    private String pay_name;
    private List<OrderItemModle> order_items;

    public RefundOrderResponse(String order_no, String amount, String apply_time, String pay_name, List<OrderItemModle> order_items) {
        this.order_no = order_no;
        this.amount = amount;
        this.apply_time = apply_time;
        this.pay_name = pay_name;
        this.order_items = order_items;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public List<OrderItemModle> getOrder_items() {
        return order_items;
    }

    public void setOrder_items(List<OrderItemModle> order_items) {
        this.order_items = order_items;
    }
}
//"order_no": "sample string 1",
//        "amount": 2,
//        "apply_time": "2017-05-20 23:50:45",
//        "pay_name": "sample string 4",
//        "order_items":