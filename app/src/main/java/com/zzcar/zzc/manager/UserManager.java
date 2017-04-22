package com.zzcar.zzc.manager;

import com.zzcar.zzc.models.AddressModel;
import com.zzcar.zzc.networks.ApiClient;
import com.zzcar.zzc.networks.ResponseParent;
import com.zzcar.zzc.networks.ZZCHeaders;
import com.zzcar.zzc.networks.requests.LoginRequest;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.utils.SecurePreferences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
    *
    * 创建作者： 黄如辉
    * 创建时间： 2017/4/10 11:57
   **/

public class UserManager {

    public static ConcurrentHashMap<String, Subscription> hashMap = new ConcurrentHashMap<>();

    /**
     * 添加到管理库中
     * @param tag
     * @param subscription
     */
    public static void add(String tag, Subscription subscription) {
        hashMap.put(tag, subscription);
    }

    /**
     * 取消某个请求
     * @param tag
     */
    public static void cancelTag(String tag) {
        if (hashMap.isEmpty()) {
            return;
        }
        if (hashMap.get(tag) == null) {
            return;
        }
        if (!hashMap.get(tag).isUnsubscribed()) {
            hashMap.get(tag).unsubscribe();
        }
    }

    /**
     * 取消某个请求并移除
     * @param tag
     */
    public static void cancelTagandRemove(String tag) {
        if (hashMap.isEmpty()) {
            return;
        }
        if (hashMap.get(tag) == null) {
            return;
        }
        if (!hashMap.get(tag).isUnsubscribed()) {
            hashMap.get(tag).unsubscribe();
            hashMap.remove(tag);
        }
    }

    /**
     * 取消所有请求
     */
    public static void cancelAll() {
        if (hashMap.isEmpty() || hashMap.size() == 0) {
            return;
        }
        Set<String> keys = hashMap.keySet();
        for (String apiKey : keys) {
            cancelTag(apiKey);
        }
        hashMap.clear();
    }


    /**
     * 众众车登录接口
     */
    public static void loginzzc(String teltphone, String password, Subscriber<ResponseParent<LoginResponse>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("loginzzc");
        LoginRequest loginResult = new LoginRequest(teltphone,password);
        ZZCHeaders zzcHeaders = new ZZCHeaders(loginResult);
        Subscription subscription = ApiClient.getApiService().login(loginResult, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("loginzzc", subscription);
    }

    /**
     * 获取用户信息
     */
    public static void getusermsg(String accesstoken, Subscriber<ResponseParent<LoginResponse>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getusermsg");
        Map<String,String> hashmap = new HashMap<>();
        hashmap.put("user_id", "");
        ZZCHeaders zzcHeaders = new ZZCHeaders(accesstoken, hashmap);
        Subscription subscription = ApiClient.getApiService().getusermsg("", zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getusermsg", subscription);
    }

    /**
     * 获取地址
     */
    public static void getaddress(String accesstoken, Subscriber<ResponseParent<List<AddressModel>>> subscriber){
        /* 防止多次点击 */
        cancelTagandRemove("getaddress");
        Map<String,String> hashmap = new HashMap<>();
        ZZCHeaders zzcHeaders = new ZZCHeaders(accesstoken, hashmap);
        Subscription subscription = ApiClient.getApiService().getaddress(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getaddress", subscription);
    }

    /**
     * 获取首页推荐车源gethomecarpush
     */
    public static void getHomeCarpush(Subscriber<ResponseParent<List<HomeCarPushResponse>>> subscriber){
        /* 防止多次点击 */
        cancelTagandRemove("getHomeCarpush");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String,String> hashmap = new HashMap<>();
        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().gethomecarpush(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getHomeCarpush", subscription);
    }
}
