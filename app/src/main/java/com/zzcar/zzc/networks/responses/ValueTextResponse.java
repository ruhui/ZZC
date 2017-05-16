package com.zzcar.zzc.networks.responses;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/16 14:48
 **/
public class ValueTextResponse {
    private String value;
    private String text;

    public ValueTextResponse(String value, String text) {
        this.value = value;
        this.text = text;
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
