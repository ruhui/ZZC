package com.zzcar.zzc.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zzcar.zzc.activities.base.BaseActivity;
import com.zzcar.zzc.interfaces.NetEvevt;
import com.zzcar.zzc.utils.NetUtil;
import com.zzcar.zzc.utils.ToastUtil;

/**
 * Created by Administrator on 2017/2/22.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {

    private NetEvevt evevt_activity = BaseActivity.evevt;
//    private NetEvevt evevt_fragment = BaseFragment.evevt;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            ToastUtil.showToast(context, "网络不可以用");
        }

        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetUtil.getNetWorkState(context);
            // 接口回调传过去状态的类型

            if (evevt_activity != null){
                evevt_activity.onNetChange(netWorkState);
            }
            evevt_activity.onNetChange(netWorkState);
//            evevt_fragment.onNetChange(netWorkState);
        }
    }
}