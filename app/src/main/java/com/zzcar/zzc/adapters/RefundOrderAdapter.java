package com.zzcar.zzc.adapters;

import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.BalanceDetailViewHold;
import com.zzcar.zzc.adapters.viewholders.BalanceDetailViewHold_;
import com.zzcar.zzc.models.OrderItemModle;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/21.
 */

public class RefundOrderAdapter extends BaseRecyclerAdapter<OrderItemModle, BalanceDetailViewHold> {

    @Override
    protected BalanceDetailViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return BalanceDetailViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(BalanceDetailViewHold itemView, OrderItemModle orderItemModle, int position) {
        itemView.bind(orderItemModle);
    }
}
