package com.zzcar.zzc.networks.requests;

/**
 * 创建时间： 2017/7/1.
 * 作者：黄如辉
 * 功能描述：修该我的员工昵称
 */

public class EditEmployeeNick {
    public String emp_id;
    public String nick;

    public EditEmployeeNick(String emp_id, String nick) {
        this.emp_id = emp_id;
        this.nick = nick;
    }
}
