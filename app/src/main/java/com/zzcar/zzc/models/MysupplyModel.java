package com.zzcar.zzc.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/15 14:52
 **/
public class MysupplyModel {
    private int info_id;
    private String name;
    private String first_image;
    private String end_time;
    private String content;

    public MysupplyModel(String content, int info_id, String name, String first_image, String end_time) {
        this.content = content;
        this.info_id = info_id;
        this.name = name;
        this.first_image = first_image;
        this.end_time = end_time;
    }


    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_image() {
        return first_image;
    }

    public void setFirst_image(String first_image) {
        this.first_image = first_image;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
//"info_id": 1,
//        "name": "sample string 2",
//        "first_image": "sample string 3",
//        "end_time": "2017-06-15 14:42:40",
//        "content": "sample string 5"
