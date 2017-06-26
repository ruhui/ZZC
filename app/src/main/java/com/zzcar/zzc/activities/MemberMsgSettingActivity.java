package com.zzcar.zzc.activities;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.interfaces.RefreshListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.ItemIconTextIcon;
import com.zzcar.zzc.views.widget.ItemOneView;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.dialogs.MyAlertDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.BooleanRes;
import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/26.
 * 作者：黄如辉
 * 功能描述：
 */
@EActivity(R.layout.activity_membermsg_setting)
public class MemberMsgSettingActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.checkBox)
    CheckBox checkBox;
    @ViewById(R.id.cancleFriend)
    ItemIconTextIcon cancle_Friend;

    private int friendId;
    private boolean isfilter;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("众众车");

        friendId = getIntent().getIntExtra("friendId", 0);
        isfilter = getIntent().getBooleanExtra("filter", false);
        boolean isfriend = getIntent().getBooleanExtra("isfriend", false);
        if (isfriend){
            cancle_Friend.setVisibility(View.VISIBLE);
        }else{
            cancle_Friend.setVisibility(View.GONE);
        }

        checkBox.setChecked(isfilter);

        cancle_Friend.setTitle("删除好友");



        cancle_Friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除好友
                final MyAlertDialog myalertDialog = new MyAlertDialog(getActivity(), true);
                myalertDialog.show();
                myalertDialog.setContent("是否删除好友");
                myalertDialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancleFriend();
                        myalertDialog.dismiss();
                    }
                });
                myalertDialog.setNegsitiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myalertDialog.dismiss();
                    }
                });

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setFilterchat();
            }
        });

        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });
    }

    private void cancleFriend() {
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_deletefriend);
        UserManager.deleteFriend(friendId, subscriber);
    }


    private void setFilterchat() {
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_filterchat);
        UserManager.filterchat(friendId, subscriber);
    }


    ResponseResultListener callback_deletefriend = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            if (returnMsg){
                ToastUtil.showToast("删除好友成功");
                EventBus.getDefault().post(new RefreshListener("REFRESHMEMBER"));
            }else{
                ToastUtil.showToast("删除好友失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            ToastUtil.showToast("删除好友失败");
        }
    };

    ResponseResultListener callback_filterchat = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            LogUtil.E("success", "success");
            if (isfilter){
                isfilter = false;
            }else{
                isfilter = true;
            }
            checkBox.setChecked(isfilter);

            EventBus.getDefault().post(new RefreshListener("REFRESHMEMBER"));
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
        }
    };
}
