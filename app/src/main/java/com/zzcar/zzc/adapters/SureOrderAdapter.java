package com.zzcar.zzc.adapters;

import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.SureOrderViewHold;
import com.zzcar.zzc.adapters.viewholders.SureOrderViewHold_;
import com.zzcar.zzc.models.OrderitemsModel;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/13 14:36
 **/
public class SureOrderAdapter extends BaseRecyclerAdapter<OrderitemsModel, SureOrderViewHold> {
    @Override
    protected SureOrderViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return SureOrderViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(SureOrderViewHold itemView, OrderitemsModel model, int position) {
        itemView.bind(model, position);
    }
}
