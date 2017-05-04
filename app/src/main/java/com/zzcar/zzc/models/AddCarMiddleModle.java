package com.zzcar.zzc.models;

import java.util.List;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/3.
 */

public class AddCarMiddleModle extends AddCarFrom{
    private String bladseriesdes;//品牌車系
    private String belongCityDes;//车辆所属城市
    private String numberBelongDes;//牌照归属城市
    private String colorDes;//颜色描述
    private String emissionDes;//排放描述
    private String usertypeDes;//用途描述
    private String outfactoryDes;//出厂时间描述
    private String cardTimeDes;//上牌时间描述
    private String safeDes;//交强险到期描述

    public AddCarMiddleModle(){
        super();
    }

    public AddCarMiddleModle(String product_id, String market_price, List<ImageList> image_path,
                             String content, String bland_id, String series_id, String year_id,
                             String spec_id, String mileage, String on_number_year, String on_number_month,
                             String car_city_id, String on_number_province_id, String on_number_city_id,
                             String car_province_id, String color, String emission, String out_factory_year,
                             String out_factory_month, String new_car_price, String exp_safe_year,
                             String exp_safe_month, String use_type, String bladseriesdes) {
        super(product_id, market_price, image_path, content, bland_id, series_id, year_id, spec_id, mileage, on_number_year, on_number_month, car_city_id, on_number_province_id, on_number_city_id, car_province_id, color, emission, out_factory_year, out_factory_month, new_car_price, exp_safe_year, exp_safe_month, use_type);
        this.bladseriesdes = bladseriesdes;
    }

    public String getBladseriesdes() {
        return bladseriesdes;
    }

    public void setBladseriesdes(String bladseriesdes) {
        this.bladseriesdes = bladseriesdes;
    }

    public String getBelongCityDes() {
        return belongCityDes;
    }

    public void setBelongCityDes(String belongCityDes) {
        this.belongCityDes = belongCityDes;
    }

    public String getNumberBelongDes() {
        return numberBelongDes;
    }

    public void setNumberBelongDes(String numberBelongDes) {
        this.numberBelongDes = numberBelongDes;
    }

    public String getColorDes() {
        return colorDes;
    }

    public void setColorDes(String colorDes) {
        this.colorDes = colorDes;
    }

    public String getEmissionDes() {
        return emissionDes;
    }

    public void setEmissionDes(String emissionDes) {
        this.emissionDes = emissionDes;
    }

    public String getUsertypeDes() {
        return usertypeDes;
    }

    public void setUsertypeDes(String usertypeDes) {
        this.usertypeDes = usertypeDes;
    }

    public String getOutfactoryDes() {
        return outfactoryDes;
    }

    public void setOutfactoryDes(String outfactoryDes) {
        this.outfactoryDes = outfactoryDes;
    }

    public String getCardTimeDes() {
        return cardTimeDes;
    }

    public void setCardTimeDes(String cardTimeDes) {
        this.cardTimeDes = cardTimeDes;
    }

    public String getSafeDes() {
        return safeDes;
    }

    public void setSafeDes(String safeDes) {
        this.safeDes = safeDes;
    }
}
