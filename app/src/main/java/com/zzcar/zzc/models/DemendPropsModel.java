package com.zzcar.zzc.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间： 2017/6/20.
 * 作者：黄如辉
 * 功能描述：
 */

public class DemendPropsModel implements Serializable{
    public int bland_id ;//品牌id
    public int series_id;//车系id
    public int price_type;//价格区间
    public int min_price;//自定义查询最低价格
    public int max_price;//自定义查询最高价格
    public int start_year;//上牌年份－起
    public int end_year;//上牌年份-止
    public List<String> color_ids = new ArrayList<>();//颜色数组

    public DemendPropsModel(){}


    public DemendPropsModel(int bland_id, int series_id, int price_typ, int min_price, int max_price, int start_year, int end_year, List<String> color_ids) {
        this.bland_id = bland_id;
        this.series_id = series_id;
        this.price_type = price_typ;
        this.min_price = min_price;
        this.max_price = max_price;
        this.start_year = start_year;
        this.end_year = end_year;
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

    public int getPrice_typ() {
        return price_type;
    }

    public void setPrice_typ(int price_typ) {
        this.price_type = price_typ;
    }

    public int getMin_price() {
        return min_price;
    }

    public void setMin_price(int min_price) {
        this.min_price = min_price;
    }

    public int getMax_price() {
        return max_price;
    }

    public void setMax_price(int max_price) {
        this.max_price = max_price;
    }

    public int getStart_year() {
        return start_year;
    }

    public void setStart_year(int start_year) {
        this.start_year = start_year;
    }

    public int getEnd_year() {
        return end_year;
    }

    public void setEnd_year(int end_year) {
        this.end_year = end_year;
    }

    public List<String> getColor_ids() {
        return color_ids;
    }

    public void setColor_ids(List<String> color_ids) {
        this.color_ids = color_ids;
    }
}
