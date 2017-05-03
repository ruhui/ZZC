package com.zzcar.zzc.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zzcar.greendao.CityModelDao;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.SelectCityActivity;
import com.zzcar.zzc.adapters.CitySelectAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.models.CityModel;
import com.zzcar.zzc.networks.requests.SearchRequest;
import com.zzcar.zzc.utils.GreenDaoUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/2 15:16
 **/

@EFragment(R.layout.fragment_cityselect)
public class CitySelectFragment extends BaseFragment {

    @ViewById(R.id.textView6)
    TextView textView6;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private int provinceId = 0;
    private String citydes = "";
    private List<CityModel> cityList = new ArrayList<>();
    private CitySelectAdapter citySelectAdapter;

    @AfterViews
    void initView(){
        textView6.setText("不限城市");
        citySelectAdapter = new CitySelectAdapter(citySelectListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(citySelectAdapter);
        citySelectAdapter.addAll(cityList);

        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SelectCityActivity)getActivity()).getSelectCity(provinceId+"", "", citydes);
            }
        });
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    /*获取城市*/
    public void setData(int parentId, String branddes) {
        provinceId = parentId;
        citydes = branddes;
        CityModelDao cityDao = GreenDaoUtils.getSingleTon().getmDaoSession().getCityModelDao();
        List<CityModel> userList = cityDao.queryBuilder()
                .where(CityModelDao.Properties.Parentid.eq(parentId))
                .build().list();
        cityList.clear();
        cityList.addAll(userList);
        if (getView() != null){
            citySelectAdapter.addAll(cityList);
        }
    }

    public void closefragment() {
        getFragmentManager().popBackStackImmediate("CitySelectFragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /*城市点选回调*/
    CitySelectAdapter.CitySelectListener citySelectListener = new CitySelectAdapter.CitySelectListener() {
        @Override
        public void setlectCity(int provinceid, int cityid, String citydes) {
            ((SelectCityActivity)getActivity()).getSelectCity(provinceid+"", cityid+"", citydes);
        }
    };
}
