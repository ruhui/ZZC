package com.zzcar.zzc.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.GoodDetailActivity_;
import com.zzcar.zzc.activities.MainActivity;
import com.zzcar.zzc.activities.SubscriberSettingActivity;
import com.zzcar.zzc.activities.SubscriberSettingActivity_;
import com.zzcar.zzc.adapters.HomeCarAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.FragmentClosePop;
import com.zzcar.zzc.interfaces.HomeAdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.HomeCarGet;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.requests.SearchRequest;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.networks.responses.HomeCarGetResponse;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.ChannelPopwindow;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.PaixuPopwindow;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/10.
 * 作者：黄如辉
 * 功能描述：我的订阅
 */
@EFragment(R.layout.fragment_subscribe)
public class SubscribeFragment extends BasePullRecyclerFragment {

    @ViewById(R.id.mTab)
    TabLayout mTab;
    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.line2)
    View view;

    private int[] tabStr = new int[]{R.string.paixu, R.string.qudao};
    /*搜索条件*/
    public SearchRequest searchRequest = new SearchRequest();
    /*渠道列表*/
    private List<CarChanelResponse> mChannelList = new ArrayList<>();
    private PopupWindow popupWindow_paixu;
    private PopupWindow popupWindow_qudao;

    private ChannelPopwindow channelPopwindow;
    /*订阅集合*/
    private List<HomeCarGet> subcarList = new ArrayList<>();
    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    /*订阅*/
    private HomeCarAdapter adapter_subcar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        getCarChannel();
        getsSubcars();

        adapter_subcar = new HomeCarAdapter(adapterListener_subcar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter_subcar);
        adapter_subcar.addAll(subcarList);


        Drawable bgdrable = getResources().getDrawable(R.drawable.select_main_item);
        int bgcolor = getActivity().getResources().getColor(R.color.mdtp_transparent_black);

        initTab();

        initBar();

        //排序
        PaixuPopwindow paixuPopwindow = new PaixuPopwindow();
        popupWindow_paixu = paixuPopwindow.showPopupWindow(getActivity(), bgdrable, bgcolor, paixuListener, searchRequest);
        //渠道
        channelPopwindow = new ChannelPopwindow();
        popupWindow_qudao = channelPopwindow.showPopupWindow(getActivity(), bgdrable, bgcolor, mChannelList, channelListener);
    }

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

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        subcarList.clear();
        CURTURNPAGE = Constant.DEFAULTPAGE;
         /*获取订阅列表*/
        getsSubcars();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTURNPAGE++;
        /*获取订阅列表*/
        getsSubcars();
    }

    private void initBar() {
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("我的订阅");
        mNavbar.setRightTxt("设置");
        mNavbar.setRightTxtColor(R.color.app_red);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                //订阅设置
               Intent intent = new Intent(getActivity(), SubscriberSettingActivity_.class);
                startActivity(intent);
            }
        });
    }

    private void initTab() {
        for (int tab:tabStr) {
            mTab.addTab(mTab.newTab().setCustomView(createTabView(tab)));
        }

//        LinearLayout linearLayout = (LinearLayout) mTab.getChildAt(0);
//        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(),
//                R.drawable.layout_divider_vertical));
//        linearLayout.setPadding(0,10,0,10);


        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                ImageView imgView = (ImageView) tab.getCustomView().findViewById(R.id.homeIcon);
                TextView homeTitle = (TextView) tab.getCustomView().findViewById(R.id.homeTitle);
                imgView.setImageResource(R.drawable.nav_icon_up_selected);
                homeTitle.setTextColor(getResources().getColor(R.color.app_red));
                if (position == 0){
                    popupWindow_paixu.showAsDropDown(view);
                    popupWindow_qudao.dismiss();
                }else if(position == 1){
                    popupWindow_paixu.dismiss();
                    popupWindow_qudao.showAsDropDown(view);
                    channelPopwindow.setAdapter(searchRequest);
                }
                setParentShowing(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView homeTitle = (TextView) tab.getCustomView().findViewById(R.id.homeTitle);
                ImageView imgView = (ImageView) tab.getCustomView().findViewById(R.id.homeIcon);
                imgView.setImageResource(R.drawable.nav_icon_down_default);
                homeTitle.setTextColor(getResources().getColor(R.color.color_333333));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                ImageView imgView = (ImageView) tab.getCustomView().findViewById(R.id.homeIcon);
                TextView homeTitle = (TextView) tab.getCustomView().findViewById(R.id.homeTitle);
                if (position == 0){
                    if (popupWindow_paixu.isShowing()){
                        popupWindow_paixu.dismiss();
                        imgView.setImageResource(R.drawable.nav_icon_down_default);
                        homeTitle.setTextColor(getResources().getColor(R.color.color_333333));
                        setParentShowing(false);
                    }else{
                        popupWindow_paixu.showAsDropDown(view);
                        imgView.setImageResource(R.drawable.nav_icon_up_selected);
                        homeTitle.setTextColor(getResources().getColor(R.color.app_red));
                        setParentShowing(true);
                    }
                    popupWindow_qudao.dismiss();
                } else if(position == 1){
                    popupWindow_paixu.dismiss();
                    if (popupWindow_qudao.isShowing()){
                        popupWindow_qudao.dismiss();
                        imgView.setImageResource(R.drawable.nav_icon_down_default);
                        homeTitle.setTextColor(getResources().getColor(R.color.color_333333));
                        setParentShowing(false);
                    }else{
                        popupWindow_qudao.showAsDropDown(view);
                        imgView.setImageResource(R.drawable.nav_icon_up_selected);
                        homeTitle.setTextColor(getResources().getColor(R.color.app_red));
                        channelPopwindow.setAdapter(searchRequest);
                        setParentShowing(true);
                    }
                }
            }
        });
    }

    private View createTabView(int titelResId) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_cheyuan_tab, null);
        ((TextView) view.findViewById(R.id.homeTitle)).setText(titelResId);
        return view;
    }

    /*排序pop的监听*/
    PaixuPopwindow.PaixuListener paixuListener = new PaixuPopwindow.PaixuListener() {
        @Override
        public void defaultValue(String title, String value) {
            searchRequest.setSort(value);
            searchRequest.setSortdes(title);
            loadStateus();
        }
    };

    /**
     * 渠道监听
     * @param value
     */
    ChannelPopwindow.ChannelListener channelListener = new ChannelPopwindow.ChannelListener() {
        @Override
        public void selectItem(String title, String value, int position) {
            showProgress();
            searchRequest.setChannel(value);
            searchRequest.setChanneldes(title);
            closePopwindow();
            setTabDefault();

            subcarList.clear();
            CURTURNPAGE = Constant.DEFAULTPAGE;
            /*获取订阅列表*/
            getsSubcars();
        }
    };

    /*点击排序的操作*/
    private void loadStateus() {
        showProgress();
        closePopwindow();
        setTabDefault();
        CURTURNPAGE = Constant.DEFAULTPAGE;
        subcarList.clear();
        CURTURNPAGE = Constant.DEFAULTPAGE;
         /*获取订阅列表*/
        getsSubcars();
        setParentShowing(false);
    }

    private void closePopwindow() {
        if (popupWindow_paixu != null){
            popupWindow_paixu.dismiss();
        }
        if (popupWindow_qudao != null){
            popupWindow_qudao.dismiss();
        }
    }

    /*需要设置值*/
    public void setTabDefault(){
        for (int i=0; i< mTab.getTabCount(); i++){
            ImageView imgView = (ImageView) mTab.getTabAt(i).getCustomView().findViewById(R.id.homeIcon);
            TextView homeTitle = (TextView) mTab.getTabAt(i).getCustomView().findViewById(R.id.homeTitle);
            imgView.setImageResource(R.drawable.nav_icon_down_default);
            homeTitle.setTextColor(getResources().getColor(R.color.color_333333));
            if (i == 0){
                homeTitle.setText(searchRequest.getSortdes());
            }else if(i == 1){
                homeTitle.setText(searchRequest.getChanneldes());
            }
        }
    }


    /*获取订阅*/
    private void getsSubcars() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<List<HomeCarPushResponse>>().getSubscriber(callback_subscar);
        UserManager.getsSubcars( searchRequest.getSort(), searchRequest.getChannel(), CURTURNPAGE, subscriber);
    }

    /**
     * 获取渠道
     */
    private void getCarChannel() {
        Subscriber subscriber = new PosetSubscriber<List<CarChanelResponse>>().getSubscriber(callback_carchannel);
        UserManager.getCarChannel(subscriber);
    }



    ResponseResultListener callback_subscar = new ResponseResultListener<HomeCarGetResponse>() {
        @Override
        public void success(HomeCarGetResponse returnMsg) {
            closeProgress();
            subcarList.clear();
            subcarList.addAll(returnMsg.getRows());
            if (CURTURNPAGE == Constant.DEFAULTPAGE){
                adapter_subcar.clear();
            }
            adapter_subcar.addAll(subcarList);
            if (CURTURNPAGE > returnMsg.getTotal_pages()){
               finishLoad(false);
            }else{
                finishLoad(true);
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    /*渠道回调*/
    ResponseResultListener callback_carchannel = new ResponseResultListener<List<CarChanelResponse>>() {
        @Override
        public void success(List<CarChanelResponse> returnMsg) {
            LogUtil.E("success","success");
            mChannelList.addAll(returnMsg);
            channelPopwindow.setAdapter(mChannelList, searchRequest);
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };

    /*activity和popwindow的联动*/
    public void setParentShowing(boolean ispopshowing){
        ((MainActivity) getActivity()).popisShowing = ispopshowing;
    }

    @Subscribe
    public void setClosePop(FragmentClosePop closePop){
        if (closePop.closePop){
            closePopwindow();
            setParentShowing(false);
            setTabDefault();
        }
    }


    @Override
    public boolean onBackPressed() {
        if (popupWindow_paixu != null || popupWindow_qudao != null){
            if (popupWindow_paixu != null){
                popupWindow_paixu.dismiss();
            }
            if (popupWindow_qudao != null){
                popupWindow_qudao.dismiss();
            }
            return false;
        }else{
            return super.onBackPressed();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closePopwindow();
        EventBus.getDefault().unregister(this);
    }
}
