package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.BusinessDemindViewHold;
import com.zzcar.zzc.adapters.viewholders.BusinessDemindViewHold_;
import com.zzcar.zzc.adapters.viewholders.DemindViewHold;
import com.zzcar.zzc.adapters.viewholders.DemindViewHold_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.DemendListener;
import com.zzcar.zzc.models.MydemandModel;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/15 14:55
 **/
public class BusinessDemendAdapter extends BaseRecyclerAdapter<MydemandModel, BusinessDemindViewHold> {
    private AdapterListener adapterListener;
    public BusinessDemendAdapter(AdapterListener adapterListener){
        this.adapterListener = adapterListener;
    }

    @Override
    protected BusinessDemindViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return BusinessDemindViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(BusinessDemindViewHold itemView, final MydemandModel model, final int position) {
        itemView.bind(model);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setOnItemListener(model, position);
            }
        });
    }


}
