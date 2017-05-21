package com.zzcar.zzc.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.BalanceDetailAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.MoneyDetailHeadModel;
import com.zzcar.zzc.models.ShouzhiDetailModel;
import com.zzcar.zzc.models.ShouzhiItem;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.CommentResponse;
import com.zzcar.zzc.networks.responses.ShouzhiDetailResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：收支明细
 * 作者：黄如辉  时间 2017/5/20.
 */

@EFragment(R.layout.fragment_footprint)
public class BalanceDetaillFragment extends BasePullRecyclerFragment{

    private List<Object> dataList = new ArrayList<>();
    private int CURTURNPAGE = Constant.DEFAULTPAGE;

    private BalanceDetailAdapter adapter;

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("收支明细");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
        //获取收支明细
        getBalanceDetail();
        adapter = new BalanceDetailAdapter(getActivity(), dataList, adapterListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    /*点击监听*/
    AdapterListener adapterListener = new AdapterListener<ShouzhiItem>() {
        @Override
        public void setOnItemListener(ShouzhiItem o, int position) {
            if (o.isIntent()){
                //是否需跳转
                int id = o.getObject_id();
                BalanceDetailDetailFragment fragment = BalanceDetailDetailFragment_.builder().build();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("title", o.getTypeDes());
                fragment.setArguments(bundle);
                showFragment(getActivity(), fragment);
            }
        }
    };


    /*收支明细*/
    private void getBalanceDetail() {
        Subscriber subscribe = new PosetSubscriber<ShouzhiDetailResponse>().getSubscriber(callback_balancedetail);
        UserManager.getBills(CURTURNPAGE, subscribe);
    }

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
        dataList.clear();
        CURTURNPAGE = Constant.DEFAULTPAGE;
        getBalanceDetail();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        showProgress();
        CURTURNPAGE++;
        getBalanceDetail();
    }

    /*组织shuju */
    private void setData(ShouzhiDetailResponse returnMsg) {
        if (returnMsg == null){
            return;
        }
        List<ShouzhiDetailModel> rows =  returnMsg.getRows();
        for (ShouzhiDetailModel detailModel : rows){
            MoneyDetailHeadModel detail = new MoneyDetailHeadModel( detailModel.getDate(), detailModel.getTotal());
            dataList.add(detail);
            for (ShouzhiItem item : detailModel.getItems()){
                dataList.add(item);
            }
        }
        if (adapter!= null){
            adapter.setDataList(dataList);
        }
    }


    ResponseResultListener callback_balancedetail = new ResponseResultListener<ShouzhiDetailResponse>() {
        @Override
        public void success(ShouzhiDetailResponse returnMsg) {
            closeProgress();
            LogUtil.E("success","success");
            if (returnMsg.getTotal_pages() <= CURTURNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }
            setData(returnMsg);
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
            closeProgress();
        }
    };


}
