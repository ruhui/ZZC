package com.zzcar.zzc.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.viewpagerindicator.IconPagerAdapter;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.MycarFromActivity;
import com.zzcar.zzc.adapters.FriendAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.FridendListResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.Tool;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述 我的好友
 * 作者：黄如辉  时间 2017/5/24.
 */
@EFragment(R.layout.fragment_msg)
public class MyFriendFragment extends BaseFragment {

    @ViewById(R.id.editText16)
    EditText edtSearchMsg;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.txtNofriend)
    TextView txtNofriend;

    private FriendAdapter adapter;
    private List<FridendListResponse> mList = new ArrayList<>();

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){

        /*获取好友列表*/
        getFriendList();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter = new FriendAdapter());
        adapter.addAll(mList);



        edtSearchMsg.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    Tool.hideInputMethod(getActivity(), edtSearchMsg);
                    /*搜索好友*/
                    getFriendList();
                    return true;
                }
                return false;
            }
        });
    }


    private void getFriendList(){
        String name = edtSearchMsg.getText().toString();
        Subscriber subscribe = new PosetSubscriber<List<FridendListResponse>>().getSubscriber(callback_friend);
        UserManager.getFriendList(name, subscribe);
    }

    ResponseResultListener callback_friend = new ResponseResultListener<List<FridendListResponse>>() {
        @Override
        public void success(List<FridendListResponse> returnMsg) {
            mList.clear();
            mList.addAll(returnMsg);
            adapter.clear();
            adapter.addAll(mList);
            if (returnMsg.size() == 0){
                txtNofriend.setVisibility(View.VISIBLE);
            }else{
                txtNofriend.setVisibility(View.GONE);
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
        }
    };
}
