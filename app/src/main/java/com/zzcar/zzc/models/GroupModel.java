package com.zzcar.zzc.models;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/27 17:51
 **/
public class GroupModel {
    private long id;
    private long user_id;
    private String name;
    private String logo;
    private String user_name;

    public GroupModel(long id, long user_id, String name, String logo, String user_name) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.logo = logo;
        this.user_name = user_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
//"id": 1,
//        "user_id": 2,
//        "name": "sample string 3",
//        "logo": "sample string 4",
//        "user_name": "sample string 5"
