package com.zzcar.zzc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.AddressModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.networks.responses.UserMsgResponse;
import com.zzcar.zzc.utils.SecurePreferences;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.util.List;

import rx.Subscriber;

@EActivity(R.layout.activity_login_acitivty)
public class LoginAcitivty extends BaseActivity {


    @AfterViews
    void initView(){

    }

    @Click(R.id.button2)
    void buttonsecond(){
        showProgress();
        getData();
    }

    @Override
    public void onNetChange(int netMobile) {

    }


    private void getData(){
        Subscriber subscriber = new PosetSubscriber<LoginResponse>().getSubscriber(callback);
        UserManager.loginzzc("13600001113","123456", subscriber);
    }

    ResponseResultListener callback = new ResponseResultListener<LoginResponse>() {
        @Override
        public void success(LoginResponse returnMsg) {
            Log.e("success","success");
            SecurePreferences.getInstance().edit().putString("Authorization", returnMsg.access_token).commit();
            SecurePreferences.getInstance().edit().putString("USERID", "13600001113").commit();
            SecurePreferences.getInstance().edit().putString("USERPASSWORD", "123456").commit();
            SecurePreferences.getInstance().edit().putString("EXPIRESDATE", returnMsg.expires_date).commit();

            Intent intent = new Intent(LoginAcitivty.this, MainActivity_.class);
            startActivity(intent);
//            getusermsg(returnMsg.access_token);
//            getAddress(returnMsg.access_token);
        }

        @Override
        public void fialed(String resCode, String message) {
            Log.e("fialed","fialed");
            closeProgress();
        }
    };

    /*获取地址*/
    private void getAddress(String accesstoken) {
        Subscriber subscriber = new PosetSubscriber<List<AddressModel>>().getSubscriber(callback_address);
        UserManager.getaddress(accesstoken, subscriber);
    }
    ResponseResultListener callback_address = new ResponseResultListener<List<AddressModel>>() {
        @Override
        public void success(List<AddressModel> returnMsg) {
            Log.e("success","success");
            closeProgress();
        }

        @Override
        public void fialed(String resCode, String message) {
            Log.e("fialed",message);
            closeProgress();
        }
    };




    //获取用户信息
    void getusermsg(String accesstoken){
        Subscriber subscriber = new PosetSubscriber<UserMsgResponse>().getSubscriber(callback_usermsg);
        UserManager.getusermsg(accesstoken, subscriber);
    }

    ResponseResultListener callback_usermsg = new ResponseResultListener<UserMsgResponse>() {
        @Override
        public void success(UserMsgResponse returnMsg) {
            Log.e("success","success");
            closeProgress();
        }

        @Override
        public void fialed(String resCode, String message) {
            Log.e("fialed",message);
            closeProgress();
        }
    };
}
