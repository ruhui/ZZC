package com.zzcar.zzc.models;

import java.util.List;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/21.
 */

public class SinglecarModel extends AddCarFrom{
    private SingleCarDes props_name;

    public SinglecarModel(String product_id, String market_price, List<String> image_path, String content, String bland_id, String series_id, String year_id, String spec_id, String mileage, String on_number_year, String on_number_month, String car_city_id, String on_number_province_id, String on_number_city_id, String car_province_id, String color, String emission, String out_factory_year, String out_factory_month, String new_car_price, String exp_safe_year, String exp_safe_month, String use_type, SingleCarDes props_name) {
        super(product_id, market_price, image_path, content, bland_id, series_id, year_id, spec_id, mileage, on_number_year, on_number_month, car_city_id, on_number_province_id, on_number_city_id, car_province_id, color, emission, out_factory_year, out_factory_month, new_car_price, exp_safe_year, exp_safe_month, use_type);
        this.props_name = props_name;
    }

    public SingleCarDes getProps_name() {
        return props_name;
    }

    public void setProps_name(SingleCarDes props_name) {
        this.props_name = props_name;
    }
}
