package com.zzcar.zzc.networks.responses;

/**
 * 创建时间： 2017/6/25.
 * 作者：黄如辉
 * 功能描述：
 */

public class BulletinResponse {
    private int id;
    private int user_id;
    private String title;
    private String content;
    private String short_content;
    private String create_time;
    private int type;
    private int object_id;
    private String read_time;
    private boolean is_read;
    private String image_path;

    public BulletinResponse(int id, int user_id, String title, String content, String short_content, String create_time, int type, int object_id, String read_time, boolean is_read, String image_path) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.short_content = short_content;
        this.create_time = create_time;
        this.type = type;
        this.object_id = object_id;
        this.read_time = read_time;
        this.is_read = is_read;
        this.image_path = image_path;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShort_content() {
        return short_content;
    }

    public void setShort_content(String short_content) {
        this.short_content = short_content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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

    public String getRead_time() {
        return read_time;
    }

    public void setRead_time(String read_time) {
        this.read_time = read_time;
    }

    public boolean is_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
//"id": 1,
//        "user_id": 2,
//        "title": "sample string 3",
//        "content": "sample string 4",
//        "short_content": "sample string 5",
//        "create_time": "2017-06-25 17:15:28",
//        "type": 0,
//        "object_id": 1,
//        "read_time": "2017-06-25 17:15:28",
//        "is_read": true,
//        "image_path": "sample string 8",