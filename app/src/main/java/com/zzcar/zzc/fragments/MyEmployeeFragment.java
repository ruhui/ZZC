package com.zzcar.zzc.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.zzcar.zzc.R;
import com.zzcar.zzc.adapters.MyEmployeeAdapter;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.EditNickAndPhoneListener;
import com.zzcar.zzc.interfaces.RefreshListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.MyEmployeeResponse;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/30.
 * 作者：黄如辉
 * 功能描述：
 */
@EFragment(R.layout.view_recycleview)
public class MyEmployeeFragment extends BaseFragment {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private MyEmployeeAdapter adapter_employee;

    @Override
    public void onNetChange(int netMobile) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @AfterViews
    void initView(){
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("员工管理");
        mNavbar.setRightTxt("添加");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finishFragment();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                showFragment(getActivity(), AddAndEditEmployFragment_.builder().build());
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter_employee = new MyEmployeeAdapter(adapterListener));

        /*获取我的员工*/
        getMyEmployee();
    }


    @Subscribe
    public void refreshData(RefreshListener refreshListener){
        if (refreshListener.TAG.equals("ADDEMPLOYEE")){
            /*获取我的员工*/
            getMyEmployee();
        }
    }

    private void getMyEmployee() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<List<MyEmployeeResponse>>().getSubscriber(callback_myployee);
        UserManager.getMyEmployee(subscriber);
    }

    AdapterListener adapterListener = new AdapterListener<MyEmployeeResponse>() {
        @Override
        public void setOnItemListener(MyEmployeeResponse o, int position) {
            //编辑员工
            EditEmployeeFragment fragment = EditEmployeeFragment_.builder().build();
            Bundle bundle = new Bundle();
            bundle.putString("userId", o.getId());
            bundle.putString("nickName", o.getNick());
            bundle.putString("phoneNum", o.getMobile());
            fragment.setArguments(bundle);
            showFragment(getActivity(), fragment);
        }
    };


    @Subscribe
    public void refreshData(EditNickAndPhoneListener refresh){
         /*获取我的员工*/
         getMyEmployee();
    }


    ResponseResultListener callback_myployee = new ResponseResultListener<List<MyEmployeeResponse>>() {
        @Override
        public void success(List<MyEmployeeResponse> returnMsg) {
            closeProgress();
            adapter_employee.clear();
            adapter_employee.addAll(returnMsg);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
