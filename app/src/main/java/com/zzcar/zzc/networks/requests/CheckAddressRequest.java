package com.zzcar.zzc.networks.requests;

/**
 * 创建时间： 2017/7/11.
 * 作者：黄如辉
 * 功能描述：
 */

public class CheckAddressRequest {
    public String order_no;
    public String ship_to;
    public String phone;
    public String province_id;
    public String city_id;
    public String area_id;
    public String region_name;
    public String address;

    public CheckAddressRequest(String order_no, String ship_to, String phone, String province_id, String city_id, String area_id, String region_name, String address) {
        this.order_no = order_no;
        this.ship_to = ship_to;
        this.phone = phone;
        this.province_id = province_id;
        this.city_id = city_id;
        this.area_id = area_id;
        this.region_name = region_name;
        this.address = address;
    }
}
//"order_no": "sample string 1",
//        "ship_to": "sample string 2",
//        "phone": "sample string 3",
//        "province_id": 4,
//        "city_id": 5,
//        "area_id": 6,
//        "region_name": "sample string 7",
//        "address": "sample string 8"