package com.zzcar.zzc.networks.requests;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/19.
 */

public class BuyIntegraRequest {
    public String amount;
    public String pay_code;

    public BuyIntegraRequest(String amount, String pay_code) {
        this.amount = amount;
        this.pay_code = pay_code;
    }
}
// "amount": 2.0,
//         "pay_code": "sample string 3"