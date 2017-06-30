package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.networks.responses.MyEmployeeResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/6/30.
 * 作者：黄如辉
 * 功能描述：
 */
@EViewGroup(R.layout.adapter_myployee)
public class MyEmployeeViewHold extends LinearLayout {

    @ViewById(R.id.imageView52)
    ImageView imgHead;
    @ViewById(R.id.textView224)
    TextView txtNick;
    @ViewById(R.id.textView225)
    TextView txtPhone;
    @ViewById(R.id.imageView53)
    ImageView imgEdit;

    private Context mContext;

    public MyEmployeeViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public MyEmployeeViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(final MyEmployeeResponse response, final AdapterListener adapterListener, final int position){
        ImageLoader.loadCircleImage(Tool.getPicUrl(mContext, response.getPhoto(), 40, 40), imgHead, R.drawable.nav_icon_head_default);
        txtNick.setText(response.getNick());
        txtPhone.setText(response.getMobile());
        imgEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.setOnItemListener(response, position);
            }
        });
    }
}
