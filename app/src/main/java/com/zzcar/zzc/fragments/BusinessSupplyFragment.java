package com.zzcar.zzc.fragments;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.PushSupplyActivity_;
import com.zzcar.zzc.adapters.SuccessSupplyAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.RefreshFragment;
import com.zzcar.zzc.interfaces.RefreshSupplyFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.MydemandModel;
import com.zzcar.zzc.models.SupplyPropsMiddleModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.SupplyResponse;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/21.
 * 作者：黄如辉
 * 功能描述：商机询价
 */
@EFragment(R.layout.fragment_pullrefresh)
public class BusinessSupplyFragment extends BasePullRecyclerFragment {

    @ViewById(R.id.pushCar)
    ImageView pushCar;

    private int CURTUNPAGE = Constant.DEFAULTPAGE;
    SupplyPropsMiddleModel prosModel = new SupplyPropsMiddleModel();
    private SuccessSupplyAdapter adapter;

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        pushCar.setVisibility(View.VISIBLE);
        pushCar.setImageResource(R.drawable.nav_button_supply_default);

        EventBus.getDefault().register(this);
        getSupply();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter = new SuccessSupplyAdapter(adapterListener));
    }


    AdapterListener adapterListener = new AdapterListener<MydemandModel>() {
        @Override
        public void setOnItemListener(MydemandModel o, int position) {

        }
    };

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTUNPAGE = Constant.DEFAULTPAGE;
        getSupply();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTUNPAGE++;
        getSupply();
    }


    @Subscribe
    public void refreshData(RefreshSupplyFragment refreshSupplyFragment){
        SupplyPropsMiddleModel proModel = refreshSupplyFragment.propsModel;
        if (proModel != null){
            this.prosModel = proModel;
            CURTUNPAGE = Constant.DEFAULTPAGE;
            getSupply();
        }
    }


    @Subscribe
    public void refreshData(RefreshFragment refreshFragment){
        if (refreshFragment.refresh ){
            if (refreshFragment.TAG.equals("MyDemend") || refreshFragment.TAG.equals("MySupply")){
                CURTUNPAGE = Constant.DEFAULTPAGE;
                getSupply();
            }
        }
    }

    private void getSupply() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<>().getSubscriber(callback_demend);
        UserManager.getsupplyList(prosModel, CURTUNPAGE, subscriber);
    }

    ResponseResultListener callback_demend = new ResponseResultListener<SupplyResponse>() {
        @Override
        public void success(SupplyResponse returnMsg) {
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
    void pushSupply(){
        Intent intent = new Intent(getActivity(), PushSupplyActivity_.class);
        startActivity(intent);
    }

}
