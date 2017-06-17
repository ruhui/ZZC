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
import com.zzcar.zzc.fragments.MydemendFragment;
import com.zzcar.zzc.fragments.MydemendFragment_;
import com.zzcar.zzc.fragments.MysupplyFragment;
import com.zzcar.zzc.fragments.MysupplyFragment_;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.NoScrollViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * 创建时间： 2017/6/15.
 * 作者：黄如辉
 * 功能描述：我的询价
 */

@EActivity(R.layout.activity_mycar)
public class MydemendActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.mTab)
    TabLayout mTab;
    @ViewById(R.id.mViewPager)
    NoScrollViewPager mPager;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        initTab();
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("我的求购");
        mNavbar.setRightTxt("发布");
        mNavbar.setRightTxtColor(R.color.app_red);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                Intent intent = new Intent(MydemendActivity.this, PushDemendActivity_.class);
                startActivity(intent);
            }
        });
    }

    private void initTab() {

        ArrayList<MydemendActivity.TabInfo> infos = new ArrayList<>();

        MydemendFragment fragment_zaishou = MydemendFragment_.builder().build();
        Bundle bundle = new Bundle();
        bundle.putString("Tag", "2");
        fragment_zaishou.setArguments(bundle);

        MydemendFragment fragment_yishou = MydemendFragment_.builder().build();
        Bundle bundle1 = new Bundle();
        bundle1.putString("Tag", "5");
        fragment_yishou.setArguments(bundle1);


        MydemendActivity.TabInfo homeTabInfo = new MydemendActivity.TabInfo(fragment_zaishou, "进行中");
        infos.add(homeTabInfo);

        MydemendActivity.TabInfo preheatTabInfo = new MydemendActivity.TabInfo(fragment_yishou, "已到期");
        infos.add(preheatTabInfo);

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

        private ArrayList<MydemendActivity.TabInfo> tabInfos;

        public TabFragmentAdapter(ArrayList<MydemendActivity.TabInfo> tabInfos) {
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
