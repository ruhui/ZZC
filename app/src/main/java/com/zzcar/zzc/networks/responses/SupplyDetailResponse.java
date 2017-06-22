package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.MemberModel;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/22 16:24
 **/
public class SupplyDetailResponse {
    private int info_id;
    private String name;
    private List<String> image_path;
    private String start_time;
    private String end_time;
    private String content;
    private MemberModel member;
    private double  mileage;
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

    public SupplyDetailResponse(int info_id, String name, List<String> image_path, String start_time, String end_time, String content, MemberModel member, double mileage, int on_number_year, int on_number_month, String car_province_city, String on_number_province_city, String color, String emission, int out_factory_year, int out_factory_month, double new_car_price, int exp_safe_year, int exp_safe_month, String use_type) {
        this.info_id = info_id;
        this.name = name;
        this.image_path = image_path;
        this.start_time = start_time;
        this.end_time = end_time;
        this.content = content;
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
    }

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImage_path() {
        return image_path;
    }

    public void setImage_path(List<String> image_path) {
        this.image_path = image_path;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
