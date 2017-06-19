package com.zzcar.zzc.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.BusinessDemendAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.DemendPropsModel;
import com.zzcar.zzc.models.MydemandModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.MydemandResponse;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/18.
 * 作者：黄如辉
 * 功能描述：商机求购
 */

@EFragment(R.layout.fragment_pullrefresh)
public class BusinessDemendFragment extends BasePullRecyclerFragment{

    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    DemendPropsModel propsModel = new DemendPropsModel();
    private int CURTUNPAGE = Constant.DEFAULTPAGE;
    private BusinessDemendAdapter adapter;

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        getDemend();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter = new BusinessDemendAdapter(adapterListener));
    }


    AdapterListener adapterListener = new AdapterListener<MydemandModel>() {
        @Override
        public void setOnItemListener(MydemandModel o, int position) {

        }
    };


    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTUNPAGE = Constant.DEFAULTPAGE;
        getDemend();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTUNPAGE++;
        getDemend();
    }



    private void getDemend() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<>().getSubscriber(callback_demend);
        UserManager.getSingelmydemand(propsModel, CURTUNPAGE, subscriber);
    }

    ResponseResultListener callback_demend = new ResponseResultListener<MydemandResponse>() {
        @Override
        public void success(MydemandResponse returnMsg) {
            closeProgress();
            if (returnMsg.getTotal_pages() < CURTUNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }
            if (CURTUNPAGE == Constant.DEFAULTPAGE){
                adapter.clear();
                adapter.addAll(returnMsg.getRows());
            }else{
                adapter.addAll(returnMsg.getRows());
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}
