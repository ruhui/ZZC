package com.zzcar.zzc.adapters.base;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9.
 */

public abstract class NewBaseQuickAdapter<T> extends BaseQuickAdapter<T> {

    protected Context mContext;
    protected int mLayoutResId;
    protected List<T> mData;

    public NewBaseQuickAdapter(Context context, int layoutResId, List<T> data) {
        super(context, layoutResId, data);
        this.mContext = context;
        this.mLayoutResId = layoutResId;
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, T t) {
        newconvert(baseViewHolder, t);
    }


    protected abstract void newconvert(BaseViewHolder baseViewHolder, T t);
}
