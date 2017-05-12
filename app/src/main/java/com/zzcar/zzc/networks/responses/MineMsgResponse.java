package com.zzcar.zzc.networks.responses;

import java.io.Serializable;

/**
 * 描述：登录用户信息
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/18 17:14
 **/
public class MineMsgResponse implements Serializable{
    private int auth_status;
    private String mobile;
    private String nick;
    private String photo;
    private String auth_status_name;
    private String remark;
    private String shop_name;
    private String emp_name;

    public MineMsgResponse(int auth_status, String mobile, String nick, String photo, String auth_status_name, String remark, String shop_name, String emp_name) {
        this.auth_status = auth_status;
        this.mobile = mobile;
        this.nick = nick;
        this.photo = photo;
        this.auth_status_name = auth_status_name;
        this.remark = remark;
        this.shop_name = shop_name;
        this.emp_name = emp_name;
    }

    public int getAuth_status() {
        return auth_status;
    }

    public void setAuth_status(int auth_status) {
        this.auth_status = auth_status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }
}

//"auth_status": 0,
//        "mobile": "sample string 3",
//        "nick": "sample string 4",
//        "photo": "sample string 5",
//        "auth_status_name": "未认证",
//        "remark": "sample string 6",
//        "shop_name": "sample string 7",
//        "emp_name": "员工"
