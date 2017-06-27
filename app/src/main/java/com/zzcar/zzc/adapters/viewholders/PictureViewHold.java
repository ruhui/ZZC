package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zzcar.zzc.R;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/6/27.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_picture)
public class PictureViewHold extends LinearLayout {

    @ViewById(R.id.imageView48)
    ImageView imgPicture;

    private Context mContext;

    public PictureViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public PictureViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(String imagePath){
        ImageLoader.loadCircleImage(Tool.getPicUrl(mContext, imagePath, 30, 30), imgPicture, R.drawable.ease_default_avatar);
    }
}
