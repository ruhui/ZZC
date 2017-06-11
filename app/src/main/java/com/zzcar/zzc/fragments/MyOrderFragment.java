package com.zzcar.zzc.fragments;

import android.view.View;

import com.zzcar.zzc.R;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.views.widget.ItemViewThird;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/6/10.
 * 作者：黄如辉
 * 功能描述：我的订单
 */
@EFragment(R.layout.fragment_myorder)
public class MyOrderFragment extends BaseFragment{

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.mybuyItem)
    ItemViewThird mybuyItem;
    @ViewById(R.id.mysaleItem)
    ItemViewThird mysaleItem;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setTitle("我的订单");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        mybuyItem.setImgResouse(R.drawable.nav_icon_maichu);
        mybuyItem.setNameText("我买到的");

        mysaleItem.setImgResouse(R.drawable.nav_icon_maidao);
        mysaleItem.setNameText("我卖出的");
    }
}
