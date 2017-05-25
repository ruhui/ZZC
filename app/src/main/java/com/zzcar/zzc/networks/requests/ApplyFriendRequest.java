package com.zzcar.zzc.networks.requests;

/**
 * 描述：请求加为好友
 * 作者：黄如辉  时间 2017/5/25.
 */

public class ApplyFriendRequest {
    private int friend_id;
    private int type;
    private String msg;

    public ApplyFriendRequest(int friend_id, int type, String msg) {
        this.friend_id = friend_id;
        this.type = type;
        this.msg = msg;
    }
}
// "friend_id": 2,
//         "type": 3,//1朋友，2关注
//         "msg": "sample string 4"