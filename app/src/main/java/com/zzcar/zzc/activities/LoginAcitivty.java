package com.zzcar.zzc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.AddressModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.networks.responses.MineMsgResponse;
import com.zzcar.zzc.networks.responses.UserMsgResponse;
import com.zzcar.zzc.utils.SecurePreferences;
import com.zzcar.zzc.utils.ToastUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import rx.Subscriber;

@EActivity(R.layout.activity_login_acitivty)
public class LoginAcitivty extends BaseActivity {

    @ViewById(R.id.editText8)
    EditText edtPhone;
    @ViewById(R.id.editText9)
    EditText edtPassword;

    @AfterViews
    void initView(){
        String phonenumber = SecurePreferences.getInstance().getString("USERMOBILE", "");
        String password = SecurePreferences.getInstance().getString("USERPASSWORD", "");
        edtPhone.setText(phonenumber);
        edtPassword.setText(password);
    }

    @Click(R.id.button2)
    void buttonsecond(){
        String phonenum = edtPhone.getText().toString();
        String password = edtPassword.getText().toString();

        if (TextUtils.isEmpty(phonenum)){
            ToastUtil.showToast("请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(password)){
            ToastUtil.showToast("请输入密码");
            return;
        }

        showProgress();
        getData(phonenum, password);
    }

    @Click(R.id.button3)
    void regist(){
        //注册
        Intent intent = new Intent(LoginAcitivty.this, RegistActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.textView101)
    void forgetpassword(){
        //忘记密码
        Intent intent = new Intent(LoginAcitivty.this, ForgetPasswordActivity_.class);
        startActivity(intent);
    }

    @Override
    public void onNetChange(int netMobile) {

    }


    private void getData(String phonenum, String password){
        Subscriber subscriber = new PosetSubscriber<LoginResponse>().getSubscriber(callback);
        UserManager.loginzzc(phonenum, password, subscriber);
    }

    ResponseResultListener callback = new ResponseResultListener<LoginResponse>() {
        @Override
        public void success(LoginResponse returnMsg) {
            String phonenum = edtPhone.getText().toString();
            String password = edtPassword.getText().toString();
            SecurePreferences.getInstance().edit().putString("Authorization", returnMsg.access_token).commit();
            SecurePreferences.getInstance().edit().putString("USERMOBILE", phonenum).commit();
            SecurePreferences.getInstance().edit().putString("USERPASSWORD", password).commit();
            SecurePreferences.getInstance().edit().putString("EXPIRESDATE", returnMsg.expires_date).commit();


            /*查看用户是否认证，未认证则跳转到认证界面*/
            getUserMsg();

        }

        @Override
        public void fialed(String resCode, String message) {
            Log.e("fialed","fialed");
            closeProgress();
        }
    };

    /*获取用户信息*/
    public void getUserMsg() {
        Subscriber subscriber = new PosetSubscriber<MineMsgResponse>().getSubscriber(callback_usermsg);
        UserManager.getUserMsg(subscriber);
    }


    ResponseResultListener callback_usermsg = new ResponseResultListener<MineMsgResponse>() {
        @Override
        public void success(MineMsgResponse returnMsg) {
            if (returnMsg.getAuth_status() == 3){
                //登录环信
                loginEM(String.valueOf(returnMsg.getId()), "car123456");
                Intent intent = new Intent(LoginAcitivty.this, MainActivity_.class);
                startActivity(intent);
                finish();
            }else{
                //未认证
                Intent intent = new Intent(LoginAcitivty.this, AuthenticationActivity_.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };


    void loginEM(String userName, String password){
        EMClient.getInstance().login(userName, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });
    }
}
