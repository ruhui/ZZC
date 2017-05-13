package com.zzcar.zzc.fragments;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zzcar.greendao.CityModelCountryDao;
import com.zzcar.greendao.CountyModeDao;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.SelectCountryActivity;
import com.zzcar.zzc.adapters.CityCountrySelectAdapter;
import com.zzcar.zzc.adapters.CountrySelectAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.CityChild;
import com.zzcar.zzc.models.CityModel;
import com.zzcar.zzc.models.CityModelCountry;
import com.zzcar.zzc.models.CountyMode;
import com.zzcar.zzc.models.ProvenceModelCountry;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.CityResponse;
import com.zzcar.zzc.utils.GreenDaoUtils;
import com.zzcar.zzc.utils.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：地区选择
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/2 15:16
 **/

@EFragment(R.layout.fragment_cityselect)
public class CountrySelectFragment extends BaseFragment {

    @ViewById(R.id.textView6)
    TextView textView6;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private List<CountyMode> cityList = new ArrayList<>();
    private CountrySelectAdapter citySelectAdapter;

    private int provinceId;
    private int cityId;
    private CountyModeDao countyDao;

    @AfterViews
    void initView(){
        if (cityList.size() == 0 ){
            getCityData();
        }
        textView6.setText("不限城市");
        citySelectAdapter = new CountrySelectAdapter(citySelectListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(citySelectAdapter);
        citySelectAdapter.clear();
        citySelectAdapter.addAll(cityList);

        textView6.setVisibility(View.GONE);
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    /*获取城市*/
    public void setData(int provinceid, int parentId) {
        this.provinceId = provinceid;
        this.cityId = parentId;

        countyDao = GreenDaoUtils.getSingleTon().getmDaoSession().getCountyModeDao();
        List<CountyMode> userList = countyDao.queryBuilder()
                .where(CountyModeDao.Properties.Parentid.eq(parentId))
                .build().list();
        cityList.clear();
        cityList.addAll(userList);
        if (cityList.size() != 0){
            if (getView() != null){
                citySelectAdapter.clear();
                citySelectAdapter.addAll(cityList);
            }
        }else{
            getCityData();
        }
    }

    public void closefragment() {
        getFragmentManager().popBackStackImmediate("CitySelectFragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /*城市点选回调*/
    CountrySelectAdapter.CitySelectListener citySelectListener = new CountrySelectAdapter.CitySelectListener() {
        @Override
        public void setlectCity(int cityId, int contryId, String region_name) {
            ((SelectCountryActivity)getActivity()).getSelectCity(provinceId, cityId, contryId, region_name);
        }
    };


    /*获取城市*/
    private void getCityData() {
        if (getView() != null){
            showProgress();
        }
        Subscriber subscriber = new PosetSubscriber<List<CityResponse>>().getSubscriber(callback_city);
        UserManager.getRegions(subscriber);
    }

    /*获取城市*/
    ResponseResultListener callback_city = new ResponseResultListener<List<CityResponse>>() {
        @Override
        public void success(List<CityResponse> returnMsg) {
            LogUtil.E("success","success");
            new LoadAsynTask(returnMsg).execute("");
        }

        @Override
        public void fialed(String resCode, String message) {
            if (getView() != null){
                closeProgress();
            }
            LogUtil.E("fialed","fialed");
        }
    };


    private class LoadAsynTask extends AsyncTask<String, Void, List<CountyMode>> {

        private List<CityResponse> returnMsg;
        public LoadAsynTask(List<CityResponse> returnMsg) {
            this.returnMsg = returnMsg;
        }

        @Override
        protected List<CountyMode>  doInBackground(String... strings) {
            List<CountyMode> listCounty = new ArrayList<>();
            for (CityResponse cityResponse : returnMsg){
                if (cityResponse.getId() == provinceId){
                    List<CityChild> listcity = cityResponse.getChildren();
                    for (CityChild cityChild : listcity){
                        if (cityChild.getId() == cityId){
                            listCounty = cityChild.getChildren();
                            for (CountyMode countyMode : listCounty){
                                CountyMode countyMode1 = new CountyMode();
                                countyMode1.setFirst_letter(countyMode.getFirst_letter());
                                countyMode1.setFull_name(countyMode.getFull_name());
                                countyMode1.setId(countyMode.getId());
                                countyMode1.setName(countyMode.getName());
                                countyMode1.setRegion_name(countyMode.getRegion_name());
                                countyMode1.setParentid(cityId);
                                countyDao.insert(countyMode1);
                            }
                            return listCounty;
                        }
                    }
                }
            }
            return listCounty;
        }

        @Override
        protected void onPostExecute( List<CountyMode> listCounty) {
            super.onPostExecute(listCounty);
            if (getView() != null){
                closeProgress();
                citySelectAdapter.clear();
                citySelectAdapter.addAll(listCounty);
            }
        }
    }
}
