package com.zzcar.zzc.networks.requests;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/8.
 */

public class CommentRequest {
    public long product_id;
    public int page;
    public String size = "10";

    public CommentRequest(long product_id, int page) {
        this.product_id = product_id;
        this.page = page;
    }
}

//    product_id 商品id long None.
//    page  当前分页 integer  None.
//    size 每页N条，默认10条