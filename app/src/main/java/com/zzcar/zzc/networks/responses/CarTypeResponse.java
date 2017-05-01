package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.CarTypeSpecs;

import java.util.List;

/**
 * Created by asus-pc on 2017/4/30.
 */

public class CarTypeResponse {
    private long id;
    private long series_id;
    private String name;
    private int year;
    private List<CarTypeSpecs> specs;

    public CarTypeResponse(long id, long series_id, String name, int year, List<CarTypeSpecs> specs) {
        this.id = id;
        this.series_id = series_id;
        this.name = name;
        this.year = year;
        this.specs = specs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSeries_id() {
        return series_id;
    }

    public void setSeries_id(long series_id) {
        this.series_id = series_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<CarTypeSpecs> getSpecs() {
        return specs;
    }

    public void setSpecs(List<CarTypeSpecs> specs) {
        this.specs = specs;
    }
}
