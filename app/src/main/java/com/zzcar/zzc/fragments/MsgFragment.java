package com.zzcar.zzc.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
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
