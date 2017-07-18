package com.zzcar.zzc.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.activities.GoodDetailActivity_;
import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.MyfavcarViewHold;
import com.zzcar.zzc.adapters.viewholders.MyfavcarViewHold_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.models.MyfavcarModle;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/21.
 */

public class MyfavCarAdapter extends BaseRecyclerAdapter<MyfavcarModle, MyfavcarViewHold> {
    private Context mContext;
    private AdapterListener adapterListener;
    public MyfavCarAdapter(AdapterListener adapterListener, Context mContext){
        this.adapterListener = adapterListener;
        this.mContext = mContext;
    }

    @Override
    protected MyfavcarViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return MyfavcarViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(MyfavcarViewHold itemView, final MyfavcarModle myfavcarModle, int position) {
        itemView.bind(myfavcarModle, position, adapterListener);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳到详情
                Intent intent = new Intent(mContext, GoodDetailActivity_.class);
                intent.putExtra("productId", myfavcarModle.getProduct_id());
                mContext.startActivity(intent);
            }
        });
    }
}
