package com.zzcar.zzc.activities;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.HomeCarGetResponse;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.NavBar3;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

/**
 * 描述：发送朋友请求
 * 作者：黄如辉  时间 2017/5/26.
 */
@EActivity(R.layout.activity_applyfriend)
public class SendFriendActivity  extends BaseActivity{

    @ViewById(R.id.mNavbar)
    NavBar3 mNavbar;
    @ViewById(R.id.imageView28)
    ImageView imgDelete;
    @ViewById(R.id.edtMsg)
    EditText edtMsg;

    private int userid;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        userid = getIntent().getIntExtra("userid", 0);

        mNavbar.setLeftMenuText("取消");
        mNavbar.setMiddleTitle("");
        mNavbar.setRightTxt("发送");
        mNavbar.setRightTxtColor(R.color.color_333333);


        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtMsg.setText("");
            }
        });

        mNavbar.setOnMenuClickListener(new NavBar3.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                /*发送好友验证*/
                String message = edtMsg.getText().toString();
                Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_cardata);
                UserManager.applyFriend(userid, 1, message, subscriber);
            }
        });
    }


    ResponseResultListener callback_cardata = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            if (returnMsg){
                ToastUtil.showToast("发送成功");
                finish();
            }else{
                ToastUtil.showToast("发送失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("发送失败");
        }
    };
}
