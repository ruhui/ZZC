package com.zzcar.zzc.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zzcar.greendao.BrandListResponseDao;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.MainActivity;
import com.zzcar.zzc.activities.SearchActivity_;
import com.zzcar.zzc.adapters.HomeCarAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.FragmentClosePop;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.HomeCarGet;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.requests.SearchRequest;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.networks.responses.HomeCarGetResponse;
import com.zzcar.zzc.utils.GreenDaoUtils;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.BrandPopwindow;
import com.zzcar.zzc.views.widget.PriceBetweenPopwindow;
import com.zzcar.zzc.views.widget.ChannelPopwindow;
import com.zzcar.zzc.views.widget.NavBarSearch;
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
 * 描述：车源
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/25 11:05
 **/

@EFragment(R.layout.fragment_home_carfrom)
public class CarFromFragment extends BasePullRecyclerFragment {

    BrandListResponseDao brandDao;
    /*搜索条件*/
    public SearchRequest searchRequest = new SearchRequest();
    private int[] tabStr = new int[]{R.string.paixu, R.string.qudao, R.string.brand, R.string.price};
    private HomeCarAdapter carfromAdapter;

    /*当前页码*/
    private int CURTURNPAGE = Constant.DEFAULTPAGE;

    private ChannelPopwindow channelPopwindow;
    private PriceBetweenPopwindow priceBetweenPopwindow;
    private BrandPopwindow brandPopwindow;

    private PopupWindow popupWindow_paixu;
    private PopupWindow popupWindow_qudao;
    private PopupWindow popupWindow_brand;
    private PopupWindow popupWindow_price;

    List<HomeCarGet> mList = new ArrayList<>();
    /*渠道列表*/
    private List<CarChanelResponse> mChannelList = new ArrayList<>();
    /*价格列表*/
    private List<CarChanelResponse> mPricelList = new ArrayList<>();
    /*品牌列表*/
    private List<BrandListResponse> mBrandList = new ArrayList<>();

    @ViewById(R.id.line2)
    View view;
    @ViewById(R.id.mTab)
    TabLayout mTab;
    @ViewById(R.id.mNavbar)
    NavBarSearch mNavbar;

    private void initBar() {
        //搜索
        mNavbar.setSearchTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity_.class);
                startActivityForResult(intent, 10200);
                closePopwindow();
                setTabDefault();
            }
        });
        mNavbar.setSearchImgListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity_.class);
                startActivityForResult(intent, 10200);
                closePopwindow();
                setTabDefault();
            }
        });

        mNavbar.onSeacherListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    CURTURNPAGE = Constant.DEFAULTPAGE;
                    mList.clear();
                    getCarsData();
                    Tool.hideInputMethod(getActivity(), mNavbar);
                    return true;
                }
                return false;
            }
        });
    }


    private void initTab() {
        for (int tab:tabStr) {
            mTab.addTab(mTab.newTab().setCustomView(createTabView(tab)));
        }

        LinearLayout linearLayout = (LinearLayout) mTab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(),
                R.drawable.layout_divider_vertical));
        linearLayout.setPadding(0,10,0,10);


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
                    popupWindow_brand.dismiss();
                    popupWindow_price.dismiss();
                }else if(position == 1){
                    popupWindow_paixu.dismiss();
                    popupWindow_qudao.showAsDropDown(view);
                    popupWindow_brand.dismiss();
                    popupWindow_price.dismiss();
                    channelPopwindow.setAdapter(searchRequest);

                }else if(position == 2){
                    popupWindow_paixu.dismiss();
                    popupWindow_qudao.dismiss();
                    popupWindow_brand.showAsDropDown(view);
                    popupWindow_price.dismiss();
                    if (mBrandList.size() == 0){
                        mBrandList.clear();
                        getBrad();
                    }else{
                        brandPopwindow.setData();
                    }
                }else if(position == 3){
                    popupWindow_paixu.dismiss();
                    popupWindow_qudao.dismiss();
                    popupWindow_brand.dismiss();
                    popupWindow_price.showAsDropDown(view);
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
                    popupWindow_brand.dismiss();
                    popupWindow_price.dismiss();
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
                        setParentShowing(true);
                        channelPopwindow.setAdapter(searchRequest);

                    }

                    popupWindow_brand.dismiss();
                    popupWindow_price.dismiss();
                }else if(position == 2){
                    if (mBrandList.size() == 0){
                        mBrandList.clear();
                        getBrad();
                    }else{
                        brandPopwindow.setData();
                    }

                    popupWindow_paixu.dismiss();
                    popupWindow_qudao.dismiss();
                    if (popupWindow_brand.isShowing()){
                        popupWindow_brand.dismiss();
                        imgView.setImageResource(R.drawable.nav_icon_down_default);
                        homeTitle.setTextColor(getResources().getColor(R.color.color_333333));
                        setParentShowing(false);
                    }else{
                        popupWindow_brand.showAsDropDown(view);
                        imgView.setImageResource(R.drawable.nav_icon_up_selected);
                        homeTitle.setTextColor(getResources().getColor(R.color.app_red));
                        setParentShowing(true);
                    }
                    popupWindow_price.dismiss();
                }else if(position == 3){
                    popupWindow_paixu.dismiss();
                    popupWindow_qudao.dismiss();
                    popupWindow_brand.dismiss();
                    if (popupWindow_price.isShowing()){
                        popupWindow_price.dismiss();
                        imgView.setImageResource(R.drawable.nav_icon_down_default);
                        homeTitle.setTextColor(getResources().getColor(R.color.color_333333));
                        setParentShowing(false);
                    }else{
                        popupWindow_price.showAsDropDown(view);
                        imgView.setImageResource(R.drawable.nav_icon_up_selected);
                        homeTitle.setTextColor(getResources().getColor(R.color.app_red));
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



    @Override
    public void onNetChange(int netMobile) {

    }

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

    private void closePopwindow() {
        if (popupWindow_paixu != null){
            popupWindow_paixu.dismiss();
        }
        if (popupWindow_qudao != null){
            popupWindow_qudao.dismiss();
        }
        if (popupWindow_brand != null){
            popupWindow_brand.dismiss();
        }
        if (popupWindow_price != null){
            popupWindow_price.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
            resertChannelStatus();
            CURTURNPAGE = Constant.DEFAULTPAGE;
            mList.clear();
            getCarsData();
        }
    };

    /**
     * 价格监听
     * @param value
     */
    PriceBetweenPopwindow.PriceBetweenListener priceBetweenListener = new PriceBetweenPopwindow.PriceBetweenListener() {
        @Override
        public void selectItem(String title, String value, int position) {
            showProgress();
            searchRequest.setMin_price("");
            searchRequest.setMax_price("");
            searchRequest.setPrice_type(value);
            searchRequest.setPrice_typedes(title);
            resertChannelStatus();
            CURTURNPAGE = Constant.DEFAULTPAGE;
            mList.clear();
            getCarsData();
        }

        @Override
        public void sureSelfPrice(String title, String startprice, String endprice) {
            searchRequest.setPrice_type("");
            searchRequest.setPrice_typedes(title);
            searchRequest.setMin_price(startprice);
            searchRequest.setMax_price(endprice);
            resertChannelStatus();
            CURTURNPAGE = Constant.DEFAULTPAGE;
            mList.clear();
            getCarsData();
        }
    };

    /*点击排序的操作*/
    private void loadStateus() {
        showProgress();
        closePopwindow();
        setTabDefault();
        CURTURNPAGE = Constant.DEFAULTPAGE;
        mList.clear();
        getCarsData();
        setParentShowing(false);
    }

    /*点击渠道的重启状态*/
    private void resertChannelStatus(){
        closePopwindow();
        setTabDefault();
        setParentShowing(false);
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
            }else if( i == 2 ){
                homeTitle.setText(searchRequest.getBland_iddes());
            }else if( i == 3){
                homeTitle.setText(searchRequest.getPrice_typedes());
            }
        }
    }


    @Override
    protected void initView(PullRecyclerView recyclerView) {
        brandDao = GreenDaoUtils.getSingleTon().getmDaoSession().getBrandListResponseDao();
        mBrandList = brandDao.loadAll();

        EventBus.getDefault().register(this);
        Drawable bgdrable = getResources().getDrawable(R.drawable.select_main_item);
        int bgcolor = getActivity().getResources().getColor(R.color.mdtp_transparent_black);

        initTab();

        initBar();

        //排序
        PaixuPopwindow paixuPopwindow = new PaixuPopwindow();
        popupWindow_paixu = paixuPopwindow.showPopupWindow(getActivity(), bgdrable, bgcolor,paixuListener, searchRequest);
        //渠道
        channelPopwindow = new ChannelPopwindow();
        popupWindow_qudao = channelPopwindow.showPopupWindow(getActivity(), bgdrable, bgcolor, mChannelList, channelListener);
        //品牌
        brandPopwindow = new BrandPopwindow();
        popupWindow_brand = brandPopwindow.showPopupWindow(getActivity(), bgdrable, bgcolor, mPricelList);
        //价格
        priceBetweenPopwindow = new PriceBetweenPopwindow();
        popupWindow_price = priceBetweenPopwindow.showPopupWindow(getActivity(), bgdrable, bgcolor, mPricelList, priceBetweenListener);

        recyclerView.enableRefresh(true);
        recyclerView.enableLoadMore(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        carfromAdapter = new HomeCarAdapter();
        recyclerView.setAdapter(carfromAdapter);
        carfromAdapter.addAll(mList);

        mList.clear();
        mChannelList.clear();
        mPricelList.clear();
        /*加载数据*/
        getCarsData();
        /*获取渠道*/
        getCarChannel();
        /*获取价格*/
        getPriceBetween();
    }


    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTURNPAGE = Constant.DEFAULTPAGE;
        mList.clear();
        getCarsData();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        showProgress();
        CURTURNPAGE++;
        getCarsData();
    }

    @Override
    public void onResume() {
        super.onResume();
        Tool.hideInputMethod(getActivity(), mNavbar);
    }

    /**
     * 车源列表
     */
    private void getCarsData(){
        String searchTxt = mNavbar.getSearchText().toString();
        Subscriber subscriber = new PosetSubscriber<HomeCarGetResponse>().getSubscriber(callback_cardata);
        UserManager.getHomeCarFrom(searchTxt, searchRequest, CURTURNPAGE, subscriber);
    }

    /**
     * 获取渠道
     */
    private void getCarChannel() {
        Subscriber subscriber = new PosetSubscriber<List<CarChanelResponse>>().getSubscriber(callback_carchannel);
        UserManager.getCarChannel(subscriber);
    }

    /**
     * 获取价格
     */
    private void getPriceBetween() {
        Subscriber subscriber = new PosetSubscriber<List<CarChanelResponse>>().getSubscriber(callback_pricebetween);
        UserManager.getPriceBwtween(subscriber);
    }

    /**
     * 获取品牌
     */
    private void getBrad() {
        Subscriber subscriber = new PosetSubscriber<List<BrandListResponse>>().getSubscriber(callback_brand);
        UserManager.getBrandList(subscriber);
    }


    /*帅选回调*/
    ResponseResultListener callback_cardata = new ResponseResultListener<HomeCarGetResponse>() {
        @Override
        public void success(HomeCarGetResponse returnMsg) {
            if (returnMsg.getRows().size() == 10){
                finishLoad(true);
            }else{
                finishLoad(false);
            }
            closeProgress();
            mList.addAll(returnMsg.getRows());
            if (carfromAdapter != null){
                carfromAdapter.clear();
                carfromAdapter.addAll(mList);
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            finishLoad(false);
            closeProgress();
            LogUtil.E("fialed","fialed");
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

    /*价格区间回调*/
    ResponseResultListener callback_pricebetween = new ResponseResultListener<List<CarChanelResponse>>() {
        @Override
        public void success(List<CarChanelResponse> returnMsg) {
            LogUtil.E("success","success");
            mPricelList.addAll(returnMsg);
            priceBetweenPopwindow.setAdapter(mPricelList, searchRequest);
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };

    /*获取品牌*/
    ResponseResultListener callback_brand = new ResponseResultListener<List<BrandListResponse>>() {
        @Override
        public void success(List<BrandListResponse> returnMsg) {
            LogUtil.E("success","success");
            mBrandList.addAll(returnMsg);
            //写入数据库
            brandDao.insertInTx(mBrandList);
            brandPopwindow.setData();
//            priceBetweenPopwindow.setAdapter(mPricelList, searchRequest);
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };


}
