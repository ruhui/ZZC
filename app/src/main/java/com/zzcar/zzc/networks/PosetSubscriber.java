package com.zzcar.zzc.networks;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.zzcar.zzc.interfaces.ActivityFinish;
import com.zzcar.zzc.interfaces.RefreshListener;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/8.
 */

public class PosetSubscriber<T> {

    public Subscriber getSubscriber(final ResponseResultListener<T> listener){
        Subscriber subscriber = new Subscriber<ResponseParent<T>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.fialed("",e.getMessage());
            }

            @Override
            public void onNext(ResponseParent<T> httpResult) {
                if (httpResult.result_code.equals("1000")){
                    //成功
                    listener.success(httpResult.data);
                }else if (httpResult.result_code.equals("401")){
                    //身份验证失败
                    EventBus.getDefault().post(new ActivityFinish(true));
                }else{
                    ToastUtil.showToast(httpResult.result_msg);
                    listener.fialed(httpResult.result_code, httpResult.result_msg);
                }
            }
        };
        return subscriber;
    }
}
