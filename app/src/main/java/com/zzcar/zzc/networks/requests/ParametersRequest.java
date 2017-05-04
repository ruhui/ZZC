package com.zzcar.zzc.networks.requests;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/3.
 */

public class ParametersRequest {
    private String channel="0";
    private List<String> emission_ids = new ArrayList<>();
    private List<String> color_ids = new ArrayList<>();
    private String bland_id="0";
    private String series_id="0";
    private String year_id="0";
    private String spec_id="0";
    private String province_id="0";
    private String city_id="0";
    private String price_type="0";
    private String min_price="0";
    private String max_price="0";
    private String mileage="0";
    private String min_mileage="0";
    private String max_mileage="0";


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        if (TextUtils.isEmpty(channel)){
            return;
        }
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
        if (TextUtils.isEmpty(bland_id)){
            return;
        }
        this.bland_id = bland_id;
    }

    public String getSeries_id() {
        return series_id;
    }

    public void setSeries_id(String series_id) {
        if (TextUtils.isEmpty(series_id)){
            return;
        }
        this.series_id = series_id;
    }

    public String getYear_id() {
        return year_id;
    }

    public void setYear_id(String year_id) {
        if (TextUtils.isEmpty(year_id)){
            return;
        }
        this.year_id = year_id;
    }

    public String getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(String spec_id) {
        if (TextUtils.isEmpty(spec_id)){
            return;
        }
        this.spec_id = spec_id;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        if (TextUtils.isEmpty(province_id)){
            return;
        }
        this.province_id = province_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        if (TextUtils.isEmpty(city_id)){
            return;
        }
        this.city_id = city_id;
    }

    public String getPrice_type() {
        return price_type;
    }

    public void setPrice_type(String price_type) {
        if (TextUtils.isEmpty(price_type)){
            return;
        }
        this.price_type = price_type;
    }

    public String getMin_price() {

        return min_price;
    }

    public void setMin_price(String min_price) {
        if (TextUtils.isEmpty(min_price)){
            return;
        }
        this.min_price = min_price;
    }

    public String getMax_price() {
        return max_price;
    }

    public void setMax_price(String max_price) {
        if (TextUtils.isEmpty(max_price)){
            return;
        }
        this.max_price = max_price;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        if (TextUtils.isEmpty(mileage)){
            return;
        }
        this.mileage = mileage;
    }

    public String getMin_mileage() {
        return min_mileage;
    }

    public void setMin_mileage(String min_mileage) {
        if (TextUtils.isEmpty(min_mileage)){
            return;
        }
        this.min_mileage = min_mileage;
    }

    public String getMax_mileage() {
        return max_mileage;
    }

    public void setMax_mileage(String max_mileage) {
        if (TextUtils.isEmpty(max_mileage)){
            return;
        }
        this.max_mileage = max_mileage;
    }
}
