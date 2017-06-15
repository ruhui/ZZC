package com.zzcar.zzc.models;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：增加车源
 * 作者：黄如辉  时间 2017/5/3.
 */

public class SaveSupplyModel {
    private String info_id;//信息id 0
    private List<String> image_path = new ArrayList<>();//图片JSON:["",""] 1
    private String content;//车况描述 1
    private String bland_id;//品牌id 1
    private String series_id;//车系id 1
    private String year_id;//年份id 1
    private String spec_id;//车型id 1
    private String mileage;//里程 1
    private String on_number_year;//上牌年份
    private String on_number_month;//上牌月份
    private String car_city_id;//车当前所在城市
    private String on_number_province_id;//牌照归属地省份
    private String on_number_city_id;//牌照归属地城市
    private String car_province_id;//车当前所在省份
    private String color;//车身颜色
    private String emission;//排放
    private String out_factory_year;//出厂年份
    private String out_factory_month;//出厂月份
    private String new_car_price;//新车指导价
    private String exp_safe_year;//强险到期年份
    private String exp_safe_month;//强险到期月份
    private String use_type;//用途

    public SaveSupplyModel() {}

    public SaveSupplyModel(String info_id,  List<String> image_path,
                           String content, String bland_id, String series_id, String year_id,
                           String spec_id, String mileage, String on_number_year, String on_number_month,
                           String car_city_id, String on_number_province_id, String on_number_city_id,
                           String car_province_id, String color, String emission, String out_factory_year,
                           String out_factory_month, String new_car_price, String exp_safe_year,
                           String exp_safe_month, String use_type) {
        this.info_id = info_id;
        this.image_path = image_path;
        this.content = content;
        this.bland_id = bland_id;
        this.series_id = series_id;
        this.year_id = year_id;
        this.spec_id = spec_id;
        this.mileage = mileage;
        this.on_number_year = on_number_year;
        this.on_number_month = on_number_month;
        this.car_city_id = car_city_id;
        this.on_number_province_id = on_number_province_id;
        this.on_number_city_id = on_number_city_id;
        this.car_province_id = car_province_id;
        this.color = color;
        this.emission = emission;
        this.out_factory_year = out_factory_year;
        this.out_factory_month = out_factory_month;
        this.new_car_price = new_car_price;
        this.exp_safe_year = exp_safe_year;
        this.exp_safe_month = exp_safe_month;
        this.use_type = use_type;
    }

    public String getProduct_id() {
        return info_id;
    }

    public void setProduct_id(String product_id) {
        this.info_id = product_id;
    }

    public List<String> getImage_path() {
        return image_path;
    }

    public void setImage_path(List<String> image_path) {
        this.image_path = image_path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBland_id() {
        return bland_id;
    }

    public void setBland_id(String bland_id) {
        this.bland_id = bland_id;
    }

    public String getSeries_id() {
        return series_id;
    }

    public void setSeries_id(String series_id) {
        this.series_id = series_id;
    }

    public String getYear_id() {
        return year_id;
    }

    public void setYear_id(String year_id) {
        this.year_id = year_id;
    }

    public String getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(String spec_id) {
        this.spec_id = spec_id;
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

    public String getOn_number_month() {
        return on_number_month;
    }

    public void setOn_number_month(String on_number_month) {
        this.on_number_month = on_number_month;
    }

    public String getCar_city_id() {
        return car_city_id;
    }

    public void setCar_city_id(String car_city_id) {
        this.car_city_id = car_city_id;
    }

    public String getOn_number_province_id() {
        return on_number_province_id;
    }

    public void setOn_number_province_id(String on_number_province_id) {
        this.on_number_province_id = on_number_province_id;
    }

    public String getOn_number_city_id() {
        return on_number_city_id;
    }

    public void setOn_number_city_id(String on_number_city_id) {
        this.on_number_city_id = on_number_city_id;
    }

    public String getCar_province_id() {
        return car_province_id;
    }

    public void setCar_province_id(String car_province_id) {
        this.car_province_id = car_province_id;
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

    public String getOut_factory_year() {
        return out_factory_year;
    }

    public void setOut_factory_year(String out_factory_year) {
        this.out_factory_year = out_factory_year;
    }

    public String getOut_factory_month() {
        return out_factory_month;
    }

    public void setOut_factory_month(String out_factory_month) {
        this.out_factory_month = out_factory_month;
    }

    public String getNew_car_price() {
        return new_car_price;
    }

    public void setNew_car_price(String new_car_price) {
        this.new_car_price = new_car_price;
    }

    public String getExp_safe_year() {
        return exp_safe_year;
    }

    public void setExp_safe_year(String exp_safe_year) {
        this.exp_safe_year = exp_safe_year;
    }

    public String getExp_safe_month() {
        return exp_safe_month;
    }

    public void setExp_safe_month(String exp_safe_month) {
        this.exp_safe_month = exp_safe_month;
    }

    public String getUse_type() {
        return use_type;
    }

    public void setUse_type(String use_type) {
        this.use_type = use_type;
    }

}

//    "product_id": 2,
//            "market_price": 3.0,
//            "image_path": [
//            "sample string 1",
//            "sample string 2"
//            ],
//            "content": "sample string 4",
//            "bland_id": 5,
//            "series_id": 6,
//            "year_id": 7,
//            "spec_id": 8,
//            "mileage": 9.0,
//            "on_number_year": 10,
//            "on_number_month": 11,
//            "car_province_id": 12,
//            "car_city_id": 13,
//            "on_number_province_id": 14,
//            "on_number_city_id": 15,
//            "color": 16,
//            "emission": 17,
//            "out_factory_year": 18,
//            "out_factory_month": 19,
//            "new_car_price": 20.0,
//            "exp_safe_year": 21,
//            "exp_safe_month": 22,
//            "use_type": 23
