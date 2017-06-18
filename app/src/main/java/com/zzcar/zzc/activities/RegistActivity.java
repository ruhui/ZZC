package com.zzcar.zzc.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.EnumSendUserType;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.networks.responses.MineMsgResponse;
import com.zzcar.zzc.utils.SecurePreferences;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/18.
 */

@EActivity(R.layout.activity_regist)
public class RegistActivity extends BaseActivity {

    private boolean selectxieyi = false;
    private boolean timerstart = false;
    private CountDownTimer myCount;

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.imgSelt)
    ImageView imgSelt;
    @ViewById(R.id.editText12)
    EditText edtNick;
    @ViewById(R.id.editText13)
    EditText edtPhone;
    @ViewById(R.id.editText7)
    EditText editCode;
    @ViewById(R.id.textView91)
    TextView txtCode;
    @ViewById(R.id.editText14)
    EditText edtLoginpwd;
    @ViewById(R.id.editText15)
    EditText edtSurepwd;
    @ViewById(R.id.textView102)
    TextView xieyi;


    @AfterViews
    void initView(){
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("商户注册");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });
    }

    @Click(R.id.imgSelt)
    public void selectXieyi() {
        if (!selectxieyi) {
            imgSelt.setImageResource(R.drawable.nav_icon_selected);
            selectxieyi = true;
        } else {
            imgSelt.setImageResource(R.drawable.nav_icon_default);
            selectxieyi = false;
        }
    }

    //登录
    @Click(R.id.button5)
    void login(){
        finish();
    }

    //提交
    @Click(R.id.button4)
    void commit(){
        String nick = edtNick.getText().toString();
        String mobile = edtPhone.getText().toString();
        String code = editCode.getText().toString();
        String loginpwd = edtLoginpwd.getText().toString();
        String surepwd = edtSurepwd.getText().toString();

        if (TextUtils.isEmpty(nick)){
            ToastUtil.showToast("请输入昵称");
            return;
        }
        if (TextUtils.isEmpty(mobile) || !Tool.checkPhoneNum(mobile)){
            ToastUtil.showToast("请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(code)){
            ToastUtil.showToast("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(loginpwd)){
            ToastUtil.showToast("请输入登录密码");
            return;
        }
        if (TextUtils.isEmpty(surepwd)){
            ToastUtil.showToast("请输入确认密码");
            return;
        }

        if (!selectxieyi){
            ToastUtil.showToast("请勾选注册协议");
            return;
        }

        showProgress();
        Subscriber subscriber =  new PosetSubscriber<LoginResponse>().getSubscriber(callback_regist);
        UserManager.regiestUser(mobile, loginpwd, surepwd, code, nick, subscriber);
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
            UserManager.getRegsms(mobile, EnumSendUserType.REGISTER, subscriber);
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

    @Override
    protected void onStop() {
        super.onStop();
        if (myCount != null){
            myCount.onFinish();
        }
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    /*获取用户信息*/
    public void getUserMsg() {
        Subscriber subscriber = new PosetSubscriber<MineMsgResponse>().getSubscriber(callback_usermsg);
        UserManager.getUserMsg(subscriber);
    }



    /*修改密码*/
    ResponseResultListener callback_regist = new ResponseResultListener<LoginResponse>() {
        @Override
        public void success(LoginResponse returnMsg) {
            ToastUtil.showToast("注册成功");
            String phonenum = edtPhone.getText().toString();
            String password = edtSurepwd.getText().toString();
            SecurePreferences.getInstance().edit().putString("Authorization", returnMsg.access_token).commit();
            SecurePreferences.getInstance().edit().putString("USERMOBILE", phonenum).commit();
            SecurePreferences.getInstance().edit().putString("USERPASSWORD", password).commit();
            SecurePreferences.getInstance().edit().putString("EXPIRESDATE", returnMsg.expires_date).commit();
            /*查看用户是否认证，未认证则跳转到认证界面*/
            getUserMsg();
            finish();
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    ResponseResultListener callback_usermsg = new ResponseResultListener<MineMsgResponse>() {
        @Override
        public void success(MineMsgResponse returnMsg) {
            //未认证
            Intent intent = new Intent(RegistActivity.this, AuthenticationActivity_.class);
            intent.putExtra("Auth_status", returnMsg.getAuth_status());
            startActivity(intent);
            finish();
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };
}
