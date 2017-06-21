package com.zzcar.zzc.models;

import java.util.List;

/**
 * 创建时间： 2017/6/21.
 * 作者：黄如辉
 * 功能描述：
 */

public class SupplyModel {
    private int info_id;
    private String name;
    private List<String> image_path;
    private String start_time;
    private String end_time;
    private String content;
    private MemberModel member;

    public SupplyModel(int info_id, String name, List<String> image_path, String start_time, String end_time, String content, MemberModel member) {
        this.info_id = info_id;
        this.name = name;
        this.image_path = image_path;
        this.start_time = start_time;
        this.end_time = end_time;
        this.content = content;
        this.member = member;
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

    public List<String> getImage_path() {
        return image_path;
    }

    public void setImage_path(List<String> image_path) {
        this.image_path = image_path;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
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

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }
}
//"info_id": 1,
//        "name": "sample string 2",
//        "image_path": [
//        "sample string 1",
//        "sample string 2"
//        ],
//        "start_time": "2017-06-21 00:33:17",
//        "end_time": "2017-06-21 00:33:17",
//        "content": "sample string 5",
