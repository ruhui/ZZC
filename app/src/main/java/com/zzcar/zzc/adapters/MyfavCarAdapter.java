package com.zzcar.zzc.adapters;

import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.MyfavcarViewHold;
import com.zzcar.zzc.adapters.viewholders.MyfavcarViewHold_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.models.MyfavcarModle;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/21.
 */

public class MyfavCarAdapter extends BaseRecyclerAdapter<MyfavcarModle, MyfavcarViewHold> {
    private AdapterListener adapterListener;
    public MyfavCarAdapter(AdapterListener adapterListener){
        this.adapterListener = adapterListener;
    }

    @Override
    protected MyfavcarViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return MyfavcarViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(MyfavcarViewHold itemView, MyfavcarModle myfavcarModle, int position) {
        itemView.bind(myfavcarModle, position, adapterListener);
    }
}
