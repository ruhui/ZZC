package com.zzcar.zzc.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/2 14:12
 **/

@Entity
public class ProvenceModelCountry {
    private int id;
    private String name;
    private String full_name;
    private String region_name;
    private String first_letter;
    public String getFirst_letter() {
        return this.first_letter;
    }
    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }
    public String getRegion_name() {
        return this.region_name;
    }
    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
    public String getFull_name() {
        return this.full_name;
    }
    public void setFull_name(String full_name) {
        this.full_name = full_name;
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
    @Generated(hash = 987208582)
    public ProvenceModelCountry(int id, String name, String full_name,
            String region_name, String first_letter) {
        this.id = id;
        this.name = name;
        this.full_name = full_name;
        this.region_name = region_name;
        this.first_letter = first_letter;
    }
    @Generated(hash = 349890626)
    public ProvenceModelCountry() {
    }

}
