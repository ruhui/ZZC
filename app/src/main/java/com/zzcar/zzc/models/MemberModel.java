package com.zzcar.zzc.models;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/6.
 */

public class MemberModel {
    private String nick;
    private String photo;
    private String remark;
    private int auth_status;
    private String auth_status_name;
    private String shop_name;
    private boolean security;

    public MemberModel(String nick, String photo, String remark, int auth_status,
                       String auth_status_name, String shop_name,boolean security) {
        this.nick = nick;
        this.photo = photo;
        this.remark = remark;
        this.auth_status = auth_status;
        this.auth_status_name = auth_status_name;
        this.shop_name = shop_name;
        this.security = security;
    }

    public boolean isSecurity() {
        return security;
    }

    public void setSecurity(boolean security) {
        this.security = security;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuth_status() {
//        0未认证，1待认证，2未通过，3已认证
        if (auth_status == 0){
            return  "未认证";
        }else if (auth_status == 1){
            return  "待认证";
        }else if (auth_status == 2){
            return  "未通过";
        }else if (auth_status == 3){
            return  "已认证";
        }
        return  "";
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

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
}

//"nick": "sample string 1",
//        "photo": "sample string 2",
//        "remark": "sample string 3",
//        "auth_status": 0,
//        "auth_status_name": "未认证",
//        "shop_name": "sample string 4"

//          "security": true