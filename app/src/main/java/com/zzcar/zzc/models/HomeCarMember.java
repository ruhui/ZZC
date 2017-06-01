package com.zzcar.zzc.models;

import java.io.Serializable;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/22 18:40
 **/
public class HomeCarMember implements Serializable{
    private String nick;
    private String photo;
    private int auth_status;
    private String auth_status_name;
    private String remark;
    private String shop_name;

    public HomeCarMember(String nick, String photo, int auth_status, String auth_status_name, String remark, String shop_name) {
        this.nick = nick;
        this.photo = photo;
        this.auth_status = auth_status;
        this.auth_status_name = auth_status_name;
        this.remark = remark;
        this.shop_name = shop_name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getAuth_status() {
        return auth_status;
    }

    public void setAuth_status(int auth_status) {
        this.auth_status = auth_status;
    }

    public String getAuth_status_name() {
        return auth_status_name;
    }

    public void setAuth_status_name(String auth_status_name) {
        this.auth_status_name = auth_status_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
}

//"nick": "sample string 1",
//        "photo": "sample string 2",
//        "auth_status": 3,
//        "auth_status_name": "已认证4S店",
//        "remark": "sample string 4",
//        "shop_name": "sample string 5"