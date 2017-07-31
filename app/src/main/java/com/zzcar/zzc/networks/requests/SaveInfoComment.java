package com.zzcar.zzc.networks.requests;

import java.util.List;

/**
 * 创建时间： 2017/7/31.
 * 作者：黄如辉
 * 功能描述：
 */

public class SaveInfoComment {
    public int info_id;
    public String at_id;
    public String content;
    public List<String> image_path;

    public SaveInfoComment(int info_id, String at_id, String content, List<String> image_path) {
        this.info_id = info_id;
        this.at_id = at_id;
        this.content = content;
        this.image_path = image_path;
    }

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }

    public String getAt_id() {
        return at_id;
    }

    public void setAt_id(String at_id) {
        this.at_id = at_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImage_path() {
        return image_path;
    }

    public void setImage_path(List<String> image_path) {
        this.image_path = image_path;
    }
}
// "info_id": 1,
//         "at_id": 1,
//         "content": "sample string 2",
//         "image_path": [
//         "sample string 1",
//         "sample string 2"
//         ],
//         "quote_price": 3.0