package com.zzcar.zzc.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.SureOrderActivity_;
import com.zzcar.zzc.adapters.CarfromItemAdapter;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.RefreshListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.OrderitemsModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.CheckoutcartResponse;
import com.zzcar.zzc.networks.responses.OrderDetailResponse;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.dialogs.MyAlertDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/14 14:13
 **/
@EFragment(R.layout.fragment_orderdetiao)
public class OrderDetailFragment extends BaseFragment {

    private CarfromItemAdapter adapter;

    @ViewById(R.id.textView182)
    TextView txtOrderNo;
    @ViewById(R.id.textView185)
    TextView txtOrderLiushuiNo;
    @ViewById(R.id.textView186)
    TextView txtPayType;
    @ViewById(R.id.textView183)
    TextView txtPayStatu;
    @ViewById(R.id.textView187)
    TextView txtCreattime;
    @ViewById(R.id.textView188)
    TextView txtPhone;
    @ViewById(R.id.textView170)
    TextView txtRevicer;
    @ViewById(R.id.textView171)
    TextView txtAddress;
    @ViewById(R.id.textView179)
    TextView txtPay;
    @ViewById(R.id.textView180)
    TextView txtCancle;
    @ViewById(R.id.textView193)
    TextView txtPayamount;
    @ViewById(R.id.textView190)
    TextView txtAmount;

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;

    @ViewById(R.id.textView184)
    TextView txtLiushui;
    @ViewById(R.id.relaBottom)
    RelativeLayout relaBottom;

    private OrderDetailResponse response;
    private String productID = "", type = "";//type 0为买家  1为卖家

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        productID = getArguments().getString("id");
        type = getArguments().getString("type");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("订单详情");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        adapter = new CarfromItemAdapter();
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(adapter);

        getOrderDetail();


        txtPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("0")) {
                    //买家
                    if (response.getStatus() == 1){
                        //支付定金
                        payWaitOrder(response.getOrder_no());
                    }else if (response.getStatus() == 5 && !response.isBuyer_confirm()){
                        //交易成共
                        final MyAlertDialog alertDialog = new MyAlertDialog(getActivity(), true);
                        alertDialog.show();
                        alertDialog.setTitle("交易成功");
                        alertDialog.setContent("是否确认订单交易成功");
                        alertDialog.setOnPositiveListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                carbuyerConfirm(response.getId());
                            }
                        });
                    }
                }else if (type.equals("1")){
                    //卖家
                    if (response.getStatus() == 2){
                        //支付定金
                        payWaitOrder(response.getOrder_no());
                    }else if (response.getStatus() == 5 && !response.isSeller_confirm()){
                        //交易成共
                        final MyAlertDialog alertDialog = new MyAlertDialog(getActivity(), true);
                        alertDialog.show();
                        alertDialog.setTitle("交易成功");
                        alertDialog.setContent("是否确认订单交易成功");
                        alertDialog.setOnPositiveListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                carsellerconfirm(response.getId());
                            }
                        });
                    }
                }
            }
        });

        txtCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("0")){
                    //买家
                    if (response.getStatus() == 1){
                        //取消
                         /*取消订单*/
                        final MyAlertDialog alertDialog = new MyAlertDialog(getActivity(), true);
                        alertDialog.show();
                        alertDialog.setTitle("删除订单");
                        alertDialog.setContent("是否删除订单");
                        alertDialog.setOnPositiveListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                cancleOrder(response.getId());
                            }
                        });
                    }
                }
            }
        });
    }

    /*支付成功后回调刷新*/
    @Subscribe
    public void refreshView(RefreshListener refreshListener){
        if (refreshListener.TAG.equals("MineOrderListFragmentFresh")){
            getOrderDetail();
        }
    }

    /*获取详细订单数据*/
    private void getOrderDetail() {
        if (TextUtils.isEmpty(productID)){
            ToastUtil.showToast("数据异常");
            return;
        }
        showProgress();
        Subscriber subscriber = new PosetSubscriber<OrderDetailResponse>().getSubscriber(callback_orderdetail);
        UserManager.getCarOrderdetail(productID, subscriber);
    }

    /*支付定金*/
    void payWaitOrder(String orderno){
        showProgress();
        Subscriber subscriber = new PosetSubscriber<CheckoutcartResponse>().getSubscriber(callback_buyorder);
        UserManager.payWairorder(orderno, subscriber);
    }


    /*买家确认*/
    void carbuyerConfirm(int id){
        showProgress();
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_sureorder);
        UserManager.carbuyerConfirm(id, subscriber);
    }

    /*卖家确认*/
    void carsellerconfirm(int id){
        showProgress();
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_sureorder);
        UserManager.carsellerconfirm(id, subscriber);
    }

    /*取消订单*/
    void cancleOrder(int id){
        showProgress();
        Subscriber subscriber = new PosetSubscriber<Integer>().getSubscriber(callback_cancleorder);
        UserManager.cancelOrder(id, subscriber);
    }


    ResponseResultListener callback_orderdetail = new ResponseResultListener<OrderDetailResponse>() {
        @Override
        public void success(OrderDetailResponse returnMsg) {
            closeProgress();
            response = returnMsg;

            if(returnMsg.getPay_info() == null){
                txtOrderLiushuiNo.setVisibility(View.GONE);
                txtPayType.setVisibility(View.GONE);
                txtCreattime.setVisibility(View.GONE);
                txtLiushui.setVisibility(View.GONE);
            }else{
                txtOrderLiushuiNo.setVisibility(View.VISIBLE);
                txtPayType.setVisibility(View.VISIBLE);
                txtCreattime.setVisibility(View.VISIBLE);
                txtLiushui.setVisibility(View.VISIBLE);
                txtOrderLiushuiNo.setText(returnMsg.getPay_info().getTransaction_no());
                txtPayType.setText(returnMsg.getPay_info().getPay_name());
                txtCreattime.setText(returnMsg.getOrder_time());
            }

            txtOrderNo.setText(returnMsg.getOrder_no());
            adapter.clear();
            adapter.addAll(returnMsg.getOrder_items());

            relaBottom.setVisibility(View.GONE);
            if (type.equals("0")){
                //买家
                String statu = "";
                switch (returnMsg.getStatus()){
                    case 1:
                        //待付款
                        statu = "待付款";
                        relaBottom.setVisibility(View.VISIBLE);
                        txtCancle.setVisibility(View.GONE);
                        txtPay.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        //待卖家支付
                        statu = "待卖家支付";
                        break;
                    case 3:
                        //已完成
                        statu = "已完成";
                        break;
                    case 4:
                        //取消
                        statu = "订单已取消";
                        relaBottom.setVisibility(View.VISIBLE);
                        txtCancle.setVisibility(View.VISIBLE);
                        txtPay.setVisibility(View.GONE);
                        break;
                    case 5:
                        //待确认
                        statu = "待确认";
                        if (!returnMsg.isBuyer_confirm()){
                            relaBottom.setVisibility(View.VISIBLE);
                            txtCancle.setVisibility(View.GONE);
                            txtPay.setVisibility(View.VISIBLE);
                            txtPay.setText("交易成功");
                        }else{
                            relaBottom.setVisibility(View.GONE);
                        }
                        break;
                }
                txtPayStatu.setText(statu);
            }else{
                //卖家
                String statu = "";
                switch (returnMsg.getStatus()){
                    case 1:
                        //待买家付款
                        statu = "待买家付款";
                        break;
                    case 2:
                        //待支付
                        statu = "待支付";
                        relaBottom.setVisibility(View.VISIBLE);
                        txtCancle.setVisibility(View.GONE);
                        txtPay.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        //已完成
                        statu = "已完成";
                        break;
                    case 4:
                        //取消
                        statu = "订单已取消";
                        break;
                    case 5:
                        //待确认
                        statu = "待确认";
                        if (!returnMsg.isSeller_confirm()){
                            relaBottom.setVisibility(View.VISIBLE);
                            txtCancle.setVisibility(View.GONE);
                            txtPay.setVisibility(View.VISIBLE);
                            txtPay.setText("交易成功");
                        }else{
                            relaBottom.setVisibility(View.GONE);
                        }
                        break;
                }
                txtPayStatu.setText(statu);
            }


            txtPayamount.setText("合计  ¥"+returnMsg.getAmount()+"元");
            txtAmount.setText(returnMsg.getAmount()+"元");

            txtPhone.setText(returnMsg.getShipping().getPhone());
            txtRevicer.setText(returnMsg.getShipping().getShip_to());
            txtAddress.setText(returnMsg.getShipping().getAddress());
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    /*确认订单回调*/
    ResponseResultListener callback_buyorder = new ResponseResultListener<CheckoutcartResponse>() {
        @Override
        public void success(CheckoutcartResponse returnMsg) {
            closeProgress();
            Intent intent = new Intent(getActivity(), SureOrderActivity_.class);
            intent.putExtra("isshowdetail", false);
            intent.putExtra("checkoutcart", returnMsg);
            startActivity(intent);
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
                EventBus.getDefault().post(new RefreshListener("MineOrderListFragmentFresh"));
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


    /*删除订单回调*/
    ResponseResultListener callback_cancleorder = new ResponseResultListener<Integer>() {
        @Override
        public void success(Integer returnMsg) {
            closeProgress();
            ToastUtil.showToast("删除成功");
            EventBus.getDefault().post(new RefreshListener("MineOrderListFragmentFresh"));
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
