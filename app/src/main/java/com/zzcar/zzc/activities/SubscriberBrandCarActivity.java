package com.zzcar.zzc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.zzcar.greendao.BrandListResponseDao;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.fragments.CarBrandCopyFragment;
import com.zzcar.zzc.fragments.CarBrandCopyFragment_;
import com.zzcar.zzc.fragments.CarBrandFragment;
import com.zzcar.zzc.fragments.CarBrandSubsribeFragment;
import com.zzcar.zzc.fragments.CarBrandSubsribeFragment_;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.interfaces.SubscribBland;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.BlandModle;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.utils.GreenDaoUtils;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Subscriber;

@EActivity(R.layout.activity_brand_car)
public class SubscriberBrandCarActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    private CarBrandSubsribeFragment carBrandFragment;
    /*品牌列表*/
    private List<BrandListResponse> mBrandList = new ArrayList<>();
    /*缓存记录品牌车系*/
    private HashMap<Integer, BlandModle> hashBland ;


    @AfterViews
    void initView(){
        hashBland = (HashMap<Integer, BlandModle>) getIntent().getSerializableExtra("hashBland");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setRightTxt("确定");
        mNavbar.setRightTxtColor(R.color.app_red);
        mNavbar.setMiddleTitle("品牌");
        mNavbar.setRightTxtColor(R.color.color_959595);

        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                Intent intent = new Intent();
                intent.putExtra("hashBland", hashBland);
                setResult(10103, intent);
                finish();
            }
        });

        BrandListResponseDao brandDao = GreenDaoUtils.getSingleTon().getmDaoSession().getBrandListResponseDao();
        List<BrandListResponse> listbrand = brandDao.loadAll();
        if (listbrand.size() > 0){
            if (carBrandFragment != null){
                if (!carBrandFragment.isAdded()){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.brandframe, carBrandFragment, CarBrandFragment.class.getName());
                    transaction.commit();
                }else{
                    carBrandFragment.setData();
                }
            }else{
                showCarBrandfragment();
            }
        }else{
            getBrad();
        }

    }


    void showCarBrandfragment(){
        carBrandFragment = CarBrandSubsribeFragment_.builder().build();
        if (carBrandFragment==null || !carBrandFragment.isAdded()){
            Bundle bundle = new Bundle();
            bundle.putSerializable("hashBland", hashBland);
            carBrandFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.brandframe, carBrandFragment, CarBrandFragment.class.getName());
            transaction.commit();
        }
    }

    @Override
    public void onNetChange(int netMobile) {

    }


    public void setBrandandType(String branid, String seriesid, String typeid,String branddes){
        Intent intent = new Intent();
        intent.putExtra("brandid", branid);
        intent.putExtra("seriesid", seriesid);
        intent.putExtra("typeid", typeid);
        intent.putExtra("branddes",branddes);
        setResult(10105, intent);
        finish();
    }


    /*获取选择的品牌车系*/
    @Subscribe
    public void getBlandAndSeries(SubscribBland subscribBland){
        this.hashBland = subscribBland.hashMap;
    }

    /**
     * 获取品牌
     */
    private void getBrad() {
        Subscriber subscriber = new PosetSubscriber<List<BrandListResponse>>().getSubscriber(callback_brand);
        UserManager.getBrandList(subscriber);
    }


    /*获取品牌*/
    ResponseResultListener callback_brand = new ResponseResultListener<List<BrandListResponse>>() {
        @Override
        public void success(List<BrandListResponse> returnMsg) {
            LogUtil.E("success","success");
            mBrandList.addAll(returnMsg);
            //写入数据库
            BrandListResponseDao brandDao = GreenDaoUtils.getSingleTon().getmDaoSession().getBrandListResponseDao();
            brandDao.insertInTx(mBrandList);
            carBrandFragment.setData();
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };
}
