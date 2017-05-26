package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.FriendViewHold_;
import com.zzcar.zzc.adapters.viewholders.MessageViewHolde;
import com.zzcar.zzc.adapters.viewholders.MessageViewHolde_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.networks.responses.MessageListResponse;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/25.
 */

public class MsgListAdapter extends BaseRecyclerAdapter<MessageListResponse, MessageViewHolde> {

    private AdapterListener adapterListener;

    public MsgListAdapter(AdapterListener adapterListener){
        this.adapterListener = adapterListener;
    }

    @Override
    protected MessageViewHolde onCreateItemView(ViewGroup parent, int viewType) {
        return MessageViewHolde_.build(parent.getContext());
    }

    @Override
    protected void onBindView(MessageViewHolde itemView, final MessageListResponse messageListResponse, final int position) {
        itemView.bind(messageListResponse, position);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setOnItemListener(messageListResponse, position);
            }
        });
    }
}
