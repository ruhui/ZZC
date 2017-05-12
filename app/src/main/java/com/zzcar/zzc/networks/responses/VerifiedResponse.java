package com.zzcar.zzc.networks.responses;

import java.io.Serializable;

/**
 * 描述：获取实名认证数据
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/12 15:40
 **/
public class VerifiedResponse implements Serializable{
    private String account_name;
    private int type;
    private String id_card;
    private int province_id;
    private int city_id;
    private int area_id;
    private String region_name;
    private String address;
    private String card_positive;
    private String card_negative;
    private String card_handheld;
    private String shop_head;
    private String shop_checkout;
    private String shop_space;//店铺内照
    private String legal_person;//法人姓名
    private String license;//营业执照
    private String license_no;//营业执照号码
    private String code;//手机验证码

    public VerifiedResponse(String account_name, int type, String id_card, int province_id, int city_id, int area_id,String region_name,
                           String address, String card_positive, String card_negative, String card_handheld,
                           String shop_head, String shop_checkout, String shop_space, String legal_person, String license, String license_no, String code) {
        this.account_name = account_name;
        this.type = type;
        this.id_card = id_card;
        this.province_id = province_id;
        this.city_id = city_id;
        this.area_id = area_id;
        this.region_name = region_name;
        this.address = address;
        this.card_positive = card_positive;
        this.card_negative = card_negative;
        this.card_handheld = card_handheld;
        this.shop_head = shop_head;
        this.shop_checkout = shop_checkout;
        this.shop_space = shop_space;
        this.legal_person = legal_person;
        this.license = license;
        this.license_no = license_no;
        this.code = code;
    }


    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getCarType(){
//        二手车类型：1二手车行，2是4S店
        String carType = "";
        switch (type){
            case 1:
                carType = "二手车行";
                break;
            case 2:
                carType = "是4S店";
                break;
        }
        return  carType;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCard_positive() {
        return card_positive;
    }

    public void setCard_positive(String card_positive) {
        this.card_positive = card_positive;
    }

    public String getCard_negative() {
        return card_negative;
    }

    public void setCard_negative(String card_negative) {
        this.card_negative = card_negative;
    }

    public String getCard_handheld() {
        return card_handheld;
    }

    public void setCard_handheld(String card_handheld) {
        this.card_handheld = card_handheld;
    }

    public String getShop_head() {
        return shop_head;
    }

    public void setShop_head(String shop_head) {
        this.shop_head = shop_head;
    }

    public String getShop_checkout() {
        return shop_checkout;
    }

    public void setShop_checkout(String shop_checkout) {
        this.shop_checkout = shop_checkout;
    }

    public String getShop_space() {
        return shop_space;
    }

    public void setShop_space(String shop_space) {
        this.shop_space = shop_space;
    }

    public String getLegal_person() {
        return legal_person;
    }

    public void setLegal_person(String legal_person) {
        this.legal_person = legal_person;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
//"account_name": "sample string 1",
//        "type": 2,
//        "id_card": "sample string 3",
//        "province_id": 4,
//        "city_id": 5,
//        "area_id": 1,
//        "address": "sample string 6",
//        "card_positive": "sample string 7",
//        "card_negative": "sample string 8",
//        "card_handheld": "sample string 9",
//        "shop_head": "sample string 10",
//        "shop_checkout": "sample string 11",
//        "shop_space": "sample string 12",
//        "legal_person": "sample string 13",
//        "license": "sample string 14",
//        "license_no": "sample string 15",
//        "code": "sample string 16"