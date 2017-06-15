package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.SingleCarDes;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/15 17:55
 **/
public class SingleSupplyResponse {
    private int info_id;
    private List<String> image_path;
    private String content;
    private int bland_id;
    private int series_id;
    private int year_id;
    private int spec_id;
    private double mileage;
    private int on_number_year;
    private int on_number_month;
    private int car_province_id;
    private int car_city_id;
    private int on_number_province_id;
    private int on_number_city_id;
    private int color;
    private int emission;
    private int out_factory_year;
    private int out_factory_month;
    private double new_car_price;
    private int exp_safe_year;
    private int exp_safe_month;
    private int use_type;
    private SingleCarDes props_name;

    public SingleSupplyResponse(int info_id, List<String> image_path, String content, int bland_id, int series_id, int year_id, int spec_id, double mileage, int on_number_year, int on_number_month, int car_province_id, int car_city_id, int on_number_province_id, int on_number_city_id, int color, int emission, int out_factory_year, int out_factory_month, double new_car_price, int exp_safe_year, int exp_safe_month, int use_type, SingleCarDes props_name) {
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
        this.car_province_id = car_province_id;
        this.car_city_id = car_city_id;
        this.on_number_province_id = on_number_province_id;
        this.on_number_city_id = on_number_city_id;
        this.color = color;
        this.emission = emission;
        this.out_factory_year = out_factory_year;
        this.out_factory_month = out_factory_month;
        this.new_car_price = new_car_price;
        this.exp_safe_year = exp_safe_year;
        this.exp_safe_month = exp_safe_month;
        this.use_type = use_type;
        this.props_name = props_name;
    }

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
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

    public int getBland_id() {
        return bland_id;
    }

    public void setBland_id(int bland_id) {
        this.bland_id = bland_id;
    }

    public int getSeries_id() {
        return series_id;
    }

    public void setSeries_id(int series_id) {
        this.series_id = series_id;
    }

    public int getYear_id() {
        return year_id;
    }

    public void setYear_id(int year_id) {
        this.year_id = year_id;
    }

    public int getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(int spec_id) {
        this.spec_id = spec_id;
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

    public int getCar_province_id() {
        return car_province_id;
    }

    public void setCar_province_id(int car_province_id) {
        this.car_province_id = car_province_id;
    }

    public int getCar_city_id() {
        return car_city_id;
    }

    public void setCar_city_id(int car_city_id) {
        this.car_city_id = car_city_id;
    }

    public int getOn_number_province_id() {
        return on_number_province_id;
    }

    public void setOn_number_province_id(int on_number_province_id) {
        this.on_number_province_id = on_number_province_id;
    }

    public int getOn_number_city_id() {
        return on_number_city_id;
    }

    public void setOn_number_city_id(int on_number_city_id) {
        this.on_number_city_id = on_number_city_id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getEmission() {
        return emission;
    }

    public void setEmission(int emission) {
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

    public int getUse_type() {
        return use_type;
    }

    public void setUse_type(int use_type) {
        this.use_type = use_type;
    }

    public SingleCarDes getProps_name() {
        return props_name;
    }

    public void setProps_name(SingleCarDes props_name) {
        this.props_name = props_name;
    }
}

