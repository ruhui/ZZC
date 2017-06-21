package com.zzcar.zzc.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间： 2017/6/21.
 * 作者：黄如辉
 * 功能描述：
 */

public class SupplyPropsMiddleModel implements Serializable{
    private String channeldes = "";//渠道
    private String bland_iddes = "";//品牌车系
    private String emission_des = "";//排放
    private String colorDes = "";//颜色
    private String mileagedes = "";//表现历程
    private String addressdes = "";//所在地

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

    public SupplyPropsMiddleModel() {}

    public String getChanneldes() {
        return channeldes;
    }

    public void setChanneldes(String channeldes) {
        this.channeldes = channeldes;
    }

    public String getBland_iddes() {
        return bland_iddes;
    }

    public void setBland_iddes(String bland_iddes) {
        this.bland_iddes = bland_iddes;
    }

    public String getEmission_des() {
        return emission_des;
    }

    public void setEmission_des(String emission_des) {
        this.emission_des = emission_des;
    }

    public String getColorDes() {
        return colorDes;
    }

    public void setColorDes(String colorDes) {
        this.colorDes = colorDes;
    }

    public String getMileagedes() {
        return mileagedes;
    }

    public void setMileagedes(String mileagedes) {
        this.mileagedes = mileagedes;
    }

    public String getAddressdes() {
        return addressdes;
    }

    public void setAddressdes(String addressdes) {
        this.addressdes = addressdes;
    }

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

    public void resetData(){
        List<String> emission_ids = new ArrayList<>();
        List<String> color_ids = new ArrayList<>();
        setChanneldes("");
        setBland_iddes("");
        setEmission_des("");
        setChanneldes("");
        setMileagedes("");
        setAddressdes("");
        setChannel(0);
        setEmission_ids(emission_ids);
        setColor_ids(color_ids);
        setBland_id(0);
        setSeries_id(0);
        setYear_id(0);
        setSeries_id(0);
        setProvince_id(0);
        setCity_id(0);
        setPrice_type(0);
        setMin_price(0);
        setMax_price(0);
        setMileage(0);
        setMin_mileage(0);
        setMax_mileage(0);
    }

    public SupplyPropsModel getSupplyProp(SupplyPropsMiddleModel model){
        SupplyPropsModel props = new SupplyPropsModel();
        props.setChannel(model.getChannel());
        props.setEmission_ids(model.getEmission_ids());
        props.setColor_ids(model.getColor_ids());
        props.setBland_id(model.getBland_id());
        props.setSeries_id(model.getSeries_id());
        props.setYear_id(model.getYear_id());
        props.setSpec_id(model.getSpec_id());
        props.setProvince_id(model.getProvince_id());
        props.setCity_id(model.getCity_id());
        props.setPrice_type(model.getPrice_type());
        props.setMin_price(model.getMin_price());
        props.setMax_price(model.getMax_price());
        props.setMileage(model.getMileage());
        props.setMin_mileage(model.getMin_mileage());
        props.setMax_mileage(model.getMax_mileage());
        return props;
    }
}
