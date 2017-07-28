package com.zzcar.zzc.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.BrandCarActivity;
import com.zzcar.zzc.adapters.CarseriesAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.CarfactoryDto;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.networks.responses.CarSeriesResponse;
import com.zzcar.zzc.utils.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Subscriber;

/**
 * Created by asus-pc on 2017/4/30.
 */

@EFragment(R.layout.layout_popwindiw_carseries)
public class CarseriesCopyFragment extends BaseFragment {
    //listView中第一项的索引
    private int mListViewFirstItem = 0;
    //listView中第一项的在屏幕中的位置
    private int mScreenY = 0;

    @ViewById(R.id.textView6)
    TextView textView6;
    @ViewById(R.id.mListView)
    ListView mListView;
    private PinyinComparator pinyinComparato;
    private CarseriesAdapter carseriaAdapter;
    private CarTypeCopyFragment cartypeFragment = CarTypeCopyFragment_.builder().build();
    private List<CarfactoryDto> mCarseriesList = new ArrayList<>();

    private String brandiddes;
    private int brandid;
    private boolean isNotspec = false, selectspece = false;

    @AfterViews
    void initView(){
        pinyinComparato = new PinyinComparator();
        carseriaAdapter = new CarseriesAdapter(getActivity(), mCarseriesList);
        mListView.setAdapter(carseriaAdapter);

        if (selectspece){
            textView6.setVisibility(View.GONE);
        }else{
            textView6.setVisibility(View.VISIBLE);
        }

        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BrandCarActivity)getActivity()).setBrandandType(brandid+"", "" , "", brandiddes, "");
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CarfactoryDto carfactory = mCarseriesList.get(i);
                String name = carfactory.getName();
                long facrotyid = carfactory.getFactory_id();
                long id = carfactory.getId();
                brandiddes += ""+name;
                if (isNotspec){
                    //不显示车型
                    ((BrandCarActivity)getActivity()).setBrandandType(brandid+"", id+"" , "", brandiddes, "");
                }else{
                    //設置車型
                    showCarTypefragment(id);
                }

            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if(mListView.getChildCount()>0)
                {
                    View childAt = mListView.getChildAt(i);
                    int[] location = new int[2];
                    if (childAt != null){
                        childAt.getLocationOnScreen(location);
                    }
                    if(i!=mListViewFirstItem) {
                        if (cartypeFragment.isAdded()){
                            cartypeFragment.closefragment();
                        }
                        mListViewFirstItem = i;
                        mScreenY = location[1];
                    }else{
                        if(mScreenY>location[1]) {
                            LogUtil.E("--->", "->向上滑动");
                            if (cartypeFragment.isAdded()){
                                cartypeFragment.closefragment();
                            }
                        } else if(mScreenY<location[1]) {
                            if (cartypeFragment.isAdded()){
                                cartypeFragment.closefragment();
                            }
                            LogUtil.E("--->", "->向下滑动");
                        }
                        mScreenY = location[1];
                    }
                }
            }
        });
    }

    @Override
    public void onNetChange(int netMobile) {

    }



    public void setBrand(int brandid, String branddes, boolean isNotspec) {
        //isNotspec是否显示车型
        this.brandid = brandid;
        this.brandiddes = branddes;
        this.isNotspec = isNotspec;
        getCarSeries(brandid);
    }

    /**
     * 获取车系
     */
    private void getCarSeries(int brandid) {
        Subscriber subscriber = new PosetSubscriber<List<CarChanelResponse>>().getSubscriber(callback_carseries);
        UserManager.getCarseries(brandid, subscriber);
    }

    ResponseResultListener callback_carseries = new ResponseResultListener<List<CarSeriesResponse>>() {
        @Override
        public void success(List<CarSeriesResponse> returnMsg) {
            LogUtil.E("success", "success");
            mCarseriesList.clear();
            Collections.sort(returnMsg, pinyinComparato);
            for (CarSeriesResponse carseri : returnMsg){
                CarfactoryDto carfactory = new CarfactoryDto();
                carfactory.setName(carseri.getName());
                mCarseriesList.add(carfactory);
                mCarseriesList.addAll(carseri.getSeries());
            }
            carseriaAdapter.updateListView(mCarseriesList);
        }

        @Override
        public void fialed(String resCode, String message) {
            mCarseriesList.clear();
            carseriaAdapter.updateListView(mCarseriesList);
            LogUtil.E("fialed", "fialed");
        }
    };


    public class PinyinComparator implements Comparator<CarSeriesResponse> {

        public int compare(CarSeriesResponse o1, CarSeriesResponse o2) {
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

    /*显示车型*/
    void showCarTypefragment(long typeid){
        if (cartypeFragment==null || !cartypeFragment.isAdded()){
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.cartypeLayout, cartypeFragment, CarBrandFragment.class.getName());
            transaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_right_exit);
            transaction.addToBackStack("CarTypeCopyFragment");
            transaction.commit();
        }
        cartypeFragment.setBrand(typeid, brandid, brandiddes);
        cartypeFragment.setSelectspece(selectspece);
    }

    public void setSelectspece(boolean selectspece){
        this.selectspece = selectspece;
    }

    void closefragment() {
        if (cartypeFragment.isAdded()){
            cartypeFragment.closefragment();
        }
        getFragmentManager().popBackStackImmediate("CarseriesCopyFragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void closeSpecFragment(){
        if (cartypeFragment.isAdded()){
            cartypeFragment.closefragment();
        }
    }
}


