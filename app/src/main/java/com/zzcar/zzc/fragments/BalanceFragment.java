package com.zzcar.zzc.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.DepositResponse;
import com.zzcar.zzc.networks.responses.MybillResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.ItemIconTextIcon;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

/**
 * 描述：我的钱包
 * 作者：黄如辉  时间 2017/5/16.
 */

@EFragment(R.layout.fragment_balance)
public class BalanceFragment extends BaseFragment{

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    @ViewById(R.id.textView83)
    TextView lastMoney;
    @ViewById(R.id.textView85)
    TextView frozenMoney;
    @ViewById(R.id.txtTixian)
    TextView txtTixain;
    @ViewById(R.id.txtShouzhiDetail)
    TextView txtShouzhiDetail;

    @ViewById(R.id.shoukuanItem)
    ItemIconTextIcon shoukuanItem;
    @ViewById(R.id.tixianItem)
    ItemIconTextIcon tixianItem;


    @Override
    public void onNetChange(int netMobile) {

    }


    @AfterViews
    void initeView(){
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("钱包");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        shoukuanItem.setTitle("店铺收款");
        tixianItem.setTitle("提现账户");

        /*提现账户*/
        tixianItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TixianTypeFragment fragment = TixianTypeFragment_.builder().build();
                Bundle bundle = new Bundle();
                bundle.putBoolean("tixian", false);
                fragment.setArguments(bundle);
                showFragment(getActivity(), fragment);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserBill();
    }

    @Click(R.id.txtTixian)
    void tixianBtn(){
        getdeposit();
    }


    /*获取用户财务*/
    public void getUserBill() {
        Subscriber subscriber = new PosetSubscriber<MybillResponse>().getSubscriber(callback_userbill);
        UserManager.getMyBill(subscriber);
    }

    /*获取提现账号*/
    private void getdeposit(){
        Subscriber subscriber = new PosetSubscriber<DepositResponse>().getSubscriber(callback_usertixian);
        UserManager.getdeposit(subscriber);
    }

    ResponseResultListener callback_userbill = new ResponseResultListener<MybillResponse>() {
        @Override
        public void success(MybillResponse returnMsg) {
            lastMoney.setText(returnMsg.getBalance()+"");
            frozenMoney.setText(returnMsg.getUnconfirmed()+"");
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };

    /*获取提现方式*/
    ResponseResultListener callback_usertixian = new ResponseResultListener<DepositResponse>() {
        @Override
        public void success(DepositResponse returnMsg) {
            LogUtil.E("success", "success");
            if (TextUtils.isEmpty(returnMsg.getBank_card())){
                //没有体现方式
                ToastUtil.showToast("没有提现账户，请先填写提现账户");
                TixianTypeFragment fragment = TixianTypeFragment_.builder().build();
                Bundle bundle = new Bundle();
                bundle.putBoolean("tixian", true);
                fragment.setArguments(bundle);
                showFragment(getActivity(), fragment);
            }else{
                //跳转到提现界面
                showFragment(getActivity(), TixianFragment_.builder().build());
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
        }
    };
}
