package com.zzcar.zzc.manager;

import android.text.TextUtils;

import com.zzcar.zzc.models.AddressModel;
import com.zzcar.zzc.networks.ApiClient;
import com.zzcar.zzc.networks.ResponseParent;
import com.zzcar.zzc.networks.ZZCHeaders;
import com.zzcar.zzc.networks.requests.LoginRequest;
import com.zzcar.zzc.networks.requests.SearchRequest;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.utils.SecurePreferences;
import com.zzcar.zzc.utils.Tool;

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
     * 获取首页推荐车源
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

    /**
     * 获取首页车源数据
     */
    public static void getHomeCarFrom(String keyword, String sort, SearchRequest props, int page,
                                      Subscriber<ResponseParent<HomeCarPushResponse>> subscriber){
        /* 防止多次点击 */
        cancelTagandRemove("getHomeCarFrom");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String,String> hashmap = new HashMap<>();
        //名称/编号
        hashmap.put("keyword", keyword);
        //排序：1最新(默认)、2销量高、3价格高到低、4价格低到高、5按车龄最小，6按里程最少
        hashmap.put("sort", sort);
        //属性,属性值的组合.格式:{"key1":value1,"key2":value2,"color_ids":[1,2]};参数 如goods/cars?page=1&props={"city_id":350200,"color_ids":[1,2]}
        hashmap.put("props ", Tool.getGson(props));
        //页码
        hashmap.put("page", String.valueOf(page));
        //默认一页显示行数
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getcars(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getHomeCarFrom", subscription);
    }

    /**
     * 获取车源渠道
     */
    public static void getCarChannel(Subscriber<ResponseParent<List<CarChanelResponse>>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getCarChannel");
        Map<String,String> hashmap = new HashMap<>();
        ZZCHeaders zzcHeaders = new ZZCHeaders(hashmap);
        Subscription subscription = ApiClient.getApiService().getcarchannel(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getCarChannel", subscription);
    }

}
