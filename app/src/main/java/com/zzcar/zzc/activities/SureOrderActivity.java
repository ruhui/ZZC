package com.zzcar.zzc.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.SureOrderAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.RefreshListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.CheckoutcartResponse;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.PayOrderView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import rx.Subscriber;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/6/13 10:05
 **/
@EActivity(R.layout.fragment_sureorder)
public class SureOrderActivity extends BaseActivity {

    private CheckoutcartResponse checkoutcart;

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.textView172)
    TextView txtPhone;
    @ViewById(R.id.textView170)
    TextView txtName;
    @ViewById(R.id.textView171)
    TextView txtAddress;
    @ViewById(R.id.textView174)
    TextView txtAmount;
    @ViewById(R.id.textView176)
    TextView txtLastMoney;
    @ViewById(R.id.textView175)
    TextView txtPay;
    @ViewById(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @ViewById(R.id.mPayOrderView)
    PayOrderView mPayOrderView;

    private SureOrderAdapter adapter;




    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        checkoutcart = (CheckoutcartResponse) getIntent().getSerializableExtra("checkoutcart");
        mNavbar.setMiddleTitle("确认订单");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(adapter = new SureOrderAdapter());
        adapter.addAll(checkoutcart.getOrder_items());

        txtAmount.setText("¥"+checkoutcart.getOrder_amount() + "万");
        txtLastMoney.setText("合计  ¥" + checkoutcart.getPay_amount() + "元");

        mPayOrderView.setSelectOnWechatListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayOrderView.selectWechatListener();
            }
        });
        mPayOrderView.setSelectOnZhifuListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPayOrderView.selectZhifubaoListener();
            }
        });
    }


    @Click(R.id.textView175)
    void payOrder(){
        boolean haschecked = mPayOrderView.hasChecked();
        if (!haschecked){
            ToastUtil.showToast("请选择支付方式");
            return;
        }
        showProgress();
        Subscriber subscriber = new PosetSubscriber<String>().getSubscriber(callback_payorder);
        UserManager.payOrder(checkoutcart.getOrder_no(), mPayOrderView.getType(), subscriber);

    }

    ResponseResultListener callback_payorder = new ResponseResultListener<String>() {
        @Override
        public void success(String returnMsg) {
            //调起支付
            closeProgress();
            ToastUtil.showToast("支付成功");
            //跳转到我买到的
            Intent intent = new Intent();
            intent.putExtra("payorder", true);
            setResult(20171, intent);
            finish();
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("支付失败");
            closeProgress();
        }
    };

}
