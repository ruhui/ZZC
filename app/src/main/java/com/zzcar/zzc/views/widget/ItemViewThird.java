package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/7.
 */

@EViewGroup(R.layout.view_item_third)
public class ItemViewThird extends LinearLayout {

    @ViewById(R.id.imageView7)
    ImageView imageView7;
    @ViewById(R.id.textView53)
    TextView textView53;

    public ItemViewThird(Context context) {
        super(context);
    }

    public ItemViewThird(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImgResouse(int drawable){
        imageView7.setImageResource(drawable);
    }

    public void setNameText(String title){
        textView53.setText(title);
    }
}
