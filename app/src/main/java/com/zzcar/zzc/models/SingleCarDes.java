package com.zzcar.zzc.models;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/21.
 */

public class SingleCarDes {
    private String car_province_city;
    private String color;
    private String emission;
    private String name;
    private String on_number_province_city;
    private String use_type;

    public SingleCarDes(String car_province_city, String color, String emission, String name, String on_number_province_city, String use_type) {
        this.car_province_city = car_province_city;
        this.color = color;
        this.emission = emission;
        this.name = name;
        this.on_number_province_city = on_number_province_city;
        this.use_type = use_type;
    }

    public String getCar_province_city() {
        return car_province_city;
    }

    public void setCar_province_city(String car_province_city) {
        this.car_province_city = car_province_city;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOn_number_province_city() {
        return on_number_province_city;
    }

    public void setOn_number_province_city(String on_number_province_city) {
        this.on_number_province_city = on_number_province_city;
    }

    public String getUse_type() {
        return use_type;
    }

    public void setUse_type(String use_type) {
        this.use_type = use_type;
    }
}
// "car_province_city" = "\U798f\U5efa \U6f33\U5dde\U5e02";
//         color = "\U84dd\U8272#0000FF";
//         emission = "\U56fd\U56db";
//         name = "2013\U6b3e \U9510\U5fd7 3.0V \U5c0a\U9510\U5bfc\U822a\U7248";
//         "on_number_province_city" = "\U798f\U5efa";
//         "use_type" = "\U975e\U8425\U8fd0";
