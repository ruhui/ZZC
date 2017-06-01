package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.CheckFriendViewHold;
import com.zzcar.zzc.adapters.viewholders.CheckFriendViewHold_;
import com.zzcar.zzc.adapters.viewholders.FriendViewHold;
import com.zzcar.zzc.adapters.viewholders.FriendViewHold_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.models.ApplyFriendModel;
import com.zzcar.zzc.networks.responses.FridendListResponse;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/24.
 */

public class CheckFriendAdapter extends BaseRecyclerAdapter<ApplyFriendModel, CheckFriendViewHold> {

    private AdapterListener adapterListener;

    public CheckFriendAdapter( AdapterListener adapterListener){
        this.adapterListener = adapterListener;
    }

    @Override
    protected CheckFriendViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return CheckFriendViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(CheckFriendViewHold itemView, final ApplyFriendModel fridendListResponse, int position) {
        itemView.bind(fridendListResponse, adapterListener, position);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListener.setOnItemListener(fridendListResponse, 10000);
            }
        });
    }
}
