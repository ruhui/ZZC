package com.zzcar.zzc.networks.responses;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/26.
 */

public class UserMessageResponse {
    private boolean filter;//是否屏蔽聊天记录
    private boolean is_friend;
    private boolean is_self;//是否自己本人
    private int sell_count;//销售中的数量
    private int sell_out_count;//已销的数量
    private int demand_count;//qiugou
    private int supply_count;//求购的数量
    private String nick;
    private String photo;
    private String auth_status_name;
    private String remark;
    private String shop_name;
    private boolean security;

    public UserMessageResponse(boolean filter, boolean is_friend, boolean is_self, int sell_count,
                               int sell_out_count, int demand_count, int supply_count, String nick,
                               String photo, String auth_status_name, String remark, String shop_name,
                               boolean security) {
        this.filter = filter;
        this.is_friend = is_friend;
        this.is_self = is_self;
        this.sell_count = sell_count;
        this.sell_out_count = sell_out_count;
        this.demand_count = demand_count;
        this.supply_count = supply_count;
        this.nick = nick;
        this.photo = photo;
        this.auth_status_name = auth_status_name;
        this.remark = remark;
        this.shop_name = shop_name;
        this.security = security;
    }

    public int getDemand_count() {
        return demand_count;
    }

    public void setDemand_count(int demand_count) {
        this.demand_count = demand_count;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public boolean is_friend() {
        return is_friend;
    }

    public void setIs_friend(boolean is_friend) {
        this.is_friend = is_friend;
    }

    public boolean is_self() {
        return is_self;
    }

    public void setIs_self(boolean is_self) {
        this.is_self = is_self;
    }

    public int getSell_count() {
        return sell_count;
    }

    public void setSell_count(int sell_count) {
        this.sell_count = sell_count;
    }

    public int getSell_out_count() {
        return sell_out_count;
    }

    public void setSell_out_count(int sell_out_count) {
        this.sell_out_count = sell_out_count;
    }

    public int getSupply_count() {
        return supply_count;
    }

    public void setSupply_count(int supply_count) {
        this.supply_count = supply_count;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAuth_status_name() {
        return auth_status_name;
    }

    public void setAuth_status_name(String auth_status_name) {
        this.auth_status_name = auth_status_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public boolean isSecurity() {
        return security;
    }

    public void setSecurity(boolean security) {
        this.security = security;
    }
}

//    "filter": true,
//            "is_friend": true,
//            "is_self": true,
//            "sell_count": 7,
//            "sell_out_count": 8,
//            "demand_count": 9,
//            "supply_count": 10,
//            "nick": "sample string 11",
//            "photo": "sample string 12",
//            "auth_status_name": "未认证",
//            "remark": "sample string 13",
//            "shop_name": "sample string 14",
//            "security": true