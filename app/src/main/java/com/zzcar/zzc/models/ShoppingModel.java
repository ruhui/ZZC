package com.zzcar.zzc.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/14 14:57
 **/
public class ShoppingModel {
    private String order_id;
    private String ship_to;
    private String phone;
    private String region_id;
    private String region_name;
    private String address;
    private String express_no;
    private String express_company_name;
    private String ship_order_number;
    private String shipping_time;

    public ShoppingModel(String order_id, String ship_to, String phone, String region_id, String region_name, String address, String express_no, String express_company_name, String ship_order_number, String shipping_time) {
        this.order_id = order_id;
        this.ship_to = ship_to;
        this.phone = phone;
        this.region_id = region_id;
        this.region_name = region_name;
        this.address = address;
        this.express_no = express_no;
        this.express_company_name = express_company_name;
        this.ship_order_number = ship_order_number;
        this.shipping_time = shipping_time;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getShip_to() {
        return ship_to;
    }

    public void setShip_to(String ship_to) {
        this.ship_to = ship_to;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExpress_no() {
        return express_no;
    }

    public void setExpress_no(String express_no) {
        this.express_no = express_no;
    }

    public String getExpress_company_name() {
        return express_company_name;
    }

    public void setExpress_company_name(String express_company_name) {
        this.express_company_name = express_company_name;
    }

    public String getShip_order_number() {
        return ship_order_number;
    }

    public void setShip_order_number(String ship_order_number) {
        this.ship_order_number = ship_order_number;
    }

    public String getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(String shipping_time) {
        this.shipping_time = shipping_time;
    }
}
//"order_id": 1,
//        "ship_to": "sample string 2",
//        "phone": "sample string 3",
//        "region_id": 4,
//        "region_name": "sample string 5",
//        "address": "sample string 6",
//        "express_no": "sample string 7",
//        "express_company_name": "sample string 8",
//        "ship_order_number": "sample string 9",
//        "shipping_time": "2017-06-14 14:46:50"
