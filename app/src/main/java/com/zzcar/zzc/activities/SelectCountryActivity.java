package com.zzcar.zzc.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zzcar.greendao.CityModelCountryDao;
import com.zzcar.greendao.ProvenceModelCountryDao;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.CityCountrySortAdapter;
import com.zzcar.zzc.adapters.CityCountyAdapter;
import com.zzcar.zzc.fragments.CityCountrySelectFragment;
import com.zzcar.zzc.fragments.CityCountrySelectFragment_;
import com.zzcar.zzc.fragments.CitySelectFragment;
import com.zzcar.zzc.fragments.CountrySelectFragment;
import com.zzcar.zzc.fragments.CountrySelectFragment_;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.CityChild;
import com.zzcar.zzc.models.CityModelCountry;
import com.zzcar.zzc.models.ProvenceModelCountry;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.CityResponse;
import com.zzcar.zzc.utils.GreenDaoUtils;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.SideBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Subscriber;

/**
 * 地区选择
 */

@EActivity(R.layout.activity_select_city)
public class SelectCountryActivity extends BaseActivity {


    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.country_lvcountry)
    ListView countryListView;
    @ViewById(R.id.sidrbar)
    SideBar sideBar;

    /*省份列表*/
    List<ProvenceModelCountry> provenctList = new ArrayList<>();
    /*城市列表*/
//    List<CityModelCountry> hotCityList = new ArrayList<>();
    private CityCountyAdapter hotAdapter;
    private CityCountrySortAdapter sortAdapter;
    private PinyinCityComparator pinyinComparator;

    private ProvenceModelCountryDao provenceDao;
    private CityModelCountryDao cityDao;
//    private CountyModeDao countyDao;

    private CityCountrySelectFragment citySelectFragment = CityCountrySelectFragment_.builder().build();
    private CountrySelectFragment coutryfragment = CountrySelectFragment_.builder().build();

    //listView中第一项的索引
    private int mListViewFirstItem = 0;
    //listView中第一项的在屏幕中的位置
    private int mScreenY = 0;

    @AfterViews
    void initView(){
        provenceDao = GreenDaoUtils.getSingleTon().getmDaoSession().getProvenceModelCountryDao();
        cityDao = GreenDaoUtils.getSingleTon().getmDaoSession().getCityModelCountryDao();
//        countyDao = GreenDaoUtils.getSingleTon().getmDaoSession().getCountyModeDao();
        if (provenceDao.count() > 0){
            List<CityModelCountry> userList = cityDao.queryBuilder()
                    .whereOr(CityModelCountryDao.Properties.Name.eq("厦门市"), CityModelCountryDao.Properties.Name.eq("深圳市"),
                            CityModelCountryDao.Properties.Name.like("福州市"),CityModelCountryDao.Properties.Name.like("泉州市"))
                    .build().list();

//            hotCityList.clear();
//            hotCityList.addAll(userList);
            provenctList.clear();
            provenctList = provenceDao.loadAll();
        }else{
            getCityData();
        }

        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("地区");
        mNavbar.setMiddleTextColor(R.color.color_333333);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

        View vHead= View.inflate(getActivity(), R.layout.view_headview_selectcity, null);
        TextView allcity = (TextView) vHead.findViewById(R.id.allcity);
        RecyclerView mRecyclerView = (RecyclerView) vHead.findViewById(R.id.hotcityRecycleView);
        allcity.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(hotAdapter = new CityCountyAdapter(citySelectListener));
//        hotAdapter.addAll(hotCityList);

//        countryListView.addHeaderView(vHead);
        pinyinComparator = new PinyinCityComparator();
        //全国
//        allcity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSelectCity("","","全国");
//            }
//        });

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = sortAdapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    countryListView.setSelection(position);
                }
            }
        });


        countryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int brandid = provenctList.get(position).getId();
                showCarSeriesfragment(brandid, position);
            }
        });
        Collections.sort(provenctList, pinyinComparator);
        sortAdapter = new CityCountrySortAdapter(getActivity(), provenctList);
        countryListView.setAdapter(sortAdapter);



        countryListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if(countryListView.getChildCount()>0)
                {
                    View childAt = countryListView.getChildAt(i);
                    int[] location = new int[2];
                    if (childAt != null){
                        childAt.getLocationOnScreen(location);
                    }
                    if(i!=mListViewFirstItem) {
                        if (citySelectFragment.isAdded()){
                            citySelectFragment.closefragment();
                        }
                        mListViewFirstItem = i;
                        mScreenY = location[1];
                    }else{
                        if(mScreenY>location[1]) {
                            LogUtil.E("--->", "->向上滑动");
                            if (citySelectFragment.isAdded()){
                                citySelectFragment.closefragment();
                            }
                        } else if(mScreenY<location[1]) {
                            if (citySelectFragment.isAdded()){
                                citySelectFragment.closefragment();
                            }
                            LogUtil.E("--->", "->向下滑动");
                        }
                        mScreenY = location[1];
                    }
                }
            }
        });
    }


    /*获取城市*/
    private void getCityData() {
        showProgress();
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
            closeProgress();
            LogUtil.E("fialed","fialed");
        }
    };

    @Override
    public void onNetChange(int netMobile) {

    }

    public class PinyinCityComparator implements Comparator<ProvenceModelCountry> {

        public int compare(ProvenceModelCountry o1, ProvenceModelCountry o2) {
            if (o1.getFirst_letter().equals("@")
                    || o2.getFirst_letter().equals("#")) {
                return -1;
            } else if (o1.getFirst_letter().equals("#")
                    || o2.getFirst_letter().equals("@")) {
                return 1;
            } else {
                return o1.getFirst_letter().compareTo(o2.getFirst_letter());
            }
        }
    }

    /*显示城市*/
    void showCarSeriesfragment(int parentId, int provincePosition){
        if (citySelectFragment==null || !citySelectFragment.isAdded()){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, citySelectFragment, CitySelectFragment.class.getName());
            transaction.addToBackStack("CitySelectFragment");
            transaction.commit();
        }
        citySelectFragment.setData(parentId, provincePosition);
    }

    /*显示市区*/
    void showCoutryFragment(int provinceid, int parentId){
        if (coutryfragment==null || !coutryfragment.isAdded()){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, coutryfragment, CityCountrySelectFragment.class.getName());
            transaction.addToBackStack("CitySelectFragment");
            transaction.commit();
        }
        coutryfragment.setData(provinceid, parentId);
    }


    public void getSelectCity(int provinceId, int cityId, int contryId, String region_name){
        Intent intent = new Intent();
        intent.putExtra("province_id", provinceId);
        intent.putExtra("city_id", cityId);
        intent.putExtra("contryId", contryId);
        intent.putExtra("region_name", region_name);
        setResult(10106, intent);
        finish();
    }


    CityCountyAdapter.CitySelectListener citySelectListener = new CityCountyAdapter.CitySelectListener() {
        @Override
        public void setlectCity(int provinceid, int cityid, String citydes) {
            showCoutryFragment(provinceid, cityid);
        }
    };

    private class LoadAsynTask extends AsyncTask<String, Void, String> {

        private List<CityResponse> returnMsg;
        public LoadAsynTask(List<CityResponse> returnMsg) {
            this.returnMsg = returnMsg;
        }

        @Override
        protected String doInBackground(String... strings) {
            if (provenceDao.count() == 0){
                //写入数据库
                for (CityResponse cityResponse:returnMsg){
                    ProvenceModelCountry provenceModel = new ProvenceModelCountry();
                    provenceModel.setFirst_letter(cityResponse.getFirst_letter());
                    provenceModel.setFull_name(cityResponse.getFull_name());
                    provenceModel.setId(cityResponse.getId());
                    provenceModel.setName(cityResponse.getName());
                    provenceModel.setRegion_name(cityResponse.getRegion_name());
                    provenceDao.insert(provenceModel);
                    for (CityChild cityChild : cityResponse.getChildren()){
                        CityModelCountry cityModel = new CityModelCountry();
                        cityModel.setRegion_name(cityChild.getRegion_name());
                        cityModel.setName(cityChild.getName());
                        cityModel.setId(cityChild.getId());
                        cityChild.setFull_name(cityChild.getFull_name());
                        cityModel.setFirst_letter(cityChild.getFirst_letter());
                        cityModel.setParentid(provenceModel.getId());
                        cityDao.insert(cityModel);
                    }
                }

            }

            List<CityModelCountry> userList = cityDao.queryBuilder()
                    .whereOr(CityModelCountryDao.Properties.Name.eq("厦门市"), CityModelCountryDao.Properties.Name.eq("深圳市"),
                            CityModelCountryDao.Properties.Name.like("福州市"),CityModelCountryDao.Properties.Name.like("泉州市"))
                    .build().list();
//            hotCityList.clear();
//            hotCityList.addAll(userList);
            provenctList.clear();
            provenctList = provenceDao.loadAll();
            Collections.sort(provenctList, pinyinComparator);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            closeProgress();
//            hotAdapter.addAll(hotCityList);
            sortAdapter.updateListView(provenctList);
        }
    }
}