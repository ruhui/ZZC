package com.zzcar.zzc.fragments;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.RegistActivity;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.RefreshListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.EnumSendUserType;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.requests.AddEmployee;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/30.
 * 作者：黄如辉
 * 功能描述：添加员工
 */
@EFragment(R.layout.fragment_addployee)
public class AddAndEditEmployFragment extends BaseFragment{

    @ViewById(R.id.edtNick)
    EditText edtNick;
    @ViewById(R.id.edtPhone)
    EditText edtPhone;
    @ViewById(R.id.edtCode)
    EditText edtCode;
    @ViewById(R.id.textView226)
    TextView txtCode;
    @ViewById(R.id.edtPassword)
    EditText edtPassword;
    @ViewById(R.id.edtSurePwd)
    EditText edtSurePwd;
    @ViewById(R.id.btnSubmit)
    Button btnSubmit;
    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    private boolean timerstart = false;
    private CountDownTimer myCount;


    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("添加员工");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });


        /*确认提叫*/
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMyEmploy();
            }
        });
    }


    /*获取验证码*/
    @Click(R.id.textView226)
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
            UserManager.getRegsms(mobile, EnumSendUserType.REGISTER, subscriber);
        }
    }


    private void addMyEmploy() {
        String nick = edtNick.getText().toString();
        String phonenum = edtPhone.getText().toString();
        String code = edtCode.getText().toString();
        String password = edtPassword.getText().toString();
        String surePwd = edtSurePwd.getText().toString();
        if (TextUtils.isEmpty(nick)){
            ToastUtil.showToast("请输入昵称");
            return;
        }
        if (TextUtils.isEmpty(phonenum) || !Tool.checkPhoneNum(phonenum)){
            ToastUtil.showToast("请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(code) ){
            ToastUtil.showToast("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 12){
            ToastUtil.showToast("请输入大于6位小于12位的密码");
            return;
        }
        if (!surePwd.equals(password)){
            ToastUtil.showToast("两次输入的密码不匹配");
            return;
        }
        showProgress();
        AddEmployee addEmploy = new AddEmployee(phonenum, password, surePwd, code, nick);
        Subscriber subscriber = new PosetSubscriber<>().getSubscriber(callback_addemployee);
        UserManager.addEmployee(addEmploy, subscriber);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (myCount != null){
            myCount.onFinish();
            myCount.cancel();
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

    ResponseResultListener callback_addemployee = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            if (returnMsg){
                ToastUtil.showToast("添加成功");
                EventBus.getDefault().post(new RefreshListener("ADDEMPLOYEE"));
                finishFragment();
            }else{
                ToastUtil.showToast("添加失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

}
