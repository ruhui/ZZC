package com.zzcar.zzc.models;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/24.
 */

public class FriendModel {
    private String nick;
    private String photo;
    private String auth_status_name;
    private String remark;
    private String shop_name;
    private boolean security;

    public FriendModel(String nick, String photo, String auth_status_name, String remark, String shop_name, boolean security) {
        this.nick = nick;
        this.photo = photo;
        this.auth_status_name = auth_status_name;
        this.remark = remark;
        this.shop_name = shop_name;
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

    public boolean getSecurity() {
        return security;
    }

    public void setSecurity(boolean security) {
        this.security = security;
    }
}
//  "nick": "sample string 4",
////         "photo": "sample string 5",
////         "auth_status_name": "未认证",
////         "remark": "sample string 6",
////         "shop_name": "sample string 7",
////         "security": true