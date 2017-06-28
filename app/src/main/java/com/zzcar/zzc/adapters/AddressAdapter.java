package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.AddressViewHold;
import com.zzcar.zzc.adapters.viewholders.AddressViewHold_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.networks.responses.AddressResponse;

/**
 * 创建时间： 2017/6/28.
 * 作者：黄如辉
 * 功能描述：
 */

public class AddressAdapter extends BaseRecyclerAdapter<AddressResponse, AddressViewHold> {

    private AdapterListener adapterListener;
    public AddressAdapter(AdapterListener adapterListener) {
        super();
        this.adapterListener = adapterListener;
    }

    @Override
    protected AddressViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return AddressViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(AddressViewHold itemView, final AddressResponse addressResponse, final int position) {
        itemView.bind(addressResponse);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setOnItemListener(addressResponse, position);
            }
        });
    }
}
