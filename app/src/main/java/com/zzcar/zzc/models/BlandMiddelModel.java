package com.zzcar.zzc.models;

/**
 * 创建时间： 2017/6/11.
 * 作者：黄如辉
 * 功能描述：
 */

public class BlandMiddelModel {
    private int id;
    private BlandModle blandModle;

    public BlandMiddelModel(int id, BlandModle blandModle) {
        this.id = id;
        this.blandModle = blandModle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BlandModle getBlandModle() {
        return blandModle;
    }

    public void setBlandModle(BlandModle blandModle) {
        this.blandModle = blandModle;
    }
}
