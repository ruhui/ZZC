package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.networks.responses.FridendListResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/24.
 */
@EViewGroup(R.layout.adapter_friend)
public class FriendViewHold extends LinearLayout {

    private Context mContext;

    @ViewById(R.id.imageView26)
    ImageView imgHeaview;
    @ViewById(R.id.textView134)
    TextView txtName;
    @ViewById(R.id.textView135)
    TextView txtCompany;
    @ViewById(R.id.textView136)
    TextView txtRenzheng;

    public FriendViewHold(Context context) {
        super(context);
        mContext = context;
    }

    public FriendViewHold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(FridendListResponse fridendmodle){
        ImageLoader.loadImage(Tool.getPicUrl(mContext, fridendmodle.getFriend().getPhoto(), 40, 40), imgHeaview);
        txtName.setText(fridendmodle.getFriend().getNick());
        txtCompany.setText(fridendmodle.getFriend().getShop_name());
        txtRenzheng.setText(fridendmodle.getFriend().getAuth_status_name());
    }
}
