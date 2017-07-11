package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.EditListAddressViewHold;
import com.zzcar.zzc.adapters.viewholders.EditListAddressViewHold_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.AddressListener;
import com.zzcar.zzc.networks.responses.AddressResponse;

/**
 * 创建时间： 2017/7/11.
 * 作者：黄如辉
 * 功能描述：
 */

public class AddressEditListAdapter extends BaseRecyclerAdapter<AddressResponse, EditListAddressViewHold> {

    private AddressListener adapterListener;

    public AddressEditListAdapter(AddressListener adapterListener) {
        super();
        this.adapterListener = adapterListener;
    }

    @Override
    protected EditListAddressViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return EditListAddressViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(EditListAddressViewHold itemView, final AddressResponse addressResponse, int position) {
        itemView.bind(addressResponse, position, adapterListener);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setOnItemClickListener(addressResponse);
            }
        });
    }
}
