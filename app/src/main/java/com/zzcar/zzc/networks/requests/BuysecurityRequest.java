package com.zzcar.zzc.networks.requests;

/**
 * 创建时间： 2017/6/13.
 * 作者：黄如辉
 * 功能描述：
 */

public class BuysecurityRequest {
    private int amount = 3000;
    public String pay_code;

    public BuysecurityRequest(String pay_code) {
        this.pay_code = pay_code;
    }
}
