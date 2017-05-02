package com.zzcar.zzc.networks.requests;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/3.
 */

public class ParametersRequest {
    private String channel="";
    private List<String> emission_ids = new ArrayList<>();
    private List<String> color_ids = new ArrayList<>();
    private String bland_id="";
    private String series_id="";
    private String year_id="";
    private String spec_id="";
    private String province_id="";
    private String city_id="";
    private String price_type="";
    private String min_price="";
    private String max_price="";
    private String mileage="";
    private String min_mileage="";
    private String max_mileage="";


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
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

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getPrice_type() {
        return price_type;
    }

    public void setPrice_type(String price_type) {
        this.price_type = price_type;
    }

    public String getMin_price() {
        return min_price;
    }

    public void setMin_price(String min_price) {
        this.min_price = min_price;
    }

    public String getMax_price() {
        return max_price;
    }

    public void setMax_price(String max_price) {
        this.max_price = max_price;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getMin_mileage() {
        return min_mileage;
    }

    public void setMin_mileage(String min_mileage) {
        this.min_mileage = min_mileage;
    }

    public String getMax_mileage() {
        return max_mileage;
    }

    public void setMax_mileage(String max_mileage) {
        this.max_mileage = max_mileage;
    }
}
