package com.zzcar.zzc.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.MsgListAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
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

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/24.
 */
@EFragment(R.layout.fragment_msgf)
public class MsgFragment extends BaseFragment {

    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private MsgListAdapter adapter;
    private List<MessageListResponse> mList = new ArrayList<>();

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*初始化环信消息监听‘*/
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        /*好友管理监听*/
        EMClient.getInstance().contactManager().setContactListener(friendListener);
    }

    @AfterViews
    void initView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter = new MsgListAdapter());
        adapter.addAll(mList);

        /*获取消息列表*/
        getMessageList();
    }

    private void getMessageList() {
        Subscriber subscriber = new PosetSubscriber<List<MessageListResponse>>().getSubscriber(callback_message);
        UserManager.getMssageList(subscriber);
    }


    /*消息监听*/
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息  收到消息，刷新列表，并且获取未读的条数
            LogUtil.E("onMessageReceived","onMessageReceived");
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

    //好友状态请求
    EMContactListener friendListener = new EMContactListener() {

        @Override
        public void onContactInvited(String username, String reason) {
            //收到好友邀请
            LogUtil.E("onContactInvited", "onContactInvited");
        }

        @Override
        public void onFriendRequestAccepted(String s) {
            //好友请求被同意
            LogUtil.E("onContactInvited", "onContactInvited");
        }

        @Override
        public void onFriendRequestDeclined(String s) {
            //好友请求被拒绝
            LogUtil.E("onContactInvited", "onContactInvited");
        }

        @Override
        public void onContactDeleted(String username) {
            //被删除时回调此方法
            LogUtil.E("onContactInvited", "onContactInvited");
        }


        @Override
        public void onContactAdded(String username) {
            //增加了联系人时回调此方法
            LogUtil.E("onContactInvited", "onContactInvited");
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        /*移除监听*/
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
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
}
