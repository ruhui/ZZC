package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/24 14:34
 **/

@EViewGroup(R.layout.view_itemtextimg)
public class ItemTextImgView extends RelativeLayout {
    @ViewById(R.id.relaPaixu)
    RelativeLayout relaPaixu;
    @ViewById(R.id.textView2)
    TextView textView2;
    @ViewById(R.id.imageView2)
    ImageView imageView2;

    public ItemTextImgView(Context context) {
        super(context);
    }

    public ItemTextImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSelect(){
        textView2.setTextColor(getResources().getColor(R.color.app_red));
        imageView2.setVisibility(VISIBLE);
    }

    public void setUnselect(){
        textView2.setTextColor(getResources().getColor(R.color.color_333333));
        imageView2.setVisibility(INVISIBLE);
    }

    public void setTextValue(String value){
        textView2.setText(value);
    }


    public void setOnSelectListener(OnClickListener listener){
        relaPaixu.setOnClickListener(listener);
    }
}
