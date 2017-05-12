package com.zzcar.zzc.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viewpagerindicator.IconPagerAdapter;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.fragments.AuthenLoadPhotoFragment;
import com.zzcar.zzc.fragments.AuthenLoadPhotoFragment_;
import com.zzcar.zzc.fragments.AuthenUsermsgFragment;
import com.zzcar.zzc.fragments.AuthenUsermsgFragment_;
import com.zzcar.zzc.fragments.CarFromFragment_;
import com.zzcar.zzc.fragments.HomeFragment_;
import com.zzcar.zzc.fragments.MineFragment_;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.VerifiedResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.NoScrollViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.ArrayList;

import rx.Subscriber;

/**
 * 认证车行
 */

@EActivity(R.layout.activity_authentication)
public class AuthenticationActivity extends BaseActivity {


    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.tabLayout)
    TabLayout mTab;
    @ViewById(R.id.viewPager)
    NoScrollViewPager mPager;

    private VerifiedResponse verifiedResponse;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        showProgress();
        getVerified();
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("车行认证");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

    }


    private View createTabView(int titelResId) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_textview, null);
        ((TextView) view.findViewById(R.id.homeTitle)).setText(titelResId);
        return view;
    }

    private void setup() {
        mPager.setNoScroll(true);
        ArrayList<TabInfo> infos = new ArrayList<>();

        AuthenUsermsgFragment authenUsermsgFragment = AuthenUsermsgFragment_.builder().build();
        Bundle bundle = new Bundle();
        bundle.putSerializable("verifiedResponse", (Serializable) verifiedResponse);
        authenUsermsgFragment.setArguments(bundle);

        AuthenLoadPhotoFragment loadPhotoFragment = AuthenLoadPhotoFragment_.builder().build();
        Bundle bundle_photo = new Bundle();
        bundle_photo.putSerializable("verifiedResponse", (Serializable) verifiedResponse);
        loadPhotoFragment.setArguments(bundle_photo);


        TabInfo homeTabInfo = new TabInfo(authenUsermsgFragment, R.drawable.tab_home, R.string.authen_usermsg);
        infos.add(homeTabInfo);

        TabInfo preheatTabInfo = new TabInfo(loadPhotoFragment, R.drawable.tab_home, R.string.authen_photo);
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


    private class TabInfo {
        private Fragment fragment;
        private int iconResId;
        private int titleResId;

        public TabInfo(Fragment fragment, int resId, int titleResId) {
            this.fragment = fragment;
            this.iconResId = resId;
            this.titleResId = titleResId;
        }
    }


    private class TabFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

        private ArrayList<TabInfo> tabInfos;

        public TabFragmentAdapter(ArrayList<TabInfo> tabInfos) {
            super(getSupportFragmentManager());
            this.tabInfos = tabInfos;
        }

        @Override
        public int getIconResId(int index) {
            return tabInfos.get(index).iconResId;
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
            return tabInfos.get(position).fragment;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //取消销毁fragment
            //super.destroyItem(container, position, object);
        }
    }

    /*获取认证*/
    private void getVerified() {
        Subscriber subscriber =  new PosetSubscriber<VerifiedResponse>().getSubscriber(callback_verified);
        UserManager.getVerified(subscriber);
    }

    ResponseResultListener callback_verified = new ResponseResultListener<VerifiedResponse>() {
        @Override
        public void success(VerifiedResponse returnMsg) {
            LogUtil.E("success","success");
            verifiedResponse = returnMsg;
            setup();
            closeProgress();
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
            closeProgress();
        }
    };
}
