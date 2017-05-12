package com.zzcar.zzc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.networks.PosetSubscriber;
import com.zzcar.zzc.networks.UploadFileWithoutLoding;
import com.zzcar.zzc.networks.responses.MineMsgResponse;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import rx.Subscriber;

@EActivity(R.layout.activity_nick_change_acitivity)
public class NickChangeAcitivity extends AppCompatActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;
    @ViewById(R.id.edtNick)
    EditText edtNick;
    @ViewById(R.id.saveValue)
    TextView saveValue;

    private String value;

    @AfterViews
    void initView(){
        value = getIntent().getStringExtra("value");
        String titlebar = getIntent().getStringExtra("titleBar");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle(titlebar);
        edtNick.setText(value);

        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });



        saveValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick = edtNick.getText().toString();
                if (TextUtils.isEmpty(nick)){
                    ToastUtil.showToast("请输入内容");
                }else{
                    if (nick.equals(value)){
                        finish();
                    }else{
                        Intent intent = new Intent();
                        intent.putExtra("value", nick);
                        setResult(20171, intent);
                        finish();
                    }
                }
            }
        });

    }
}
