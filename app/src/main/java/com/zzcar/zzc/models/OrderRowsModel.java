package com.zzcar.zzc.models;

import java.util.List;

/**
 * 创建时间： 2017/6/13.
 * 作者：黄如辉
 * 功能描述：
 */

public class OrderRowsModel {
    private int id;
    private String order_no;
    private int status;
    private double amount;
    private boolean buyer_confirm;
    private boolean seller_confirm;
    private List<OrderitemsModel> order_items;

    public OrderRowsModel(int id, String order_no, int status, double amount, boolean buyer_confirm, boolean seller_confirm, List<OrderitemsModel> order_items) {
        this.id = id;
        this.order_no = order_no;
        this.status = status;
        this.amount = amount;
        this.buyer_confirm = buyer_confirm;
        this.seller_confirm = seller_confirm;
        this.order_items = order_items;
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
//"id": 1,
//        "order_no": "sample string 2",
//        "status": 0,
//        "amount": 3.0,
//        "buyer_confirm": true,
//        "seller_confirm": true,
