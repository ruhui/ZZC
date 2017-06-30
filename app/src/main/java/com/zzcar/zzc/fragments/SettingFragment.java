package com.zzcar.zzc.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.zzcar.greendao.BrandListResponseDao;
import com.zzcar.greendao.CarSeriesResponseDao;
import com.zzcar.greendao.CarfactoryDtoDao;
import com.zzcar.greendao.CityModelCountryDao;
import com.zzcar.greendao.CityModelDao;
import com.zzcar.greendao.CountyModeDao;
import com.zzcar.greendao.ProvenceModelCountryDao;
import com.zzcar.greendao.ProvenceModelDao;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.AboutZzcarActivity_;
import com.zzcar.zzc.activities.LoginAcitivty_;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.manager.DataCleanManager;
import com.zzcar.zzc.utils.GreenDaoUtils;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.SecurePreferences;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/21.
 */
@EFragment(R.layout.fragment_setting)
public class SettingFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.relaYuangong)
    RelativeLayout relaYuangong;
    @ViewById(R.id.relaPhone)
    RelativeLayout relaPhone;
    @ViewById(R.id.aboutApp)
    RelativeLayout aboutApp;
    @ViewById(R.id.cancelMemory)
    RelativeLayout cancelMemory;
    @ViewById(R.id.textView131)
    TextView exitApp;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("设置");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        /*关于众众车*/
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutZzcarActivity_.class);
                startActivity(intent);
            }
        });

        /*员工管理*/
        relaYuangong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(getActivity(), MyEmployeeFragment_.builder().build());
            }
        });
    }

    @Click(R.id.textView131)
    void exitApp(){
        SecurePreferences.getInstance().edit().putString("Authorization", "").commit();
        SecurePreferences.getInstance().edit().putString("EXPIRESDATE","").commit();
        /*退出环信*/
        EMClient.getInstance().logout(true);
        Intent intent = new Intent(getActivity(), LoginAcitivty_.class);
        startActivity(intent);
        getActivity().finish();
    }

    /*清除缓存*/
    @Click(R.id.cancelMemory)
    void cancelMemory(){
        showProgress();
        new CleanMemoryAsynTask().execute("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.E("onDestroy", "onDestroy");
    }

    class CleanMemoryAsynTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            DataCleanManager.cleanInternalCache(getActivity());
            DataCleanManager.cleanDatabases(getActivity());

//            BrandListResponseDao brandDao = GreenDaoUtils.getSingleTon().getmDaoSession().getBrandListResponseDao();
//            brandDao.dropTable(brandDao.getDatabase(), true);

//            BrandListResponseDao brandListResponseDao = GreenDaoUtils.getSingleTon().getmDaoSession().getBrandListResponseDao();
//            if (brandListResponseDao.count() > 0){
//                brandListResponseDao.deleteAll();
//            }
//            CarfactoryDtoDao carfactoryDtoDao = GreenDaoUtils.getSingleTon().getmDaoSession().getCarfactoryDtoDao();
//            if (carfactoryDtoDao.count() > 0){
//                carfactoryDtoDao.deleteAll();
//            }
//            CarSeriesResponseDao carSeriesResponseDao = GreenDaoUtils.getSingleTon().getmDaoSession().getCarSeriesResponseDao();
//            if (carSeriesResponseDao.count() > 0){
//                carSeriesResponseDao.deleteAll();
//            }
//            CityModelCountryDao cityModelCountryDao = GreenDaoUtils.getSingleTon().getmDaoSession().getCityModelCountryDao();
//            if (cityModelCountryDao.count() > 0){
//                cityModelCountryDao.deleteAll();
//            }
//            CityModelDao cityModelDao = GreenDaoUtils.getSingleTon().getmDaoSession().getCityModelDao();
//            if (cityModelDao.count() > 0){
//                cityModelDao.deleteAll();
//            }
//            CountyModeDao countyModeDao = GreenDaoUtils.getSingleTon().getmDaoSession().getCountyModeDao();
//            if (countyModeDao.count() > 0){
//                countyModeDao.deleteAll();
//            }
//            ProvenceModelCountryDao provenceModelCountryDao =  GreenDaoUtils.getSingleTon().getmDaoSession().getProvenceModelCountryDao();
//            if (provenceModelCountryDao.count() > 0){
//                provenceModelCountryDao.deleteAll();
//            }
//            ProvenceModelDao provenceModelDao = GreenDaoUtils.getSingleTon().getmDaoSession().getProvenceModelDao();
//            if (provenceModelDao.count() > 0){
//                provenceModelDao.deleteAll();
//            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            closeProgress();
            ToastUtil.showToast("清理完成");
        }
    }
}
