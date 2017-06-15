package com.zzcar.zzc.adapters;

import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.SupplyViewHold;
import com.zzcar.zzc.adapters.viewholders.SupplyViewHold_;
import com.zzcar.zzc.interfaces.SupplyListener;
import com.zzcar.zzc.models.MysupplyModel;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/15 14:55
 **/
public class SupplyAdapter extends BaseRecyclerAdapter<MysupplyModel, SupplyViewHold> {

    private SupplyListener supplyListener;

    public SupplyAdapter(SupplyListener supplyListener){
        this.supplyListener = supplyListener;
    }

    @Override
    protected SupplyViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return SupplyViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(SupplyViewHold itemView, MysupplyModel mysupplyModel, int position) {
        itemView.bind(mysupplyModel, supplyListener);
    }


}
