package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.HomeCarPushViewHOld;
import com.zzcar.zzc.adapters.viewholders.HomeCarPushViewHOld_;
import com.zzcar.zzc.adapters.viewholders.LivemsgViewHold_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;

/**
 * 创建时间： 2017/6/7.
 * 作者：黄如辉
 * 功能描述：
 */

public class HomeCarPushAdapter extends BaseRecyclerAdapter<HomeCarPushResponse, HomeCarPushViewHOld> {

    private AdapterListener caradapter_listener;

    public HomeCarPushAdapter(AdapterListener caradapter_listener) {
        super();
        this.caradapter_listener = caradapter_listener;
    }

    @Override
    protected HomeCarPushViewHOld onCreateItemView(ViewGroup parent, int viewType) {
        return  HomeCarPushViewHOld_.build(parent.getContext());
    }

    @Override
    protected void onBindView(HomeCarPushViewHOld itemView, final HomeCarPushResponse homeCarPushResponse, final int position) {
        itemView.bind(homeCarPushResponse, position);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caradapter_listener.setOnItemListener(homeCarPushResponse, position);
            }
        });
    }
}
