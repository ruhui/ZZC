package com.zzcar.zzc.models;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/20.
 */

public class ShouzhiItem {
    private int object_id;
    private int type;
    private String name;
    private double ammount;
    private String time;
    private String status_name;

    public ShouzhiItem(int object_id, int type, String name, double ammount, String time, String status_name) {
        this.object_id = object_id;
        this.type = type;
        this.name = name;
        this.ammount = ammount;
        this.time = time;
        this.status_name = status_name;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getTypeDes(){
        String destype = "";
        switch (type){
            case 0:
                destype = "";
                break;
            case 1:
                destype = "收款";
                break;
            case 2:
                destype = "订单收入";
                break;
            case 3:
                destype = "退款";
                break;
            case 4:
                destype = "提现";
                break;
            case 5:
                destype = "分润";
                break;
            case 6:
                destype = "手续费";
                break;
            case 7:
                destype = "支付";
                break;
            case 8:
                destype = "充值";
                break;
            case 9:
                destype = "积分充值";
                break;
            case 10:
                destype = "退回手续费";
                break;
            case 11:
                destype = "交易担保";
                break;
        }
        return  destype;
    }

    public boolean isIntent(){
        if (type == 6 || type == 9 || type == 5 || type == 10){
            return false;
        }else{
            return true;
        }
    }

}
//"object_id": 1,
//        "type": 0,
//        "name": "sample string 2",
//        "ammount": 3.0,
//        "time": "sample string 4",
//        "status_name": "sample string 5"
