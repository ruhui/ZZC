package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/3.
 */

@EViewGroup(R.layout.view_item_one)
public class ItemOneView extends LinearLayout {

    @ViewById(R.id.txtLeft)
    TextView txtLeft;
    @ViewById(R.id.textView7)
    TextView txtMiddle;

    public ItemOneView(Context context) {
        super(context);
    }

    public ItemOneView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTxtLeft(String text){
        txtLeft.setText(text);
    }

    public void setHint(String text){
        txtMiddle.setHint(text);
    }

    public void setTxtMiddle(String text){
        txtMiddle.setText(text);
    }

    public String getTxtMiddle(){
        return txtMiddle.getText().toString();
    }
}
