package com.zzcar.zzc.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.WebActivity;
import com.zzcar.zzc.activities.WebActivity_;
import com.zzcar.zzc.adapters.HomePictureAdapter;
import com.zzcar.zzc.adapters.PictureAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.HomeLiveMode;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.HomeAdverResponse;
import com.zzcar.zzc.networks.responses.HomeLivemsgResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.ScrollBanner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：主页
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/18 15:30
 **/

@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment implements ScrollBanner.ScrollerBannerListener {

    @ViewById(R.id.mRollPagerView)
    RollPagerView mRollViewPager;
    @ViewById(R.id.mScrollBanner)
    ScrollBanner mScrollBanner;

    /*图片集合*/
    private List<HomeAdverResponse> picList = new ArrayList<>();
    private HomePictureAdapter adapter_adv;

    @Override
    public void onNetChange(int netMobile) {


    }

    @AfterViews
    void initView(){
        initRollView();
        initData();
    }

    void initData(){
        /*获取首页图片*/
        getHomeAd();
        /*获取实况*/
        getHomeLivemsg();


        mScrollBanner.setScrolItemClickListener(this);
    }


    private void initRollView() {
        //获取屏幕宽度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(2000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        adapter_adv = new HomePictureAdapter(getActivity(), dm.widthPixels, picList, adapterListener);
        //设置适配器
        mRollViewPager.setAdapter(adapter_adv);
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.RED, Color.WHITE));
    }

    /*广告图点击*/
    AdapterListener adapterListener = new AdapterListener<HomeAdverResponse>() {
        @Override
        public void setOnItemListener(HomeAdverResponse o, int position) {
            Intent intent = new Intent(getActivity(), WebActivity_.class);
            intent.putExtra("titleBar", o.getName());
            intent.putExtra("webUrl", o.getUrl());
            startActivity(intent);
        }
    };

    /*获取首页的数据啦*/
    private void getHomeAd() {
        Subscriber subscriber = new PosetSubscriber<List<HomeAdverResponse>>().getSubscriber(callback_homeadv);
        UserManager.getHomeAd(subscriber);
    }

    /*获取实况*/
    private void getHomeLivemsg() {
        Subscriber subscriber = new PosetSubscriber<HomeLivemsgResponse>().getSubscriber(callback_shikuang);
        UserManager.getHomeLivemsg(Constant.DEFAULTPAGE, subscriber);
    }





    /*首页图片回调*/
    ResponseResultListener callback_homeadv = new ResponseResultListener<List<HomeAdverResponse>>() {
        @Override
        public void success(List<HomeAdverResponse> returnMsg) {
            picList.clear();
            picList.addAll(returnMsg);
            adapter_adv.setPicture(picList);
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };

    /*实况*/
    ResponseResultListener callback_shikuang = new ResponseResultListener<HomeLivemsgResponse>() {
        @Override
        public void success(HomeLivemsgResponse returnMsg) {
            mScrollBanner.setList(returnMsg.getRows());
            mScrollBanner.startScroll();
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
        }
    };

    /*广告条实况点击*/
    @Override
    public void setClickListener(HomeLiveMode model) {
        LogUtil.E("setClickListener", model.getContent());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mScrollBanner != null){
            mScrollBanner.stopScroll();
        }
    }
}
