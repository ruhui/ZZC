package com.zzcar.zzc.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.zzcar.zzc.R;
import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.fragments.base.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import cn.jpush.android.api.JPushInterface;

@EActivity(R.layout.activity_jupsh_receive)
public class JupshReceiveActivity extends BaseActivity {

    @AfterViews
    void initView(){
        Bundle bundle = getIntent().getExtras();
        String extData = bundle.getString(JPushInterface.EXTRA_EXTRA);
        //showFragment(MineMsgFragment_.builder().build());
    }

    private void showJupshFragment(BaseFragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(0, 0, 0, 0);
        transaction.add(R.id.container, fragment, fragment.getClass().getName());
//        fragment.setmLastStackName("" + System.currentTimeMillis() + hashCode());
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void finishActivity(boolean isfinish) {
        if (isfinish){
            finish();
        }
    }

    @Override
    public void onNetChange(int netMobile) {

    }
}
