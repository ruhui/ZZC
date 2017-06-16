package com.zzcar.zzc.adapters;

import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.DemindViewHold;
import com.zzcar.zzc.adapters.viewholders.DemindViewHold_;
import com.zzcar.zzc.adapters.viewholders.SupplyViewHold;
import com.zzcar.zzc.adapters.viewholders.SupplyViewHold_;
import com.zzcar.zzc.interfaces.DemendListener;
import com.zzcar.zzc.interfaces.SupplyListener;
import com.zzcar.zzc.models.MydemandModel;
import com.zzcar.zzc.models.MysupplyModel;
import com.zzcar.zzc.networks.responses.MydemandResponse;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/15 14:55
 **/
public class DemendAdapter extends BaseRecyclerAdapter<MydemandModel, DemindViewHold> {

    private DemendListener supplyListener;

    public DemendAdapter(DemendListener supplyListener){
        this.supplyListener = supplyListener;
    }

    @Override
    protected DemindViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return DemindViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(DemindViewHold itemView, MydemandModel mysupplyModel, int position) {
        itemView.bind(mysupplyModel, supplyListener);
    }


}
