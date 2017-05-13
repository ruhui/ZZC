package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.ItemCityItem;
import com.zzcar.zzc.adapters.viewholders.ItemCityItem_;
import com.zzcar.zzc.models.CityModel;
import com.zzcar.zzc.models.CityModelCountry;

/**
 * Created by asus-pc on 2017/4/28.
 */

public class CityCountyAdapter extends BaseRecyclerAdapter<CityModelCountry, ItemCityItem> {

    private CitySelectListener citySelectListener;

    public CityCountyAdapter(CitySelectListener citySelectListener){
        this.citySelectListener = citySelectListener;
    }

    @Override
    protected ItemCityItem onCreateItemView(ViewGroup parent, int viewType) {
        return ItemCityItem_.build(parent.getContext());
    }

    @Override
    protected void onBindView(ItemCityItem itemView, final CityModelCountry cityModel, int position) {
        itemView.bind(cityModel.getName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                citySelectListener.setlectCity(cityModel.getParentid(), cityModel.getId(), cityModel.getRegion_name());
            }
        });
    }

    public interface CitySelectListener{
        public void setlectCity(int provinceid, int cityid, String citydes);
    }
}
