package com.zzcar.zzc.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/31 10:49
 **/

@Entity
public class MyEaseUser {
    private String id;
    private String avatar;//头像
    private String nick;
    public String getNick() {
        return this.nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @Generated(hash = 579851621)
    public MyEaseUser(String id, String avatar, String nick) {
        this.id = id;
        this.avatar = avatar;
        this.nick = nick;
    }
    @Generated(hash = 956755323)
    public MyEaseUser() {
    }
}
