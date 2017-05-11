package com.zzcar.zzc.manager;

import android.text.TextUtils;

import com.zzcar.zzc.models.AddCarFrom;
import com.zzcar.zzc.models.AddCarMiddleModle;
import com.zzcar.zzc.models.AddressModel;
import com.zzcar.zzc.networks.ApiClient;
import com.zzcar.zzc.networks.ResponseParent;
import com.zzcar.zzc.networks.ZZCHeaders;
import com.zzcar.zzc.networks.requests.LoginRequest;
import com.zzcar.zzc.networks.requests.ParametersRequest;
import com.zzcar.zzc.networks.requests.ProduceIdResquest;
import com.zzcar.zzc.networks.requests.SaveCommentRequest;
import com.zzcar.zzc.networks.requests.SearchRequest;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.networks.responses.CarSeriesResponse;
import com.zzcar.zzc.networks.responses.CarTypeResponse;
import com.zzcar.zzc.networks.responses.CheckSuccessResponse;
import com.zzcar.zzc.networks.responses.CityResponse;
import com.zzcar.zzc.networks.responses.ColorResponse;
import com.zzcar.zzc.networks.responses.CommentResponse;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.networks.responses.MineMsgResponse;
import com.zzcar.zzc.networks.responses.MybillResponse;
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
    public static void getHomeCarFrom(String keyword, SearchRequest props, int page,
                                      Subscriber<ResponseParent<HomeCarPushResponse>> subscriber){
        /* 防止多次点击 */
        cancelTagandRemove("getHomeCarFrom");
        ParametersRequest parameters = new ParametersRequest();
        parameters.setChannel(props.getChannel());
        parameters.setEmission_ids(props.getEmission_ids());
        parameters.setColor_ids(props.getColor_ids());
        parameters.setBland_id(props.getBland_id());
        parameters.setSeries_id(props.getSeries_id());
        parameters.setYear_id(props.getYear_id());
        parameters.setSpec_id(props.getSpec_id());
        parameters.setProvince_id(props.getProvince_id());
        parameters.setCity_id(props.getCity_id());
        parameters.setPrice_type(props.getPrice_type());
        parameters.setMin_price(props.getMin_price());
        parameters.setMax_price(props.getMax_price());
        parameters.setMileage(props.getMileage());
        parameters.setMin_mileage(props.getMin_mileage());
        parameters.setMax_mileage(props.getMax_mileage());

        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String,String> hashmap = new HashMap<>();
        //名称/编号
        hashmap.put("keyword", keyword);
        //排序：1最新(默认)、2销量高、3价格高到低、4价格低到高、5按车龄最小，6按里程最少
        hashmap.put("sort", props.getSort());
        //属性,属性值的组合.格式:{"key1":value1,"key2":value2,"color_ids":[1,2]};参数 如goods/cars?page=1&props={"city_id":350200,"color_ids":[1,2]}
        hashmap.put("props", Tool.getGson(parameters));
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

    /**
     * 获取价格区间
     */
    public static void getPriceBwtween(Subscriber<ResponseParent<List<CarChanelResponse>>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getPriceBwtween");
        Map<String,String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(hashmap);
        Subscription subscription = ApiClient.getApiService().getpricebetween(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getPriceBwtween", subscription);
    }

    /**
     * 获取品牌
     */
    public static void getBrandList(Subscriber<ResponseParent<List<BrandListResponse>>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getBrandList");
        Map<String,String> hashmap = new HashMap<>();
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getbarnd(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getBrandList", subscription);
    }

    /**
     * 获取车系
     */
    public static void getCarseries(int brandid, Subscriber<ResponseParent<List<CarSeriesResponse>>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getCarseries");
        Map<String,String> hashmap = new HashMap<>();
        hashmap.put("id", brandid+"");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getcarseries(brandid, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getCarseries", subscription);
    }

    /**
     * 获取车型
     */
    public static void getCarTYpe(long carseriasid, Subscriber<ResponseParent<List<CarTypeResponse>>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getCarTYpe");
        Map<String,String> hashmap = new HashMap<>();
        hashmap.put("id", carseriasid+"");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getspec(carseriasid, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getCarTYpe", subscription);
    }

    /**
     * 排放
     */
    public static void getEmission(Subscriber<ResponseParent<List<CarChanelResponse>>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getEmission");
        Map<String,String> hashmap = new HashMap<>();
        ZZCHeaders zzcHeaders = new ZZCHeaders(hashmap);
        Subscription subscription = ApiClient.getApiService().getemission(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getEmission", subscription);
    }

    /**
     * 里程
     */
    public static void getMileage(Subscriber<ResponseParent<List<CarChanelResponse>>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getMileage");
        Map<String,String> hashmap = new HashMap<>();
        ZZCHeaders zzcHeaders = new ZZCHeaders(hashmap);
        Subscription subscription = ApiClient.getApiService().getmileage(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getMileage", subscription);
    }

    /**
     * 城市
     */
    public static void getProvincecity(Subscriber<ResponseParent<List<CityResponse>>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getProvincecity");
        Map<String,String> hashmap = new HashMap<>();
        ZZCHeaders zzcHeaders = new ZZCHeaders(hashmap);
        Subscription subscription = ApiClient.getApiService().getprovincecity(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getProvincecity", subscription);
    }

    /**
     * 颜色
     */
    public static void getColor(Subscriber<ResponseParent<List<ColorResponse>>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getColor");
        Map<String,String> hashmap = new HashMap<>();
        ZZCHeaders zzcHeaders = new ZZCHeaders(hashmap);
        Subscription subscription = ApiClient.getApiService().getcolor(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getColor", subscription);
    }

    /**
     * 用途
     */
    public static void getUserType(Subscriber<ResponseParent<List<CarChanelResponse>>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getUserType");
        Map<String,String> hashmap = new HashMap<>();
        ZZCHeaders zzcHeaders = new ZZCHeaders(hashmap);
        Subscription subscription = ApiClient.getApiService().getusertype(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getUserType", subscription);
    }

    /**
     * 发布修改车源
     */
    public static void savecar(AddCarMiddleModle middleModle, Subscriber<ResponseParent<Boolean>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("savecar");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        AddCarFrom addCarFrom = middleModle.getAddCarFrom(middleModle);
        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, addCarFrom);
        Subscription subscription = ApiClient.getApiService().savecar(addCarFrom, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("savecar", subscription);
    }

    /**
     * 车源详情
     * @param id
     * @param subscriber
     */
    public static void getCarDetail(int id, Subscriber<ResponseParent<List<CarChanelResponse>>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getCarDetail");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", String.valueOf(id));
        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getcardetail(id, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getCarDetail", subscription);
    }


    /**
     * 获取登录的用户信息
     * @param subscriber
     */
    public static void getUserMsg(Subscriber<ResponseParent<MineMsgResponse>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getUserMsg");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getusermsg(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getUserMsg", subscription);
    }

    /**
     * 获取我的财务
     * @param subscriber
     */
    public static void getMyBill(Subscriber<ResponseParent<MybillResponse>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getMyBill");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getuserbill(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getMyBill", subscription);
    }

    /**
     * 获取评论列表
     */
    public static void getCommentList(int product_id, int page, Subscriber<ResponseParent<CommentResponse>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getCommentList");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("product_id", String.valueOf(product_id));
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getcomments(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getCommentList", subscription);
    }

    /**
     * 保存评价
     * @param product_id 商品id
     * @param at_id
     * @param content
     * @param image_path
     * @param subscriber
     */
    public static void saveComment(int product_id, String at_id, String content, List<String> image_path, Subscriber<ResponseParent<Boolean>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("saveComment");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        SaveCommentRequest saveCommentRequest = new SaveCommentRequest(product_id, at_id, content, image_path);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, saveCommentRequest);
        Subscription subscription = ApiClient.getApiService().savecomment(saveCommentRequest, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("saveComment", subscription);
    }


    /**
     * 取消或者添加收藏
     * @param product_id
     * @param subscriber
     */
    public static void savefavorte(int product_id, Subscriber<ResponseParent<Integer>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        ProduceIdResquest saveCommentRequest = new ProduceIdResquest(product_id);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, saveCommentRequest);
        Subscription subscription = ApiClient.getApiService().savefavorte(saveCommentRequest, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
