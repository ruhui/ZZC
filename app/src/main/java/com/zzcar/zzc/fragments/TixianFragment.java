package com.zzcar.zzc.fragments;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

/**
 * 描述：提现界面
 * 创建作者： 黄如辉
 * 创建时间： 2017/5/16 17:14
 **/

@EFragment(R.layout.fragment_tixian)
public class TixianFragment extends BaseFragment{


    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.textView92)
    TextView bankname;

    @ViewById(R.id.textView93)
    TextView cardNumber;
    //提现金额
    @ViewById(R.id.tixinMoney)
    TextView tixinMoney;
    @ViewById(R.id.edtMoney)
    EditText edtMoney;

    private double accept_eposit = 0;

    @Override
    public void onNetChange(int netMobile) {

    }



    @AfterViews
    void initView(){
        getdeposit();
        getUserBill();
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("提现");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
     }

    @Click(R.id.txtSubmit)
    void submit(){
        applyDeposit();
    }



    /* 提交提现 */
    public void applyDeposit() {
        String money = edtMoney.getText().toString();
        if (TextUtils.isEmpty(money)){
            ToastUtil.showToast("请输入提现金额");
            return;
        }
        String account = Tool.formatPrice(Double.valueOf(money));

        if (Double.valueOf(account) > accept_eposit){
            ToastUtil.showToast("输入提现金额不得超出可提现金额");
            return;
        }
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_eposit);
        UserManager.applyDeposit(Double.valueOf(account), "oneday", 1, subscriber);
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


    /*获取提现方式*/
    ResponseResultListener callback_usertixian = new ResponseResultListener<DepositResponse>() {
        @Override
        public void success(DepositResponse returnMsg) {
            bankname.setText(returnMsg.getBank_name());
            cardNumber.setText(returnMsg.getBank_card());
        }

        @Override
        public void fialed(String resCode, String message) {
        }
    };


    ResponseResultListener callback_userbill = new ResponseResultListener<MybillResponse>() {
        @Override
        public void success(MybillResponse returnMsg) {
            tixinMoney.setText("可提现金额"+returnMsg.getReceipt_eposit()+"元");
            accept_eposit = returnMsg.getReceipt_eposit();
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed","fialed");
        }
    };

    /*是否提现成功*/
    ResponseResultListener callback_eposit = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            if (returnMsg){
                ToastUtil.showToast("提现成功");
                finishFragment();
            }else{
                ToastUtil.showToast("提现失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("提现失败");
        }
    };
}
