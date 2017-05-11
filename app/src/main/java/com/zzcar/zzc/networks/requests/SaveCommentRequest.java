package com.zzcar.zzc.networks.requests;

import java.util.List;

/**
 * 描述： 保存评论
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/11 19:42
 **/
public class SaveCommentRequest {
    public int product_id;
    public String content;
    public String at_id;
    public List<String> image_path;

    public SaveCommentRequest(int product_id, String at_id, String content,  List<String> image_path) {
        this.product_id = product_id;
        this.content = content;
        this.at_id = at_id;
        this.image_path = image_path;
    }
}
