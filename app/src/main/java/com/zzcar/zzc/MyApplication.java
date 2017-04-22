package com.zzcar.zzc;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;


 /**
    *
    * 创建作者： 黄如辉
    * 创建时间： 2017/4/18 11:42
   **/

public class MyApplication extends Application {

    private static MyApplication mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //集成测试，正式试用时要设为false
//        MobclickAgent.setDebugMode(true);
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mInstance = null;
    }
}
