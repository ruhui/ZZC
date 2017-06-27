package com.zzcar.zzc.activities;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.PictureRoundAdapter;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.MemberModel;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.GroupMenberResponse;
import com.zzcar.zzc.utils.LogUtil;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    @ViewById(R.id.relaBottom)
    RelativeLayout relaBottom;

    private boolean isfirstLgoin = true;
    private String groupId;
    private PictureRoundAdapter adapter_group;
    private List<String> strList = new ArrayList<>();
    private List<MemberModel> listMember = new ArrayList<>();

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

        checkBox.setChecked(false);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isfirstLgoin){
                    setFilterchat();
                }
                isfirstLgoin = false;
            }
        });

        relaBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupSettingActivity.this, GroupMemberActivity_.class);
                intent.putExtra("listMember", (Serializable) listMember);
                startActivity(intent);
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(GroupSettingActivity.this);
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter_group = new PictureRoundAdapter(adapterListener));

        showProgress();
        /*获取群成员数据*/
        getGroupData();
    }


    AdapterListener adapterListener = new AdapterListener() {
        @Override
        public void setOnItemListener(Object o, int position) {
            Intent intent = new Intent(GroupSettingActivity.this, GroupMemberActivity_.class);
            intent.putExtra("listMember", (Serializable) listMember);
            startActivity(intent);
        }
    };


    private void setFilterchat() {
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_filterchat);
        UserManager.setGrouptip(groupId, subscriber);
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
        UserManager.getGroupuser(groupId, subscriber);
    }

    ResponseResultListener callback_group = new ResponseResultListener<GroupMenberResponse>() {
        @Override
        public void success(GroupMenberResponse returnMsg) {
            closeProgress();
            int groupCount = returnMsg.getMembers().size();
            txtGroupNum.setText(groupCount + "人");
            txtGroupName.setText(returnMsg.getGroup().getName());
            if (isfirstLgoin && returnMsg.isUn_tip()){
                checkBox.setChecked(returnMsg.isUn_tip());
            }
            strList.clear();
            listMember.clear();
            for (MemberModel model : returnMsg.getMembers()){
                strList.add(model.getPhoto());
                if (model.setFirstLetter(model.getNick()) != null){
                    model.setFirst_letter(model.setFirstLetter(model.getNick()));
                }else{
                    model.setFirst_letter("");
                }
                listMember.add(model);
            }

            if (adapter_group.getItemCount() == 0){
                adapter_group.clear();
                adapter_group.addAll(strList);
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

}
