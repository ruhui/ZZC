package com.zzcar.zzc.networks.responses;

import com.zzcar.zzc.models.GroupModel;
import com.zzcar.zzc.models.MemberModel;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/27 17:50
 **/
public class GroupMenberResponse {
    private boolean un_tip; //消息免打扰
    private GroupModel group;
    private List<MemberModel> members;

    public GroupMenberResponse(boolean un_tip, GroupModel group, List<MemberModel> members) {
        this.un_tip = un_tip;
        this.group = group;
        this.members = members;
    }

    public boolean isUn_tip() {
        return un_tip;
    }

    public void setUn_tip(boolean un_tip) {
        this.un_tip = un_tip;
    }

    public GroupModel getGroup() {
        return group;
    }

    public void setGroup(GroupModel group) {
        this.group = group;
    }

    public List<MemberModel> getMembers() {
        return members;
    }

    public void setMembers(List<MemberModel> members) {
        this.members = members;
    }
}
