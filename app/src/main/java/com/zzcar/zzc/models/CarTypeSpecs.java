package com.zzcar.zzc.models;

/**
 * Created by asus-pc on 2017/4/30.
 */

public class CarTypeSpecs {
    private long id;
    private String name;
    private long year_id;
    private String price;

    public CarTypeSpecs(long id, String name, long year_id, String price) {
        this.id = id;
        this.name = name;
        this.year_id = year_id;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getYear_id() {
        return year_id;
    }

    public void setYear_id(long year_id) {
        this.year_id = year_id;
    }
}
