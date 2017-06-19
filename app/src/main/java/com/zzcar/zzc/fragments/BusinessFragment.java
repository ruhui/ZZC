package com.zzcar.zzc.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.IconPagerAdapter;
import com.zzcar.zzc.R;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.views.widget.NavbarSwitch;
import com.zzcar.zzc.views.widget.NavbarSwitch2;
import com.zzcar.zzc.views.widget.NoScrollViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * 描述：商机
 * 作者：黄如辉  时间 2017/5/24.
 */
@EFragment(R.layout.fragment_home_demend)
public class BusinessFragment extends BaseFragment{

    @ViewById(R.id.navbarSwitch)
    NavbarSwitch2 navbarSwitch;
    @ViewById(R.id.mViewPager)
    NoScrollViewPager mPager;

    private ArrayList<Fragment> listFragment = new ArrayList<>();
    private int curturnPage = 0;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initVie(){
        navbarSwitch.setLeftText("求购");
        navbarSwitch.setRightText("询价");

        navbarSwitch.setSwitchListener(new NavbarSwitch2.SwitchListener() {
            @Override
            public void messageListener() {
                mPager.setCurrentItem(0);
                curturnPage = 0;
            }

            @Override
            public void fridendListener() {
                mPager.setCurrentItem(1);
                curturnPage = 1;
            }
        });

        navbarSwitch.setSearch(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curturnPage == 0){
                    //求购
                }else{
                    //询价
                }
            }
        });

        BusinessDemendFragment demendFragment = BusinessDemendFragment_.builder().build();
        BusinessDemendFragment supplyFragment = BusinessDemendFragment_.builder().build();
        listFragment.add(demendFragment);
        listFragment.add(supplyFragment);

        TabFragmentAdapter adapter = new TabFragmentAdapter(listFragment);
        mPager.setAdapter(adapter);

        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        navbarSwitch.setMsgListener();
                        break;
                    case 1:
                        navbarSwitch.setFridListener();
                        break;
                }
            }
        });
    }


    private class TabFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

        private ArrayList<Fragment> tabInfos;

        public TabFragmentAdapter(ArrayList<Fragment> tabInfos) {
            super(getChildFragmentManager());
            this.tabInfos = tabInfos;
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
            return tabInfos.get(position);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //取消销毁fragment,提高性能
            //super.destroyItem(container, position, object);
        }
    }
}
