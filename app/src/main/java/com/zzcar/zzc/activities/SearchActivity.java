package com.zzcar.zzc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.views.widget.ItemIconTextIcon;
import com.zzcar.zzc.views.widget.NavBar2;
import com.zzcar.zzc.views.widget.NavBar2_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_search)
public class SearchActivity extends BaseActivity {

    @ViewById(R.id.mNavbar)
    NavBar2 mNavbar;

    @ViewById(R.id.itemIconTextIcon1)
    ItemIconTextIcon itemIconTextIcon1;
    @ViewById(R.id.itemIconTextIcon2)
    ItemIconTextIcon itemIconTextIcon2;
    @ViewById(R.id.itemIconTextIcon3)
    ItemIconTextIcon itemIconTextIcon3;
    @ViewById(R.id.itemIconTextIcon4)
    ItemIconTextIcon itemIconTextIcon4;
    @ViewById(R.id.itemIconTextIcon5)
    ItemIconTextIcon itemIconTextIcon5;
    @ViewById(R.id.itemIconTextIcon6)
    ItemIconTextIcon itemIconTextIcon6;
    @ViewById(R.id.itemIconTextIcon7)
    ItemIconTextIcon itemIconTextIcon7;

    @AfterViews
    void initView(){
        mNavbar.setLeftMenuIcon(R.drawable.nav_icon_lift_default);
        mNavbar.setMiddleTitle("高级筛选");
        mNavbar.setRightTxt("清空条件");
        mNavbar.setRightTxtColor(R.color.color_959595);

        itemIconTextIcon1.setTitle("所在地");itemIconTextIcon1.setRightText("福建厦门");
        itemIconTextIcon2.setTitle("品牌车系");itemIconTextIcon2.setRightText("2014宝马");
        itemIconTextIcon3.setTitle("价格");itemIconTextIcon3.setRightText("20-50万");
        itemIconTextIcon4.setTitle("颜色");itemIconTextIcon4.setRightText("红色");
        itemIconTextIcon5.setTitle("标现里程");itemIconTextIcon5.setRightText("一万公里");
        itemIconTextIcon6.setTitle("排放");itemIconTextIcon6.setRightText("国五");
        itemIconTextIcon7.setTitle("渠道");itemIconTextIcon7.setRightText("二手车行");

        mNavbar.setOnMenuClickListener(new NavBar2.OnMenuClickListener() {
            @Override
            public void onLeftMenuClick(View view) {
                super.onLeftMenuClick(view);
                finish();
            }

            @Override
            public void onRightMenuClick(View view) {
                super.onRightMenuClick(view);
            }
        });
    }

    @Override
    public void onNetChange(int netMobile) {

    }
}
