package com.zzcar.zzc.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.GoodDetailActivity;
import com.zzcar.zzc.activities.SureOrderActivity_;
import com.zzcar.zzc.adapters.MyorderAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BasePullRecyclerFragment;
import com.zzcar.zzc.interfaces.RefreshListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.OrderRowsModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.CheckoutcartResponse;
import com.zzcar.zzc.networks.responses.OrderListResponse;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.dialogs.MyAlertDialog;
import com.zzcar.zzc.views.widget.pullview.PullRecyclerView;

import org.androidannotations.annotations.EFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/13.
 * 作者：黄如辉
 * 功能描述：
 */

@EFragment(R.layout.fragment_pullrefresh)
public class MineOrderListFragment extends BasePullRecyclerFragment {


    private MyorderAdapter adapter;
    private int toolbarName;
    private int CURTURNPAGE = Constant.DEFAULTPAGE;
    private String status = "0";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarName = getArguments().getInt("toolbarName", 0);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    protected void initView(PullRecyclerView recyclerView) {
        //我买到的：待付款1,待卖家支付2,已完成3,待确认5
        switch (toolbarName){
            case 1:
                status = "0";
                break;
            case 2:
                status = "1";
                break;
            case 3:
                status = "2";
                break;
            case 4:
                status = "5";
                break;
            case 5:
                status = "3";
                break;
        }

        /*获取列表数据*/
        getOrderList();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter = new MyorderAdapter(orderClickListener));
    }

    /*事件监听*/
    MyorderAdapter.OrderClickListener orderClickListener = new MyorderAdapter.OrderClickListener() {
        @Override
        public void itemClickListener(OrderRowsModel model) {
            /*点击行*/
            OrderDetailFragment fragment = OrderDetailFragment_.builder().build();
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(model.getOrder_no()));
            fragment.setArguments(bundle);
            showFragment(getActivity(), fragment);
        }

        @Override
        public void payorderClickListener(OrderRowsModel model) {
            /*支付订单*/
            payWaitOrder(model.getOrder_no());
        }

        @Override
        public void cancleOrderClickListener(final OrderRowsModel model) {
            /*取消订单*/
            final MyAlertDialog alertDialog = new MyAlertDialog(getActivity(), true);
            alertDialog.show();
            alertDialog.setTitle("删除订单");
            alertDialog.setContent("是否删除订单");
            alertDialog.setOnPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    cancleOrder(model.getId());
                }
            });
        }

        @Override
        public void successClickListener(final OrderRowsModel model) {
            /*交易成功*/
            final MyAlertDialog alertDialog = new MyAlertDialog(getActivity(), true);
            alertDialog.show();
            alertDialog.setTitle("交易成功");
            alertDialog.setContent("是否确认订单交易成功");
            alertDialog.setOnPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    carbuyerConfirm(model.getId());
                }
            });

        }
    };

    @Override
    protected void onRefresh(RecyclerView recyclerView) {
         /*获取列表数据*/
         CURTURNPAGE = Constant.DEFAULTPAGE;
         getOrderList();
    }

    @Override
    protected void onLoadMore(RecyclerView recyclerView) {
        showProgress();
        CURTURNPAGE++;
        getOrderList();
    }

    void getOrderList(){
        Subscriber subscriber = new PosetSubscriber<OrderListResponse>().getSubscriber(callback_getorder);
        UserManager.buyCarorder(status, CURTURNPAGE, subscriber);
    }

    /*取消订单*/
    void cancleOrder(int id){
        showProgress();
        Subscriber subscriber = new PosetSubscriber<Integer>().getSubscriber(callback_cancleorder);
        UserManager.cancelOrder(id, subscriber);
    }

    /*买家确认*/
    void carbuyerConfirm(int id){
        showProgress();
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_sureorder);
        UserManager.carbuyerConfirm(id, subscriber);
    }

    ResponseResultListener callback_getorder = new ResponseResultListener<OrderListResponse>() {
        @Override
        public void success(OrderListResponse returnMsg) {
            closeProgress();
            if (returnMsg.getTotal_pages() < CURTURNPAGE){
                finishLoad(false);
            }else{
                finishLoad(true);
            }
            if (CURTURNPAGE == Constant.DEFAULTPAGE){
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


    /*支付成功后回调刷新*/
    @Subscribe
    public void refreshView(RefreshListener refreshListener){
        if (refreshListener.TAG.equals("MineOrderListFragmentFresh")){
            CURTURNPAGE = Constant.DEFAULTPAGE;
            getOrderList();
        }
    }

    void payWaitOrder(String orderno){
        showProgress();
        Subscriber subscriber = new PosetSubscriber<CheckoutcartResponse>().getSubscriber(callback_buyorder);
        UserManager.payWairorder(orderno, subscriber);
    }


    /*确认订单回调*/
    ResponseResultListener callback_buyorder = new ResponseResultListener<CheckoutcartResponse>() {
        @Override
        public void success(CheckoutcartResponse returnMsg) {
            closeProgress();
            Intent intent = new Intent(getActivity(), SureOrderActivity_.class);
            intent.putExtra("checkoutcart", returnMsg);
            startActivity(intent);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };


    /*删除订单回调*/
    ResponseResultListener callback_cancleorder = new ResponseResultListener<Integer>() {
        @Override
        public void success(Integer returnMsg) {
            closeProgress();
            ToastUtil.showToast("删除成功");
            CURTURNPAGE = Constant.DEFAULTPAGE;
            getOrderList();
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    /*买家确认回调*/
    ResponseResultListener callback_sureorder = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            if (returnMsg){
                ToastUtil.showToast("确认成功");
                CURTURNPAGE = Constant.DEFAULTPAGE;
                getOrderList();
            }else{
                ToastUtil.showToast("确认失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
            ToastUtil.showToast("确认失败");
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
