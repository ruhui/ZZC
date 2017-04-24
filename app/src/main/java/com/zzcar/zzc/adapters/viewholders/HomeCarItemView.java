package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.zzcar.zzc.R;

import org.androidannotations.annotations.EViewGroup;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/24 18:15
 **/

@EViewGroup(R.layout.adapter_homecarfrom)
public class HomeCarItemView extends LinearLayout {

    public HomeCarItemView(Context context) {
        super(context);
    }

    public HomeCarItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind() {

    }
}
