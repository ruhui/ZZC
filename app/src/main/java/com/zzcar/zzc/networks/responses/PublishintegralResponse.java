package com.zzcar.zzc.networks.responses;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/17 16:02
 **/
public class PublishintegralResponse {
    private int integral;
    private boolean is_pubilsh;

    public PublishintegralResponse(int integral, boolean is_pubilsh) {
        this.integral = integral;
        this.is_pubilsh = is_pubilsh;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public boolean is_pubilsh() {
        return is_pubilsh;
    }

    public void setIs_pubilsh(boolean is_pubilsh) {
        this.is_pubilsh = is_pubilsh;
    }
}
