package com.zzcar.zzc.models;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间： 2017/6/21.
 * 作者：黄如辉
 * 功能描述：
 */

public class SupplyPropsModel {
    private int channel;
    private List<String> emission_ids = new ArrayList<>();
    private List<String> color_ids = new ArrayList<>();
    private int bland_id;
    private int series_id;
    private int year_id;
    private int spec_id;
    private int province_id;
    private int city_id;
    private int price_type;
    private double min_price;
    private double max_price;
    private int mileage;
    private double min_mileage;
    private double max_mileage;

    public SupplyPropsModel() {}

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public List<String> getEmission_ids() {
        return emission_ids;
    }

    public void setEmission_ids(List<String> emission_ids) {
        this.emission_ids = emission_ids;
    }

    public List<String> getColor_ids() {
        return color_ids;
    }

    public void setColor_ids(List<String> color_ids) {
        this.color_ids = color_ids;
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

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getPrice_type() {
        return price_type;
    }

    public void setPrice_type(int price_type) {
        this.price_type = price_type;
    }

    public double getMin_price() {
        return min_price;
    }

    public void setMin_price(double min_price) {
        this.min_price = min_price;
    }

    public double getMax_price() {
        return max_price;
    }

    public void setMax_price(double max_price) {
        this.max_price = max_price;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getMin_mileage() {
        return min_mileage;
    }

    public void setMin_mileage(double min_mileage) {
        this.min_mileage = min_mileage;
    }

    public double getMax_mileage() {
        return max_mileage;
    }

    public void setMax_mileage(double max_mileage) {
        this.max_mileage = max_mileage;
    }
}
