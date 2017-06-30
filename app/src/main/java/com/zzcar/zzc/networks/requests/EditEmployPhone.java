package com.zzcar.zzc.networks.requests;

/**
 * 创建时间： 2017/7/1.
 * 作者：黄如辉
 * 功能描述：修改我的员工手机
 */

public class EditEmployPhone {
    public String emp_id;
    public String mobile;
    public String code;

    public EditEmployPhone(String emp_id, String mobile, String code) {
        this.emp_id = emp_id;
        this.mobile = mobile;
        this.code = code;
    }
}
