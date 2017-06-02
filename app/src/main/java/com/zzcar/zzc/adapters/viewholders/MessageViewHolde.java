package com.zzcar.zzc.adapters.viewholders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.zzcar.zzc.R;
import com.zzcar.zzc.interfaces.AdapterListener;
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

    public void bind(MessageListResponse message, int position){
//        1业务消息，2新朋友（验证朋友），3聊天,4群聊
        switch (message.getType()){
            case 1:
                ImageLoader.loadResourceImage(R.drawable.iconmsg_yewu, imgHeadView, 0);
                break;
            case 2:
                ImageLoader.loadResourceImage(R.drawable.nav_icon_head_haoyou, imgHeadView, 0);
                break;
            case 3:
                ImageLoader.loadImage(Tool.getPicUrl(mContext, message.getPhoto(), 40, 40), imgHeadView, R.drawable.nav_icon_head_default);
                break;
            case 4:
                ImageLoader.loadImage(Tool.getPicUrl(mContext, message.getPhoto(), 40, 40), imgHeadView, R.drawable.nav_icon_head_default);
                break;
        }
        txtName.setText(message.getName());
        txtContent.setText(message.getShort_content());
        txTime.setText(Tool.getTimeFormat(message.getCreate_time()));
        EMChatManager emChatManager = EMClient.getInstance().chatManager();
        int unread = 0;
        EMConversation conversation = emChatManager.getConversation(Integer.valueOf((int) message.getObject_id())+"");
        if (conversation !=null){
            unread = conversation.getUnreadMsgCount();
        }
        if (unread > 99){
            unRedCount.setText("99+");
            unRedCount.setVisibility(VISIBLE);
        }else{
            if (unread == 0){
                unRedCount.setVisibility(INVISIBLE);
            }else{
                unRedCount.setVisibility(VISIBLE);
            }
            unRedCount.setText(message.getNew_count()+"");
        }
    }
}
