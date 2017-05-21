package com.zzcar.zzc.models;

import java.util.List;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/21.
 */

public class MyfavcarModle {
    private int product_id;
    private String name;
    private String market_price;
    private int stock;
    private boolean promotion;
    private String first_image;
    private String update_time;
    private MemberModel member;
    private String mileage;
    private String on_number_year;
    private String car_province_city;

    public MyfavcarModle(int product_id, String name, String market_price, int stock,
                         boolean promotion, String first_image, String update_time, MemberModel member,
                         String mileage, String on_number_year, String car_province_city) {
        this.product_id = product_id;
        this.name = name;
        this.market_price = market_price;
        this.stock = stock;
        this.promotion = promotion;
        this.first_image = first_image;
        this.update_time = update_time;
        this.member = member;
        this.mileage = mileage;
        this.on_number_year = on_number_year;
        this.car_province_city = car_province_city;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean getPromotion() {
        return promotion;
    }

    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }

    public String getFirst_image() {
        return first_image;
    }

    public void setFirst_image(String first_image) {
        this.first_image = first_image;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getOn_number_year() {
        return on_number_year;
    }

    public void setOn_number_year(String on_number_year) {
        this.on_number_year = on_number_year;
    }

    public String getCar_province_city() {
        return car_province_city;
    }

    public void setCar_province_city(String car_province_city) {
        this.car_province_city = car_province_city;
    }
}
//  "product_id": 1,
//          "name": "sample string 2",
//          "market_price": 3,
//          "stock": 4,
//          "promotion": true,
//          "first_image": "sample string 6",
//          "update_time": "2017-05-21 09:59:28",
//          "member": {
//          "nick": "sample string 1",
//          "photo": "sample string 2",
//          "remark": "sample string 3",
//          "auth_status_name": "未认证",
//          "shop_name": "sample string 4",
//          "security": true
//          },
//          "mileage": 8,
//          "on_number_year": 9,
//          "car_province_city": "sample string 10"
