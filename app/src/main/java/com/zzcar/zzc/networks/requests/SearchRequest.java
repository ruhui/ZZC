package com.zzcar.zzc.networks.requests;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/25 17:52
 **/
public class SearchRequest {
    private String sort="1";
    private String channel="";
    private List<String> emission_ids;
    private List<String> color_ids;
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

    private String sortdes = "排序";
    private String channeldes = "渠道";
    private String bland_iddes = "品牌";
    private String price_typedes = "价格";
    private String emission_des = "";

    public String getSortdes() {
        return sortdes;
    }

    public String getEmission_des() {
        return emission_des;
    }

    public void setEmission_des(String emission_des) {
        this.emission_des = emission_des;
    }

    public String getChanneldes() {
        return channeldes;
    }

    public String getBland_iddes() {
        return bland_iddes;
    }

    public String getPrice_typedes() {
        return price_typedes;
    }

    public void setSortdes(String sortdes) {
        this.sortdes = sortdes;
    }

    public void setChanneldes(String channeldes) {
        this.channeldes = channeldes;
    }

    public void setBland_iddes(String bland_iddes) {
        this.bland_iddes = bland_iddes;
    }

    public void setPrice_typedes(String price_typedes) {
        this.price_typedes = price_typedes;
    }

    public SearchRequest() {}

    /*默认状态*/
    public void defaultStatus(){
        sort = "1";channel="";emission_ids.clear();color_ids.clear();bland_id="";series_id="";year_id="";max_mileage="";
        spec_id="";province_id="";city_id="";price_type="";min_price="";max_price="";mileage="";min_mileage="";
        sortdes="排序";channeldes="渠道";bland_iddes="品牌";price_typedes="价格";emission_des = "";
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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


//channel 渠道：1二手车行、24S店integerNone.
// emission_ids排放 Collection of integer None.
// color_ids颜色数组 Collection of integerNone.
// bland_id品牌id longNone.
// series_id 车系id longNone.
// year_id年份id longNone.
// spec_id车型idlongNone.
// province_id 所在省id  long None.
// city_id 所在城市id long  None.
// price_type 价格区间  integer None.
// min_price 自定义查询最低价格  decimal number   None.
// max_price 自定义查询最高价格  decimal number  None.
// mileage  里程区间 integer None.
// min_mileage 自定义查询最低里程 decimal number None.
// max_mileage 自定义查询最高里程