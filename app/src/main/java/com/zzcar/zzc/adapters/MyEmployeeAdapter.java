package com.zzcar.zzc.adapters;

import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.MyEmployeeViewHold;
import com.zzcar.zzc.adapters.viewholders.MyEmployeeViewHold_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.networks.responses.MyEmployeeResponse;

/**
 * 创建时间： 2017/6/30.
 * 作者：黄如辉
 * 功能描述：
 */

public class MyEmployeeAdapter  extends BaseRecyclerAdapter<MyEmployeeResponse, MyEmployeeViewHold> {

    private AdapterListener adapterListener;

    public MyEmployeeAdapter(AdapterListener adapterListener){
        this.adapterListener = adapterListener;
    }

    @Override
    protected MyEmployeeViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return MyEmployeeViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(MyEmployeeViewHold itemView, MyEmployeeResponse response, int position) {
        itemView.bind(response, adapterListener, position);
    }
}
