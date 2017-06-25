package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.SystemMsgViewHold;
import com.zzcar.zzc.adapters.viewholders.SystemMsgViewHold_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.models.SystemModel;

/**
 * 创建时间： 2017/6/25.
 * 作者：黄如辉
 * 功能描述：
 */

public class SystemAdapter extends BaseRecyclerAdapter<SystemModel, SystemMsgViewHold> {

    private AdapterListener adapterListener;

    public SystemAdapter(AdapterListener adapterListener){
        this.adapterListener = adapterListener;
    }

    @Override
    protected SystemMsgViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return SystemMsgViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(SystemMsgViewHold itemView, final SystemModel model, final int position) {
        itemView.bind(model);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setOnItemListener(model, position);
            }
        });
    }
}
