package com.zzcar.zzc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viewpagerindicator.IconPagerAdapter;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.fragments.ChatFragment;
import com.zzcar.zzc.fragments.MycarfromFragment;
import com.zzcar.zzc.fragments.MycarfromFragment_;
import com.zzcar.zzc.fragments.MycarfromSelectFragment;
import com.zzcar.zzc.fragments.MycarfromSelectFragment_;
import com.zzcar.zzc.utils.ImageLoader;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.NoScrollViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * 描述：我的车源提供给聊天使用
 * 作者：黄如辉  时间 2017/5/21.
 */

@EActivity(R.layout.activity_mycar)
public class MycarFromSelectActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.mTab)
    TabLayout mTab;
    @ViewById(R.id.mViewPager)
    NoScrollViewPager mPager;

    private String userid;

    private String[] tabStr = new String[]{"在售"};

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        userid = getIntent().getStringExtra("userid");
        initTab();
        mTab.setVisibility(View.GONE);
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("在售车源");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

    }



    private void initTab() {

        ArrayList<TabInfo> infos = new ArrayList<>();
        MycarfromSelectFragment fragment_zaishou = MycarfromSelectFragment_.builder().build();
        TabInfo homeTabInfo = new TabInfo(fragment_zaishou, "在售");
        infos.add(homeTabInfo);

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

    private class TabFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

        private ArrayList<TabInfo> tabInfos;

        public TabFragmentAdapter(ArrayList<TabInfo> tabInfos) {
            super(getSupportFragmentManager());
            this.tabInfos = tabInfos;
        }

        public String getTitleResId(int index) {
            return tabInfos.get(index).titleResId;
        }

        @Override
        public int getIconResId(int index) {
            return 0;
        }

        @Override
        public int getCount() {
            return tabInfos.size();
        }


        @Override
        public Fragment getItem(int position) {
            return tabInfos.get(position).fragment;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //取消销毁fragment,提高性能
            //super.destroyItem(container, position, object);
        }
    }

    private View createTabView(String titelResId) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_cheyuan_tab, null);
        ((TextView) view.findViewById(R.id.homeTitle)).setText(titelResId);
        view.findViewById(R.id.homeIcon).setVisibility(View.INVISIBLE);
        return view;
    }

    private class TabInfo {
        private Fragment fragment;
        private String titleResId;

        public TabInfo(Fragment fragment, String titleResId) {
            this.fragment = fragment;
            this.titleResId = titleResId;
        }
    }

}
