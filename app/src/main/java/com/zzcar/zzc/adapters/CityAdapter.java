package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.activities.SelectCityActivity;
import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.ItemCarBrandView;
import com.zzcar.zzc.adapters.viewholders.ItemCarBrandView_;
import com.zzcar.zzc.adapters.viewholders.ItemCityItem;
import com.zzcar.zzc.adapters.viewholders.ItemCityItem_;
import com.zzcar.zzc.models.CityModel;
import com.zzcar.zzc.networks.requests.SearchRequest;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.networks.responses.CityResponse;

/**
 * Created by asus-pc on 2017/4/28.
 */

public class CityAdapter extends BaseRecyclerAdapter<CityModel, ItemCityItem> {

    private CitySelectListener citySelectListener;

    public CityAdapter(CitySelectListener citySelectListener){
        this.citySelectListener = citySelectListener;
    }

    @Override
    protected ItemCityItem onCreateItemView(ViewGroup parent, int viewType) {
        return ItemCityItem_.build(parent.getContext());
    }

    @Override
    protected void onBindView(ItemCityItem itemView, final CityModel cityModel, int position) {
        itemView.bind(cityModel);
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
