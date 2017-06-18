package com.zzcar.zzc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.ChatActivity;
import com.zzcar.zzc.adapters.MsgListAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.MessageListResponse;
import com.zzcar.zzc.utils.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

import static com.hyphenate.easeui.EaseConstant.CHATTYPE_SINGLE;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/24.
 */
@EFragment(R.layout.fragment_msgf)
public class MsgFragment extends BaseFragment{

    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private MsgListAdapter adapter;
    private List<MessageListResponse> mList = new ArrayList<>();

    @Override
    public void onNetChange(int netMobile) {

    }


    @AfterViews
    void initView(){
        /*获取消息列表*/
        getMessageList();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter = new MsgListAdapter(adapterListener));
        adapter.addAll(mList);
        /*初始化环信消息监听‘*/
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    AdapterListener adapterListener = new AdapterListener<MessageListResponse>() {
        @Override
        public void setOnItemListener(MessageListResponse o, int position) {
            MessageListResponse messageList =  mList.get(position);
            //1业务消息，2新朋友（验证朋友），3聊天,4群聊
            switch (messageList.getType()){
                case 1:
                    //业务消息
                    break;
                case 2:
                    //新朋友（验证朋友）
                    showFragment(getActivity(), CheckFriendFragment_.builder().build());
                    break;
                case 3:

                    //传入参数
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("userId", o.getObject_id()+"");
                    intent.putExtra("nick", o.getName());
                    intent.putExtra("headImg", o.getPhoto());
                    intent.putExtra("isfriend", o.is_friend());
                    intent.putExtra("chatType", CHATTYPE_SINGLE);
                    startActivity(intent);
                    break;
                case 4:
                    //群聊
                    Intent intent_group = new Intent(getActivity(), ChatActivity.class);
                    intent_group.putExtra("chatType", EaseConstant.CHATTYPE_GROUP);
                    intent_group.putExtra("userId", o.getObject_id()+"");
                    intent_group.putExtra("nick", o.getName());
                    intent_group.putExtra("headImg", o.getPhoto());
                    intent_group.putExtra("isfriend", true);
                    startActivity(intent_group);
                    break;
            }
        }
    };

    private void getMessageList() {
        Subscriber subscriber = new PosetSubscriber<List<MessageListResponse>>().getSubscriber(callback_message);
        UserManager.getMssageList(subscriber);
    }



    ResponseResultListener callback_message = new ResponseResultListener<List<MessageListResponse>>() {
        @Override
        public void success(List<MessageListResponse> returnMsg) {
            mList.clear();
            mList.addAll(returnMsg);
            adapter.clear();
            adapter.addAll(mList);
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
        }
    };


    /*环信消息监听*/
    /*消息监听*/
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息  收到消息，刷新列表，并且获取未读的条数
            getMessageList();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            LogUtil.E("onCmdMessageReceived","onCmdMessageReceived");
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            LogUtil.E("onMessageRead","onMessageRead");
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            LogUtil.E("onMessageChanged","onMessageChanged");
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);

    }
}
