package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.SuccessSupplyViewHold;
import com.zzcar.zzc.adapters.viewholders.SuccessSupplyViewHold_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.models.SupplyModel;

/**
 * 创建时间： 2017/6/21.
 * 作者：黄如辉
 * 功能描述：
 */

public class SuccessSupplyAdapter extends BaseRecyclerAdapter<SupplyModel, SuccessSupplyViewHold> {

    private AdapterListener adapterListener;

    public SuccessSupplyAdapter(AdapterListener adapterListener) {
        super();
        this.adapterListener = adapterListener;
    }

    @Override
    protected SuccessSupplyViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return SuccessSupplyViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(SuccessSupplyViewHold itemView, final SupplyModel supplyModel, final int position) {
        itemView.bind(supplyModel);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setOnItemListener(supplyModel, position);
            }
        });
    }
}
