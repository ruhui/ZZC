package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.ItemCarBrandView;
import com.zzcar.zzc.adapters.viewholders.ItemCityItem;
import com.zzcar.zzc.adapters.viewholders.ItemTextView;
import com.zzcar.zzc.adapters.viewholders.ItemTextView_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.networks.responses.ValueTextResponse;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/16 14:58
 **/
public class TixanTypeAdapter extends BaseRecyclerAdapter<ValueTextResponse, ItemTextView> {

    private AdapterListener adapterListener;

    public TixanTypeAdapter(AdapterListener adapterListener){
        this.adapterListener = adapterListener;
    }

    @Override
    protected ItemTextView onCreateItemView(ViewGroup parent, int viewType) {
        return ItemTextView_.build(parent.getContext());
    }

    @Override
    protected void onBindView(ItemTextView itemView, final ValueTextResponse valueTextResponse, final int position) {
        itemView.bindcenter(valueTextResponse.getText());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setOnItemListener(valueTextResponse, position);
            }
        });
    }
}
