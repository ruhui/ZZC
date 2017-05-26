package com.zzcar.zzc.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hyphenate.chat.EMClient;
import com.zzcar.zzc.R;
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

    }


    @AfterViews
    void initView(){
        /*获取消息列表*/
        getMessageList();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter = new MsgListAdapter(adapterListener));
        adapter.addAll(mList);
    }

    AdapterListener adapterListener = new AdapterListener<MessageListResponse>() {
        @Override
        public void setOnItemListener(MessageListResponse o, int position) {
            showFragment(getActivity(), CheckFriendFragment_.builder().build());
        }
    };

    private void getMessageList() {
        Subscriber subscriber = new PosetSubscriber<List<MessageListResponse>>().getSubscriber(callback_message);
        UserManager.getMssageList(subscriber);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    ResponseResultListener callback_message = new ResponseResultListener<List<MessageListResponse>>() {
        @Override
        public void success(List<MessageListResponse> returnMsg) {
            mList.clear();
            mList.addAll(returnMsg);
            adapter.clear();
            adapter.replaceWith(mList);
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
        }
    };
}
