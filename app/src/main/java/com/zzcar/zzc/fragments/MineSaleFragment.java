package com.zzcar.zzc.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * 描述：我买到的
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/13 15:22
 **/
@EFragment(R.layout.fragment_mybuyorder)
public class MineSaleFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.tab)
    TabLayout mTab;
    @ViewById(R.id.pager)
    ViewPager mPager;

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void initView(){

        setup();
        mNavbar.setMiddleTitle("我卖出的");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);

        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
    }

    private void setup() {
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        ArrayList<TabInfo> infos = new ArrayList<>();



        MineOrderSaleFragment allfragment = MineOrderSaleFragment_.builder().build();
        Bundle bundle = new Bundle();
        bundle.putInt("toolbarName", 1);//全部
        allfragment.setArguments(bundle);
        MineOrderSaleFragment daizhifufragment = MineOrderSaleFragment_.builder().build();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("toolbarName", 2);//待支付
        daizhifufragment.setArguments(bundle1);
        MineOrderSaleFragment daimaijiazhifufragment = MineOrderSaleFragment_.builder().build();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("toolbarName", 3);//待买家支付
        daimaijiazhifufragment.setArguments(bundle2);
        MineOrderSaleFragment daiquerenfragment = MineOrderSaleFragment_.builder().build();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("toolbarName", 4);//待确定
        daiquerenfragment.setArguments(bundle3);
        MineOrderSaleFragment wanchengfragment = MineOrderSaleFragment_.builder().build();
        Bundle bundle4 = new Bundle();
        bundle4.putInt("toolbarName", 5);//完成
        wanchengfragment.setArguments(bundle4);

        infos.add(new TabInfo(allfragment,  R.string.tab_mybuy_all));
        infos.add(new TabInfo(daizhifufragment, R.string.tab_mybuy_daizhifu));
        infos.add(new TabInfo(daimaijiazhifufragment, R.string.tab_mysale_daimaifangzhifu));
        infos.add( new TabInfo(daiquerenfragment, R.string.tab_mybuy_daiqueren));
        infos.add(new TabInfo(wanchengfragment, R.string.tab_mybuy_daiwancheng));

        TabFragmentAdapter adapter = new TabFragmentAdapter(infos);
        mPager.setAdapter(adapter);


        for (int i = 0; i < adapter.getCount(); i++) {
            //i == 0设置为可点击
            mTab.addTab(mTab.newTab().setCustomView(createTabView(adapter.getTitleResId(i))), i == 0);
        }

        mTab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mPager));
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTab.getTabAt(position).select();
            }
        });
    }


    private View createTabView(int titelResId) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_textview, null);
        ((TextView) view.findViewById(R.id.homeTitle)).setText(titelResId);
        return view;
    }

    private class TabInfo {
        private Fragment fragment;
        private int titleResId;

        public TabInfo(Fragment fragment, int titleResId) {
            this.fragment = fragment;
            this.titleResId = titleResId;
        }
    }

    private class TabFragmentAdapter extends FragmentPagerAdapter {

        private ArrayList<TabInfo> tabInfos;

        public TabFragmentAdapter(ArrayList<TabInfo> tabInfos) {
            super(getChildFragmentManager());
            this.tabInfos = tabInfos;
        }


        public int getTitleResId(int index) {
            return tabInfos.get(index).titleResId;
        }

        @Override
        public int getCount() {
            return tabInfos.size();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = tabInfos.get(position).fragment;
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }
    }


    @Override
    public boolean onBackPressed() {
        finishFragment();
        return super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
