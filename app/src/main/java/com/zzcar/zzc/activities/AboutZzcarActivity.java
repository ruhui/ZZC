package com.zzcar.zzc.activities;

import android.view.View;
import android.widget.TextView;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.utils.Tool;
import com.zzcar.zzc.views.widget.NavBar2;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * 创建时间： 2017/6/29.
 * 作者：黄如辉
 * 功能描述：
 */
@EActivity(R.layout.activity_about_zzcar)
public class AboutZzcarActivity extends BaseActivity {

    @ViewById(R.id.textView223)
    TextView txtVersion;
    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    @Override
    public void onNetChange(int netMobile) {

    }

    @AfterViews
    void initView(){
        mNavbar.setMiddleTitle("关于众众车");
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }
        });
        String version = Tool.getAppVersion(AboutZzcarActivity.this, false);
        txtVersion.setText(version);
    }
}
