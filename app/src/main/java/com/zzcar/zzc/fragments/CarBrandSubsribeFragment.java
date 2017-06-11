package com.zzcar.zzc.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.greendao.BrandListResponseDao;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.BrandCarActivity;
import com.zzcar.zzc.adapters.CarBrandAdapter;
import com.zzcar.zzc.adapters.SortAdapter;
import com.zzcar.zzc.adapters.SortSubscribeAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.models.BlandModle;
import com.zzcar.zzc.models.PinyinComparator;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.utils.GreenDaoUtils;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.SideBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by asus-pc on 2017/4/30.
 */

@EFragment(R.layout.layout_popwindiw_brand)
public class CarBrandSubsribeFragment extends BaseFragment {

    private List<BrandListResponse> mBrandList = new ArrayList<>();

    private CarseriesSubscribeFragment carseriesFragment = CarseriesSubscribeFragment_.builder().build();
    private SortSubscribeAdapter sortAdapter;
    private PinyinComparator pinyinComparator;

    @ViewById(R.id.country_lvcountry)
    ListView sortListView;
    @ViewById(R.id.textView5)
    TextView textView5;
    @ViewById(R.id.sidrbar)
    SideBar sideBar;

    //listView中第一项的索引
    private int mListViewFirstItem = 0;
    //listView中第一项的在屏幕中的位置
    private int mScreenY = 0;
    private HashMap<Integer, BlandModle> hashBland = new HashMap<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hashBland = (HashMap<Integer, BlandModle>) getArguments().getSerializable("hashBland");
    }

    @AfterViews
    void initView(){

        pinyinComparator = new PinyinComparator();

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = sortAdapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int brandid = mBrandList.get(position).getId();
                String branddes = mBrandList.get(position).getName();
                showCarSeriesfragment(brandid, branddes);

            }
        });
        Collections.sort(mBrandList, pinyinComparator);
        sortAdapter = new SortSubscribeAdapter(getActivity(), mBrandList, hashBland);
        sortListView.setAdapter(sortAdapter);

        //取消
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });


        sortListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if(sortListView.getChildCount()>0)
                {
                    View childAt = sortListView.getChildAt(i);
                    int[] location = new int[2];
                    if (childAt != null){
                        childAt.getLocationOnScreen(location);
                    }
                    if(i!=mListViewFirstItem) {
                        if (carseriesFragment.isAdded()){
                            carseriesFragment.closefragment();
                        }
                        mListViewFirstItem = i;
                        mScreenY = location[1];
                    }else{
                        if(mScreenY>location[1]) {
                            LogUtil.E("--->", "->向上滑动");
                            if (carseriesFragment.isAdded()){
                                carseriesFragment.closefragment();
                            }
                        } else if(mScreenY<location[1]) {
                            if (carseriesFragment.isAdded()){
                                carseriesFragment.closefragment();
                            }
                            LogUtil.E("--->", "->向下滑动");
                        }
                        mScreenY = location[1];
                    }
                }
            }
        });

        if (mBrandList.size() > 0){
            Collections.sort(mBrandList, pinyinComparator);
            sortAdapter.updateListView(mBrandList);
        }else{
            setData();
        }
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    public void setData(){
        BrandListResponseDao brandDao = GreenDaoUtils.getSingleTon().getmDaoSession().getBrandListResponseDao();
        List<BrandListResponse> listbrand = brandDao.loadAll();
        mBrandList.addAll(listbrand);

        if(getView() != null){
            Collections.sort(mBrandList, pinyinComparator);
            sortAdapter.updateListView(mBrandList);
        }
    }

    public void closefragment() {
        getFragmentManager().popBackStackImmediate("CarBrandCopyFragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /*显示车系*/
    void showCarSeriesfragment(int brandid, String branddes){
        if (carseriesFragment==null || !carseriesFragment.isAdded()){
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, carseriesFragment, CarBrandSubsribeFragment.class.getName());
            transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_right_exit);
            transaction.addToBackStack("CarseriesCopyFragment");
            transaction.commit();
        }
        carseriesFragment.setBrand(brandid, branddes, hashBland);
    }
}
