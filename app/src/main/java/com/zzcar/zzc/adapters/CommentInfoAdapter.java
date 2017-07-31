package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.models.CommentModle;
import com.zzcar.zzc.models.InforComment;
import com.zzcar.zzc.views.widget.CommentInfoView;
import com.zzcar.zzc.views.widget.CommentInfoView_;
import com.zzcar.zzc.views.widget.CommentView;
import com.zzcar.zzc.views.widget.CommentView_;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/9.
 */

public class CommentInfoAdapter extends BaseRecyclerAdapter<InforComment, CommentInfoView> {

    private AdapterListener adapterListener;

    public CommentInfoAdapter(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    @Override
    protected CommentInfoView onCreateItemView(ViewGroup parent, int viewType) {
        return CommentInfoView_.build(parent.getContext());
    }

    @Override
    protected void onBindView(CommentInfoView itemView, final InforComment commentModle, final int position) {
        itemView.bind(commentModle);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListener.setOnItemListener(commentModle, position);
            }
        });
    }
}
