package com.zzcar.zzc.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.EditActivity;
import com.zzcar.zzc.activities.EditActivity_;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.EditNickAndPhoneListener;
import com.zzcar.zzc.views.widget.ItemIconTextIcon;
import com.zzcar.zzc.views.widget.NavBar;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 创建时间： 2017/7/1.
 * 作者：黄如辉
 * 功能描述：
 */
@EFragment(R.layout.fragment_editemployee)
public class EditEmployeeFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.itemNick)
    ItemIconTextIcon itemNick;
    @ViewById(R.id.itemPhone)
    ItemIconTextIcon itemPhone;

    private String userId, nickName, phoneNum;

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userId = getArguments().getString("userId");
        nickName = getArguments().getString("nickName");
        phoneNum = getArguments().getString("phoneNum");
    }

    @AfterViews
    void initView(){

        mNavbar.setMiddleTitle("编辑员工");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }
        });

        itemNick.setTitle(nickName);itemPhone.setTitle(phoneNum);

        itemNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改昵称
                Intent intent = new Intent(getActivity(), EditActivity_.class);
                intent.putExtra("title", "修改昵称");
                intent.putExtra("userId", userId);
                intent.putExtra("nickName", itemNick.getLeftText());
                startActivity(intent);
            }
        });

        itemPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改手机
                Intent intent = new Intent(getActivity(), EditActivity_.class);
                intent.putExtra("title", "修改手机号");
                intent.putExtra("userId", userId);
                intent.putExtra("phonenumber", itemPhone.getLeftText());
                startActivity(intent);
            }
        });
    }

    @Subscribe
    public void refreshData(EditNickAndPhoneListener refresh){
        if (!TextUtils.isEmpty(refresh.nickName)){
            itemNick.setTitle(refresh.nickName);
        }

        if (!TextUtils.isEmpty(refresh.phoneNumber)){
            itemPhone.setTitle(refresh.phoneNumber);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
