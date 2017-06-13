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
import com.zzcar.zzc.interfaces.RefreshListener;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/13 15:22
 **/
@EFragment(R.layout.fragment_mybuyorder)
public class MineBuyFragment extends BaseFragment {

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
        mNavbar.setMiddleTitle("我买到的");
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
        mTab.setTabMode(TabLayout.MODE_FIXED);
        ArrayList<TabInfo> infos = new ArrayList<>();

        Bundle bundle = new Bundle();
        bundle.putBoolean("noNarbar", true);

//        UnpaidFragment unpaidFragment = UnpaidFragment_.builder().build();
//        WaitingDeliveryFragment waitingDeliveryFragment = WaitingDeliveryFragment_.builder().build();
//        ReceivingFragment receivingFragment =  ReceivingFragment_.builder().build();
//
//        unpaidFragment.setArguments(bundle);
//        waitingDeliveryFragment.setArguments(bundle);
//        receivingFragment.setArguments(bundle);
//
//        TabInfo tuijianTabInfo = new TabInfo(MyOrderAllFragment_.builder().build(), R.string.all);
//        infos.add(tuijianTabInfo);
//
//        TabInfo sportsTabInfo = new TabInfo(unpaidFragment,  R.string.tab_ming_pending_payment);
//        infos.add(sportsTabInfo);
//
//        TabInfo clothesTabInfo = new TabInfo(waitingDeliveryFragment, R.string.tab_ming_to_be_delivered);
//        infos.add(clothesTabInfo);
//
//        TabInfo shoesTabInfo = new TabInfo(receivingFragment, R.string.tab_ming_to_be_received);
//        infos.add(shoesTabInfo);

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
