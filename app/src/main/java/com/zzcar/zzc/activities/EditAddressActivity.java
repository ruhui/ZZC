package com.zzcar.zzc.activities;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.constants.Constant;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.UploadFileWithoutLoding;
import com.zzcar.zzc.views.widget.ItemIconTextIcon;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
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

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        boolean isadd = getIntent().getBooleanExtra("addAddress", true);
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
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showProgress();
        if (data != null){
            if (requestCode == 20171){
                String nick = data.getStringExtra("value");
                itemName.setRightText(nick);
            }else if (requestCode == 20172){
                String phonenum = data.getStringExtra("value");
                itemPhone.setRightText(phonenum);
            }
        }
    }
}
