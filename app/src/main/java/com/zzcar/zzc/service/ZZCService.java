package com.zzcar.zzc.service;

import com.zzcar.zzc.models.AddressModel;
import com.zzcar.zzc.networks.ResponseParent;
import com.zzcar.zzc.networks.requests.LoginRequest;
import com.zzcar.zzc.networks.responses.HomeCarGetResponse;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.networks.responses.UserMsgResponse;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 描述：
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/14 15:57
 **/
public interface ZZCService {
    /*登录*/
    @POST("account/login")
    Observable<ResponseParent<LoginResponse>> login(@Body LoginRequest httpClient, @HeaderMap Map<String, String> header);

    /*获取用户信息*/
    @GET("account/user")
    Observable<ResponseParent<UserMsgResponse>> getusermsg( @Query("user_id")String user_id, @HeaderMap Map<String, String> header);

    /*获取收货地址*/
    @GET(" account/address")
    Observable<ResponseParent<List<AddressModel>>> getaddress(@HeaderMap Map<String, String> header);

    /*首页推荐车源*/
    @GET("index/car_push")
    Observable<ResponseParent<List<HomeCarPushResponse>>> gethomecarpush(@HeaderMap Map<String, String> header);

    /*车源列表*/
    @GET("goods/cars")
    Observable<ResponseParent<HomeCarGetResponse>> getcars(@QueryMap Map<String, String> paraMap,@HeaderMap Map<String, String> header);
}
