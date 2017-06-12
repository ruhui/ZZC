package com.zzcar.zzc.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.GoodDetailActivity_;
import com.zzcar.zzc.activities.MainActivity;
import com.zzcar.zzc.activities.WebActivity;
import com.zzcar.zzc.activities.WebActivity_;
import com.zzcar.zzc.adapters.HomeCarAdapter;
import com.zzcar.zzc.adapters.HomeCarPushAdapter;
import com.zzcar.zzc.adapters.HomePictureAdapter;
import com.zzcar.zzc.adapters.PictureAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.HomeAdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.interfaces.ShowOrHiddenListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.HomeCarGet;
import com.zzcar.zzc.models.HomeLiveMode;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.HomeAdverResponse;
import com.zzcar.zzc.networks.responses.HomeCarGetResponse;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;
import com.zzcar.zzc.networks.responses.HomeLivemsgResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.pulltorefresh.PullToRefreshBase;
import com.zzcar.zzc.views.pulltorefresh.PullToRefreshScrollView;
import com.zzcar.zzc.views.widget.NavBarDetail;
import com.zzcar.zzc.views.widget.NavBarHomeSearch;
import com.zzcar.zzc.views.widget.ScrollBanner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：主页
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/18 15:30
 **/

@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment{

    @ViewById(R.id.mRollPagerView)
    RollPagerView mRollViewPager;
    @ViewById(R.id.mScrollBanner)
    ScrollBanner mScrollBanner;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    /*订阅*/
    @ViewById(R.id.recyclerview_subcars)
    RecyclerView recyclerview_subcars;
    @ViewById(R.id.scrollView)
    PullToRefreshScrollView myScrollView;
    @ViewById(R.id.textView153)
    TextView subcarMore;
    @ViewById(R.id.mToolbar)
    NavBarHomeSearch mToolbar;
    @ViewById(R.id.relaSearch)
    RelativeLayout relaSearch;

    /*图片集合*/
    private List<HomeAdverResponse> picList = new ArrayList<>();
    /*车源集合*/
    private List<HomeCarPushResponse> carpushList = new ArrayList<>();
    /*订阅集合*/
    private List<HomeCarGet> subcarList = new ArrayList<>();

    /*车源*/
    private HomeCarPushAdapter adapter_carpush;
    /*订阅*/
    private HomeCarAdapter adapter_subcar;
    private HomePictureAdapter adapter_adv;

    private int CURTURNPAGE = Constant.DEFAULTPAGE;

    @Override
    public void onNetChange(int netMobile) {
    }

    @AfterViews
    void initView(){
        EventBus.getDefault().register(this);
        initRollView();
        initData();
        setAlpha(0f);
        myScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        myScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (refreshView.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_END){
                    CURTURNPAGE++;
                     /*获取订阅列表*/
                    getsSubcars();
                }else{
                    subcarList.clear();
                    CURTURNPAGE = Constant.DEFAULTPAGE;
                    initData();
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter_carpush = new HomeCarPushAdapter(caradapter_listener);
        mRecyclerView.setAdapter(adapter_carpush);
        adapter_carpush.addAll(carpushList);

        adapter_subcar = new HomeCarAdapter(adapterListener_subcar);
        recyclerview_subcars.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview_subcars.setNestedScrollingEnabled(false);
        recyclerview_subcars.setAdapter(adapter_subcar);
        adapter_subcar.addAll(subcarList);

        /*我的订阅更多*/
        subcarMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(getActivity(), SubscribeFragment_.builder().build());
            }
        });

        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setPageCurturPage(1);
            }
        });

    }

    void initData(){
        /*获取首页图片*/
        getHomeAd();
        /*获取实况*/
        getHomeLivemsg();
        /*获取车源*/
        getCarpush();
        /*获取订阅列表*/
        getsSubcars();
    }

    /*交易担保*/
    @Click(R.id.relaJiaoyi)
    void sendJiaoyi(){
        showFragment(getActivity(), JiaoyiFragment_.builder().build());
    }

    /*订单*/
    @Click(R.id.relaOrder)
    void sendOrder(){
        showFragment(getActivity(), MyOrderFragment_.builder().build());
    }

    /*钱包*/
    @Click(R.id.relaWallet)
    void sendWalet(){
        showFragment(getActivity(), BalanceFragment_.builder().build());
    }

    /*记录*/
    @Click(R.id.relaRecord)
    void sendRecord(){
        ToastUtil.showToast("开发中");
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

    @Subscribe
    public void onMessageEvent(ShowOrHiddenListener event){
        int height =mToolbar.getMeasuredHeight();
        float scrollY = event.height;
        if (scrollY <= 0){
            setAlpha(0);
            relaSearch.setAlpha(1);
        }else if(scrollY >= height){
            setAlpha(1);
            relaSearch.setAlpha(0);
        }else{
            relaSearch.setAlpha(0);
            setAlpha((float) scrollY/height);
        }
    }

    @Click(R.id.relaSearch)
    void scrollToCarFrom(){
        ((MainActivity)getActivity()).setPageCurturPage(1);
    }



    public void setAlpha(float alpha){
        mToolbar.setAlpha(alpha);
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

    /*获取车源行*/
    AdapterListener caradapter_listener = new AdapterListener<HomeCarPushResponse>() {
        @Override
        public void setOnItemListener(HomeCarPushResponse o, int position) {
            Intent intent = new Intent(getActivity(), GoodDetailActivity_.class);
            intent.putExtra("productId", Integer.valueOf(o.product_id));
            startActivity(intent);
        }
    };


    /*/订阅点击行*/
      /*adapter行点击监听*/
    HomeAdapterListener adapterListener_subcar = new HomeAdapterListener() {
        @Override
        public void setOnItemClckListener(int position, int productId, HomeCarGet homeCarGet) {
            Intent intent = new Intent(getActivity(), GoodDetailActivity_.class);
            intent.putExtra("productId", productId);
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

    /*获取车源*/
    private void getCarpush(){
        Subscriber subscriber = new PosetSubscriber<List<HomeCarPushResponse>>().getSubscriber(callback_scarpush);
        UserManager.getCarpush(subscriber);
    }

    /*获取订阅*/
    private void getsSubcars() {
        Subscriber subscriber = new PosetSubscriber<List<HomeCarPushResponse>>().getSubscriber(callback_subscar);
        UserManager.getsSubcars("", "", CURTURNPAGE, subscriber);
    }


    /*实况*/
    @Click(R.id.textView146)
    void homeLivemsg(){
        showFragment(getActivity(), LiveMsgFragment_.builder().build());
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

    /*车源回调*/
    ResponseResultListener callback_scarpush = new ResponseResultListener<List<HomeCarPushResponse>>() {
        @Override
        public void success(List<HomeCarPushResponse> returnMsg) {
            carpushList.clear();
            carpushList.addAll(returnMsg);
            adapter_carpush.clear();
            adapter_carpush.addAll(carpushList);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    ResponseResultListener callback_subscar = new ResponseResultListener<HomeCarGetResponse>() {
        @Override
        public void success(HomeCarGetResponse returnMsg) {
            myScrollView.onRefreshComplete();
            subcarList.clear();
            subcarList.addAll(returnMsg.getRows());
            if (CURTURNPAGE == Constant.DEFAULTPAGE){
                adapter_subcar.clear();
            }
            adapter_subcar.addAll(subcarList);
            if (CURTURNPAGE > returnMsg.getTotal_pages()){
                //无更多数据
                ToastUtil.showToast("无更多数据");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            myScrollView.onRefreshComplete();
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mScrollBanner != null){
            mScrollBanner.stopScroll();
        }
    }
}
