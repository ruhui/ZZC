package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.models.CommentModle;
import com.zzcar.zzc.networks.responses.CommentResponse;
import com.zzcar.zzc.views.widget.CommentView;
import com.zzcar.zzc.views.widget.CommentView_;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/9.
 */

public class CommentAdapter extends BaseRecyclerAdapter<CommentModle, CommentView> {

    private AdapterListener adapterListener;

    public CommentAdapter(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    @Override
    protected CommentView onCreateItemView(ViewGroup parent, int viewType) {
        return CommentView_.build(parent.getContext());
    }

    @Override
    protected void onBindView(CommentView itemView, final CommentModle commentModle, final int position) {
        itemView.bind(commentModle);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListener.setOnItemListener(commentModle, position);
            }
        });
    }
}
