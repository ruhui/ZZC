package com.zzcar.zzc.models;

import java.io.Serializable;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/13 10:39
 **/
public class ShippingModel implements Serializable{
    private String order_no;
    private String ship_to;
    private String phone;
    private String province_id;
    private String city_id;
    private String area_id;
    private String region_name;
    private String address;
    private String order_id;
    private String region_id;
    private String express_no;
    private String express_company_name;
    private String ship_order_number;
    private String shipping_time;

    public ShippingModel(String order_no, String ship_to, String phone, String province_id, String city_id, String area_id, String region_name, String address, String order_id, String region_id, String express_no, String express_company_name, String ship_order_number, String shipping_time) {
        this.order_no = order_no;
        this.ship_to = ship_to;
        this.phone = phone;
        this.province_id = province_id;
        this.city_id = city_id;
        this.area_id = area_id;
        this.region_name = region_name;
        this.address = address;
        this.order_id = order_id;
        this.region_id = region_id;
        this.express_no = express_no;
        this.express_company_name = express_company_name;
        this.ship_order_number = ship_order_number;
        this.shipping_time = shipping_time;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
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

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
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
//"order_no": "sample string 1",
//        "ship_to": "sample string 2",
//        "phone": "sample string 3",
//        "province_id": 4,
//        "city_id": 5,
//        "area_id": 6,
//        "region_name": "sample string 7",
//        "address": "sample string 8"
