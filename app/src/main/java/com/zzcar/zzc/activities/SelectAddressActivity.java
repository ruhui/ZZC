package com.zzcar.zzc.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.AddressAdapter;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.AddressResponse;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/6/28.
 * 作者：黄如辉
 * 功能描述：
 */
@EActivity(R.layout.activity_address_manager)
public class SelectAddressActivity extends BaseActivity {

    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    private AddressAdapter adapter_address;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("选择地址");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setRightTxt("管理");
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
                //
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(SelectAddressActivity.this));
        mRecyclerView.setAdapter(adapter_address = new AddressAdapter(adapterListener));

        /*获取地址*/
        getAddressList();
    }


    AdapterListener adapterListener = new AdapterListener<AddressResponse>() {
        @Override
        public void setOnItemListener(AddressResponse o, int position) {

        }
    };

    private void getAddressList() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<List<AddressResponse>>().getSubscriber(callback_address);
        UserManager.getAddressList(subscriber);
    }

    ResponseResultListener callback_address = new ResponseResultListener<List<AddressResponse>>() {
        @Override
        public void success(List<AddressResponse> returnMsg) {
            closeProgress();
            adapter_address.clear();
            adapter_address.addAll(returnMsg);
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}
