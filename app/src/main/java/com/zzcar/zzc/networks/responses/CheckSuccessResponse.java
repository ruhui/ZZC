package com.zzcar.zzc.networks.responses;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/5 15:55
 **/
public class CheckSuccessResponse {
    private boolean success;

    public CheckSuccessResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
