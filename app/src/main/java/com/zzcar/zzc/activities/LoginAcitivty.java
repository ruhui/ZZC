package com.zzcar.zzc.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.AddressModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.networks.responses.MineMsgResponse;
import com.zzcar.zzc.networks.responses.UserMsgResponse;
import com.zzcar.zzc.utils.LogUtil;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }
    }

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
            closeProgress();
            if (returnMsg.getAuth_status() == 3){
                SecurePreferences.getInstance().edit().putInt("Auth_Status", returnMsg.getAuth_status()).commit();
                //登录环信
                SecurePreferences.getInstance().edit().putString("EMChatUsername", String.valueOf(returnMsg.getId())).commit();
                loginEM(String.valueOf(returnMsg.getId()), Constant.EMCHATPASSWORD);
                Intent intent = new Intent(LoginAcitivty.this, MainActivity_.class);
                startActivity(intent);
                finish();
            }else{
                //未认证
                Intent intent = new Intent(LoginAcitivty.this, AuthenticationActivity_.class);
                intent.putExtra("Auth_status", returnMsg.getAuth_status());
                startActivity(intent);
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
                switch (code) {
                    // 网络异常 2
                    case EMError.NETWORK_ERROR:
                        LogUtil.E("网络错误 code: " + code , " message:" + message);
                        break;
                    // 无效的用户名 101
                    case EMError.INVALID_USER_NAME:
                        LogUtil.E("无效的用户名 code: " + code , " message:" + message);
                        break;
                    // 无效的密码 102
                    case EMError.INVALID_PASSWORD:
                        LogUtil.E("无效的密码  code: " + code , " message:" + message);
                        break;
                    // 用户认证失败，用户名或密码错误 202
                    case EMError.USER_AUTHENTICATION_FAILED:
                        LogUtil.E("用户认证失败，用户名或密码错误  code: " + code , " message:" + message);
                        break;
                    // 用户不存在 204
                    case EMError.USER_NOT_FOUND:
                        LogUtil.E("用户不存在  code: " + code , " message:" + message);
                        break;
                    // 无法访问到服务器 300
                    case EMError.SERVER_NOT_REACHABLE:
                        LogUtil.E("无法访问到服务器  code: " + code , " message:" + message);
                        break;
                    // 等待服务器响应超时 301
                    case EMError.SERVER_TIMEOUT:
                        LogUtil.E("等待服务器响应超时  code: " + code , " message:" + message);
                        break;
                    // 服务器繁忙 302
                    case EMError.SERVER_BUSY:
                        LogUtil.E("服务器繁忙 code: " + code , " message:" + message);
                        break;
                    // 未知 Server 异常 303 一般断网会出现这个错误
                    case EMError.SERVER_UNKNOWN_ERROR:
                        LogUtil.E("未知的服务器异常 code: " + code , " message:" + message);
                        break;
                    default:
                        LogUtil.E("ml_sign_in_failed code code: " + code , " message:" + message);
                        break;
                }
            }
        });
    }
}
