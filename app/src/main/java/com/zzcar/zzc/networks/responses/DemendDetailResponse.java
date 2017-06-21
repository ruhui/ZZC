package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.MemberModel;

import java.util.List;

/**
 * 创建时间： 2017/6/22.
 * 作者：黄如辉
 * 功能描述：
 */

public class DemendDetailResponse {
    private int info_id;
    private String name;
    private String start_time;
    private String end_time;
    private int on_number_min_year;
    private int on_number_max_year;
    private double min_price;
    private double max_price;
    private List<String> color;
    private List<String> inside_color;
    private String content;
    private MemberModel member;

    public DemendDetailResponse(int info_id, String name, String start_time, String end_time, int on_number_min_year, int on_number_max_year, double min_price, double max_price, List<String> color, List<String> inside_color, String content, MemberModel member) {
        this.info_id = info_id;
        this.name = name;
        this.start_time = start_time;
        this.end_time = end_time;
        this.on_number_min_year = on_number_min_year;
        this.on_number_max_year = on_number_max_year;
        this.min_price = min_price;
        this.max_price = max_price;
        this.color = color;
        this.inside_color = inside_color;
        this.content = content;
        this.member = member;
    }

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
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

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }
}
//"info_id": 1,
//        "name": "sample string 2",
//        "start_time": "2017-06-22 00:52:15",
//        "end_time": "2017-06-22 00:52:15",
//        "on_number_min_year": 5,
//        "on_number_max_year": 6,
//        "min_price": 7.0,
//        "max_price": 8.0,
