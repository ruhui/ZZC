package com.zzcar.zzc.networks.responses;

/**
 * 描述：用户信息
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/18 17:14
 **/
public class UserMsgResponse {
    public String nick;
    public String photo;
    public String auth_status;
    public String auth_status_name;
    public String remark;
    public String shop_name;


    public UserMsgResponse(String nick, String photo, String auth_status, String auth_status_name, String remark, String shop_name) {
        this.nick = nick;
        this.photo = photo;
        this.auth_status = auth_status;
        this.auth_status_name = auth_status_name;
        this.remark = remark;
        this.shop_name = shop_name;
    }
}

//"result_code": 1,
//        "result_msg": "sample string 2",
//        "data": {
//        "nick": "sample string 1",
//        "photo": "sample string 2",
//        "auth_status": 3,
//        "auth_status_name": "已认证4S店",
//        "remark": "sample string 4",
//        "shop_name": "sample string 5"
//        }
