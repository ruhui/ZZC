package com.zzcar.zzc.networks.requests;

/**
 * 描述：刷新token
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/31 18:07
 **/
public class RefreshLoginRequest {
    public String access_token;

    public RefreshLoginRequest(String access_token) {
        this.access_token = access_token;
    }
}
