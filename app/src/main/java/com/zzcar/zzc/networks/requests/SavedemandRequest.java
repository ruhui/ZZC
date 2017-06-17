package com.zzcar.zzc.networks.requests;

import com.zzcar.zzc.models.PropsName;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/17 16:23
 **/
public class SavedemandRequest {
    public String info_id;
    public String bland_id;
    public String series_id;
    public int on_number_min_year;
    public int on_number_max_year;
    public double min_price;
    public double max_price;
    public List<String> color;
    public List<String> inside_color;
    public String content;

    public SavedemandRequest() {}

    public SavedemandRequest(String info_id, String bland_id, String series_id, int on_number_min_year, int on_number_max_year,
                             double min_price, double max_price, List<String> color, List<String> inside_color, String content) {
        this.info_id = info_id;
        this.bland_id = bland_id;
        this.series_id = series_id;
        this.on_number_min_year = on_number_min_year;
        this.on_number_max_year = on_number_max_year;
        this.min_price = min_price;
        this.max_price = max_price;
        this.color = color;
        this.inside_color = inside_color;
        this.content = content;
    }

    public String getInfo_id() {
        return info_id;
    }

    public void setInfo_id(String info_id) {
        this.info_id = info_id;
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

    public int getOn_number_min_year() {
        return on_number_min_year;
    }

    public void setOn_number_min_year(int on_number_min_year) {
        this.on_number_min_year = on_number_min_year;
    }

    public int getOn_number_max_year() {
        return on_number_max_year;
    }

    public void setOn_number_max_year(int on_number_max_year) {
        this.on_number_max_year = on_number_max_year;
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

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public List<String> getInside_color() {
        return inside_color;
    }

    public void setInside_color(List<String> inside_color) {
        this.inside_color = inside_color;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
