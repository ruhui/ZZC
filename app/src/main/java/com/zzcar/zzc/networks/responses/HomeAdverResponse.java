package com.zzcar.zzc.networks.responses;

/**
 * 创建时间： 2017/6/6.
 * 作者：黄如辉
 * 功能描述：首页图片
 */

public class HomeAdverResponse {
    private int id;
    private String name;
    private String url;
    private String image_path;

    public HomeAdverResponse(int id, String name, String url, String image_path) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.image_path = image_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
//"id": 1,
//        "name": "sample string 2",
//        "url": "sample string 3",
//        "image_path": "sample string 4"
