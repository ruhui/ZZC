package com.zzcar.zzc.networks.requests;

/**
 * 创建时间： 2017/6/29.
 * 作者：黄如辉
 * 功能描述：
 */

public class SaveAddressaRequest {
    public long id;
    public long user_id;
    public long province_id;
    public long city_id;
    public long area_id;
    public String ship_to;
    public String address;
    public String phone;
    public boolean is_default;
    public String region_name;

    public SaveAddressaRequest(){}

    public SaveAddressaRequest(long id, long user_id, long province_id, long city_id, long area_id, String ship_to, String address, String phone, boolean is_default, String region_name) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getProvince_id() {
        return province_id;
    }

    public void setProvince_id(long province_id) {
        this.province_id = province_id;
    }

    public long getCity_id() {
        return city_id;
    }

    public void setCity_id(long city_id) {
        this.city_id = city_id;
    }

    public long getArea_id() {
        return area_id;
    }

    public void setArea_id(long area_id) {
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
