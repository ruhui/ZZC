package com.zzcar.zzc.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/17 17:52
 **/
public class PropsName {
    private String name;
    private String color;
    private String inside_color;

    public PropsName(String name, String color, String inside_color) {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getInside_color() {
        return inside_color;
    }

    public void setInside_color(String inside_color) {
        this.inside_color = inside_color;
    }
}
