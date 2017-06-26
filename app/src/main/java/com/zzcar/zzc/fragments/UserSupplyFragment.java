package com.zzcar.zzc.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.PushSupplyActivity_;
import com.zzcar.zzc.activities.SupplyDetailActivity_;
import com.zzcar.zzc.adapters.SuccessSupplyAdapter;
import com.zzcar.zzc.adapters.SupplyAdapter;
import com.zzcar.zzc.adapters.UserSupplyAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.RefreshFragment;
import com.zzcar.zzc.interfaces.RefreshSupplyFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.SupplyModel;
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
public class UserSupplyFragment extends BasePullRecyclerFragment {

    @ViewById(R.id.pushCar)
    ImageView pushCar;

    private int CURTUNPAGE = Constant.DEFAULTPAGE;
    SupplyPropsMiddleModel prosModel = new SupplyPropsMiddleModel();
    private UserSupplyAdapter adapter;
    private String userid = "";
    private boolean dismisspush;

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userid = getArguments().getString("userid");
        dismisspush = getArguments().getBoolean("dismisspush");
    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        pushCar.setVisibility(View.VISIBLE);
        pushCar.setImageResource(R.drawable.nav_button_supply_default);

        EventBus.getDefault().register(this);
        getSupply();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter = new UserSupplyAdapter(adapterListener));
        if (dismisspush){
            pushCar.setVisibility(View.GONE);
        }else {
            pushCar.setVisibility(View.VISIBLE);
        }
    }


    AdapterListener adapterListener = new AdapterListener<SupplyModel>() {
        @Override
        public void setOnItemListener(SupplyModel o, int position) {
            Intent intent = new Intent(getActivity(), SupplyDetailActivity_.class);
            intent.putExtra("info_id", o.getInfo_id());
            startActivity(intent);
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
        UserManager.getsupplyList(userid, prosModel, CURTUNPAGE, subscriber);
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
