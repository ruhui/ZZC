package com.zzcar.zzc.models;

import org.greenrobot.greendao.annotation.Entity;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 描述：地区
 * 作者：黄如辉  时间 2017/5/13.
 */

@Entity
public class CountyMode {
    private int parentid;
    private String first_letter;
    private String full_name;
    private int id;
    private String name;
    private String region_name;
    public String getRegion_name() {
        return this.region_name;
    }
    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFull_name() {
        return this.full_name;
    }
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    public String getFirst_letter() {
        return this.first_letter;
    }
    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }
    public int getParentid() {
        return this.parentid;
    }
    public void setParentid(int parentid) {
        this.parentid = parentid;
    }
    @Generated(hash = 1587250204)
    public CountyMode(int parentid, String first_letter, String full_name, int id,
            String name, String region_name) {
        this.parentid = parentid;
        this.first_letter = first_letter;
        this.full_name = full_name;
        this.id = id;
        this.name = name;
        this.region_name = region_name;
    }
    @Generated(hash = 944856589)
    public CountyMode() {
    }

}
