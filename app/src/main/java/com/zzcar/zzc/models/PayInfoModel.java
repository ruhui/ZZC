package com.zzcar.zzc.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/14 14:55
 **/
public class PayInfoModel {
    private String id;
    private String order_no;
    private String pay_type;
    private String pay_code;
    private String pay_name;
    private String pay_no;
    private String transaction_no;
    private String create_time;
    private String pay_time;

    public PayInfoModel(String id, String order_no, String pay_type, String pay_code, String pay_name, String pay_no, String transaction_no, String create_time, String pay_time) {
        this.id = id;
        this.order_no = order_no;
        this.pay_type = pay_type;
        this.pay_code = pay_code;
        this.pay_name = pay_name;
        this.pay_no = pay_no;
        this.transaction_no = transaction_no;
        this.create_time = create_time;
        this.pay_time = pay_time;
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

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_code() {
//        alipay支付宝，weixin微信，unionpay银联
        if (pay_code.contains("alipay")){
            return "支付宝";
        }else if (pay_code.contains("weixin")){
            return "微信";
        }else if (pay_code.contains("unionpay")){
            return "银联";
        }else {
            return pay_code;
        }
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

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }
}
//id": 1,
//        "order_no": "sample string 2",
//        "pay_type": 1,
//        "pay_code": "sample string 3",
//        "pay_name": "sample string 4",
//        "pay_no": "sample string 5",
//        "transaction_no": "sample string 6",
//        "create_time": "2017-06-14 14:46:50",
//        "pay_time": "2017-06-14 14:46:50"
