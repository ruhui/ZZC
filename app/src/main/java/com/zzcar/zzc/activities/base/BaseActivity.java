package com.zzcar.zzc.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.umeng.analytics.MobclickAgent;
import com.zzcar.zzc.R;
import com.zzcar.zzc.fragments.base.BaseFragment;
import com.zzcar.zzc.interfaces.ActivityFinish;
import com.zzcar.zzc.interfaces.NetEvevt;
import com.zzcar.zzc.manager.UserManager;
import com.zzcar.zzc.utils.NetUtil;
import com.zzcar.zzc.views.widget.ProgressDialog;
import com.zzcar.zzc.views.widget.ProgressDialog_;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
    * 基类
    * @author ruhui
    * @time 2017/2/21 18:05
    *   mProgressDlg进度条     mErrorPage错误界面
   **/

public abstract class BaseActivity extends AppCompatActivity implements NetEvevt {


    public static NetEvevt evevt;
    /**
     * 网络类型
     */
    private int netMobile = -1;
    private static final int REQUEST_CODE = 0; // 请求码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        evevt = this;
        inspectNet();
        EventBus.getDefault().register(this);
    }

    /**
     * 初始化时判断有没有网络
     */
    public boolean inspectNet() {
        this.netMobile = NetUtil.getNetWorkState(BaseActivity.this);
        return isNetConnect();
    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == 1) {
            return true;
        } else if (netMobile == 0) {
            return true;
        } else if (netMobile == -1) {
            return false;

        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        UserManager.cancelAll();
    }

    private boolean isLoadingShown;
    private ProgressDialog mProgressDlg;

    boolean displayLoadingPage(int layoutId, View view) {
        ViewGroup layout = null;
        if (getView() != null) {
            int index = -1;
            if (layoutId != View.NO_ID) {
                layout = (ViewGroup) getView().findViewById(layoutId);
                //TODO
                if (layout != null && layout instanceof View && !(layout instanceof ViewGroup)) {
                    layout = (ViewGroup) layout.getParent();
                    if (layout instanceof LinearLayout) {
                        index = 0;
                    }
                }
            } else {
                layout = ((ViewGroup) this.getView());
            }
            if (layout != null) {
                layout.addView(view, index, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
        return layout != null;
    }

    public BaseActivity getActivity() {
        return this;
    }

    public View getView() {
        return getWindow().getDecorView();
    }

    private boolean displayView(View view, int layoutId) {
        ViewGroup layout = null;
        if (view != null) {
            int index = -1;
            if (layoutId != View.NO_ID) {
                layout = (ViewGroup) findViewById(layoutId);
            } else {
                layout = (ViewGroup) getWindow().getDecorView();
            }
            if (layout != null) {
                if (layout instanceof LinearLayout) {
                    index = 0;
                }
                layout.addView(view, index, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
        return layout != null;
    }

    ProgressDialog mProgressDialog;

    protected ProgressDialog showProgress() {
        return showProgress((ViewGroup) getView());
    }

    protected ProgressDialog showProgress(ViewGroup viewParent) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog_.build(getActivity());
            mProgressDialog.addToView(viewParent);
        }
        mProgressDialog.show();
        return mProgressDialog;
    }

    protected void closeProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
    }


    public void showProgress(@StringRes int msgRes) {
        ProgressDialog dialog = showProgress();
        dialog.setTipMsg(msgRes);
    }

    public void showProgress(String msg) {
        ProgressDialog dialog = showProgress();
        dialog.setTipMsg(msg);
    }

    public boolean progressViewIsShowing() {
        if (mProgressDlg != null) {
            return mProgressDlg.isShowing();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeProgress();
    }

    public void showFragment(BaseFragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(0, 0, 0, 0);
        transaction.add(R.id.container, fragment, fragment.getClass().getName());
        fragment.setmLastStackName("" + System.currentTimeMillis() + hashCode());
        transaction.addToBackStack(fragment.getmLastStackName());
        transaction.commitAllowingStateLoss();
    }

    public int getNetMobile() {
        return netMobile;
    }


    @Subscribe
    public void finishPage(ActivityFinish activityFinish){
        if (activityFinish.isfinish){
            finish();
        }
    }
}
