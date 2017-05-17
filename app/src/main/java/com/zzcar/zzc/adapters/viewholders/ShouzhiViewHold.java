package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/17 20:07
 **/

@EViewGroup(R.layout.adapter_shouzhi)
public class ShouzhiViewHold extends LinearLayout {

    @ViewById(R.id.textView95)
    TextView txtTime;
    @ViewById(R.id.textView96)
    TextView textView96;
    @ViewById(R.id.textView97)
    TextView textView97;
    @ViewById(R.id.textView98)
    TextView textView98;
    @ViewById(R.id.textView99)
    TextView textView99;
    @ViewById(R.id.textView100)
    TextView textView100;

    public ShouzhiViewHold(Context context) {
        super(context);
    }

    public ShouzhiViewHold(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
