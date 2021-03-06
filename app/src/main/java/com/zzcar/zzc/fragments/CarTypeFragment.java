package com.zzcar.zzc.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.CarTypeAdapter;
import com.zzcar.zzc.adapters.CarseriesAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.BrandCarseriesAndType;
import com.zzcar.zzc.interfaces.BrandListener;
import com.zzcar.zzc.interfaces.CarseriesAndType;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.CarTypeSpecs;
import com.zzcar.zzc.models.CarfactoryDto;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.networks.responses.CarSeriesResponse;
import com.zzcar.zzc.networks.responses.CarTypeResponse;
import com.zzcar.zzc.utils.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Subscriber;

/**
 * Created by asus-pc on 2017/4/30.
 */

@EFragment(R.layout.layout_popwindiw_carseries)
public class CarTypeFragment extends BaseFragment {
    @ViewById(R.id.mListView)
    ListView mListView;
    @ViewById(R.id.textView6)
    TextView textView6;
    private CarTypeAdapter carTypeAdapter;
    private List<CarTypeSpecs> mCartypeList = new ArrayList<>();
    private long branid;
    private String brandiddes;
    private long seriesid;

    @AfterViews
    void initView(){
        textView6.setText("不限车型");
        carTypeAdapter = new CarTypeAdapter(getActivity(), mCartypeList);
        mListView.setAdapter(carTypeAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CarTypeSpecs carTypeSpecs = mCartypeList.get(i);
                EventBus.getDefault().post(new BrandCarseriesAndType(branid+"", seriesid+"", carTypeSpecs.getId()+"", brandiddes));
            }

        });
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new CarseriesAndType(branid+"", seriesid+"", brandiddes));
            }
        });


    }

    @Override
    public void onNetChange(int netMobile) {

    }


    @Override
    public boolean onBackPressed() {
        finishFragment();
        return super.onBackPressed();
    }

    public void setBrand(long typeid, long brandid, String brandiddes) {
        this.branid = brandid;
        this.brandiddes = brandiddes;
        this.seriesid = typeid;
        getCarType(typeid);
    }

    /**
     * 获取车系
     */
    private void getCarType(long carserialid) {
        Subscriber subscriber = new PosetSubscriber<List<CarTypeResponse>>().getSubscriber(callback_carseries);
        UserManager.getCarTYpe(carserialid, subscriber);
    }

    ResponseResultListener callback_carseries = new ResponseResultListener<List<CarTypeResponse>>() {
        @Override
        public void success(List<CarTypeResponse> returnMsg) {
            LogUtil.E("success", "success");
            mCartypeList.clear();
            for (CarTypeResponse carseri : returnMsg){
                CarTypeSpecs carfactory = new CarTypeSpecs(carseri.getYear(), carseri.getName(), 0, "0");
                mCartypeList.add(carfactory);
                mCartypeList.addAll(carseri.getSpecs());
            }
            carTypeAdapter.updateListView(mCartypeList);
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
        }
    };



    void closefragment() {
        getFragmentManager().popBackStackImmediate("CarTypeFragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}


