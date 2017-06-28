package com.zzcar.zzc.networks.responses;

/**
 * 创建时间： 2017/6/28.
 * 作者：黄如辉
 * 功能描述：
 */

public class AddressResponse {
    private String id;
    private String user_id;
    private String province_id;
    private String city_id;
    private String area_id;
    private String ship_to;
    private String address;
    private String phone;
    private boolean is_default;
    private String region_name;

    public AddressResponse(String id, String user_id, String province_id, String city_id, String area_id, String ship_to, String address, String phone, boolean is_default, String region_name) {
        this.id = id;
        this.user_id = user_id;
        this.province_id = province_id;
        this.city_id = city_id;
        this.area_id = area_id;
        this.ship_to = ship_to;
        this.address = address;
        this.phone = phone;
        this.is_default = is_default;
        this.region_name = region_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getShip_to() {
        return ship_to;
    }

    public void setShip_to(String ship_to) {
        this.ship_to = ship_to;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean is_default() {
        return is_default;
    }

    public void setIs_default(boolean is_default) {
        this.is_default = is_default;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
}
// "id": 1,
//         "user_id": 2,
//         "province_id": 3,
//         "city_id": 4,
//         "area_id": 5,
//         "ship_to": "sample string 6",
//         "address": "sample string 7",
//         "phone": "sample string 8",
//         "is_default": true,
//         "region_name": "sample string 10"
