package com.zzcar.zzc.networks.requests;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/22 18:24
 **/
public class HomeCarGetResquest {
    private String keyword;
    private int saleStatus;
    private int sort;
    private long category_id;
    private long user_id;
    private boolean sell_out;
    private boolean select_sale;
    private String props;
    private int page;
    private int size;

    public HomeCarGetResquest(String keyword, int saleStatus, int sort, long category_id, long user_id, boolean sell_out, boolean select_sale, String props, int page) {
        this.keyword = keyword;
        this.saleStatus = saleStatus;
        this.sort = sort;
        this.category_id = category_id;
        this.user_id = user_id;
        this.sell_out = sell_out;
        this.select_sale = select_sale;
        this.props = props;
        this.page = page;
        this.size = 10;
    }
}

//keyword
//
//        名称/编号
//        string
//
//        None.
//        saleStatus
//
//        商品状态：0销售中（默认），1下架中，2审核不通过，3违规下架,4删除的
//        integer
//
//        None.
//        sort
//
//        排序：1最新(默认)、2销量高、3价格高到低、4价格低到高、5按车龄最小，6按里程最少
//        integer
//
//        None.
//        category_id
//
//        商品分类
//        long
//
//        None.
//        user_id
//
//        用户id，用户个人主页，要传值
//        long
//
//        None.
//        sell_out
//
//        是否销售完
//        boolean
//
//        None.
//        select_sale
//
//        是否查询促销
//        boolean
//
//        None.
//        props
//
//        属性,属性值的组合.格式:{"key1":value1,"key2":value2,"color_ids":[1,2]};参数 如goods/cars?page=1&props={"city_id":350200,"color_ids":[1,2]}
//        string
//
//        None.
//        page
//
//        当前分页
//        integer
//
//        None.
//        size
//
//        每页N条，默认10条
//        integer
//
//        None.
