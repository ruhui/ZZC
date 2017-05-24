package com.zzcar.zzc.service;

import com.zzcar.zzc.models.AddCarFrom;
import com.zzcar.zzc.models.AddressModel;
import com.zzcar.zzc.models.SinglecarModel;
import com.zzcar.zzc.networks.ResponseParent;
import com.zzcar.zzc.networks.requests.ApplyDepositRequest;
import com.zzcar.zzc.networks.requests.BuyIntegraRequest;
import com.zzcar.zzc.networks.requests.ForgetPwdResquest;
import com.zzcar.zzc.networks.requests.LoginRequest;
import com.zzcar.zzc.networks.requests.NickRequest;
import com.zzcar.zzc.networks.requests.PhotoRequest;
import com.zzcar.zzc.networks.requests.ProduceIdResquest;
import com.zzcar.zzc.networks.requests.SaveCommentRequest;
import com.zzcar.zzc.networks.requests.SendRegsmsRequest;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.networks.responses.CarDetailRespose;
import com.zzcar.zzc.networks.responses.CarSeriesResponse;
import com.zzcar.zzc.networks.responses.CarTypeResponse;
import com.zzcar.zzc.networks.responses.CityResponse;
import com.zzcar.zzc.networks.responses.ColorResponse;
import com.zzcar.zzc.networks.responses.CommentResponse;
import com.zzcar.zzc.networks.responses.DepositResponse;
import com.zzcar.zzc.networks.responses.FridendListResponse;
import com.zzcar.zzc.networks.responses.HomeCarGetResponse;
import com.zzcar.zzc.networks.responses.HomeCarPushResponse;
import com.zzcar.zzc.networks.responses.IntegralDetailResponse;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.networks.responses.MessageListResponse;
import com.zzcar.zzc.networks.responses.MineMsgResponse;
import com.zzcar.zzc.networks.responses.MybillResponse;
import com.zzcar.zzc.networks.responses.MyfavcarResponse;
import com.zzcar.zzc.networks.responses.RefundOrderResponse;
import com.zzcar.zzc.networks.responses.ShouzhiDetailResponse;
import com.zzcar.zzc.networks.responses.UserMsgResponse;
import com.zzcar.zzc.networks.responses.ValueTextResponse;
import com.zzcar.zzc.networks.responses.VerifiedResponse;

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

    /*获取车源渠道*/
    @GET("common/car_channel")
    Observable<ResponseParent<List<CarChanelResponse>>> getcarchannel(@HeaderMap Map<String, String> header);

    /*获取价格区间*/
    @GET("common/price_type")
    Observable<ResponseParent<List<CarChanelResponse>>> getpricebetween(@HeaderMap Map<String, String> header);

    /*获取汽车品牌*/
    @GET("common/car_bland")
    Observable<ResponseParent<List<BrandListResponse>>> getbarnd(@HeaderMap Map<String, String> header);

    /*获取车系*/
    @GET("common/car_series")
    Observable<ResponseParent<List<CarSeriesResponse>>> getcarseries(@Query("id") int id, @HeaderMap Map<String, String> header);

    /*获取车型*/
    @GET("common/car_spec")
    Observable<ResponseParent<List<CarTypeResponse>>> getspec(@Query("id") long id,@HeaderMap Map<String, String> header);

    /*排放*/
    @GET("common/emission")
    Observable<ResponseParent<List<CarChanelResponse>>> getemission(@HeaderMap Map<String, String> header);

    /*里程*/
    @GET("common/mileage")
    Observable<ResponseParent<List<CarChanelResponse>>> getmileage(@HeaderMap Map<String, String> header);

    /*城市*/
    @GET("common/province_city")
    Observable<ResponseParent<List<CityResponse>>> getprovincecity(@HeaderMap Map<String, String> header);

   /*城市  到县区*/
   @GET("common/regions")
   Observable<ResponseParent<List<CityResponse>>> getregions(@HeaderMap Map<String, String> header);

    /*颜色*/
    @GET("common/color")
    Observable<ResponseParent<List<ColorResponse>>> getcolor(@HeaderMap Map<String, String> header);

    /*用途*/
    @GET("common/use_type")
    Observable<ResponseParent<List<CarChanelResponse>>> getusertype(@HeaderMap Map<String, String> header);

    /*添加车源*/
    @POST("goods/save_car")
    Observable<ResponseParent<Boolean>> savecar(@Body AddCarFrom httpClient, @HeaderMap Map<String, String> header);

    /*车源详情*/
    @GET("goods/car_detail")
    Observable<ResponseParent<CarDetailRespose>> getcardetail(@Query("id") int id, @HeaderMap Map<String, String> header);

    /*获取用户信息*/
    @GET("account/user")
    Observable<ResponseParent<MineMsgResponse>> getusermsg(@HeaderMap Map<String, String> header);

    /*获取钱包和积分*/
    @GET("account/bill")
    Observable<ResponseParent<MybillResponse>> getuserbill(@HeaderMap Map<String, String> header);

   /*评论列表*/
   @GET("goods/comments")
   Observable<ResponseParent<CommentResponse>> getcomments(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*添加评论*/
    @POST("goods/save_comment")
    Observable<ResponseParent<Boolean>> savecomment(@Body SaveCommentRequest saveComment, @HeaderMap Map<String, String> header);

    /*取消或添加收藏*/
    @POST("goods/save_favorte")
    Observable<ResponseParent<Integer>> savefavorte(@Body ProduceIdResquest savefavorte, @HeaderMap Map<String, String> header);

    /*上传用户头像*/
    @POST("account/photo")
    Observable<ResponseParent<Boolean>> photo(@Body PhotoRequest photo, @HeaderMap Map<String, String> header);

    /*修改昵称*/
    @POST("account/name")
    Observable<ResponseParent<Boolean>> savenick(@Body NickRequest nick, @HeaderMap Map<String, String> header);

    /*获取实名认证GET */
    @GET("account/verified")
    Observable<ResponseParent<VerifiedResponse>> getverified(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*获取验证码*/
    @POST("common/regsms")
    Observable<ResponseParent<Boolean>> getregsms(@Body SendRegsmsRequest sendCode, @HeaderMap Map<String, String> header);

    /*车行认证*/
    @POST("account/save_verified")
    Observable<ResponseParent<Boolean>> saveverified(@Body VerifiedResponse verfied, @HeaderMap Map<String, String> header);

    /*获取积分明细*/
    @GET("account/integral_detail")
    Observable<ResponseParent<IntegralDetailResponse>> getintegraldetail(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*获取体现方式*/
    @GET("account/deposit")
    Observable<ResponseParent<DepositResponse>> getdeposit(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*获取提现银行*/
    @GET("common/bank")
    Observable<ResponseParent<List<ValueTextResponse>>> getbank(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*保存提现账户*/
    @POST("account/save_deposit")
    Observable<ResponseParent<Boolean>> savedeposit(@Body DepositResponse response, @HeaderMap Map<String, String> header);

    /*申请提现*/
    @POST("account/apply_deposit")
    Observable<ResponseParent<Boolean>> applydeposit(@Body ApplyDepositRequest response, @HeaderMap Map<String, String> header);

    /*收支明细*/
    @GET("account/bills")
    Observable<ResponseParent<ShouzhiDetailResponse>> getbills(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*忘记密码*/
    @POST("account/forget")
    Observable<ResponseParent<Boolean>> forgetpassword(@Body ForgetPwdResquest request, @HeaderMap Map<String, String> header);

    /*注册*/
    @POST("account/register")
    Observable<ResponseParent<Boolean>> register(@Body ForgetPwdResquest request, @HeaderMap Map<String, String> header);

    /*购买积分*/
    @POST("account/buy_integral")
    Observable<ResponseParent<String>> buyintegral(@Body BuyIntegraRequest request, @HeaderMap Map<String, String> header);

    /*退款详情*/
    @GET("account/refund_order")
    Observable<ResponseParent<RefundOrderResponse>> getrefundorder(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*我的收藏*/
    @GET("goods/my_fav_car")
    Observable<ResponseParent<MyfavcarResponse>> getmyfavcar(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*我的车源*/
    @GET("goods/my_car")
    Observable<ResponseParent<MyfavcarResponse>> getmycar(@QueryMap Map<String, Object> hashMap, @HeaderMap Map<String, String> header);

    /*上下架*/
    @POST("goods/up_down")
    Observable<ResponseParent<Integer>> updown(@Body ProduceIdResquest request, @HeaderMap Map<String, String> header);

    /*获取单条车源信息*/
    @GET(" goods/car")
    Observable<ResponseParent<SinglecarModel>> getcar(@Query("id") String id, @HeaderMap Map<String, String> header);

    /*获取好友列表*/
    @GET("friend/friend")
    Observable<ResponseParent<List<FridendListResponse>>> getfriendlist(@Query("name") String name, @HeaderMap Map<String, String> header);

    /*消息列表数据*/
    @GET("msg/news_msg")
    Observable<ResponseParent<List<MessageListResponse>>> getmessagelist(@HeaderMap Map<String, String> header);
}
