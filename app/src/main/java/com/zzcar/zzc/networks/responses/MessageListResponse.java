package com.zzcar.zzc.networks.responses;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/25.
 */

public class MessageListResponse {
    private int object_id;
    private String name;
    private String photo;
    private int type;
    private String create_time;
    private String short_content;
    private int new_count;

    public MessageListResponse(int object_id, String name, String photo, int type, String create_time, String short_content, int new_count) {
        this.object_id = object_id;
        this.name = name;
        this.photo = photo;
        this.type = type;
        this.create_time = create_time;
        this.short_content = short_content;
        this.new_count = new_count;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getShort_content() {
        return short_content;
    }

    public void setShort_content(String short_content) {
        this.short_content = short_content;
    }

    public int getNew_count() {
        return new_count;
    }

    public void setNew_count(int new_count) {
        this.new_count = new_count;
    }
}
//"object_id": 1,
//        "name": "sample string 2",
//        "photo": "sample string 3",
//        "type": 4,
//        "create_time": "2017-05-24 23:38:38",
//        "short_content": "sample string 6",
//        "new_count": 7
