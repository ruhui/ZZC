package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.models.IntegralDetail;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/15.
 */

@EViewGroup(R.layout.adapter_integral)
public class IntegralViewHold extends LinearLayout {

    @ViewById(R.id.textView78)
    TextView title;
    @ViewById(R.id.textView79)
    TextView timedata;
    @ViewById(R.id.textView80)
    TextView point;

    public IntegralViewHold(Context context) {
        super(context);
    }

    public IntegralViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(IntegralDetail integralDetail){
        title.setText(integralDetail.getName());
        timedata.setText(integralDetail.getCreate_time());
        point.setText(integralDetail.getIntegral()+"");
    }
}
