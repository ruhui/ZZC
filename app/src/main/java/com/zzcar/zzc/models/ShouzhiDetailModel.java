package com.zzcar.zzc.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/17 19:51
 **/
public class ShouzhiDetailModel {
    private int id;
    private int bill_id;
    private int object_id;
    private int bill_type;
    private String type_name;
    private double amount;
    private double balance;
    private int status;
    private String status_name;
    private String order_no;
    private String pay_code;
    private String pay_name;
    private String pay_no;
    private String transaction_no;
    private String create_time;

    public ShouzhiDetailModel(int id, int bill_id, int object_id, int bill_type, String type_name,
                              double amount, double balance, int status, String status_name, String order_no,
                              String pay_code, String pay_name, String pay_no, String transaction_no, String create_time) {
        this.id = id;
        this.bill_id = bill_id;
        this.object_id = object_id;
        this.bill_type = bill_type;
        this.type_name = type_name;
        this.amount = amount;
        this.balance = balance;
        this.status = status;
        this.status_name = status_name;
        this.order_no = order_no;
        this.pay_code = pay_code;
        this.pay_name = pay_name;
        this.pay_no = pay_no;
        this.transaction_no = transaction_no;
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public int getBill_type() {
        return bill_type;
    }

    public void setBill_type(int bill_type) {
        this.bill_type = bill_type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public String getTransaction_no() {
        return transaction_no;
    }

    public void setTransaction_no(String transaction_no) {
        this.transaction_no = transaction_no;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
//"id": 1,
//        "bill_id": 2,
//        "object_id": 1,
//        "bill_type": 0,
//        "type_name": "",
//        "amount": 3,
//        "balance": 4,
//        "status": 0,
//        "status_name": "",
//        "order_no": "sample string 5",
//        "pay_code": "sample string 6",
//        "pay_name": "sample string 7",
//        "pay_no": "sample string 8",
//        "transaction_no": "sample string 9",
//        "create_time": "2017-05-17 19:50:17"
