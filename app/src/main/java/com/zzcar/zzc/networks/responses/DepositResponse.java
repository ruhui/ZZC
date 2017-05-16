package com.zzcar.zzc.networks.responses;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/16 13:49
 **/
public class DepositResponse {
    private String bank;//开户银行,字典编号
    private String bank_name;//开户银行，中文
    private String bank_card;//卡号
    private String account_name;//开户姓名/公司名称
    private int type;//提现账户类型：1个人，2公司
    private String code;//手机验证码

    public DepositResponse(String bank, String bank_name, String bank_card, String account_name, int type, String code) {
        this.bank = bank;
        this.bank_name = bank_name;
        this.bank_card = bank_card;
        this.account_name = account_name;
        this.type = type;
        this.code = code;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
//"bank": "sample string 1",
//        "bank_name": "sample string 2",
//        "bank_card": "sample string 3",
//        "account_name": "sample string 4",
//        "type": 5,
//        "code": "sample string 6"