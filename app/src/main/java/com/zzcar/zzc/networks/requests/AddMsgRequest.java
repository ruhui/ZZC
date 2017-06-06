package com.zzcar.zzc.networks.requests;

import com.zzcar.zzc.models.CarFromModel;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/1 9:40
 **/
public class AddMsgRequest {
    public String to_id;
    public String content;
    public String image_path;
    public CarFromModel ext;// {type:1,product_id:1,price:23.2},type=1表示扩展车源,product_id车源id,price价格

    public AddMsgRequest(String to_id, String content, String image_path, CarFromModel ext) {
        this.to_id = to_id;
        this.content = content;
        this.image_path = image_path;
        this.ext = ext;
    }
}
