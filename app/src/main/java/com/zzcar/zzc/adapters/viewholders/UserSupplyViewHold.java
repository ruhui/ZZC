package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.interfaces.SupplyListener;
import com.zzcar.zzc.models.MysupplyModel;
import com.zzcar.zzc.models.SupplyModel;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/15 14:56
 **/
@EViewGroup(R.layout.adapter_supply)
public class UserSupplyViewHold extends LinearLayout {

    @ViewById(R.id.textView192)
    TextView txtTime;
    @ViewById(R.id.textView8)
    TextView txtContent;
    @ViewById(R.id.imageView8)
    ImageView imgProduct;
    @ViewById(R.id.imageView43)
    ImageView imgCancle;
    @ViewById(R.id.imageView42)
    ImageView imgEdittext;

    private Context mContext;

    public UserSupplyViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public UserSupplyViewHold(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(final SupplyModel model){
        txtTime.setText(model.getEnd_time());
        txtContent.setText(model.getContent());
        ImageLoader.loadImage(Tool.getPicUrl(mContext, model.getFirst_image(), 92, 67), imgProduct);
        imgCancle.setVisibility(GONE);
        imgEdittext.setVisibility(GONE);
    }
}
