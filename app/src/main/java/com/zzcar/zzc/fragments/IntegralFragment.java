package com.zzcar.zzc.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.IntegralAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.RefreshFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.IntegralDetail;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.IntegralDetailResponse;
import com.zzcar.zzc.networks.responses.MybillResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：积分
 * 作者：黄如辉  时间 2017/5/15.
 */

@EFragment(R.layout.fragment_integral)
public class IntegralFragment extends BasePullRecyclerFragment{

    private int CURTURNPAGE = Constant.DEFAULTPAGE;

    private List<IntegralDetail> mList = new ArrayList<>();
    private IntegralAdapter adapter;

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.textView75)
    TextView point;

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    /*获取积分明细*/
    private void getIntegralDetail() {
        Subscriber subscriber = new PosetSubscriber<IntegralDetailResponse>().getSubscriber(callback_intefraldetail);
        UserManager.getIntegraldetail(CURTURNPAGE, subscriber);
    }

    /*获取用户财务*/
    public void getUserBill() {
        Subscriber subscriber = new PosetSubscriber<MybillResponse>().getSubscriber(callback_userbill);
        UserManager.getMyBill(subscriber);
    }


    @Override
    protected void initView(PullRecyclerView recyclerView) {
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("积分");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        recyclerView.enableRefresh(true);
        recyclerView.enableLoadMore(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter = new IntegralAdapter());
        adapter.addAll(mList);
        getUserBill();
        //获取积分明细
        getIntegralDetail();
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        CURTURNPAGE = Constant.DEFAULTPAGE;
        mList.clear();
        getIntegralDetail();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        CURTURNPAGE++;
        getIntegralDetail();
        showProgress();
    }

    @Click(R.id.relaBuyintegra)
    void buyIntegral(){
        showFragment(getActivity(), BuyIntegralFragment_.builder().build());
    }

    @Subscribe
    public void refreshData(RefreshFragment refresh){
        if (refresh.refresh){
            //刷新数据
            getUserBill();
            //获取积分明细
            getIntegralDetail();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    ResponseResultListener callback_userbill = new ResponseResultListener<MybillResponse>() {
        @Override
        public void success(MybillResponse returnMsg) {
            point.setText(returnMsg.getIntegral()+"");
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };


    /*获取详情*/
    ResponseResultListener callback_intefraldetail = new ResponseResultListener<IntegralDetailResponse>() {
        @Override
        public void success(IntegralDetailResponse returnMsg) {
            closeProgress();
            if (returnMsg.getTotal_pages() <= CURTURNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }
            List<IntegralDetail> list = returnMsg.getRows();
            mList.addAll(list);
            adapter.clear();
            adapter.addAll(mList);
        }


        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
            finishLoad(true);
        }
    };
}
