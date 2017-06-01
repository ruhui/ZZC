package com.zzcar.zzc.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.PushCarActivity_;
import com.zzcar.zzc.adapters.MycarfromAdapter;
import com.zzcar.zzc.adapters.UserCarAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.HomeAdapterListener;
import com.zzcar.zzc.interfaces.RefreshFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.interfaces.TablayoutTitle;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.HomeCarGet;
import com.zzcar.zzc.models.MyfavcarModle;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.HomeCarGetResponse;
import com.zzcar.zzc.networks.responses.MyfavcarResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：提供给聊天使用的我的车源
 * 作者：黄如辉  时间 2017/5/21.
 */
@EFragment(R.layout.fragment_pullrefresh)
public class MycarfromSelectFragment extends BasePullRecyclerFragment {

    private MycarfromAdapter adapter;
    private List<MyfavcarModle> mList = new ArrayList<>();
    private int CURTURNPAGE = Constant.DEFAULTPAGE;


    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        adapter = new MycarfromAdapter(adapterListener, "011");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.addAll(mList);
        getMycar();
    }

    /*获取我的车源*/
    private void getMycar() {
        Subscriber subscriber = new PosetSubscriber<MyfavcarResponse>().getSubscriber(callback_mycar);
        UserManager.getMycar(CURTURNPAGE, "0", subscriber );
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTURNPAGE = Constant.DEFAULTPAGE;
        mList.clear();
        getMycar();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        showProgress();
        CURTURNPAGE++;
        getMycar();
    }


    //刷新界面
    @Subscribe
    public void refresh(RefreshFragment refresh){
        if(refresh.TAG.equals("Mycar") && refresh.refresh){
            CURTURNPAGE = Constant.DEFAULTPAGE;
            mList.clear();
            getMycar();
        }
    }

    AdapterListener adapterListener = new AdapterListener<MyfavcarModle>() {
        @Override
        public void setOnItemListener(MyfavcarModle o, int position) {
            Intent intent = new Intent();
            intent.putExtra("homeCarGet", (Serializable) o );
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    };



    ResponseResultListener callback_mycar = new ResponseResultListener<MyfavcarResponse>() {
        @Override
        public void success(MyfavcarResponse returnMsg) {
            closeProgress();
            if (returnMsg.getTotal_pages() <= CURTURNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }
            List<MyfavcarModle>  listdata = returnMsg.getRows();
            mList.addAll(listdata);
            adapter.clear();
            adapter.addAll(mList);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };



}
