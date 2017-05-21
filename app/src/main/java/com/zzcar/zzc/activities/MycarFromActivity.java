package com.zzcar.zzc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.IconPagerAdapter;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.fragments.CarBrandFragment;
import com.zzcar.zzc.fragments.CarFromFragment_;
import com.zzcar.zzc.fragments.HomeFragment_;
import com.zzcar.zzc.fragments.MineFragment_;
import com.zzcar.zzc.fragments.MycarfromFragment;
import com.zzcar.zzc.fragments.MycarfromFragment_;
import com.zzcar.zzc.views.widget.NavBar;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.NoScrollViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * 描述：我的车源
 * 作者：黄如辉  时间 2017/5/21.
 */

@EActivity(R.layout.activity_mycar)
public class MycarFromActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.mTab)
    TabLayout mTab;
    @ViewById(R.id.mViewPager)
    NoScrollViewPager mPager;

    private String[] tabStr = new String[]{"在售", "已售", "未上架"};

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        initTab();
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("我的车源");
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
                Intent intent = new Intent(MycarFromActivity.this, PushCarActivity_.class);
                startActivity(intent);
            }
        });
    }

    private void initTab() {

        ArrayList<TabInfo> infos = new ArrayList<>();

        MycarfromFragment fragment_zaishou = MycarfromFragment_.builder().build();
        Bundle bundle = new Bundle();
        bundle.putString("Tag", "0");
        fragment_zaishou.setArguments(bundle);

        MycarfromFragment fragment_yishou = MycarfromFragment_.builder().build();
        Bundle bundle1 = new Bundle();
        bundle1.putString("Tag", "1");
        fragment_yishou.setArguments(bundle1);

        MycarfromFragment fragment_weishangjai = MycarfromFragment_.builder().build();
        Bundle bundle2 = new Bundle();
        bundle2.putString("Tag", "2");
        fragment_weishangjai.setArguments(bundle2);

        TabInfo homeTabInfo = new TabInfo(fragment_zaishou, "在售");
        infos.add(homeTabInfo);

        TabInfo preheatTabInfo = new TabInfo(fragment_yishou, "已售");
        infos.add(preheatTabInfo);

        TabInfo shopingTabInfo = new TabInfo(fragment_weishangjai, "未上架");
        infos.add(shopingTabInfo);

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
