package com.zzcar.zzc.models;

import android.text.TextUtils;

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

    public AddCarMiddleModle(String product_id, String market_price, List<String> image_path,
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

    /*判断是否为空*/
    public String alertMsg(AddCarMiddleModle middleModle){
        if (TextUtils.isEmpty(middleModle.getContent())){
            return "请输入车况描述";
        }else if (TextUtils.isEmpty(middleModle.getMarket_price())){
            return "请输入价格";
        }else if (middleModle.getImage_path().size() == 0){
            return "请上传图片";
        }else if(TextUtils.isEmpty(middleModle.getBland_id())){
            return "请选择品牌";
        }else if (TextUtils.isEmpty(middleModle.getMileage())){
            return "请输入表显里程";
        }else if (TextUtils.isEmpty(middleModle.getOn_number_year())){
            return "请选择上牌时间";
        }else if (TextUtils.isEmpty(middleModle.getOn_number_province_id())){
            return "请选择牌照归属地";
        }else if (TextUtils.isEmpty(middleModle.getCar_province_id())){
            return "请选择当前车辆所在地";
        }else if (TextUtils.isEmpty(middleModle.getColorDes())){
            return "请选择车身颜色";
        }else if (TextUtils.isEmpty(middleModle.getEmissionDes())){
            return "请选择排放标准";
        }else if (TextUtils.isEmpty(middleModle.getOut_factory_year())){
            return "请选择出厂时间";
        }else if (TextUtils.isEmpty(middleModle.getNew_car_price())){
            return "请输入新车指导价格";
        }else if (TextUtils.isEmpty(middleModle.getExp_safe_year())){
            return "请选择强制险到期时间";
        }else if (TextUtils.isEmpty(middleModle.getUse_type())){
            return "请输入用途";
        }else{
            return "";
        }
    }

    public AddCarFrom getAddCarFrom(AddCarMiddleModle middleModle){
        AddCarFrom addCarFrom = new AddCarMiddleModle();
        addCarFrom.setProduct_id(middleModle.getProduct_id());
        addCarFrom.setMarket_price(middleModle.getMarket_price());
        addCarFrom.setImage_path(middleModle.getImage_path());
        addCarFrom.setContent(middleModle.getContent());
        addCarFrom.setBland_id(middleModle.getBland_id());
        addCarFrom.setSeries_id(middleModle.getSeries_id());
        addCarFrom.setSpec_id(middleModle.getSpec_id());
        addCarFrom.setMileage(middleModle.getMileage());
        addCarFrom.setOn_number_year(middleModle.getOn_number_year());
        addCarFrom.setOn_number_month(middleModle.getOn_number_month());
        addCarFrom.setCar_city_id(middleModle.getCar_city_id());
        addCarFrom.setOn_number_province_id(middleModle.getOn_number_province_id());
        addCarFrom.setOn_number_city_id(middleModle.getOn_number_city_id());
        addCarFrom.setCar_province_id(middleModle.getCar_province_id());
        addCarFrom.setColor(middleModle.getColor());
        addCarFrom.setEmission(middleModle.getEmission());
        addCarFrom.setOut_factory_year(middleModle.getOut_factory_year());
        addCarFrom.setOut_factory_month(middleModle.getOut_factory_month());
        addCarFrom.setNew_car_price(middleModle.getNew_car_price());
        addCarFrom.setExp_safe_year(middleModle.getExp_safe_year());
        addCarFrom.setExp_safe_month(middleModle.getExp_safe_month());
        addCarFrom.setUse_type(middleModle.getUse_type());
        return addCarFrom;
    }

    public void setAddCarFrom(SinglecarModel addCarFrom){
        SingleCarDes singleCarDes = addCarFrom.getProps_name();
        setBladseriesdes(singleCarDes.getName());
        setBelongCityDes(singleCarDes.getCar_province_city());
        setNumberBelongDes(singleCarDes.getOn_number_province_city());
        setColorDes(singleCarDes.getColor());
        setEmissionDes(singleCarDes.getEmission());
        setUsertypeDes(singleCarDes.getUse_type());
        setOutfactoryDes(addCarFrom.getOut_factory_year()+"年"+addCarFrom.getOut_factory_month()+"月");
        setCardTimeDes(addCarFrom.getOn_number_year()+"年"+addCarFrom.getOn_number_month()+"月");
        setSafeDes(addCarFrom.getExp_safe_year()+"年"+addCarFrom.getExp_safe_month()+"月");

        setProduct_id(addCarFrom.getProduct_id());
        setMarket_price(addCarFrom.getMarket_price());
        setImage_path(addCarFrom.getImage_path());
        setContent(addCarFrom.getContent());
        setBland_id(addCarFrom.getBland_id());
        setSeries_id(addCarFrom.getSeries_id());
        setYear_id(addCarFrom.getYear_id());
        setSpec_id( addCarFrom.getSpec_id());
        setMileage(addCarFrom.getMileage());
        setOn_number_year(addCarFrom.getOn_number_year());
        setOn_number_month(addCarFrom.getOn_number_month());
        setCar_city_id(addCarFrom.getCar_city_id());
        setOn_number_province_id(addCarFrom.getOn_number_province_id());
        setOn_number_city_id(addCarFrom.getOn_number_city_id());
        setCar_province_id(addCarFrom.getCar_province_id());
        setColor(addCarFrom.getColor());
        setEmissionDes(addCarFrom.getEmission());
        setOut_factory_year(addCarFrom.getOut_factory_year());
        setOut_factory_month(addCarFrom.getOut_factory_month());
        setNew_car_price(addCarFrom.getNew_car_price());
        setExp_safe_year(addCarFrom.getExp_safe_year());
        setExp_safe_month(addCarFrom.getExp_safe_month());
        setUse_type(addCarFrom.getUse_type());
    }
}
