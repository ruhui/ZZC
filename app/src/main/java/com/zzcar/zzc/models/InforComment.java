package com.zzcar.zzc.models;

import java.util.List;

/**
 * 创建时间： 2017/7/31.
 * 作者：黄如辉
 * 功能描述：
 */

public class InforComment {
    private int id;
    private int info_id;
    private int user_id;
    private int at_id;
    private String content;
    private String create_time;
    private double quote_price;
    private List<String> image_path;
    private MemberModel member;
    private MemberModel at_member;

    public InforComment(int id, int info_id, int user_id, int at_id, String content, String create_time, double quote_price, List<String> image_path, MemberModel member, MemberModel at_member) {
        this.id = id;
        this.info_id = info_id;
        this.user_id = user_id;
        this.at_id = at_id;
        this.content = content;
        this.create_time = create_time;
        this.quote_price = quote_price;
        this.image_path = image_path;
        this.member = member;
        this.at_member = at_member;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAt_id() {
        return at_id;
    }

    public void setAt_id(int at_id) {
        this.at_id = at_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public double getQuote_price() {
        return quote_price;
    }

    public void setQuote_price(double quote_price) {
        this.quote_price = quote_price;
    }

    public List<String> getImage_path() {
        return image_path;
    }

    public void setImage_path(List<String> image_path) {
        this.image_path = image_path;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

    public MemberModel getAt_member() {
        return at_member;
    }

    public void setAt_member(MemberModel at_member) {
        this.at_member = at_member;
    }
}
//"id": 1,
//        "info_id": 2,
//        "user_id": 3,
//        "at_id": 1,
//        "content": "sample string 4",
//        "create_time": "2017-07-31 21:44:23",
//        "quote_price": 6,
//        "image_path":