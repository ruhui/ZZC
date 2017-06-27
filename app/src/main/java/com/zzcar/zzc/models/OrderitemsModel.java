package com.zzcar.zzc.models;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/13 10:41
 **/
public class OrderitemsModel implements Serializable{
    private String id;
    private String order_id;
    private String product_id;
    private String quantity;
    private String unit_price;
    private String product_name;
    private String thumbnails_url;
    private String market_price;
    private BodyModel body;

    public OrderitemsModel(String id, String order_id, String product_id, String quantity, String unit_price, String product_name, String thumbnails_url, String market_price, BodyModel body) {
        this.id = id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.product_name = product_name;
        this.thumbnails_url = thumbnails_url;
        this.market_price = market_price;
        this.body = body;
    }

    public OrderItemModle getOrderItemModel(OrderitemsModel orderitemsModel){
        OrderItemModle modle = new OrderItemModle();
        modle.setId(orderitemsModel.getId());
        modle.setOrder_id(orderitemsModel.getOrder_id());
        modle.setProduct_id(orderitemsModel.getProduct_id());
        modle.setQuantity(orderitemsModel.getQuantity());
        modle.setUnit_price(orderitemsModel.getUnit_price());
        modle.setProduct_name(orderitemsModel.getProduct_name());
        modle.setThumbnails_url(orderitemsModel.getThumbnails_url());
        modle.setMarket_price(orderitemsModel.getMarket_price());
        return modle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getThumbnails_url() {
        return thumbnails_url;
    }

    public void setThumbnails_url(String thumbnails_url) {
        this.thumbnails_url = thumbnails_url;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public BodyModel getBody() {
        return body;
    }

    public void setBody(BodyModel body) {
        this.body = body;
    }
}
//"id": 1,
//        "order_id": 2,
//        "product_id": 1,
//        "quantity": 3,
//        "unit_price": 4.0,
//        "product_name": "sample string 5",
//        "thumbnails_url": "sample string 6",
//        "market_price": 7.0,
