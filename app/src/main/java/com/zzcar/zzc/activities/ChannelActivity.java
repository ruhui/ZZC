package com.zzcar.zzc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.ChannelAdapter;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

@EActivity(R.layout.view_channel_recycleview)
public class ChannelActivity extends BaseActivity {

    /*渠道列表*/
    private List<CarChanelResponse> mChannelList = new ArrayList<>();
    private ChannelAdapter adapter;

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.relaItem)
    RelativeLayout relaItem;



    @AfterViews
    void initView(){

        String actionTitle = getIntent().getStringExtra("actionTitle");
        /*不显示不限，true为不显示*/
        boolean dismisbuxian =  getIntent().getBooleanExtra("dismisbuxian", false);
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        if (!TextUtils.isEmpty(actionTitle)){
            mNavbar.setMiddleTitle(actionTitle);
        }else{
            mNavbar.setMiddleTitle("渠道");
        }

        if (dismisbuxian){
            relaItem.setVisibility(View.GONE);
        }
        mNavbar.setRightTxtColor(R.color.color_959595);
        getCarChannel();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter = new ChannelAdapter(getActivity(), mChannelList, itemClickListener));
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
            }
        });

        relaItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("channelid", "");
                intent.putExtra("channeldes", "不限");
                setResult(10101, intent);
                finish();
            }
        });
    }


    ChannelAdapter.ItemClickListener itemClickListener = new ChannelAdapter.ItemClickListener() {
        @Override
        public void setOnItemClickListener(String text, String value, int position) {
            Intent intent = new Intent();
            intent.putExtra("channelid", value);
            intent.putExtra("channeldes", text);
            setResult(10101, intent);
            finish();
        }
    };

    @Override
    public void onNetChange(int netMobile) {

    }

    /**
     * 获取渠道
     */
    private void getCarChannel() {
        Subscriber subscriber = new PosetSubscriber<List<CarChanelResponse>>().getSubscriber(callback_carchannel);
        UserManager.getCarChannel(subscriber);
    }

    /*渠道回调*/
    ResponseResultListener callback_carchannel = new ResponseResultListener<List<CarChanelResponse>>() {
        @Override
        public void success(List<CarChanelResponse> returnMsg) {
            LogUtil.E("success","success");
            mChannelList.clear();
            mChannelList.addAll(returnMsg);
            adapter.setData(mChannelList);
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };
}
