package com.zzcar.zzc.adapters;

import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.ShouzhiViewHold;
import com.zzcar.zzc.models.ShouzhiDetailModel;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/17 19:47
 **/
public class ShouzhiDEtailAdapter extends BaseRecyclerAdapter<ShouzhiDetailModel, ShouzhiViewHold> {

    @Override
    protected ShouzhiViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindView(ShouzhiViewHold itemView, ShouzhiDetailModel shouzhiDetailModel, int position) {

    }
}
