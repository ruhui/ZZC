package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.SupplyViewHold;
import com.zzcar.zzc.adapters.viewholders.SupplyViewHold_;
import com.zzcar.zzc.adapters.viewholders.UserSupplyViewHold;
import com.zzcar.zzc.adapters.viewholders.UserSupplyViewHold_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.SupplyListener;
import com.zzcar.zzc.models.MysupplyModel;
import com.zzcar.zzc.models.SupplyModel;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/15 14:55
 **/
public class UserSupplyAdapter extends BaseRecyclerAdapter<SupplyModel, UserSupplyViewHold> {

    private AdapterListener adapterListener;

    public UserSupplyAdapter(AdapterListener adapterListener){
        this.adapterListener = adapterListener;
    }

    @Override
    protected UserSupplyViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return UserSupplyViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(UserSupplyViewHold itemView, final SupplyModel mysupplyModel, final int position) {
        itemView.bind(mysupplyModel);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setOnItemListener(mysupplyModel, position);
            }
        });
    }


}
