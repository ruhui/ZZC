package com.zzcar.zzc.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.GroupMenberResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

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

    private String groupId;
//    private PictureRoundAdapter adapter_group;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        groupId = getIntent().getStringExtra("groupId");

        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("群组设置");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setFilterchat();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(GroupSettingActivity.this));
//        mRecyclerView.setAdapter();

        showProgress();
        /*获取群成员数据*/
        getGroupData();
    }

    private void setFilterchat() {
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_filterchat);
        UserManager.filterchat(Long.valueOf(groupId), subscriber);
    }

    ResponseResultListener callback_filterchat = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            LogUtil.E("success", "success");
            getGroupData();
        }

        @Override
        public void fialed(String resCode, String message) {
            LogUtil.E("fialed", "fialed");
        }
    };

    private void getGroupData() {
        Subscriber subscriber = new PosetSubscriber<GroupMenberResponse>().getSubscriber(callback_group);
        UserManager.setGrouptip(groupId, subscriber);
    }

    ResponseResultListener callback_group = new ResponseResultListener<GroupMenberResponse>() {
        @Override
        public void success(GroupMenberResponse returnMsg) {
            closeProgress();
            int groupCount = returnMsg.getMembers().size();
            txtGroupNum.setText(groupCount + "人");
            txtGroupName.setText(returnMsg.getGroup().getName());
            checkBox.setChecked(returnMsg.isUn_tip());
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

}
