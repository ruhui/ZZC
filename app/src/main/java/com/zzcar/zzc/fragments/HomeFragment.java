package com.zzcar.zzc.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.SearchActivity_;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.MyPopwindow;
import com.zzcar.zzc.views.widget.NavBarSearch;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import rx.Subscriber;

/**
 * 描述：主页
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/18 15:30
 **/

@EFragment(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {

    private int[] tabStr = new int[]{R.string.paixu, R.string.qudao, R.string.brand, R.string.price};

    private PopupWindow popupWindow_paixu;
    private PopupWindow popupWindow_qudao;
    private PopupWindow popupWindow_brand;
    private PopupWindow popupWindow_price;



    @ViewById(R.id.line2)
    View view;
    @ViewById(R.id.mTab)
    TabLayout mTab;
    @ViewById(R.id.mNavbar)
    NavBarSearch mNavbar;

    @AfterViews
    void initView(){

        Drawable bgdrable = getResources().getDrawable(R.drawable.select_main_item);
        int bgcolor = getActivity().getResources().getColor(R.color.mdtp_transparent_black);

        initTab();

        initBar();

        getCarPush();


        MyPopwindow popwindow = new MyPopwindow();
        popupWindow_paixu = popwindow.showPopupWindow(getActivity(), bgdrable, bgcolor);
        popupWindow_qudao = popwindow.showPopupWindow(getActivity(), bgdrable, bgcolor);
        popupWindow_brand = popwindow.showPopupWindow(getActivity(), bgdrable, bgcolor);
        popupWindow_price = popwindow.showPopupWindow(getActivity(), bgdrable, bgcolor);

    }

    private void initBar() {
        //搜索
        mNavbar.setSearchTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity_.class);
                startActivityForResult(intent, 10200);
            }
        });
    }

    private void getCarPush() {
        Subscriber subscriber = new PosetSubscriber<List<HomeCarPushResponse>>().getSubscriber(callback_carpush);
        UserManager.getHomeCarpush(subscriber);
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
                    }else{
                        popupWindow_paixu.showAsDropDown(view);
                        imgView.setImageResource(R.drawable.nav_icon_up_selected);
                        homeTitle.setTextColor(getResources().getColor(R.color.app_red));
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
                    }else{
                        popupWindow_qudao.showAsDropDown(view);
                        imgView.setImageResource(R.drawable.nav_icon_up_selected);
                        homeTitle.setTextColor(getResources().getColor(R.color.app_red));
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
                    }else{
                        popupWindow_brand.showAsDropDown(view);
                        imgView.setImageResource(R.drawable.nav_icon_up_selected);
                        homeTitle.setTextColor(getResources().getColor(R.color.app_red));
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
                    }else{
                        popupWindow_price.showAsDropDown(view);
                        imgView.setImageResource(R.drawable.nav_icon_up_selected);
                        homeTitle.setTextColor(getResources().getColor(R.color.app_red));
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

}
