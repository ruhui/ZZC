package com.zzcar.zzc.activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.fragments.AddAndEditEmployFragment;
import com.zzcar.zzc.interfaces.EditNickAndPhoneListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.EnumSendUserType;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.requests.EditEmployPhone;
import com.zzcar.zzc.networks.requests.EditEmployeeNick;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/1.
 * 作者：黄如辉
 * 功能描述：
 */
@EActivity(R.layout.activity_edit)
public class EditActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.edtValue)
    EditText edtValue;
    @ViewById(R.id.textView226)
    TextView txtCode;
    @ViewById(R.id.btnSubmit)
    Button btnSubmit;
    @ViewById(R.id.relaCode)
    RelativeLayout relaCode;
    @ViewById(R.id.edtCode)
    EditText edtCode;

    private String nickName="", phoneNumber = "", userId;
    private boolean timerstart = false;
    private CountDownTimer myCount;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        userId = getIntent().getStringExtra("userId");
        nickName = getIntent().getStringExtra("nickName");
        phoneNumber = getIntent().getStringExtra("phonenumber");
        String title = getIntent().getStringExtra("title");

        mNavbar.setMiddleTitle(title);
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

        if (!TextUtils.isEmpty(nickName)){
            relaCode.setVisibility(View.GONE);
            edtValue.setText(nickName);
        }else if (!TextUtils.isEmpty(nickName)){
            relaCode.setVisibility(View.VISIBLE);
            edtValue.setText(phoneNumber);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存
                String value = edtValue.getText().toString();
                String code = edtCode.getText().toString();
                if (TextUtils.isEmpty(code)){
                    ToastUtil.showToast("请输入验证码");
                    return;
                }
                if (!TextUtils.isEmpty(nickName)){
                    //修改昵称
                    EditEmployeeNick request = new EditEmployeeNick(userId, value);
                    editNickMethod(request);
                }else if (!TextUtils.isEmpty(phoneNumber)){
                    //修改手机
                    EditEmployPhone request = new EditEmployPhone(userId, value, code);
                    editPhoneMethod(request);
                }
            }
        });
    }

    private void editPhoneMethod(EditEmployPhone request) {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_editnick);
        UserManager.editEmployeemobile(request, subscriber);
    }

    private void editNickMethod(EditEmployeeNick request) {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_editnick);
        UserManager.editEmployeename(request, subscriber);
    }


    /*获取验证码*/
    @Click(R.id.textView226)
    void sendcode(){
        if (!timerstart){
            String mobile = edtValue.getText().toString();
            if (TextUtils.isEmpty(mobile) || !Tool.checkPhoneNum(mobile)){
                ToastUtil.showToast("请输入正确的手机号码");
                return;
            }
            timerstart = true;
            txtCode.setText(Constant.TIMECOUNT+"s");
            myCount = new MyCount(Constant.TIMECOUNT, 1000).start();
            Subscriber subscriber =  new PosetSubscriber<Boolean>().getSubscriber(callback_sendcode);
            UserManager.getRegsms(mobile, EnumSendUserType.CHANGEMOBILE, subscriber);
        }
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


    ResponseResultListener callback_editnick = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            closeProgress();
            if (returnMsg){
                ToastUtil.showToast("修改成功");
                String value = edtValue.getText().toString();
                if (!TextUtils.isEmpty(nickName)){
                    //修改昵称
                    EventBus.getDefault().post(new EditNickAndPhoneListener(value, ""));
                }else if (!TextUtils.isEmpty(phoneNumber)){
                    //修改手机
                    EventBus.getDefault().post(new EditNickAndPhoneListener("", value));
                }
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
