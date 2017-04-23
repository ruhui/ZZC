package com.zzcar.zzc.fragments;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.SearchActivity;
import com.zzcar.zzc.activities.SearchActivity_;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.AddressModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.networks.responses.UserMsgResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBarSearch;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
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
        initTab();

        initBar();

        getCarPush();
        popupWindow_paixu = showpaihaoPopupWindow();
        popupWindow_qudao = showpaihaoPopupWindow();
        popupWindow_brand = showpaihaoPopupWindow();
        popupWindow_price = showpaihaoPopupWindow();

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


    private PopupWindow showpaihaoPopupWindow() {
        PopupWindow popupWindow;
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_paixu, null);
        RelativeLayout relaPaixu = (RelativeLayout) contentView.findViewById(R.id.relaPaixu);
        RelativeLayout relaNewfabu = (RelativeLayout) contentView.findViewById(R.id.relaNewfabu);
        RelativeLayout relaPriceUP = (RelativeLayout) contentView.findViewById(R.id.relaPriceUP);
        RelativeLayout relaPriceDown = (RelativeLayout) contentView.findViewById(R.id.relaPriceDown);
        RelativeLayout relaDriverTime = (RelativeLayout) contentView.findViewById(R.id.relaDriverTime);
        RelativeLayout relaLicheng = (RelativeLayout) contentView.findViewById(R.id.relaLicheng);
        TextView relaDown = (TextView) contentView.findViewById(R.id.relaDown);


        relaPaixu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        relaDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow_paixu.dismiss();
                popupWindow_qudao.dismiss();
                popupWindow_brand.dismiss();
                popupWindow_price.dismiss();
            }
        });
//
//        TextView txtMessage = (TextView) contentView.findViewById(R.id.txtMessage);
//        TextView txtHome = (TextView) contentView.findViewById(R.id.txtHome);
//        TextView txtShopping = (TextView) contentView.findViewById(R.id.txtShopping);
//        TextView txtFankui = (TextView) contentView.findViewById(R.id.txtFankui);
//        TextView txtMineApp = (TextView) contentView.findViewById(R.id.txtMineApp);
//
//        //消息
//        txtMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        //首页
//        txtHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        //购物车
//        txtShopping.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        //我要反馈
//        txtFankui.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        //我的聚疯
//        txtMineApp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(false);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.select_main_item));
        ColorDrawable dw = new ColorDrawable(getActivity().getResources().getColor(R.color.mdtp_transparent_black));
        popupWindow.setBackgroundDrawable(dw);
            // 设置好参数之后再show
//            popupWindow_paixu.showAsDropDown(view);
        return popupWindow;
    }


}
