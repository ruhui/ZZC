package com.zzcar.zzc.models;

import java.io.Serializable;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/13 11:06
 **/
public class BodyModel implements Serializable{
    private String mileage;
    private String on_number_year;
    private String car_province_city;

    public BodyModel(String mileage, String on_number_year, String car_province_city) {
        this.mileage = mileage;
        this.on_number_year = on_number_year;
        this.car_province_city = car_province_city;
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
