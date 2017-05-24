package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.FriendModel;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/24.
 */

public class FridendListResponse {
    private int id;
    private int user_id;
    private int friend_id;
    private boolean read_info;
    private int type;
    private FriendModel friend;

    public FridendListResponse(int id, int user_id, int friend_id, boolean read_info, int type, FriendModel friend) {
        this.id = id;
        this.user_id = user_id;
        this.friend_id = friend_id;
        this.read_info = read_info;
        this.type = type;
        this.friend = friend;
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

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public boolean isRead_info() {
        return read_info;
    }

    public void setRead_info(boolean read_info) {
        this.read_info = read_info;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public FriendModel getFriend() {
        return friend;
    }

    public void setFriend(FriendModel friend) {
        this.friend = friend;
    }
}
// "id": 1,
//         "user_id": 2,
//         "friend_id": 3,
//         "read_info": true,
//         "type": 5,
//         "friend": {
//         "nick": "sample string 4",
//         "photo": "sample string 5",
//         "auth_status_name": "未认证",
//         "remark": "sample string 6",
//         "shop_name": "sample string 7",
//         "security": true
//         }
