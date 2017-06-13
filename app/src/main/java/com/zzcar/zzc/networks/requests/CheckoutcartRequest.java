package com.zzcar.zzc.networks.requests;

import com.zzcar.zzc.models.CheckoutcartModel;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/13 10:31
 **/
public class CheckoutcartRequest {
    public List<CheckoutcartModel> items;
    public int shipping_type;

    public CheckoutcartRequest(List<CheckoutcartModel> items, int shipping_type) {
        this.items = items;
        this.shipping_type = shipping_type;
    }
}
