package com.zzcar.zzc.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zzcar.greendao.BrandListResponseDao;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.fragments.CarBrandCopyFragment;
import com.zzcar.zzc.fragments.CarBrandCopyFragment_;
import com.zzcar.zzc.fragments.CarBrandFragment;
import com.zzcar.zzc.fragments.CarBrandFragment_;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.requests.SearchRequest;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.utils.GreenDaoUtils;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

@EActivity(R.layout.activity_brand_car)
public class BrandCarActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    private CarBrandCopyFragment carBrandFragment;
    /*品牌列表*/
    private List<BrandListResponse> mBrandList = new ArrayList<>();
    /*是否选择车型*/
    private boolean notspec = false, selectspece = false;


    @AfterViews
    void initView(){
        //是否一定要选择车型
        selectspece = getIntent().getBooleanExtra("selectspec", false);
        notspec = getIntent().getBooleanExtra("notspec", false);
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
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
                    carBrandFragment.setSelectspece(selectspece);
                }
            }else{
                showCarBrandfragment();
            }
        }else{
            getBrad();
        }

    }


    void showCarBrandfragment(){
        carBrandFragment = CarBrandCopyFragment_.builder().build();
        carBrandFragment.setSelectspece(selectspece);
        if (carBrandFragment==null || !carBrandFragment.isAdded()){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.brandframe, carBrandFragment, CarBrandFragment.class.getName());
            transaction.commit();
        }
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    /*是否不显示车型, true作为不显示*/
    public boolean isNotspec(){
        return  notspec;
    }


    public void setBrandandType(String branid, String seriesid, String typeid,String branddes, String price){
        Intent intent = new Intent();
        intent.putExtra("brandid", branid);
        intent.putExtra("seriesid", seriesid);
        intent.putExtra("typeid", typeid);
        intent.putExtra("branddes",branddes);
        intent.putExtra("price",price);
        setResult(10105, intent);
        finish();
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
            carBrandFragment.setSelectspece(selectspece);
            carBrandFragment.setData();
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };
}
