package com.zzcar.zzc.networks;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.zzcar.zzc.interfaces.ResponseResultListener;
import com.zzcar.zzc.utils.ToastUtil;
import com.zzcar.zzc.utils.Tool;

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
                }else{
                    ToastUtil.showToast(httpResult.result_msg);
                    listener.fialed(httpResult.result_code, httpResult.result_msg);
                }
            }
        };
        return subscriber;
    }


    <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            Gson mGson = new Gson();
            mList.add(mGson.fromJson(elem, cls));
        }
        return mList;
    }
}
