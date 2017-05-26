package com.zzcar.zzc.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/26 19:40
 **/
public class ApplyFriendModel {
    private int user_id;
    private String name;
    private String photo;
    private String short_content;
    private boolean is_friend;

    public ApplyFriendModel(int user_id, String name, String photo, String short_content, boolean is_friend) {
        this.user_id = user_id;
        this.name = name;
        this.photo = photo;
        this.short_content = short_content;
        this.is_friend = is_friend;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getShort_content() {
        return short_content;
    }

    public void setShort_content(String short_content) {
        this.short_content = short_content;
    }

    public boolean is_friend() {
        return is_friend;
    }

    public void setIs_friend(boolean is_friend) {
        this.is_friend = is_friend;
    }
}
//"user_id": 1,
//        "name": "sample string 2",
//        "photo": "sample string 3",
//        "short_content": "sample string 4",
//        "is_friend": true
