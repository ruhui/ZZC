package com.zzcar.zzc.networks.responses;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/2 17:14
 **/
public class ColorResponse {
    private String color;
    private String value;
    private String text;

    public ColorResponse(String color, String value, String text) {
        this.color = color;
        this.value = value;
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

//"color": "sample string 1",
//        "value": "sample string 3",
//        "text": "sample string 4"