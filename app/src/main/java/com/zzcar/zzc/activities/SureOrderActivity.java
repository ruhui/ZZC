package com.zzcar.zzc.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.SureOrderAdapter;
import com.zzcar.zzc.fragments.OrderDetailFragment;
import com.zzcar.zzc.fragments.OrderDetailFragment_;
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
    private boolean isshowdetail = true;

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.relaAddress)
    RelativeLayout relaAddress;
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
    @ViewById(R.id.imageView39)
    ImageView imgSelect;

    /*默认地址不显示，默认打勾*/
    private boolean showAddress = true;
    private SureOrderAdapter adapter;




    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        isshowdetail = getIntent().getBooleanExtra("isshowdetail", true);
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

        if (checkoutcart.getShipping() != null){
            txtName.setText(checkoutcart.getShipping().getShip_to());
            txtAddress.setText(checkoutcart.getShipping().getRegion_name());
            txtPhone.setText(checkoutcart.getShipping().getPhone());
        }
    }


    @Click(R.id.imageView39)
    void AddressSelect(){
        if (showAddress){
            if (checkoutcart.getShipping() == null){
                //去添加地址
                Intent intent = new Intent(SureOrderActivity.this, SelectAddressActivity_.class);
                startActivityForResult(intent, 20176);
            }else{
                imgSelect.setImageResource(R.drawable.nav_icon_default);
                showAddress = false;
                txtName.setText(checkoutcart.getShipping().getShip_to());
                txtAddress.setText(checkoutcart.getShipping().getRegion_name());
                txtPhone.setText(checkoutcart.getShipping().getPhone());
                relaAddress.setVisibility(View.VISIBLE);
            }
        }else{
            imgSelect.setImageResource(R.drawable.nav_icon_selected);
            showAddress = true;
        }
    }


    /*地址点击*/
    @Click(R.id.relaAddress)
    void selectAddress(){
        Intent intent = new Intent(SureOrderActivity.this, SelectAddressActivity_.class);
        startActivityForResult(intent, 20176);
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
            //广播刷新消息
            EventBus.getDefault().post(new RefreshListener("MineOrderListFragmentFresh"));
            //跳转到详情界面

            if (isshowdetail){
                OrderDetailFragment fragment = OrderDetailFragment_.builder().build();
                Bundle bundle = new Bundle();
                bundle.putString("id", checkoutcart.getOrder_no());
                fragment.setArguments(bundle);
                showFragment(fragment);
            }else{
                finish();
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("支付失败");
            closeProgress();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (requestCode == 20176){
                //列表反回
            }else if (requestCode == 20177){
                //编辑反回
            }
            String sendto = data.getStringExtra("sendto");
            String address = data.getStringExtra("address");
            String phone = data.getStringExtra("phone");
            txtName.setText(sendto);
            txtAddress.setText(address);
            txtPhone.setText(phone);

            if (!TextUtils.isEmpty(sendto)){
                relaAddress.setVisibility(View.VISIBLE);
                imgSelect.setImageResource(R.drawable.nav_icon_default);
                showAddress = false;
            }
        }
    }
}
