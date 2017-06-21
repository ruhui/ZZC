package com.zzcar.zzc.models;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间： 2017/6/20.
 * 作者：黄如辉
 * 功能描述：
 */

public class MiddleDemendModel extends DemendPropsModel {

    private String branddes;
    private String Price_typedes;
    private String colorDes;
    private String timeDes;

    public MiddleDemendModel() {}

    public MiddleDemendModel(String branddes, String price_typedes, String colorDes, String timeDes) {
        this.branddes = branddes;
        Price_typedes = price_typedes;
        this.colorDes = colorDes;
        this.timeDes = timeDes;
    }

    public String getTimeDes() {
        return timeDes;
    }

    public void setTimeDes(String timeDes) {
        this.timeDes = timeDes;
    }

    public String getColorDes() {
        return colorDes;
    }

    public void setColorDes(String colorDes) {
        this.colorDes = colorDes;
    }

    public String getPrice_typedes() {
        return Price_typedes;
    }

    public void setPrice_typedes(String price_typedes) {
        Price_typedes = price_typedes;
    }

    public String getBranddes() {
        return branddes;
    }

    public void setBranddes(String branddes) {
        this.branddes = branddes;
    }

    public DemendPropsModel getPorps(MiddleDemendModel model){
        DemendPropsModel propsModel = new DemendPropsModel();
        propsModel.setBland_id(model.getBland_id());
        propsModel.setSeries_id(model.getSeries_id());
        propsModel.setPrice_typ(model.getPrice_typ());
        propsModel.setMin_price(model.getMin_price());
        propsModel.setMax_price(model.getMax_price());
        propsModel.setStart_year(model.getStart_year());
        propsModel.setEnd_year(model.getEnd_year());
        propsModel.setColor_ids(model.getColor_ids());
        return propsModel;
    }

    public DemendPropsModel clearData(){
        ArrayList<String> liststr = new ArrayList<>();
        DemendPropsModel propsModel = new DemendPropsModel();
        propsModel.setBland_id(0);
        propsModel.setSeries_id(0);
        propsModel.setPrice_typ(0);
        propsModel.setMin_price(0);
        propsModel.setMax_price(0);
        propsModel.setStart_year(0);
        propsModel.setEnd_year(0);
        propsModel.setColor_ids(liststr);
        return propsModel;
    }
}
