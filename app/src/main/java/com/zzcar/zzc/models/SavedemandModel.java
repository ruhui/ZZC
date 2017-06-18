package com.zzcar.zzc.models;

import com.zzcar.zzc.networks.requests.SavedemandRequest;

import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/17 16:23
 **/
public class SavedemandModel extends SavedemandRequest{

    public String blandDes;
    public String timeDes;
    public String carColorDes;//车身颜色
    public String carInColorDes;//内饰颜色

    public SavedemandModel(String info_id, String bland_id, String series_id, int on_number_min_year, int on_number_max_year, double min_price, double max_price, List<String> color, List<String> inside_color, String content) {
        super(info_id, bland_id, series_id, on_number_min_year, on_number_max_year, min_price, max_price, color, inside_color, content);
    }

    public SavedemandModel() {
        super();
    }

    public SavedemandRequest getRequest(SavedemandModel model){
        SavedemandRequest request = new SavedemandRequest();
        request.setInfo_id(model.getInfo_id());
        request.setBland_id(model.getBland_id());
        request.setSeries_id(model.getSeries_id());
        request.setOn_number_min_year(model.getOn_number_min_year());
        request.setOn_number_max_year(model.getOn_number_max_year());
        request.setMin_price(model.getMin_price());
        request.setMax_price(model.getMax_price());
        request.setColor(model.getColor());
        request.setInside_color(model.getInside_color());
        request.setContent(model.getContent());
        return  request;
    }

    public SavedemandModel(int info_id, int bland_id, int series_id, int on_number_min_year, int on_number_max_year, double min_price, double max_price, List<String> color, List<String> inside_color, String content, String blandDes) {
        super();
        this.blandDes = blandDes;
    }

    public String getBlandDes() {
        return blandDes;
    }

    public void setBlandDes(String blandDes) {
        this.blandDes = blandDes;
    }

    public String getTimeDes() {
        return timeDes;
    }

    public void setTimeDes(String timeDes) {
        this.timeDes = timeDes;
    }

    public String getCarColorDes() {
        return carColorDes;
    }

    public void setCarColorDes(String carColorDes) {
        this.carColorDes = carColorDes;
    }

    public String getCarInColorDes() {
        return carInColorDes;
    }

    public void setCarInColorDes(String carInColorDes) {
        this.carInColorDes = carInColorDes;
    }
}
