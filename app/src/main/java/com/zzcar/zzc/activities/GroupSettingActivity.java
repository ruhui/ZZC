package com.zzcar.zzc.activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.interfaces.RefreshListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/26.
 * 作者：黄如辉
 * 功能描述：
 */
@EActivity(R.layout.activity_group_setting)
public class GroupSettingActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.textView209)
    TextView txtGroupName;
    @ViewById(R.id.textView211)
    TextView txtGroupNum;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.checkBox)
    CheckBox checkBox;

    private int groupId;
    private boolean isfilter;


    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){

        groupId = getIntent().getIntExtra("groupId", 0);
        isfilter = getIntent().getBooleanExtra("filter", false);

        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("群组设置");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

        checkBox.setChecked(isfilter);


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setFilterchat();
            }
        });
    }



    private void setFilterchat() {
//        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_filterchat);
//        UserManager.filterchat(friendId, subscriber);
    }

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
