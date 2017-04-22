package com.zzcar.zzc.networks.responses;

/**
 * Created by asus-pc on 2017/4/22.
 */

public class HomeCarPushResponse {
    public String user_id;
    public String shop_name;
    public String product_id;
    public String name;
    public String first_image;
    public String price;

    public HomeCarPushResponse(String user_id, String shop_name,
                               String product_id, String name,
                               String first_image, String price) {
        this.user_id = user_id;
        this.shop_name = shop_name;
        this.product_id = product_id;
        this.name = name;
        this.first_image = first_image;
        this.price = price;
    }
}

// {
//         "user_id": 1,
//         "shop_name": "sample string 2",
//         "product_id": 3,
//         "name": "sample string 4",
//         "first_image": "sample string 5",
//         "price": 6.0
//