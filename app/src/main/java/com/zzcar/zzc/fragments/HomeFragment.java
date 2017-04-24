package com.zzcar.zzc.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.MainActivity;
import com.zzcar.zzc.activities.SearchActivity_;
import com.zzcar.zzc.adapters.HomeCarAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.FragmentClosePop;
import com.zzcar.zzc.interfaces.PopcloseListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.HomeCarGet;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.HomeCarGetResponse;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.PaixuPopwindow;
import com.zzcar.zzc.views.widget.NavBarSearch;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.AfterViews;
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
public class HomeFragment extends BasePullRecyclerFragment {

    private int[] tabStr = new int[]{R.string.paixu, R.string.qudao, R.string.brand, R.string.price};
    private HomeCarAdapter carfromAdapter;

    /*排序显示的哪个*/
    private String popCode = "1";
    /*当前页码*/
    private int CURTURNPAGE = 1;

    private PopupWindow popupWindow_paixu;
    private PopupWindow popupWindow_qudao;
    private PopupWindow popupWindow_brand;
    private PopupWindow popupWindow_price;

    List<HomeCarGet> mList = new ArrayList<>();

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
            }
        });
        mNavbar.setSearchImgListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity_.class);
                startActivityForResult(intent, 10200);
            }
        });
    }

    private void getCarPush() {
//        Subscriber subscriber = new PosetSubscriber<List<HomeCarPushResponse>>().getSubscriber(callback_carpush);
//        UserManager.getHomeCarpush(subscriber);
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

                }else if(position == 2){
                    popupWindow_paixu.dismiss();
                    popupWindow_qudao.dismiss();
                    popupWindow_brand.showAsDropDown(view);
                    popupWindow_price.dismiss();
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
                    }
                    popupWindow_brand.dismiss();
                    popupWindow_price.dismiss();
                }else if(position == 2){
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


    ResponseResultListener callback_carpush = new ResponseResultListener<List<HomeCarPushResponse>>() {
        @Override
        public void success(List<HomeCarPushResponse> returnMsg) {
            LogUtil.E("success", "success");

        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
        }
    };


    public void setParentShowing(boolean ispopshowing){
        ((MainActivity) getActivity()).popisShowing = ispopshowing;
    }

    @Subscribe
    public void setClosePop(FragmentClosePop closePop){
        if (closePop.closePop){
            closePopwindow();
            setParentShowing(false);
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
            showProgress();
            popCode = value;
            closePopwindow();
            setTabDefault();
            getCarsData();
        }

        @Override
        public void newPush(String title, String value) {
            popCode = value;
            closePopwindow();
            setTabDefault();
            showProgress();
            getCarsData();
        }

        @Override
        public void priceUp(String title, String value) {
            popCode = value;
            closePopwindow();
            setTabDefault();
            showProgress();
            getCarsData();
        }

        @Override
        public void priceDown(String title, String value) {
            popCode = value;
            closePopwindow();
            setTabDefault();
            showProgress();
            getCarsData();
        }

        @Override
        public void driverAge(String title, String value) {
            popCode = value;
            closePopwindow();
            setTabDefault();
            showProgress();
            getCarsData();
        }

        @Override
        public void mileage(String title, String value) {
            popCode = value;
            closePopwindow();
            setTabDefault();
            showProgress();
            getCarsData();
        }

        @Override
        public void downdismis() {
            closePopwindow();
            setTabDefault();
        }
    };

    public void setTabDefault(){
        for (int i=0; i< mTab.getTabCount(); i++){
            ImageView imgView = (ImageView) mTab.getTabAt(i).getCustomView().findViewById(R.id.homeIcon);
            TextView homeTitle = (TextView) mTab.getTabAt(i).getCustomView().findViewById(R.id.homeTitle);
            imgView.setImageResource(R.drawable.nav_icon_down_default);
            homeTitle.setTextColor(getResources().getColor(R.color.color_333333));
        }
    }


    @Override
    protected void initView(PullRecyclerView recyclerView) {
        EventBus.getDefault().register(this);
        Drawable bgdrable = getResources().getDrawable(R.drawable.select_main_item);
        int bgcolor = getActivity().getResources().getColor(R.color.mdtp_transparent_black);

        initTab();

        initBar();

        getCarPush();

        recyclerView.enableLoadMore(true);
        recyclerView.enableRefresh(true);

        //排序
        PaixuPopwindow paixuPopwindow = new PaixuPopwindow();
        popupWindow_paixu = paixuPopwindow.showPopupWindow(getActivity(), bgdrable, bgcolor,paixuListener, popCode);
        //渠道
        PaixuPopwindow paixuPopwindow1 = new PaixuPopwindow();
        popupWindow_qudao = paixuPopwindow1.showPopupWindow(getActivity(), bgdrable, bgcolor, paixuListener, popCode);
        //品牌
        PaixuPopwindow paixuPopwindow2 = new PaixuPopwindow();
        popupWindow_brand = paixuPopwindow2.showPopupWindow(getActivity(), bgdrable, bgcolor,paixuListener, popCode);
        //价格
        PaixuPopwindow paixuPopwindow3 = new PaixuPopwindow();
        popupWindow_price = paixuPopwindow3.showPopupWindow(getActivity(), bgdrable, bgcolor,paixuListener, popCode);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        carfromAdapter = new HomeCarAdapter();
        recyclerView.setAdapter(carfromAdapter);
        carfromAdapter.addAll(mList);

        /*加载数据*/
        getCarsData();
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        showProgress();
        CURTURNPAGE = 1;
        mList.clear();
        getCarsData();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTURNPAGE++;
        getCarsData();
    }


    /**
     * 车源列表
      */
    private void getCarsData(){
        String searchTxt = mNavbar.getSearchText().toString();
        Subscriber subscriber = new PosetSubscriber<HomeCarGetResponse>().getSubscriber(callback_cardata);
        UserManager.getHomeCarFrom(searchTxt, popCode, "", CURTURNPAGE, subscriber);
    }

    ResponseResultListener callback_cardata = new ResponseResultListener<HomeCarGetResponse>() {
        @Override
        public void success(HomeCarGetResponse returnMsg) {
            if (returnMsg.getRows().size() == 10){
                finishLoad(true);
            }else{
                finishLoad(false);
            }
            closeProgress();
            LogUtil.E("success","success");
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
}
