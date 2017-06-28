package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.PayInfoModel;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/28 11:40
 **/
public class TransferDetailResponse {
    private int id;
    private int user_id;
    private String order_no;
    private double amount;
    private int status;
    private double settle_amount;
    private int pay_id;
    private String bank;
    private String bank_card;
    private String account_name;
    private String apply_time;
    private PayInfoModel pay_info;

    public TransferDetailResponse(int id, int user_id, String order_no, double amount, int status, double settle_amount, int pay_id, String bank, String bank_card, String account_name, String apply_time, PayInfoModel pay_info) {
        this.id = id;
        this.user_id = user_id;
        this.order_no = order_no;
        this.amount = amount;
        this.status = status;
        this.settle_amount = settle_amount;
        this.pay_id = pay_id;
        this.bank = bank;
        this.bank_card = bank_card;
        this.account_name = account_name;
        this.apply_time = apply_time;
        this.pay_info = pay_info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        //1提现申请，2完成，3转账中，4失败
        String stautNmae = "";
        switch (status){
            case 1:
                stautNmae = "提现申请";
                break;
            case 2:
                stautNmae = "完成";
                break;
            case 3:
                stautNmae = "转账中";
                break;
            case 4:
                stautNmae = "失败";
                break;
        }
        return stautNmae;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getSettle_amount() {
        return settle_amount;
    }

    public void setSettle_amount(double settle_amount) {
        this.settle_amount = settle_amount;
    }

    public int getPay_id() {
        return pay_id;
    }

    public void setPay_id(int pay_id) {
        this.pay_id = pay_id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    public PayInfoModel getPay_info() {
        return pay_info;
    }

    public void setPay_info(PayInfoModel pay_info) {
        this.pay_info = pay_info;
    }
}
//"id": 1,
//        "user_id": 2,
//        "order_no": "sample string 3",
//        "amount": 4.0,
//        "status": 0,
//        "settle_amount": 5.0,
//        "pay_id": 1,
//        "bank": "sample string 6",
//        "bank_card": "sample string 7",
//        "account_name": "sample string 8",
//        "apply_time": "2017-06-28 11:38:32",