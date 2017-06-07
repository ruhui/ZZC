package com.zzcar.zzc.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.LivemsgViewHold;
import com.zzcar.zzc.adapters.viewholders.LivemsgViewHold_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.models.HomeLiveMode;

/**
 * 创建时间： 2017/6/7.
 * 作者：黄如辉
 * 功能描述：
 */

public class LiveMsgAdapter extends BaseRecyclerAdapter<HomeLiveMode, LivemsgViewHold> {

    public LiveMsgAdapter(Context activity, AdapterListener adapterListener) {
        super();
    }

    @Override
    protected LivemsgViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return LivemsgViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(LivemsgViewHold itemView, HomeLiveMode homeLiveMode, int position) {
        itemView.bind(homeLiveMode, position);
    }
}
