package com.zzcar.zzc.adapters;

import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.FriendViewHold_;
import com.zzcar.zzc.adapters.viewholders.MessageViewHolde;
import com.zzcar.zzc.adapters.viewholders.MessageViewHolde_;
import com.zzcar.zzc.networks.responses.MessageListResponse;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/25.
 */

public class MsgListAdapter extends BaseRecyclerAdapter<MessageListResponse, MessageViewHolde> {
    @Override
    protected MessageViewHolde onCreateItemView(ViewGroup parent, int viewType) {
        return MessageViewHolde_.build(parent.getContext());
    }

    @Override
    protected void onBindView(MessageViewHolde itemView, MessageListResponse messageListResponse, int position) {
        itemView.bind(messageListResponse);
    }
}
