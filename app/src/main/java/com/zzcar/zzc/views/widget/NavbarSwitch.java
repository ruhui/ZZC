package com.zzcar.zzc.views.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zzcar.zzc.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/24.
 */
@EViewGroup(R.layout.view_nav_bar4)
public class NavbarSwitch extends Toolbar {

    @ViewById(R.id.textView132)
    TextView txtMessage;
    @ViewById(R.id.textView133)
    TextView txtFriend;
    private SwitchListener listener;

    public NavbarSwitch(Context context) {
        super(context);
    }

    public NavbarSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Click(R.id.textView132)
    public void setMessageClickListener(){
        if(listener != null){
            txtMessage.setBackgroundResource(R.drawable.shape_radio_message_switch);
            txtMessage.setTextColor(Color.WHITE);
            txtFriend.setBackgroundResource(R.drawable.shape_radio_fridend_unswich);
            txtFriend.setTextColor(Color.RED);
            listener.messageListener();
        }
    }

    @Click(R.id.textView133)
    public void setFridendListener(){
        if (listener != null){
            txtMessage.setBackgroundResource(R.drawable.shape_radio_message_unswitch);
            txtMessage.setTextColor(Color.RED);
            txtFriend.setBackgroundResource(R.drawable.shape_radio_fridend_swich);
            txtFriend.setTextColor(Color.WHITE);
            listener.fridendListener();
        }
    }

    public void setFridListener(){
        txtMessage.setBackgroundResource(R.drawable.shape_radio_message_unswitch);
        txtMessage.setTextColor(Color.RED);
        txtFriend.setBackgroundResource(R.drawable.shape_radio_fridend_swich);
        txtFriend.setTextColor(Color.WHITE);
        listener.fridendListener();
    }

    public void setMsgListener(){
        txtMessage.setBackgroundResource(R.drawable.shape_radio_message_switch);
        txtMessage.setTextColor(Color.WHITE);
        txtFriend.setBackgroundResource(R.drawable.shape_radio_fridend_unswich);
        txtFriend.setTextColor(Color.RED);
        listener.messageListener();
    }

    public void setSwitchListener(SwitchListener listener){
        this.listener = listener;
    }

    public interface SwitchListener{
        public void messageListener();
        public void fridendListener();
    }
}
