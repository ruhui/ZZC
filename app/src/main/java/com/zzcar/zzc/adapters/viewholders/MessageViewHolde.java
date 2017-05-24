package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.networks.responses.MessageListResponse;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/25.
 */
@EViewGroup(R.layout.adapter_message)
public class MessageViewHolde extends LinearLayout {

    @ViewById(R.id.imageView26)
    ImageView imgHeadView;
    @ViewById(R.id.textView134)
    TextView txtName;
    @ViewById(R.id.textView135)
    TextView txtContent;
    @ViewById(R.id.textView137)
    TextView txTime;
    @ViewById(R.id.textView138)
    TextView unRedCount;

    private Context mContext;


    public MessageViewHolde(Context context) {
        super(context);
        mContext = context;
    }

    public MessageViewHolde(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void bind(MessageListResponse message){
//        1业务消息，2新朋友（验证朋友），3聊天,4群聊
        switch (message.getType()){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
        ImageLoader.loadImage(Tool.getPicUrl(mContext, message.getPhoto(), 40, 40), imgHeadView);
        txtName.setText(message.getName());
        txtContent.setText(message.getShort_content());
        txTime.setText(Tool.getTimeFormat(message.getCreate_time()));
        if (message.getNew_count() > 99){
            unRedCount.setText("99+");
        }else{
            unRedCount.setText(message.getNew_count());
        }
    }
}
