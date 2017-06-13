package com.zzcar.zzc.adapters;

import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.CarfromViewHold;
import com.zzcar.zzc.adapters.viewholders.CarfromViewHold_;
import com.zzcar.zzc.adapters.viewholders.OrderViewHold_;
import com.zzcar.zzc.models.OrderitemsModel;

/**
 * 创建时间： 2017/6/14.
 * 作者：黄如辉
 * 功能描述：
 */

public class CarfromItemAdapter extends BaseRecyclerAdapter<OrderitemsModel, CarfromViewHold> {
    @Override
    protected CarfromViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return CarfromViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(CarfromViewHold itemView, OrderitemsModel model, int position) {
        itemView.bind(model);
    }
}
