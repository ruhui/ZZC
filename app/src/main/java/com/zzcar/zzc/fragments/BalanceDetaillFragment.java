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
                switch (o.getType()) {
                    case 1:
                        //收款account/receipt  收款详情
                        ReceiptDetailFragment fragment_bshoukuan = ReceiptDetailFragment_.builder().build();
                        Bundle bundle_shoukuan = new Bundle();
                        bundle_shoukuan.putString("id", String.valueOf(o.getObject_id()));
                        bundle_shoukuan.putString("title", o.getTypeDes());
                        fragment_bshoukuan.setArguments(bundle_shoukuan);
                        showFragment(getActivity(), fragment_bshoukuan);
                        break;
                    case 2:
                        //收入order/car_detail	订单详情
                        BusinessDetailFragment fragment_business = BusinessDetailFragment_.builder().build();
                        Bundle bundle_business = new Bundle();
                        bundle_business.putString("id", o.getId());
                        bundle_business.putString("title", o.getTypeDes());
                        fragment_business.setArguments(bundle_business);
                        showFragment(getActivity(), fragment_business);
                        break;
                    case 3:
                        //退款GET account/refund_order	退款详情
                        int id_refund = o.getObject_id();
                        BalanceDetailDetailFragment fragment = BalanceDetailDetailFragment_.builder().build();
                        Bundle bundle = new Bundle();
                        bundle.putString("id", String.valueOf(id_refund));
                        bundle.putString("title", o.getTypeDes());
                        fragment.setArguments(bundle);
                        showFragment(getActivity(), fragment);
                        break;
                    case 4:
                        //提现GET account/transfer	提现详情
                        TransferDetailFragment fragment_tranfer = TransferDetailFragment_.builder().build();
                        Bundle bundle_tranfer = new Bundle();
                        bundle_tranfer.putString("id", String.valueOf(o.getObject_id()));
                        bundle_tranfer.putString("title", o.getTypeDes());
                        fragment_tranfer.setArguments(bundle_tranfer);
                        showFragment(getActivity(), fragment_tranfer);
                        break;
                    case 7:
                        //交易付款GET order/car_detail	订单详情
                        BusinessDetailFragment fragment_business2 = BusinessDetailFragment_.builder().build();
                        Bundle bundle_business2 = new Bundle();
                        bundle_business2.putString("id", o.getId());
                        bundle_business2.putString("title", o.getTypeDes());
                        fragment_business2.setArguments(bundle_business2);
                        showFragment(getActivity(), fragment_business2);
                        break;
                    default:
                        break;
                }

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
