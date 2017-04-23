package com.zzcar.zzc.manager;

import android.text.TextUtils;

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
    public static void getHomeCarFrom(String keyword,String saleStatus,String sort,String category_id,String sell_out, String select_sale,String props,String page,
                                      Subscriber<ResponseParent<List<HomeCarPushResponse>>> subscriber){
        /* 防止多次点击 */
        cancelTagandRemove("getHomeCarFrom");

        if (TextUtils.isEmpty(saleStatus)){
            saleStatus = "0";
        }
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        String user_id = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String,String> hashmap = new HashMap<>();
        //名称/编号
        hashmap.put("keyword", "");
        //商品状态：0销售中（默认），1下架中，2审核不通过，3违规下架,4删除的
        hashmap.put("saleStatus", "0");
        //排序：1最新(默认)、2销量高、3价格高到低、4价格低到高、5按车龄最小，6按里程最少
        hashmap.put("sort", sort);
        //商品分类
        hashmap.put("category_id", category_id);
        //用户id，用户个人主页，要传值
        hashmap.put("user_id", user_id);
        // 是否销售完
        hashmap.put("sell_out", sell_out);
        //  是否查询促销
        hashmap.put("select_sale", select_sale);
        //属性,属性值的组合.格式:{"key1":value1,"key2":value2,"color_ids":[1,2]};参数 如goods/cars?page=1&props={"city_id":350200,"color_ids":[1,2]}
        hashmap.put("props ", props );
        //  是否查询促销
        hashmap.put("page", page);
        //  是否查询促销
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getcars(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getHomeCarFrom", subscription);
    }
}
