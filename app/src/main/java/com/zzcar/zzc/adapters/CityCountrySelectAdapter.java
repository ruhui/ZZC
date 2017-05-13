package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.ItemTextView;
import com.zzcar.zzc.adapters.viewholders.ItemTextView_;
import com.zzcar.zzc.models.CityModel;
import com.zzcar.zzc.models.CityModelCountry;

/**
 * 描述：地区
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/2 16:05
 **/
public class CityCountrySelectAdapter extends BaseRecyclerAdapter<CityModelCountry, ItemTextView> {

    CitySelectListener mListener;

    public CityCountrySelectAdapter(CitySelectListener listener){
        this.mListener = listener;
    }

    @Override
    protected ItemTextView onCreateItemView(ViewGroup parent, int viewType) {
        return ItemTextView_.build(parent.getContext());
    }

    @Override
    protected void onBindView(ItemTextView itemView, final CityModelCountry cityModel, final int position) {
        itemView.bind(cityModel.getName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setlectCity(cityModel.getParentid(), cityModel.getId());
            }
        });
    }

    public interface CitySelectListener{
        public void setlectCity(int provinceid, int cityid);
    }
}
