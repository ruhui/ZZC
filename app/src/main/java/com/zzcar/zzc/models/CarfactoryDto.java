package com.zzcar.zzc.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by asus-pc on 2017/4/30.
 */

@Entity
public class CarfactoryDto {
    @Id private long id;
    private long factory_id;
    private String name;
    private int state;
    private int sort;
    public int getSort() {
        return this.sort;
    }
    public void setSort(int sort) {
        this.sort = sort;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getFactory_id() {
        return this.factory_id;
    }
    public void setFactory_id(long factory_id) {
        this.factory_id = factory_id;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Generated(hash = 1853782501)
    public CarfactoryDto(long id, long factory_id, String name, int state, int sort) {
        this.id = id;
        this.factory_id = factory_id;
        this.name = name;
        this.state = state;
        this.sort = sort;
    }
    @Generated(hash = 523626274)
    public CarfactoryDto() {
    }
}

//id 车厂id，用于排序 long None.
//factory_id 车厂id long None.
// name 名称 string None.
//first_letter 首字母string None.
//state 排序用:order by sort,state integer None.
//sort 排序 integer None.
