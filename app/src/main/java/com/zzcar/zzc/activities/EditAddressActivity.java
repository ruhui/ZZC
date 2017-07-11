package com.zzcar.zzc.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzcar.greendao.MyEaseUserDao;
import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.models.MyEaseUser;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.UploadFileWithoutLoding;
import com.zzcar.zzc.networks.requests.SaveAddressaRequest;
import com.zzcar.zzc.networks.responses.AddressDetail;
import com.zzcar.zzc.networks.responses.MineMsgResponse;
import com.zzcar.zzc.utils.GreenDaoUtils;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.ItemIconTextIcon;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import me.iwf.photopicker.PhotoPicker;
import rx.Subscriber;

/**
 * 创建时间： 2017/6/29.
 * 作者：黄如辉
 * 功能描述：
 */
@EActivity(R.layout.activity_address_edit)
public class EditAddressActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.itemName)
    ItemIconTextIcon itemName;
    @ViewById(R.id.itemPhone)
    ItemIconTextIcon itemPhone;
    @ViewById(R.id.itemCity)
    ItemIconTextIcon itemCity;
    @ViewById(R.id.edtAddress)
    EditText edtAddress;
    @ViewById(R.id.imgSelect)
    ImageView imgSelect;

    private String addressId;
    private int userId;
    private boolean isDefault = false;
    private SaveAddressaRequest addressaRequest = new SaveAddressaRequest();

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){

        boolean isadd = getIntent().getBooleanExtra("addAddress", true);
        addressId = getIntent().getStringExtra("addressId");

        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        if (isadd){
            mNavbar.setMiddleTitle("添加收货地址");
        }else{
            mNavbar.setMiddleTitle("编辑收货地址");
        }
        itemName.setTitle("收货人姓名");
        itemPhone.setTitle("手机号");
        itemCity.setTitle("省/市/行政中心");

        //填写收货人姓名
        itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAddressActivity.this, NickChangeAcitivity_.class);
                intent.putExtra("titleBar", "姓名");
                intent.putExtra("value", "");
                startActivityForResult(intent, 20171);
            }
        });
        //填写手机号码
        itemPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAddressActivity.this, NickChangeAcitivity_.class);
                intent.putExtra("titleBar", "手机号");
                intent.putExtra("value", "");
                startActivityForResult(intent, 20172);
            }
        });

        //地址选择
        itemCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectCountryActivity_.class);
                startActivityForResult(intent, 20173);
            }
        });

        //是否设置默认
        imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDefault){
                    isDefault = false;
                    imgSelect.setImageResource(R.drawable.nav_icon_default);
                }else{
                    isDefault = true;
                    imgSelect.setImageResource(R.drawable.nav_icon_selected);
                }
            }
        });

        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });

        getUserMsg();

        if (!TextUtils.isEmpty(addressId)){
            //获取地址
            getAddressByid(addressId);
        }
    }

    private void getAddressByid(String addressId) {
        showProgress();
        Subscriber subscriber = new PosetSubscriber<AddressDetail>().getSubscriber(callback_address_Detail);
        UserManager.getAddressdetail(addressId, subscriber);
    }

    @Click(R.id.txtSave)
    void saveAddress(){
        String nickname = itemName.getRightText().toString();
        String phonenum = itemPhone.getRightText().toString();
        String regonname = itemCity.getRightText().toString();
        String addressDetail = edtAddress.getText().toString();
        if (TextUtils.isEmpty(nickname)){
            ToastUtil.showToast("请输入收货人姓名");
            return;
        }
        if (TextUtils.isEmpty(phonenum)){
            ToastUtil.showToast("请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(regonname)){
            ToastUtil.showToast("请输入省市行政中心");
            return;
        }
        if (TextUtils.isEmpty(addressDetail)){
            ToastUtil.showToast("请输入详细地址");
            return;
        }

        addressaRequest.setShip_to(nickname);
        addressaRequest.setPhone(phonenum);
        addressaRequest.setRegion_name(regonname);
        addressaRequest.setAddress(addressDetail);
        addressaRequest.setIs_default(isDefault);
        addressaRequest.setUser_id(userId);
        if (!TextUtils.isEmpty(addressId)){
            addressaRequest.setId(Long.valueOf(addressId));
        }
        showProgress();
        Subscriber subscriber = new PosetSubscriber<Boolean>().getSubscriber(callback_address);
        UserManager.saveAddress(addressaRequest, subscriber);
    }

    /*获取用户信息*/
    public void getUserMsg() {
        Subscriber subscriber = new PosetSubscriber<MineMsgResponse>().getSubscriber(callback_usermsg);
        UserManager.getUserMsg(subscriber);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (requestCode == 20171){
                String nick = data.getStringExtra("value");
                itemName.setRightText(nick);
            }else if (requestCode == 20172){
                String phonenum = data.getStringExtra("value");
                itemPhone.setRightText(phonenum);
            }else if(requestCode == 20173){
                int province_id = data.getIntExtra("province_id", 0);
                int city_id = data.getIntExtra("city_id", 0);
                int contryId = data.getIntExtra("contryId", 0);
                String region_name = data.getStringExtra("region_name");
                addressaRequest.setProvince_id(province_id);
                addressaRequest.setCity_id(city_id);
                addressaRequest.setArea_id(contryId);
                itemCity.setRightText(region_name);
            }
        }
    }

    ResponseResultListener callback_address = new ResponseResultListener<Boolean>() {
        @Override
        public void success(Boolean returnMsg) {
            ToastUtil.showToast("保存成功");
            closeProgress();
            finish();
        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
            ToastUtil.showToast("保存失败");
        }
    };

    ResponseResultListener callback_usermsg = new ResponseResultListener<MineMsgResponse>() {
        @Override
        public void success(MineMsgResponse returnMsg) {
            userId = returnMsg.getId();
        }

        @Override
        public void fialed(String resCode, String message) {

        }
    };


    ResponseResultListener callback_address_Detail = new ResponseResultListener<AddressDetail>() {
        @Override
        public void success(AddressDetail returnMsg) {
            closeProgress();
            addressaRequest.setShip_to(returnMsg.getShip_to());
            addressaRequest.setPhone(returnMsg.getPhone());
            addressaRequest.setRegion_name(returnMsg.getRegion_name());
            addressaRequest.setAddress(returnMsg.getAddress());
            addressaRequest.setIs_default(returnMsg.is_default());
            addressaRequest.setId(Long.valueOf(returnMsg.getId()));
            addressaRequest.setUser_id(returnMsg.getUser_id());
            addressaRequest.setProvince_id(returnMsg.getProvince_id());
            addressaRequest.setCity_id(returnMsg.getCity_id());
            addressaRequest.setArea_id(returnMsg.getArea_id());
            itemName.setRightText(returnMsg.getShip_to());
            itemPhone.setRightText(returnMsg.getPhone());
            itemCity.setRightText(returnMsg.getRegion_name());
            edtAddress.setText(returnMsg.getAddress());

            if (!returnMsg.is_default()){
                isDefault = false;
                imgSelect.setImageResource(R.drawable.nav_icon_default);
            }else{
                isDefault = true;
                imgSelect.setImageResource(R.drawable.nav_icon_selected);
            }

        }

        @Override
        public void fialed(String resCode, String message) {
            closeProgress();
        }
    };
}
