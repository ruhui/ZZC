package com.zzcar.zzc.activities;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.TixianTypeFragment;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.EnumSendUserType;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.utils.SecurePreferences;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

@EActivity(R.layout.activity_forget_password)
public class ForgetPasswordActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.editText10)
    EditText newPassword;
    @ViewById(R.id.editText11)
    EditText surePassword;
    @ViewById(R.id.edtPhone)
    EditText edtPhone;
    @ViewById(R.id.editText7)
    EditText edtCode;
    @ViewById(R.id.textView91)
    TextView txtCode;


    private boolean timerstart = false;
    private CountDownTimer myCount;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("重置密码");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });
    }


    /*提交申请*/
    @Click(R.id.txtCommit)
    void applyChangepassword(){
        String npassword = newPassword.getText().toString();
        String surpassword = surePassword.getText().toString();
        String mobile = edtPhone.getText().toString();
        String code = edtCode.getText().toString();
        if (TextUtils.isEmpty(mobile) || !Tool.checkPhoneNum(mobile)){
            ToastUtil.showToast("请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(npassword)){
            ToastUtil.showToast("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(surpassword)){
            ToastUtil.showToast("请输入确认密码");
            return;
        }
        if (!npassword.equals(surpassword)){
            ToastUtil.showToast("两次输入密码不匹配");
            return;
        }
        if (TextUtils.isEmpty(code)){
            ToastUtil.showToast("请输入验证码");
            return;
        }
        showProgress();
        Subscriber subscriber =  new PosetSubscriber<Boolean>().getSubscriber(callback_forgetpassword);
        UserManager.forgetPassword(mobile, npassword, surpassword, code, subscriber);
    }


    /*获取验证码*/
    @Click(R.id.textView91)
    void sendcode(){
        if (!timerstart){
            String mobile = edtPhone.getText().toString();
            if (TextUtils.isEmpty(mobile) || !Tool.checkPhoneNum(mobile)){
                ToastUtil.showToast("请输入正确的手机号码");
                return;
            }
            timerstart = true;
            txtCode.setText(Constant.TIMECOUNT+"s");
            myCount = new MyCount(Constant.TIMECOUNT, 1000).start();
            Subscriber subscriber =  new PosetSubscriber<Boolean>().getSubscriber(callback_sendcode);
            UserManager.getRegsms(mobile, EnumSendUserType.FORGET, subscriber);
        }
    }

    /*定义一个倒计时的内部类*/
    class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            txtCode.setText( millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            timerstart = false;
            txtCode.setText(getString(R.string.app_login_getcode));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (myCount != null){
            myCount.onFinish();
        }
    }

    /*獲得验证码*/
    ResponseResultListener callback_sendcode = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            if (returnMsg){
                ToastUtil.showToast("获取成功");
            }else{
                ToastUtil.showToast("获取失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("获取失败");
        }
    };

    /*修改密码*/
    ResponseResultListener callback_forgetpassword = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            if (returnMsg){
                ToastUtil.showToast("修改成功");
                finish();
            }else{
                ToastUtil.showToast("修改失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}
