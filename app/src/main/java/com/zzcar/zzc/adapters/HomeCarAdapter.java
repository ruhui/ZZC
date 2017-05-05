package com.zzcar.zzc.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.HomeCarItemView;
import com.zzcar.zzc.adapters.viewholders.HomeCarItemView_;
import com.zzcar.zzc.interfaces.HomeAdapterListener;
import com.zzcar.zzc.models.HomeCarGet;

import org.androidannotations.annotations.EViewGroup;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/24 18:13
 **/

public class HomeCarAdapter extends BaseRecyclerAdapter<HomeCarGet, HomeCarItemView> {

    private HomeAdapterListener adapterListener;

    public HomeCarAdapter(HomeAdapterListener adapterListener) {
        super();
        this.adapterListener = adapterListener;
    }

    @Override
    protected HomeCarItemView onCreateItemView(ViewGroup parent, int viewType) {
        return HomeCarItemView_.build(parent.getContext());
    }

    @Override
    protected void onBindView(HomeCarItemView itemView, final HomeCarGet homeCarGet, final int position) {
        itemView.bind(homeCarGet, position);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterListener.setOnItemClckListener(position, homeCarGet.getProduct_id());
            }
        });
    }
}
