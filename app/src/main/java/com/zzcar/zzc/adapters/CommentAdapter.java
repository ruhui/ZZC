package com.zzcar.zzc.adapters;

import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.models.CommentModle;
import com.zzcar.zzc.networks.responses.CommentResponse;
import com.zzcar.zzc.views.widget.CommentView;
import com.zzcar.zzc.views.widget.CommentView_;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/9.
 */

public class CommentAdapter extends BaseRecyclerAdapter<CommentModle, CommentView> {
    @Override
    protected CommentView onCreateItemView(ViewGroup parent, int viewType) {
        return CommentView_.build(parent.getContext());
    }

    @Override
    protected void onBindView(CommentView itemView, CommentModle commentModle, int position) {
        itemView.bind(commentModle);
    }
}
