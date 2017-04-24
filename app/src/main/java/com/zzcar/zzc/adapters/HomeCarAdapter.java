package com.zzcar.zzc.adapters;

import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.HomeCarItemView;
import com.zzcar.zzc.adapters.viewholders.HomeCarItemView_;
import com.zzcar.zzc.models.HomeCarGet;

import org.androidannotations.annotations.EViewGroup;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/24 18:13
 **/

public class HomeCarAdapter extends BaseRecyclerAdapter<HomeCarGet, HomeCarItemView> {

    @Override
    protected HomeCarItemView onCreateItemView(ViewGroup parent, int viewType) {
        return HomeCarItemView_.build(parent.getContext());
    }

    @Override
    protected void onBindView(HomeCarItemView itemView, HomeCarGet homeCarGet, int position) {
        itemView.bind(homeCarGet, position);
    }
}
