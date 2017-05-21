package com.zzcar.zzc.adapters;

import android.view.ViewGroup;

import com.zzcar.zzc.adapters.base.BaseRecyclerAdapter;
import com.zzcar.zzc.adapters.viewholders.MycarfromViewHold;
import com.zzcar.zzc.adapters.viewholders.MycarfromViewHold_;
import com.zzcar.zzc.adapters.viewholders.MyfavcarViewHold_;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.models.MyfavcarModle;

/**
 * 描述：我的车源
 * 作者：黄如辉  时间 2017/5/21.
 */


public class MycarfromAdapter extends BaseRecyclerAdapter<MyfavcarModle, MycarfromViewHold>{

    private AdapterListener adapterListener;
    //tag 0为已售，1为在售，2为未上架
    private String tag;

    public MycarfromAdapter(AdapterListener adapterListener, String tag){
        this.adapterListener = adapterListener;
        this.tag = tag;
    }

    @Override
    protected MycarfromViewHold onCreateItemView(ViewGroup parent, int viewType) {
        return MycarfromViewHold_.build(parent.getContext());
    }

    @Override
    protected void onBindView(MycarfromViewHold itemView, MyfavcarModle myfavcarModle, int position) {
        itemView.bind(myfavcarModle, position, adapterListener, tag);
    }
}
