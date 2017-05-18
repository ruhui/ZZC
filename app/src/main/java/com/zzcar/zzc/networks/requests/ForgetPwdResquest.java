package com.zzcar.zzc.networks.requests;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/18.
 */

public class ForgetPwdResquest {
    public String mobile;
    public String password;
    public String re_password;
    public String code;
    public String nick;

    public ForgetPwdResquest(String mobile, String password, String re_password, String code) {
        this.mobile = mobile;
        this.password = password;
        this.re_password = re_password;
        this.code = code;
    }

    public ForgetPwdResquest(String mobile, String password, String re_password, String code, String nick) {
        this.mobile = mobile;
        this.password = password;
        this.re_password = re_password;
        this.code = code;
        this.nick = nick;
    }
}

//        "mobile": "sample string 1",
//        "password": "sample string 2",
//        "re_password": "sample string 3",
//        "code": "sample string 4",
//        "nick": "sample string 5"
//