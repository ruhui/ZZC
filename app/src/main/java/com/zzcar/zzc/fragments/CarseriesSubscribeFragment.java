package com.zzcar.zzc.fragments;

import android.content.Context;
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
import com.zzcar.zzc.adapters.CarseriesSubscribeAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.interfaces.SubscribBland;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.BlandModle;
import com.zzcar.zzc.models.CarfactoryDto;
import com.zzcar.zzc.models.SeriesItemsModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.networks.responses.CarSeriesResponse;
import com.zzcar.zzc.utils.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import rx.Subscriber;

/**
 * Created by asus-pc on 2017/4/30.
 */

@EFragment(R.layout.layout_popwindiw_carseries)
public class CarseriesSubscribeFragment extends BaseFragment {
    //listView中第一项的索引
    private int mListViewFirstItem = 0;
    //listView中第一项的在屏幕中的位置
    private int mScreenY = 0;

    @ViewById(R.id.textView6)
    TextView textView6;
    @ViewById(R.id.mListView)
    ListView mListView;
    private PinyinComparator pinyinComparato;
    private CarseriesSubscribeAdapter carseriaAdapter;
    private List<CarfactoryDto> mCarseriesList = new ArrayList<>();

    private String brandiddes;
    private int brandid;
    private HashMap<Integer, BlandModle> hashBland = new HashMap<>();


    @AfterViews
    void initView(){
        pinyinComparato = new PinyinComparator();
        carseriaAdapter = new CarseriesSubscribeAdapter(getActivity(), mCarseriesList, hashBland.get(brandid));
        mListView.setAdapter(carseriaAdapter);

        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BrandCarActivity)getActivity()).setBrandandType(brandid+"", "" , "", brandiddes);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CarfactoryDto carfactory = mCarseriesList.get(i);
                String name = carfactory.getName();
                long id = carfactory.getId();
                if (hashBland.containsKey(brandid)){
                    BlandModle blandModle = hashBland.get(brandid);
                    List<SeriesItemsModel> series_items = blandModle.getSeries_items();
                    boolean iscontain = false;
                    int position = 0;
                    for (int j =0; j<series_items.size(); j++){
                        SeriesItemsModel model = series_items.get(j);
                        if (model.getId() == id){
                            iscontain = true;
                            position = j;
                            break;
                        }
                    }
                    if (iscontain){
                        series_items.remove(position);
                    }else{
                        SeriesItemsModel seriesItemsModel = new SeriesItemsModel(id, name);
                        series_items.add(seriesItemsModel);
                    }
                    hashBland.put(brandid, blandModle);
                }else{
                    List<SeriesItemsModel> series_items = new ArrayList<SeriesItemsModel>();
                    SeriesItemsModel seriesItemsModel = new SeriesItemsModel(id, name);
                    series_items.add(seriesItemsModel);
                    BlandModle blandModle = new BlandModle(brandid, series_items, brandiddes);
                    hashBland.put(brandid, blandModle);
                }
                /*反回到上级*/
                EventBus.getDefault().post(new SubscribBland(hashBland));
                carseriaAdapter.updateView(hashBland.get(brandid));
                carseriaAdapter.notifyDataSetChanged();
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
                }
            }
        });
    }

    @Override
    public void onNetChange(int netMobile) {

    }



    public void setBrand(int brandid, String branddes, HashMap<Integer, BlandModle> hashBland ) {
        this.hashBland = hashBland;
        this.brandid = brandid;
        this.brandiddes = branddes;
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


    void closefragment() {
        getFragmentManager().popBackStackImmediate("CarseriesCopyFragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}


