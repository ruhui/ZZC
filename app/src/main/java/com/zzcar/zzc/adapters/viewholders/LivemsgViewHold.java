package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.models.HomeLiveMode;
import com.zzcar.zzc.models.ImageList;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/6/7.
 * 作者：黄如辉
 * 功能描述：
 */

@EViewGroup(R.layout.adapter_livemsg)
public class LivemsgViewHold extends LinearLayout {

    @ViewById(R.id.imageView34)
    ImageView imgHead;
    @ViewById(R.id.textView147)
    TextView txtName;
    @ViewById(R.id.textView148)
    TextView txtTime;
    @ViewById(R.id.textView149)
    TextView txtContent;

    private Context mContext;

    public LivemsgViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public LivemsgViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(HomeLiveMode model, int position){
        ImageLoader.loadCircleImage(Tool.getPicUrl(mContext, model.getMember().getPhoto(), 20, 20), imgHead, R.drawable.nav_icon_head_default);
        txtName.setText(model.getMember().getNick());
        txtTime.setText(Tool.getTimeFormat(model.getCreate_time()));
        txtContent.setText(model.getContent());
    }
}
