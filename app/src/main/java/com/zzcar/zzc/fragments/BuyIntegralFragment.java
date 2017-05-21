package com.zzcar.zzc.fragments;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.RefreshFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.PayOrderView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;

/**
 * 描述：购买积分
 * 作者：黄如辉  时间 2017/5/19.
 */
@EFragment(R.layout.fragment_buyintegral)
public class BuyIntegralFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.textView105)
    EditText edtIntegra;
    @ViewById(R.id.mPayOrderView)
    PayOrderView mPayOrderView;

    @Override
    public void onNetChange(int netMobile) {

    }
    @AfterViews
    void initView(){
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("购买积分");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });
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

    @Click(R.id.btnCommit)
    void commitApply(){
        String point = edtIntegra.getText().toString();
        if (TextUtils.isEmpty(point)){
            ToastUtil.showToast("请输入积分");
            return;
        }
        if (!mPayOrderView.hasChecked()){
            ToastUtil.showToast("请选择支付方式");
            return;
        }

        String pay_code = "";
        int selectposition = mPayOrderView.getSlect();
        switch (selectposition){
            case 0:
                //微信支付
                pay_code = "weixin_sdk";
                break;
            case 1:
                //支付宝
                pay_code = "alipay_sdk";
                break;
        }
        showProgress();
        Subscriber subscriber = new PosetSubscriber<String>().getSubscriber(calback_buyintegra);
        UserManager.buyIntegral(point, pay_code, subscriber);
        Tool.hideInputMethod(getActivity(), edtIntegra);
    }


    ResponseResultListener calback_buyintegra = new ResponseResultListener<String>() {
        @Override
        public void success(String returnMsg) {
            if (!TextUtils.isEmpty(returnMsg)){
                ToastUtil.showToast("购买成功");
                EventBus.getDefault().post(new RefreshFragment(true, ""));
                finishFragment();
            }else{
                ToastUtil.showToast("购买失败");
            }
            closeProgress();
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("购买失败");
            closeProgress();
        }
    };
}
