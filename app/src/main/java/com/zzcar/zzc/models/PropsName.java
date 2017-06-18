package com.zzcar.zzc.models;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/17 17:52
 **/
public class PropsName {
    private String name;
    private List<String> color;
    private List<String> inside_color;

    public PropsName(String name, List<String> color, List<String> inside_color) {
        this.name = name;
        this.color = color;
        this.inside_color = inside_color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public List<String> getInside_color() {
        return inside_color;
    }

    public void setInside_color(List<String> inside_color) {
        this.inside_color = inside_color;
    }
}
