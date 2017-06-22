package com.zzcar.zzc.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.DemendDetailActivity_;
import com.zzcar.zzc.activities.MydemendActivity;
import com.zzcar.zzc.activities.PushDemendActivity_;
import com.zzcar.zzc.adapters.BusinessDemendAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.RefreshDemendFragment;
import com.zzcar.zzc.interfaces.RefreshFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.DemendPropsModel;
import com.zzcar.zzc.models.MiddleDemendModel;
import com.zzcar.zzc.models.MydemandModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.MydemandResponse;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/18.
 * 作者：黄如辉
 * 功能描述：商机求购
 */

@EFragment(R.layout.fragment_pullrefresh)
public class BusinessDemendFragment extends BasePullRecyclerFragment{

    @ViewById(R.id.pushCar)
    ImageView pushCar;

    DemendPropsModel propsModel = new DemendPropsModel();

    private int CURTUNPAGE = Constant.DEFAULTPAGE;
    private BusinessDemendAdapter adapter;
    private String userid = "";

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userid = getArguments().getString("userid");
    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        /*发布求购*/
        pushCar.setVisibility(View.VISIBLE);
        pushCar.setImageResource(R.drawable.nav_button_demend_default);
        EventBus.getDefault().register(this);
        getDemend();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter = new BusinessDemendAdapter(adapterListener));
    }


    AdapterListener adapterListener = new AdapterListener<MydemandModel>() {
        @Override
        public void setOnItemListener(MydemandModel o, int position) {
            Intent intent = new Intent(getActivity(), DemendDetailActivity_.class);
            intent.putExtra("info_id", o.getInfo_id());
            startActivity(intent);
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

    @Subscribe
    public void refreshData(RefreshDemendFragment refreshDemendFragment){
        MiddleDemendModel proModel = refreshDemendFragment.propsModel;
        if (proModel != null){
            propsModel = proModel.getPorps(proModel);
            CURTUNPAGE = Constant.DEFAULTPAGE;
            getDemend();
        }
    }

    @Subscribe
    public void refreshData(RefreshFragment refreshFragment){
        if (refreshFragment.refresh ){
            if (refreshFragment.TAG.equals("MyDemend") || refreshFragment.TAG.equals("MySupply")){
                CURTUNPAGE = Constant.DEFAULTPAGE;
                getDemend();
            }
        }
    }

    private void getDemend() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<>().getSubscriber(callback_demend);
        UserManager.getSingelmydemand(userid, propsModel, CURTUNPAGE, subscriber);
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
            finishLoad(false);
            closeProgress();
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


    @Click(R.id.pushCar)
    void pushDemend(){
        Intent intent = new Intent(getActivity(), PushDemendActivity_.class);
        startActivity(intent);
    }

}
