package com.zzcar.zzc.networks.responses;

/**
 * 创建时间： 2017/6/30.
 * 作者：黄如辉
 * 功能描述：
 */

public class MyEmployeeResponse {
    private String id;
    private String mobile;
    private String nick;
    private String photo;

    public MyEmployeeResponse(String id, String mobile, String nick, String photo) {
        this.id = id;
        this.mobile = mobile;
        this.nick = nick;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
//"id": 1,
//        "mobile": "sample string 2",
//        "nick": "sample string 3",
//        "photo": "sample string 4"