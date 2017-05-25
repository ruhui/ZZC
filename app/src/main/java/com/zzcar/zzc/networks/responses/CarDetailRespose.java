package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.MemberModel;

import java.util.List;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/6.
 */

public class CarDetailRespose {
    private int product_id;
    private int user_id;
    private String name;
    private double market_price;
    private double price;
    private int stock;
    private boolean promotion;
    private List<String> image_path;
    private String create_time;
    private String update_time;
    private String content;
    private int sale_status;
    private MemberModel member;
    private double mileage;
    private int on_number_year;
    private int on_number_month;
    private String car_province_city;
    private String on_number_province_city;
    private String color;
    private String emission;
    private int out_factory_year;
    private int out_factory_month;
    private double new_car_price;
    private int exp_safe_year;
    private int exp_safe_month;
    private String use_type;
    private boolean is_favorte;

    public CarDetailRespose(int product_id, String name, double market_price, double price, int stock,
                            boolean promotion, List<String> image_path, String create_time, String update_time,
                            String content, int sale_status, MemberModel member, double mileage, int on_number_year,
                            int on_number_month, String car_province_city, String on_number_province_city, String color,
                            String emission, int out_factory_year, int out_factory_month, double new_car_price,
                            int exp_safe_year, int exp_safe_month, String use_type, boolean is_favorte, int user_id) {
        this.product_id = product_id;
        this.name = name;
        this.market_price = market_price;
        this.price = price;
        this.stock = stock;
        this.promotion = promotion;
        this.image_path = image_path;
        this.create_time = create_time;
        this.update_time = update_time;
        this.content = content;
        this.sale_status = sale_status;
        this.member = member;
        this.mileage = mileage;
        this.on_number_year = on_number_year;
        this.on_number_month = on_number_month;
        this.car_province_city = car_province_city;
        this.on_number_province_city = on_number_province_city;
        this.color = color;
        this.emission = emission;
        this.out_factory_year = out_factory_year;
        this.out_factory_month = out_factory_month;
        this.new_car_price = new_car_price;
        this.exp_safe_year = exp_safe_year;
        this.exp_safe_month = exp_safe_month;
        this.use_type = use_type;
        this.is_favorte = is_favorte;
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean is_favorte() {
        return is_favorte;
    }

    public void setIs_favorte(boolean is_favorte) {
        this.is_favorte = is_favorte;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public List<String> getImage_path() {
        return image_path;
    }

    public void setImage_path(List<String> image_path) {
        this.image_path = image_path;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSale_status() {
        return sale_status;
    }

    public void setSale_status(int sale_status) {
        this.sale_status = sale_status;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
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

    public int getOn_number_month() {
        return on_number_month;
    }

    public void setOn_number_month(int on_number_month) {
        this.on_number_month = on_number_month;
    }

    public String getCar_province_city() {
        return car_province_city;
    }

    public void setCar_province_city(String car_province_city) {
        this.car_province_city = car_province_city;
    }

    public String getOn_number_province_city() {
        return on_number_province_city;
    }

    public void setOn_number_province_city(String on_number_province_city) {
        this.on_number_province_city = on_number_province_city;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEmission() {
        return emission;
    }

    public void setEmission(String emission) {
        this.emission = emission;
    }

    public int getOut_factory_year() {
        return out_factory_year;
    }

    public void setOut_factory_year(int out_factory_year) {
        this.out_factory_year = out_factory_year;
    }

    public int getOut_factory_month() {
        return out_factory_month;
    }

    public void setOut_factory_month(int out_factory_month) {
        this.out_factory_month = out_factory_month;
    }

    public double getNew_car_price() {
        return new_car_price;
    }

    public void setNew_car_price(double new_car_price) {
        this.new_car_price = new_car_price;
    }

    public int getExp_safe_year() {
        return exp_safe_year;
    }

    public void setExp_safe_year(int exp_safe_year) {
        this.exp_safe_year = exp_safe_year;
    }

    public int getExp_safe_month() {
        return exp_safe_month;
    }

    public void setExp_safe_month(int exp_safe_month) {
        this.exp_safe_month = exp_safe_month;
    }

    public String getUse_type() {
        return use_type;
    }

    public void setUse_type(String use_type) {
        this.use_type = use_type;
    }
}
//"product_id": 1,
//        "name": "sample string 2",
//        "market_price": 3.0,
//        "price": 4.0,
//        "stock": 5,
//        "promotion": true,
//        "image_path": [
//        "sample string 1",
//        "sample string 2"
//        ],
//        "create_time": "2017-05-06 22:35:27",
//        "update_time": "2017-05-06 22:35:27",
//        "content": "sample string 9",
//        "sale_status": 1,
//        "member": {
//        "nick": "sample string 1",
//        "photo": "sample string 2",
//        "remark": "sample string 3",
//        "auth_status": 0,
//        "auth_status_name": "未认证",
//        "shop_name": "sample string 4"
//        },
//        "mileage": 10.0,
//        "on_number_year": 11,
//        "on_number_month": 12,
//        "car_province_city": "sample string 13",
//        "on_number_province_city": "sample string 14",
//        "color": "sample string 15",
//        "emission": "sample string 16",
//        "out_factory_year": 17,
//        "out_factory_month": 18,
//        "new_car_price": 19.0,
//        "exp_safe_year": 20,
//        "exp_safe_month": 21,
//        "use_type": "sample string 22"
