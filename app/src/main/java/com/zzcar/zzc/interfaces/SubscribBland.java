package com.zzcar.zzc.interfaces;

import com.zzcar.zzc.models.BlandModle;

import java.util.HashMap;

/**
 * 创建时间： 2017/6/11.
 * 作者：黄如辉
 * 功能描述：
 */

public class SubscribBland {
    public HashMap<Integer, BlandModle> hashMap;

    public SubscribBland(HashMap<Integer, BlandModle> hashMap) {
        this.hashMap = hashMap;
    }
}
