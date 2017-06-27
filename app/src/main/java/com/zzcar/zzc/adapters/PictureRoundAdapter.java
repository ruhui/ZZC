package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.PictureViewHold;
import com.zzcar.zzc.adapters.viewholders.PictureViewHold_;
import com.zzcar.zzc.interfaces.AdapterListener;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/27 18:52
 **/
public class PictureRoundAdapter extends BaseRecyclerAdapter<String, PictureViewHold> {

    private AdapterListener adapterListener;

    public PictureRoundAdapter(AdapterListener adapterListener){
        this.adapterListener = adapterListener;
    }

    @Override
    protected PictureViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return PictureViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(PictureViewHold itemView, final String s, final int position) {
        itemView.bind(s);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setOnItemListener(s, position);
            }
        });
    }
}
