package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.ItemTextView;
import com.zzcar.zzc.adapters.viewholders.ItemTextView_;
import com.zzcar.zzc.models.CityModelCountry;
import com.zzcar.zzc.models.CountyMode;

/**
 * 描述：地区
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/2 16:05
 **/
public class CountrySelectAdapter extends BaseRecyclerAdapter<CountyMode, ItemTextView> {

    CitySelectListener mListener;

    public CountrySelectAdapter(CitySelectListener listener){
        this.mListener = listener;
    }

    @Override
    protected ItemTextView onCreateItemView(ViewGroup parent, int viewType) {
        return ItemTextView_.build(parent.getContext());
    }

    @Override
    protected void onBindView(ItemTextView itemView, final CountyMode cityModel, int position) {
        itemView.bind(cityModel.getName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setlectCity(cityModel.getParentid(), cityModel.getId(), cityModel.getRegion_name());
            }
        });
    }

    public interface CitySelectListener{
        public void setlectCity(int provinceid, int cityid, String region_name);
    }
}
