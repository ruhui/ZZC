package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.BlandModle;

import java.util.List;

/**
 * 创建时间： 2017/6/10.
 * 作者：黄如辉
 * 功能描述：
 */

public class MysubscribeResponse {
    private List<BlandModle> bland;
    private String bland_series_text;
    private int start_year;
    private int end_year;
    private List<String> emission;
    private String emission_text;
    private double start_price;
    private double end_price;

    public MysubscribeResponse(){}

    public MysubscribeResponse(List<BlandModle> bland, String bland_series_text, int start_year, int end_year,
                               List<String> emission, String emission_text, double start_price, double end_price) {
        this.bland = bland;
        this.bland_series_text = bland_series_text;
        this.start_year = start_year;
        this.end_year = end_year;
        this.emission = emission;
        this.emission_text = emission_text;
        this.start_price = start_price;
        this.end_price = end_price;
    }

    public List<BlandModle> getBland() {
        return bland;
    }

    public void setBland(List<BlandModle> bland) {
        this.bland = bland;
    }

    public String getBland_series_text() {
        return bland_series_text;
    }

    public void setBland_series_text(String bland_series_text) {
        this.bland_series_text = bland_series_text;
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

    public List<String> getEmission() {
        return emission;
    }

    public void setEmission(List<String> emission) {
        this.emission = emission;
    }

    public String getEmission_text() {
        return emission_text;
    }

    public void setEmission_text(String emission_text) {
        this.emission_text = emission_text;
    }

    public double getStart_price() {
        return start_price;
    }

    public void setStart_price(double start_price) {
        this.start_price = start_price;
    }

    public double getEnd_price() {
        return end_price;
    }

    public void setEnd_price(double end_price) {
        this.end_price = end_price;
    }
}
//bland 品牌json
//bland_series_text
