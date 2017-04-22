package com.zzcar.zzc.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/22 18:32
 **/
public class HomeCarGet {
    private int product_id;
    private String name;
    private double market_price;
    private int stock;
    private boolean promotion;
    private String first_image;
    private String update_time;
    private HomeCarMember homeCarMember;
    private double mileage;
    private int on_number_year;
    private String car_province_city;

    public HomeCarGet(int product_id, String name, double market_price, int stock, boolean promotion,
                      String first_image, String update_time, HomeCarMember homeCarMember, double mileage, int on_number_year, String car_province_city) {
        this.product_id = product_id;
        this.name = name;
        this.market_price = market_price;
        this.stock = stock;
        this.promotion = promotion;
        this.first_image = first_image;
        this.update_time = update_time;
        this.homeCarMember = homeCarMember;
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

    public double getMarket_price() {
        return market_price;
    }

    public void setMarket_price(double market_price) {
        this.market_price = market_price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isPromotion() {
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

    public HomeCarMember getHomeCarMember() {
        return homeCarMember;
    }

    public void setHomeCarMember(HomeCarMember homeCarMember) {
        this.homeCarMember = homeCarMember;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public int getOn_number_year() {
        return on_number_year;
    }

    public void setOn_number_year(int on_number_year) {
        this.on_number_year = on_number_year;
    }

    public String getCar_province_city() {
        return car_province_city;
    }

    public void setCar_province_city(String car_province_city) {
        this.car_province_city = car_province_city;
    }
}

//{
//        "product_id": 1,
//        "name": "sample string 2",
//        "market_price": 3.0,
//        "stock": 4,
//        "promotion": true,
//        "first_image": "sample string 6",
//        "update_time": "2017-04-22 18:24:41",
//        "member": {
//
//        },
//        "mileage": 8.0,
//        "on_number_year": 9,
//        "car_province_city": "sample string 10"
//        },