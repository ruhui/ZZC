package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zzcar.zzc.R;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/9.
 */

@EViewGroup(R.layout.adapter_imageview)
public class ItemImageView extends RelativeLayout {

    private Context mContext;
    @ViewById(R.id.imageView9)
    ImageView imageView;

    public ItemImageView(Context context) {
        super(context);
        mContext = context;
    }

    public ItemImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(String picture, int dimwidth, int dimheigh){
        ImageLoader.loadImage(Tool.getPicUrl(mContext, picture, dimwidth, dimheigh), imageView,R.drawable.pic_bg_default);
    }
}
