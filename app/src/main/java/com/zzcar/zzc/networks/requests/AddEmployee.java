package com.zzcar.zzc.networks.requests;

/**
 * 创建时间： 2017/7/1.
 * 作者：黄如辉
 * 功能描述：
 */

public class AddEmployee {
    public String mobile;
    public String password;
    public String re_password;
    public String code;
    public String nick;

    public AddEmployee(String mobile, String password, String re_password, String code, String nick) {
        this.mobile = mobile;
        this.password = password;
        this.re_password = re_password;
        this.code = code;
        this.nick = nick;
    }
}
//"mobile": "sample string 1",
//        "password": "sample string 2",
//        "re_password": "sample string 3",
//        "code": "sample string 4",
//        "nick": "sample string 5"