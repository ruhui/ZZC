package com.zzcar.zzc.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zzcar.greendao.CityModelCountryDao;
import com.zzcar.greendao.CityModelDao;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.SelectCityActivity;
import com.zzcar.zzc.adapters.CityCountrySelectAdapter;
import com.zzcar.zzc.adapters.CitySelectAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.models.CityModel;
import com.zzcar.zzc.models.CityModelCountry;
import com.zzcar.zzc.utils.GreenDaoUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：地区选择
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/2 15:16
 **/

@EFragment(R.layout.fragment_cityselect)
public class CityCountrySelectFragment extends BaseFragment {

    @ViewById(R.id.textView6)
    TextView textView6;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private CountrySelectFragment coutryfragment = CountrySelectFragment_.builder().build();

    private List<CityModelCountry> cityList = new ArrayList<>();
    private CityCountrySelectAdapter citySelectAdapter;
    private int provincePosition;

    @AfterViews
    void initView(){
        textView6.setText("不限城市");
        citySelectAdapter = new CityCountrySelectAdapter(citySelectListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(citySelectAdapter);
        citySelectAdapter.addAll(cityList);

        textView6.setVisibility(View.GONE);
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    /*获取城市*/
    public void setData(int parentId, int provincePosition) {
        this.provincePosition = provincePosition;
        CityModelCountryDao cityDao = GreenDaoUtils.getSingleTon().getmDaoSession().getCityModelCountryDao();
        List<CityModelCountry> userList = cityDao.queryBuilder()
                .where(CityModelCountryDao.Properties.Parentid.eq(parentId))
                .build().list();
        cityList.clear();
        cityList.addAll(userList);
        if (getView() != null){
            citySelectAdapter.clear();
            citySelectAdapter.addAll(cityList);
        }
    }

    public void closefragment() {
        getFragmentManager().popBackStackImmediate("CitySelectFragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /*城市点选回调*/
    CityCountrySelectAdapter.CitySelectListener citySelectListener = new CityCountrySelectAdapter.CitySelectListener() {
        @Override
        public void setlectCity(int provinceid, int cityid) {
            showCoutryFragment(provinceid, cityid);
//            ((SelectCityActivity)getActivity()).getSelectCity(provinceid+"", cityid+"", citydes);
        }
    };


    /*显示市区*/
    void showCoutryFragment(int provinceid, int parentId){
        if (coutryfragment==null || !coutryfragment.isAdded()){
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, coutryfragment, CityCountrySelectFragment.class.getName());
            transaction.addToBackStack("CitySelectFragment");
            transaction.commit();
        }
        coutryfragment.setData(provinceid, parentId);
    }
}
