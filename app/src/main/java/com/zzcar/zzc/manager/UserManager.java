package com.zzcar.zzc.manager;

import android.text.TextUtils;

import com.zzcar.zzc.models.AddCarFrom;
import com.zzcar.zzc.models.AddCarMiddleModle;
import com.zzcar.zzc.models.AddressModel;
import com.zzcar.zzc.models.BlandModle;
import com.zzcar.zzc.models.CarFromModel;
import com.zzcar.zzc.models.CheckoutcartModel;
import com.zzcar.zzc.models.DemendPropsModel;
import com.zzcar.zzc.models.EnumSendUserType;
import com.zzcar.zzc.models.PayOrderModel;
import com.zzcar.zzc.models.SaveSupplyModel;
import com.zzcar.zzc.models.SavedemandModel;
import com.zzcar.zzc.models.SinglecarModel;
import com.zzcar.zzc.models.SupplyPropsMiddleModel;
import com.zzcar.zzc.models.SupplyPropsModel;
import com.zzcar.zzc.networks.ApiClient;
import com.zzcar.zzc.networks.ResponseParent;
import com.zzcar.zzc.networks.ZZCHeaders;
import com.zzcar.zzc.networks.requests.AddEmployee;
import com.zzcar.zzc.networks.requests.AddMsgRequest;
import com.zzcar.zzc.networks.requests.ApplyDepositRequest;
import com.zzcar.zzc.networks.requests.ApplyFriendRequest;
import com.zzcar.zzc.networks.requests.BuyIntegraRequest;
import com.zzcar.zzc.networks.requests.BuysecurityRequest;
import com.zzcar.zzc.networks.requests.CheckAddressRequest;
import com.zzcar.zzc.networks.requests.CheckoutcartRequest;
import com.zzcar.zzc.networks.requests.DeleteAddressRequest;
import com.zzcar.zzc.networks.requests.DeleteFriendRequest;
import com.zzcar.zzc.networks.requests.EditEmployPhone;
import com.zzcar.zzc.networks.requests.EditEmployeeNick;
import com.zzcar.zzc.networks.requests.EmptyRequest;
import com.zzcar.zzc.networks.requests.FilterRequest;
import com.zzcar.zzc.networks.requests.ForgetPwdResquest;
import com.zzcar.zzc.networks.requests.GroupIdRequest;
import com.zzcar.zzc.networks.requests.IdRequest;
import com.zzcar.zzc.networks.requests.InfoidRequest;
import com.zzcar.zzc.networks.requests.LoginRequest;
import com.zzcar.zzc.networks.requests.NickRequest;
import com.zzcar.zzc.networks.requests.OrderNoRequest;
import com.zzcar.zzc.networks.requests.OrderidResquest;
import com.zzcar.zzc.networks.requests.ParametersRequest;
import com.zzcar.zzc.networks.requests.PhotoRequest;
import com.zzcar.zzc.networks.requests.ProduceIdResquest;
import com.zzcar.zzc.networks.requests.RefreshLoginRequest;
import com.zzcar.zzc.networks.requests.SaveAddressaRequest;
import com.zzcar.zzc.networks.requests.SaveCommentRequest;
import com.zzcar.zzc.networks.requests.SaveInfoComment;
import com.zzcar.zzc.networks.requests.SavedemandRequest;
import com.zzcar.zzc.networks.requests.SearchRequest;
import com.zzcar.zzc.networks.requests.SendRegsmsRequest;
import com.zzcar.zzc.networks.requests.ShippingTypeRequest;
import com.zzcar.zzc.networks.responses.AcountOrderResponse;
import com.zzcar.zzc.networks.responses.AddressDetail;
import com.zzcar.zzc.networks.responses.AddressResponse;
import com.zzcar.zzc.networks.responses.ApplyFriendResponse;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.networks.responses.BulletinResponse;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.networks.responses.CarSeriesResponse;
import com.zzcar.zzc.networks.responses.CarTypeResponse;
import com.zzcar.zzc.networks.responses.CheckoutcartResponse;
import com.zzcar.zzc.networks.responses.CityResponse;
import com.zzcar.zzc.networks.responses.ColorResponse;
import com.zzcar.zzc.networks.responses.CommentResponse;
import com.zzcar.zzc.networks.responses.DemendDetailResponse;
import com.zzcar.zzc.networks.responses.DepositResponse;
import com.zzcar.zzc.networks.responses.EmptyResponse;
import com.zzcar.zzc.networks.responses.FridendListResponse;
import com.zzcar.zzc.networks.responses.GroupMenberResponse;
import com.zzcar.zzc.networks.responses.HomeAdverResponse;
import com.zzcar.zzc.networks.responses.HomeCarGetResponse;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;
import com.zzcar.zzc.networks.responses.HomeLivemsgResponse;
import com.zzcar.zzc.networks.responses.InforCommentResponse;
import com.zzcar.zzc.networks.responses.IntegralDetailResponse;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.networks.responses.MessageListResponse;
import com.zzcar.zzc.networks.responses.MineMsgResponse;
import com.zzcar.zzc.networks.responses.MyEmployeeResponse;
import com.zzcar.zzc.networks.responses.MybillResponse;
import com.zzcar.zzc.networks.responses.MydemandResponse;
import com.zzcar.zzc.networks.responses.MyfavcarResponse;
import com.zzcar.zzc.networks.responses.MysubscribeResponse;
import com.zzcar.zzc.networks.responses.MysupplyResponse;
import com.zzcar.zzc.networks.responses.OrderDetailResponse;
import com.zzcar.zzc.networks.responses.OrderListResponse;
import com.zzcar.zzc.networks.responses.PublishintegralResponse;
import com.zzcar.zzc.networks.responses.ReceiptDetailResponse;
import com.zzcar.zzc.networks.responses.RefundOrderResponse;
import com.zzcar.zzc.networks.responses.SavedemandResponse;
import com.zzcar.zzc.networks.responses.ShouzhiDetailResponse;
import com.zzcar.zzc.networks.responses.SingleSupplyResponse;
import com.zzcar.zzc.networks.responses.SupplyDetailResponse;
import com.zzcar.zzc.networks.responses.SupplyResponse;
import com.zzcar.zzc.networks.responses.SystemMsgResponse;
import com.zzcar.zzc.networks.responses.TransferDetailResponse;
import com.zzcar.zzc.networks.responses.UserMessageResponse;
import com.zzcar.zzc.networks.responses.ValueTextResponse;
import com.zzcar.zzc.networks.responses.VerifiedResponse;
import com.zzcar.zzc.networks.responses.VersionResponse;
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
     * 获取用户的车源数据
     */
    public static void getUserCarFrom(String userid, int page, String sellout,
                                      Subscriber<ResponseParent<HomeCarPushResponse>> subscriber){
        /* 防止多次点击 */

        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String,String> hashmap = new HashMap<>();
        hashmap.put("user_id", userid);
        hashmap.put("sell_out", sellout);
        //页码
        hashmap.put("page", String.valueOf(page));
        //默认一页显示行数
        hashmap.put("size", "10");


        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getcars(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
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
     * 获取县
     */
    public static void getRegions(Subscriber<ResponseParent<List<CityResponse>>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getRegions");
        Map<String,String> hashmap = new HashMap<>();
        ZZCHeaders zzcHeaders = new ZZCHeaders(hashmap);
        Subscription subscription = ApiClient.getApiService().getregions(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getRegions", subscription);
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
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        AddCarFrom addCarFrom = middleModle.getAddCarFrom(middleModle);
        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, addCarFrom);
       ApiClient.getApiService().savecar(addCarFrom, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
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
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getusermsg(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取我的财务
     * @param subscriber
     */
    public static void getMyBill(Subscriber<ResponseParent<MybillResponse>> subscriber){
         /* 防止多次点击 */
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getuserbill(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
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
        ApiClient.getApiService().savefavorte(saveCommentRequest, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 上传图片
     * @param photo
     * @param subscriber
     */
    public static void savePhoto(String photo, Subscriber<ResponseParent<Boolean>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        PhotoRequest photoClass = new PhotoRequest(photo);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, photoClass);
         ApiClient.getApiService().photo(photoClass, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 修改昵称
     * @param nick
     * @param subscriber
     */
    public static void saveNick(String nick, Subscriber<ResponseParent<Boolean>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        NickRequest nickRequest = new NickRequest(nick);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, nickRequest);
        ApiClient.getApiService().savenick(nickRequest, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
     * 获取实名认证
     */
    public static void getVerified(Subscriber<ResponseParent<VerifiedResponse>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getVerified");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getverified(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getVerified", subscription);
    }


    /**
     * 获取验证码
     * getregsms
     */
    public static void getRegsms(String mobile, EnumSendUserType  userType, Subscriber<ResponseParent<VerifiedResponse>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getRegsms");
        SendRegsmsRequest sandCode = new SendRegsmsRequest(mobile, userType);

        ZZCHeaders zzcHeaders = new ZZCHeaders( sandCode);
        Subscription subscription = ApiClient.getApiService().getregsms(sandCode, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getRegsms", subscription);
    }

    /**
     * 车行实名认证
     * @param verifiedResponse
     * @param subscriber
     */
    public static void saveVerified(VerifiedResponse verifiedResponse, Subscriber<ResponseParent<VerifiedResponse>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("saveVerified");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, verifiedResponse);
        Subscription subscription = ApiClient.getApiService().saveverified(verifiedResponse, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("saveVerified", subscription);
    }

    /**
     * 获取积分详情
     * @param page
     * @param subscriber
     */
    public static void getIntegraldetail(int page, Subscriber<ResponseParent<IntegralDetailResponse>> subscriber){
         /* 防止多次点击 */
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getintegraldetail(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取提现方式
     * @param subscriber
     */
    public static void getdeposit(Subscriber<ResponseParent<DepositResponse>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getdeposit");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getdeposit(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getdeposit", subscription);
    }


    /**
     * 获取可提现银行
     * @param subscriber
     */
    public static void getBank(Subscriber<ResponseParent<List<ValueTextResponse>>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getBank");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(hashmap);
        Subscription subscription = ApiClient.getApiService().getbank(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getBank", subscription);
    }

    /**
     * 保存提现账号
     * @param bankcode
     * @param bankname
     * @param bankcard
     * @param accountname
     * @param code
     * @param subscriber
     */
    public static void saveDeposit(String bankcode,String bankname, String bankcard, String accountname, String code,
                               Subscriber<ResponseParent<Boolean>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("saveDeposit");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        DepositResponse response = new DepositResponse(bankcode, bankname, bankcard, accountname, 2, code);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, response);
        Subscription subscription = ApiClient.getApiService().savedeposit(response, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("saveDeposit", subscription);
    }

    /**
     * 申请提现
     * @param amount
     * @param settle_type 结算类型：oneday,fast
     * @param type 提现类型：1收款，2交易
     * @param subscriber
     */
    public static void applyDeposit(double amount, String settle_type, int type, Subscriber<ResponseParent<Boolean>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("applyDeposit");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        ApplyDepositRequest response = new ApplyDepositRequest(amount, settle_type, type);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, response);
        Subscription subscription = ApiClient.getApiService().applydeposit(response, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("applyDeposit", subscription);
    }

    /**
     * 收支明细
     * @param page
     * @param subscriber
     */
    public static void getBills(int page, Subscriber<ResponseParent<ShouzhiDetailResponse>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getCommentList");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getbills(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getCommentList", subscription);
    }

    /**
     * 修改密码
     * @param mobile
     * @param password
     * @param re_password
     * @param code
     * @param subscriber
     */
    public static void forgetPassword(String mobile, String password, String re_password, String code, Subscriber<Boolean> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("forgetPassword");
        ForgetPwdResquest request = new ForgetPwdResquest(mobile, password, re_password, code);

        ZZCHeaders zzcHeaders = new ZZCHeaders( request);
        Subscription subscription = ApiClient.getApiService().forgetpassword(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("forgetPassword", subscription);
    }


    /**
     * 注册
     * @param mobile
     * @param password
     * @param re_password
     * @param code
     * @param subscriber
     */
    public static void regiestUser(String mobile, String password, String re_password, String code,String nick, Subscriber<LoginResponse> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("regiestUser");
        ForgetPwdResquest request = new ForgetPwdResquest(mobile, password, re_password, code, nick);

        ZZCHeaders zzcHeaders = new ZZCHeaders( request);
        Subscription subscription = ApiClient.getApiService().register(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("regiestUser", subscription);
    }

    /**
     * 购买积分
     * @param amount
     * @param pay_code
     * @param subscriber
     */
    public static void buyIntegral(String amount, String pay_code, Subscriber<Boolean> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("buyIntegral");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        BuyIntegraRequest request = new BuyIntegraRequest(amount, pay_code);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, request);
        Subscription subscription = ApiClient.getApiService().buyintegral(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("buyIntegral", subscription);
    }

    /**
     * 退款详情
     * @param id
     * @param subscriber
     */
    public static void getRefundorder(int id, Subscriber<ResponseParent<RefundOrderResponse>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getRefundorder");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", String.valueOf(id));

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getrefundorder(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getRefundorder", subscription);
    }

    /**
     * 我的收藏
     * @param page
     * @param subscriber
     */
    public static void getMyfavcar(int page, Subscriber<ResponseParent<MyfavcarResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getmyfavcar(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 我的车源
     * @param page
     * @param subscriber
     */
    public static void getMycar(int page, String tag, Subscriber<ResponseParent<MyfavcarResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, Object> hashmap = new HashMap<>();
        if (tag.equals("0")){
            //在售
            hashmap.put("sell_out", "false");
        }else if (tag.equals("1")){
            //已售
            hashmap.put("sell_out", "true");
        }else if(tag.equals("2")){
            //未上架
            hashmap.put("saleStatus", "1");
        }
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getmycar(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 上下架
     * @param product_id
     * @param subscriber
     */
    public static void updown(int product_id, Subscriber<Boolean> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        ProduceIdResquest request = new ProduceIdResquest(product_id);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, request);
        ApiClient.getApiService().updown(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 编辑车源时，获取车源信息
     */
    public static void getSingleCar(String id,  Subscriber<ResponseParent<SinglecarModel>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", id);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getcar(id, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*获取好友列表*/
    public static void getFriendList(String name,  Subscriber<ResponseParent<List<FridendListResponse>>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("name", name);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getfriendlist(name, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /*获取消息列表*/
    public static void getMssageList(Subscriber<ResponseParent<List<MessageListResponse>>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getmessagelist(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 请求加为好友或者关注
     * @param friendid 朋友id
     * @param type 1朋友，2关注
     * @param msg
     * @param subscriber
     */
    public static void applyFriend(int friendid, int type,String msg, Subscriber<Boolean> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        ApplyFriendRequest request = new ApplyFriendRequest(friendid, type, msg);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, request);
        ApiClient.getApiService().applyfriend(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取申请添加的好友列表
     * @param objectid
     * @param page
     * @param subscriber
     */
    public static void getApplyFriend(String objectid, int page, Subscriber<ResponseParent<ApplyFriendResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        if (!TextUtils.isEmpty(objectid)){
            hashmap.put("object_id", String.valueOf(objectid));
        }
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size", "20");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getapplyfriend(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 加为好友或者关注
     * @param friendid 朋友id
     * @param type 1朋友，2关注
     * @param subscriber
     */
    public static void addFriend(int friendid, int type, Subscriber<Boolean> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        ApplyFriendRequest request = new ApplyFriendRequest(friendid, type, "");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, request);
        ApiClient.getApiService().addfriend(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取用户信息
     * @param userid
     * @param subscriber
     */
    public static void getUserMessage(int userid, Subscriber<ResponseParent<UserMessageResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("user_id", userid+"");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getUserMessage(userid, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 刷新数据
     * @param subscriber
     */
    public static void refreshLogin(Subscriber<LoginResponse> subscriber){
        String accessToken = SecurePreferences.getInstance().getString("Authorization", "");
        RefreshLoginRequest request = new RefreshLoginRequest(accessToken);

        ZZCHeaders zzcHeaders = new ZZCHeaders(request);
        ApiClient.getApiService().refreshlogin(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 聊天保存数据
     * @param toid
     * @param content
     * @param imgpath
     * @param carFromModel
     * @param subscriber
     */
    public static void addChatMessage(String toid, String content, String imgpath, CarFromModel carFromModel, Subscriber<Boolean> subscriber){
        String accessToken = SecurePreferences.getInstance().getString("Authorization", "");
        AddMsgRequest request = new AddMsgRequest(toid, content, imgpath, carFromModel);

        ZZCHeaders zzcHeaders = new ZZCHeaders(accessToken, request);
        ApiClient.getApiService().addchatmessage(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取首页图片
     * @param subscriber
     */
    public static void getHomeAd(Subscriber<ResponseParent<List<HomeAdverResponse>>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getHomeAd(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 首页获取实况
     * @param page
     * @param subscriber
     */
    public static void getHomeLivemsg(int page, Subscriber<ResponseParent<HomeLivemsgResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size", "20");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getHomeLivemsg(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 首页车源
     * @param subscriber
     */
    public static void getCarpush(Subscriber<ResponseParent<List<HomeCarPushResponse>>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getCarpush(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取订阅列表
     * @param sort
     * @param channel
     * @param page
     * @param subscriber
     */
    public static void getsSubcars(String sort, String channel, int page, Subscriber<ResponseParent<HomeCarGetResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("sort", sort);
        hashmap.put("channel", channel);
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getsSubcars(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取订阅信息
     * @param subscriber
     */
    public static void getMysubscribe(Subscriber<ResponseParent<MysubscribeResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getmysubscribe(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 保存订阅
     * @param subscriber
     */
    public static void saveSubscribe(MysubscribeResponse request, Subscriber<Boolean> subscriber){
        String accessToken = SecurePreferences.getInstance().getString("Authorization", "");

        ZZCHeaders zzcHeaders = new ZZCHeaders(accessToken, request);
        ApiClient.getApiService().savesubscribe(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 支付交易担保金
     * @param subscriber
     */
    public static void buySecurity(String pay_code, Subscriber<String> subscriber){
        String accessToken = SecurePreferences.getInstance().getString("Authorization", "");
        BuysecurityRequest request = new BuysecurityRequest(pay_code);

        ZZCHeaders zzcHeaders = new ZZCHeaders(accessToken, request);
        ApiClient.getApiService().buysecurity(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 退交易担保金
     * @param subscriber
     */
    public static void refundSecurity( Subscriber<Boolean> subscriber){
        String accessToken = SecurePreferences.getInstance().getString("Authorization", "");
        EmptyRequest request = new EmptyRequest();

        ZZCHeaders zzcHeaders = new ZZCHeaders(accessToken, request);
        ApiClient.getApiService().refundsecurity(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 直接购买，交易使用,确认订单
     * @param items
     * @param shipping_type
     * @param subscriber
     */
    public static void getSureorder(List<CheckoutcartModel> items, int shipping_type, Subscriber<CheckoutcartResponse> subscriber){
        String accessToken = SecurePreferences.getInstance().getString("Authorization", "");
        CheckoutcartRequest request = new CheckoutcartRequest(items, shipping_type);

        ZZCHeaders zzcHeaders = new ZZCHeaders(accessToken, request);
        ApiClient.getApiService().getSureorder(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 支付订单
     * @param order_no
     * @param pay_code
     * @param subscriber
     */
    public static void payOrder(String order_no, String pay_code, Subscriber<String> subscriber){
        String accessToken = SecurePreferences.getInstance().getString("Authorization", "");
        PayOrderModel request = new PayOrderModel(order_no, pay_code);

        ZZCHeaders zzcHeaders = new ZZCHeaders(accessToken, request);
        ApiClient.getApiService().payorder(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /*获取我买到的列表*/
    public static void buyCarorder(String order_status,int page, Subscriber<ResponseParent<OrderListResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("order_status", order_status);
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size", "10");


        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().buycarorder(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 待支付生成订单
     * @param order_no
     * @param subscriber
     */
    public static void payWairorder(String order_no, Subscriber<CheckoutcartResponse> subscriber){
        String accessToken = SecurePreferences.getInstance().getString("Authorization", "");
        OrderNoRequest request = new OrderNoRequest(order_no);

        ZZCHeaders zzcHeaders = new ZZCHeaders(accessToken, request);
        ApiClient.getApiService().payWairorder(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 删除订单
     * @param orderid
     * @param subscriber
     */
    public static void cancelOrder(int orderid, Subscriber<Integer> subscriber){
        String accessToken = SecurePreferences.getInstance().getString("Authorization", "");
        OrderidResquest request = new OrderidResquest(String.valueOf(orderid));

        ZZCHeaders zzcHeaders = new ZZCHeaders(accessToken, request);
        ApiClient.getApiService().cancelorder(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     *  买家确认
     * @param orderid
     * @param subscriber
     */
    public static void carbuyerConfirm(int orderid, Subscriber<Integer> subscriber){
        String accessToken = SecurePreferences.getInstance().getString("Authorization", "");
        OrderidResquest request = new OrderidResquest(String.valueOf(orderid));

        ZZCHeaders zzcHeaders = new ZZCHeaders(accessToken, request);
        ApiClient.getApiService().carbuyerconfirm(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 我卖出的
     * @param order_status
     * @param page
     * @param subscriber
     */
    public static void sellCarorder(String order_status,int page, Subscriber<ResponseParent<OrderListResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("order_status", order_status);
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().sellcarorder(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 卖家确认
     * @param orderid
     * @param subscriber
     */
    public static void carsellerconfirm(int orderid, Subscriber<Integer> subscriber){
        String accessToken = SecurePreferences.getInstance().getString("Authorization", "");
        OrderidResquest request = new OrderidResquest(String.valueOf(orderid));

        ZZCHeaders zzcHeaders = new ZZCHeaders(accessToken, request);
        ApiClient.getApiService().carsellerconfirm(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 订单详情
     * @param id
     * @param subscriber
     */
    public static void getCarOrderdetail(String id, Subscriber<ResponseParent<OrderDetailResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("order_no", id);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().cardetail(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 我的询价
     * @param status
     * @param page
     * @param subscriber
     */
    public static void getMysupply(String status, int page, Subscriber<ResponseParent<MysupplyResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("status", status);
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size", "20");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getmysupply(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 发布询价
     * @param middleModle
     * @param subscriber
     */
    public static void saveSupply(AddCarMiddleModle middleModle, Subscriber<ResponseParent<Boolean>> subscriber){

        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        SaveSupplyModel addCarFrom = middleModle.getSaveSupplyFrom(middleModle);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, addCarFrom);
        ApiClient.getApiService().savesupply(addCarFrom, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 删除我的询价
     * @param info_id
     * @param subscriber
     */
    public static void deleteSupplyinfo(int info_id, Subscriber<ResponseParent<Boolean>> subscriber){

        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        InfoidRequest addCarFrom = new InfoidRequest(info_id);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, addCarFrom);
        ApiClient.getApiService().deleteinfo(addCarFrom, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 编辑询价
     * @param id
     * @param subscriber
     */
    public static void getSinglesupply(String id,  Subscriber<ResponseParent<SingleSupplyResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", id);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getsinglesupply(id, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 我的求购
     * @param status
     * @param page
     * @param subscriber
     */
    public static void getMydemand(String status, int page, Subscriber<ResponseParent<MydemandResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("status", status);
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size", "20");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getmydemand(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 是否可以发布，需要多少积分,{id}：1需求，2询价
     * @param id
     * @param subscriber
     */
    public static void getPublishintegral(String id,  Subscriber<ResponseParent<PublishintegralResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", id);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getPublishintegral(id, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 新增/编辑车求购(管理)
     * @param savedeman
     * @param subscriber
     */
    public static void saveDemand(SavedemandModel savedeman, Subscriber<ResponseParent<Boolean>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        SavedemandRequest request = savedeman.getRequest(savedeman);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, request);
        ApiClient.getApiService().savedemand(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取单条求购
     * @param id
     * @param subscriber
     */
    public static void getSingelmydemand(String id, Subscriber<ResponseParent<SavedemandResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", id);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getsingelmydemand(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 商机 求购
     * @param props
     * @param CURURNPAGE
     * @param subscriber
     */
    public static void getSingelmydemand(String userid, DemendPropsModel props, int CURURNPAGE, Subscriber<ResponseParent<MydemandResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        if (userid == null){
            userid = "";
        }
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("user_id", userid);
        hashmap.put("status", "2");
        hashmap.put("props", Tool.getGson(props));
        hashmap.put("page", String.valueOf(CURURNPAGE));
        hashmap.put("size", "20");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getdemand(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 商机询价
     * @param model
     * @param CURURNPAGE
     * @param subscriber
     */
    public static void getsupplyList(String userid, SupplyPropsMiddleModel model, int CURURNPAGE, Subscriber<ResponseParent<SupplyResponse>> subscriber){
        SupplyPropsModel supplyProp = model.getSupplyProp(model);
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        if (userid == null){
            userid = "";
        }
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("user_id", userid);
        hashmap.put("status", "2");
        hashmap.put("props", Tool.getGson(supplyProp));
        hashmap.put("page", String.valueOf(CURURNPAGE));
        hashmap.put("size", "20");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getsupplyList(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 求购详情
     * @param infoId
     * @param subscriber
     */
    public static void getDemendDetail(int infoId, Subscriber<ResponseParent<DemendDetailResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", String.valueOf(infoId));

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getDemendDetail(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 商机询价
     * @param infoId
     * @param subscriber
     */
    public static void getSupplyDetail(int infoId, Subscriber<ResponseParent<SupplyDetailResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", String.valueOf(infoId));

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getSupplyDetail(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 业务消息
     * @param object_id
     * @param page
     * @param subscriber
     */
    public static void getSystemMsg(long object_id,int page, Subscriber<ResponseParent<SystemMsgResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("object_id", String.valueOf(object_id));
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size","20");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getSystemMsg(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 公告详情
     * @param object_id
     * @param subscriber
     */
    public static void getBulletin(long object_id, Subscriber<ResponseParent<BulletinResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", String.valueOf(object_id));

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getBulletin(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 设置已读
     * @param ids
     * @param subscriber
     */
    public static void setread(List<Integer> ids, Subscriber<ResponseParent<Boolean>> subscriber){

        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        IdRequest addCarFrom = new IdRequest(ids);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, addCarFrom);
        ApiClient.getApiService().setread(addCarFrom, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 删除好友
     * @param friendid
     * @param subscriber
     */
    public static void deleteFriend(int friendid, Subscriber<ResponseParent<Boolean>> subscriber){

        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        DeleteFriendRequest addCarFrom = new DeleteFriendRequest(friendid);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, addCarFrom);
        ApiClient.getApiService().delfriend(addCarFrom, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 屏蔽和取消屏蔽
     * @param friendid
     * @param subscriber
     */
    public static void filterchat(long friendid, Subscriber<ResponseParent<Boolean>> subscriber){

        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        FilterRequest addCarFrom = new FilterRequest(friendid);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, addCarFrom);
        ApiClient.getApiService().filterchat(addCarFrom, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取群组成员
     * @param group_id
     * @param subscriber
     */
    public static void getGroupuser(String group_id, Subscriber<ResponseParent<GroupMenberResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("group_id", group_id);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getGroupuser(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 屏蔽消息群组
     * @param groupid
     * @param subscriber
     */
    public static void setGrouptip(String groupid, Subscriber<ResponseParent<Boolean>> subscriber){

        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        GroupIdRequest addCarFrom = new GroupIdRequest(groupid);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, addCarFrom);
        ApiClient.getApiService().setGrouptip(addCarFrom, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 收款详情
     * @param id
     * @param subscriber
     */
    public static void getReceiptDetail(String id, Subscriber<ResponseParent<ReceiptDetailResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", id);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getReceiptDetail(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 提现详情
     * @param id
     * @param subscriber
     */
    public static void getTransferDetail(String id, Subscriber<ResponseParent<TransferDetailResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", id);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getTransferDetail(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 支付明细的收入
     * @param id
     * @param subscriber
     */
    public static void getAcountOrder(String id, Subscriber<ResponseParent<AcountOrderResponse>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", id);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getAcountOrder(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }



    /**
     * 获取评论列表
     */
    public static void getInfocomments(int product_id, int page, Subscriber<ResponseParent<InforCommentResponse>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("getCommentList");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("info_id", String.valueOf(product_id));
        hashmap.put("page", String.valueOf(page));
        hashmap.put("size", "10");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        Subscription subscription = ApiClient.getApiService().getInfocomments(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("getCommentList", subscription);
    }

    /**
     * 保存评价
     * @param info_id 商品id
     * @param at_id
     * @param content
     * @param image_path
     * @param subscriber
     */
    public static void saveInfocomment(int info_id, String at_id, String content, List<String> image_path, Subscriber<ResponseParent<Boolean>> subscriber){
         /* 防止多次点击 */
        cancelTagandRemove("saveComment");
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        SaveInfoComment saveCommentRequest = new SaveInfoComment(info_id, at_id, content, image_path);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, saveCommentRequest);
        Subscription subscription = ApiClient.getApiService().saveInfocomment(saveCommentRequest, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        add("saveComment", subscription);
    }

    /**
     * 获取收货地址列表
     * @param subscriber
     */
    public static void getAddressList(Subscriber<ResponseParent<List<AddressResponse>>> subscriber){
         /* 防止多次点击 */
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getAddressList(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 保存编辑地址
     * @param request
     * @param subscriber
     */
    public static void saveAddress(SaveAddressaRequest request, Subscriber<ResponseParent<Boolean>> subscriber){
         /* 防止多次点击 */
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, request);
        ApiClient.getApiService().saveAddress(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 我的员工
     * @param subscriber
     */
    public static void getMyEmployee( Subscriber<ResponseParent<List<MyEmployeeResponse>>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getMyEmployee(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 添加员工
     * @param request
     * @param subscriber
     */
    public static void addEmployee(AddEmployee request, Subscriber<ResponseParent<Boolean>> subscriber){
         /* 防止多次点击 */
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, request);
        ApiClient.getApiService().addEmployee(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 修改员工昵称
     * @param request
     * @param subscriber
     */
    public static void editEmployeename(EditEmployeeNick request, Subscriber<ResponseParent<Boolean>> subscriber){
         /* 防止多次点击 */
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, request);
        ApiClient.getApiService().editEmployeename(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 修改员工手机
     * @param request
     * @param subscriber
     */
    public static void editEmployeemobile(EditEmployPhone request, Subscriber<ResponseParent<Boolean>> subscriber){
         /* 防止多次点击 */
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, request);
        ApiClient.getApiService().editEmployeemobile(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取版本号
     * @param subscriber
     */
    public static void getAppNewVersion( Subscriber<ResponseParent<VersionResponse>> subscriber){
        Map<String, String> hashmap = new HashMap<>();

        ZZCHeaders zzcHeaders = new ZZCHeaders(hashmap);
        ApiClient.getApiService().getAppNewVersion(zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 删除地址
     * @param addressId
     * @param subscriber
     */
    public static void addressDelete(String addressId, Subscriber<ResponseParent<Boolean>> subscriber){
         /* 防止多次点击 */
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        DeleteAddressRequest request = new DeleteAddressRequest(addressId);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, request);
        ApiClient.getApiService().addressDelete(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 地址详情
     * @param addressid
     * @param subscriber
     */
    public static void getAddressdetail(String addressid, Subscriber<ResponseParent<AddressDetail>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");
        Map<String, String> hashmap = new HashMap<>();
        hashmap.put("id", addressid);

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, hashmap);
        ApiClient.getApiService().getAddressdetail(hashmap, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 更新订单的发货地址
     * @param request
     * @param subscriber
     */
    public static void checkoutAddress(CheckAddressRequest request, Subscriber<ResponseParent<Boolean>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, request);
        ApiClient.getApiService().checkoutAddress(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 更新订单的发货类型
     * @param request
     * @param subscriber
     */
    public static void shippingType(ShippingTypeRequest request, Subscriber<ResponseParent<Boolean>> subscriber){
        String Authorization = SecurePreferences.getInstance().getString("Authorization", "");

        ZZCHeaders zzcHeaders = new ZZCHeaders(Authorization, request);
        ApiClient.getApiService().shippingType(request, zzcHeaders.getHashMap())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
