package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.models.SystemModel;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/6/25.
 * 作者：黄如辉
 * 功能描述：
 */

@EViewGroup(R.layout.adapter_systemmsg)
public class SystemMsgViewHold extends LinearLayout {

    @ViewById(R.id.textView204)
    TextView txtTitle;
    @ViewById(R.id.textView205)
    TextView txtContent;
    @ViewById(R.id.textView206)
    TextView txtTime;
    private Context mContext;

    public SystemMsgViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public SystemMsgViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(SystemModel model){
        if (model.is_read()){
            txtTitle.setTextColor(mContext.getResources().getColor(R.color.gray));
            txtContent.setTextColor(mContext.getResources().getColor(R.color.gray));
            txtTime.setTextColor(mContext.getResources().getColor(R.color.gray));
        }else{
            txtTitle.setTextColor(mContext.getResources().getColor(R.color.color_090909));
            txtContent.setTextColor(mContext.getResources().getColor(R.color.color_090909));
            txtTime.setTextColor(mContext.getResources().getColor(R.color.color_090909));
        }
        txtTitle.setText(model.getTitle());
        txtContent.setText(model.getContent());
        txtTime.setText(model.getCreate_time());
    }
}
