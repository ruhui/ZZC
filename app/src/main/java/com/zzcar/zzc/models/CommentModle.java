package com.zzcar.zzc.models;

import java.util.List;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/8.
 */

public class CommentModle {
    private int id;
    private int product_id;
    private int user_id;
    private int at_id;
    private String content;
    private String create_time;
    private List<String> image_path;
    private MemberModel member;
    private MemberModel at_member;

    public CommentModle(int id, int product_id, int user_id, int at_id, String content, String create_time,
                        List<String> image_path, MemberModel member, MemberModel at_member) {
        this.id = id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.at_id = at_id;
        this.content = content;
        this.create_time = create_time;
        this.image_path = image_path;
        this.member = member;
        this.at_member = at_member;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAt_id() {
        return at_id;
    }

    public void setAt_id(int at_id) {
        this.at_id = at_id;
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

    public List<String> getImage_path() {
        return image_path;
    }

    public void setImage_path(List<String> image_path) {
        this.image_path = image_path;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

    public MemberModel getAt_member() {
        return at_member;
    }

    public void setAt_member(MemberModel at_member) {
        this.at_member = at_member;
    }
}
//"id": 1,
//        "product_id": 2,
//        "user_id": 3,
//        "at_id": 1,
//        "content": "sample string 4",
//        "create_time": "2017-05-08 23:09:36",