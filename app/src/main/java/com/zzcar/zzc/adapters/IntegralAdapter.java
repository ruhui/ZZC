package com.zzcar.zzc.adapters;

import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.IntegralViewHold;
import com.zzcar.zzc.adapters.viewholders.IntegralViewHold_;
import com.zzcar.zzc.models.IntegralDetail;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/15.
 */

public class IntegralAdapter extends BaseRecyclerAdapter<IntegralDetail, IntegralViewHold> {
    @Override
    protected IntegralViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return IntegralViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(IntegralViewHold itemView, IntegralDetail integralDetail, int position) {
        itemView.bind(integralDetail);
    }
}
