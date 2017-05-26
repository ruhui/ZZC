package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.models.ApplyFriendModel;
import com.zzcar.zzc.networks.responses.FridendListResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/24.
 */
@EViewGroup(R.layout.adapter_check_friend)
public class CheckFriendViewHold extends LinearLayout {

    private Context mContext;

    @ViewById(R.id.imageView26)
    ImageView imgHeaview;
    @ViewById(R.id.textView134)
    TextView txtName;
    @ViewById(R.id.textView135)
    TextView txtCompany;
    @ViewById(R.id.textView136)
    TextView txtRenzheng;

    public CheckFriendViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public CheckFriendViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(final ApplyFriendModel fridendmodle, final AdapterListener adapterListener, final int position){
        ImageLoader.loadImage(Tool.getPicUrl(mContext, fridendmodle.getPhoto(), 40, 40), imgHeaview);
        txtName.setText(fridendmodle.getName());
        txtCompany.setText(fridendmodle.getShort_content());
        if (fridendmodle.is_friend()){
            txtRenzheng.setText("已添加");
            txtRenzheng.setTextColor(Color.BLACK);
            txtRenzheng.setBackgroundColor(Color.WHITE);
        }else{
            txtRenzheng.setText("接受");
            txtRenzheng.setTextColor(Color.WHITE);
            txtRenzheng.setBackgroundResource(R.drawable.shape_red_radio);
        }

        txtRenzheng.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fridendmodle.is_friend()){
                    //不是朋友  添加朋友
                    adapterListener.setOnItemListener(fridendmodle, position);
                }
            }
        });
    }
}
