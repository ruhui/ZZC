package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/6/7.
 * 作者：黄如辉
 * 功能描述：
 */

@EViewGroup(R.layout.adapter_homecarpush)
public class HomeCarPushViewHOld extends LinearLayout {

    @ViewById(R.id.imageView35)
    ImageView imgPorductl;
    @ViewById(R.id.textView150)
    TextView txtProductdes;
    @ViewById(R.id.textView151)
    TextView txtMoney;

    private Context mContext;


    public HomeCarPushViewHOld(Context context) {
        super(context);
        mContext = context;
    }

    public HomeCarPushViewHOld(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(HomeCarPushResponse model, int position){
        ImageLoader.loadImage(Tool.getPicUrl(mContext, model.first_image, 160, 105), imgPorductl, R.drawable.pic_bg_default);
        txtProductdes.setText(model.name);
        txtMoney.setText("¥" + model.price + "万元");
    }
}
