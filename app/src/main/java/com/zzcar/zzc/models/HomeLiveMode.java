package com.zzcar.zzc.models;

import java.util.List;

/**
 * 创建时间： 2017/6/7.
 * 作者：黄如辉
 * 功能描述：
 */

public class HomeLiveMode {
    private int id;
    private int user_id;
    private int type;
    private int object_id;
    private String content;
    private String create_time;
    private MemberModel member;

    public HomeLiveMode(int id, int user_id, int type, int object_id, String content, String create_time, MemberModel member) {
        this.id = id;
        this.user_id = user_id;
        this.type = type;
        this.object_id = object_id;
        this.content = content;
        this.create_time = create_time;
        this.member = member;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }
}
//"id": 1,
//        "user_id": 2,
//        "type": 3,
//        "object_id": 4,
//        "content": "sample string 5",
//        "create_time": "2017-06-06 23:48:56",
//        "member