package com.zzcar.zzc.networks.requests;

/**
 * 创建时间： 2017/7/11.
 * 作者：黄如辉
 * 功能描述：
 */

public class ShippingTypeRequest {
    public String order_no;
    public String shipping_type;

    public ShippingTypeRequest(String order_no, String shipping_type) {
        this.order_no = order_no;
        this.shipping_type = shipping_type;
    }
}
//  "order_no": "sample string 1",
//          "shipping_type": 1