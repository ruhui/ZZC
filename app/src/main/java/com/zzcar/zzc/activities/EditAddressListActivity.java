package com.zzcar.zzc.activities;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.adapters.AddressAdapter;
import com.zzcar.zzc.adapters.AddressEditListAdapter;
import com.zzcar.zzc.interfaces.AdapterListener;
import com.zzcar.zzc.interfaces.AddressListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.responses.AddressResponse;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.dialogs.MyAlertDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import rx.Subscriber;

/**
 * 创建时间： 2017/7/11.
 * 作者：黄如辉
 * 功能描述：编辑地址，保存和删除
 */
@EActivity(R.layout.activity_address_edit_list)
public class EditAddressListActivity extends BaseActivity{


    @ViewById(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    private AddressEditListAdapter adapter_address;
    private int position = -1;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("编辑地址");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(EditAddressListActivity.this));
        mRecyclerView.setAdapter(adapter_address = new AddressEditListAdapter(adapterListener));

        /*获取地址*/
        getAddressList();
    }


    AddressListener adapterListener = new AddressListener() {
        @Override
        public void cancleAddress(final AddressResponse model, int posiTion) {
            position = posiTion;
            //删除地址
            final MyAlertDialog myAlertDialog = new MyAlertDialog(EditAddressListActivity.this, true);
            myAlertDialog.show();
            myAlertDialog.setTitle("删除地址");
            myAlertDialog.setContent("是否删除地址");
            myAlertDialog.setPosiButtion("删除");
            myAlertDialog.setNegButtion("取消");
            myAlertDialog.setOnPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myAlertDialog.dismiss();
                    deleteAddress(model.getId());
                }
            });
        }

        @Override
        public void setOnItemClickListener(AddressResponse model) {
            Intent intent = new Intent(EditAddressListActivity.this, EditAddressActivity_.class);
            intent.putExtra("addAddress", false);
            intent.putExtra("addressId", model.getId());
            startActivityForResult(intent, 20177);
        }
    };


    @Click(R.id.txtAddress)
    void addAddress(){
        Intent intent = new Intent(EditAddressListActivity.this, EditAddressActivity_.class);
        intent.putExtra("addAddress", true);
        startActivityForResult(intent, 20177);
    }

    private void getAddressList() {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<List<AddressResponse>>().getSubscriber(callback_address);
        UserManager.getAddressList(subscriber);
    }


    private void deleteAddress(String id){
        showProgress();
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_delete);
        UserManager.addressDelete(id, subscriber);
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

    ResponseResultListener callback_delete = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            position = -1;
            closeProgress();
            if (returnMsg){
                ToastUtil.showToast("删除成功");
                adapter_address.remove(position);
            }else{
                ToastUtil.showToast("删除失败");
            }
        }

        @Override
        public void fialed(String resCode, String message) {
            position = -1;
            closeProgress();
            ToastUtil.showToast("删除失败");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //刷新数据
        getAddressList();
    }
}
