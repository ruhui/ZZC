package com.zzcar.zzc.interfaces;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/20.
 */

public class RefreshFragment {
    public boolean refresh = false;
    public String TAG;

    public RefreshFragment(boolean refresh, String tag ){
        this.refresh = refresh;
        this.TAG =tag;
    }
}
