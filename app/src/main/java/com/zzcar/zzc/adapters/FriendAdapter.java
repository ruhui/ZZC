package com.zzcar.zzc.adapters;

import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.FriendViewHold;
import com.zzcar.zzc.adapters.viewholders.FriendViewHold_;
import com.zzcar.zzc.networks.responses.FridendListResponse;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/24.
 */

public class FriendAdapter extends BaseRecyclerAdapter<FridendListResponse, FriendViewHold> {

    @Override
    protected FriendViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return FriendViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(FriendViewHold itemView, FridendListResponse fridendListResponse, int position) {
        itemView.bind(fridendListResponse);
    }
}
