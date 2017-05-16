package com.zzcar.zzc.networks.requests;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/16 19:47
 **/
public class ApplyDepositRequest {
    public double amount;
    public String settle_type;
    public int type;

    public ApplyDepositRequest(double amount, String settle_type, int type) {
        this.amount = amount;
        this.settle_type = settle_type;
        this.type = type;
    }
}

//"amount": 2.0,
//        "settle_type": "sample string 3",
//        "type": 1