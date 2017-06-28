package com.zzcar.zzc.service;

import com.zzcar.zzc.models.AddCarFrom;
import com.zzcar.zzc.models.AddressModel;
import com.zzcar.zzc.models.PayOrderModel;
import com.zzcar.zzc.models.SaveSupplyModel;
import com.zzcar.zzc.models.SinglecarModel;
import com.zzcar.zzc.networks.ResponseParent;
import com.zzcar.zzc.networks.requests.AddMsgRequest;
import com.zzcar.zzc.networks.requests.ApplyDepositRequest;
import com.zzcar.zzc.networks.requests.ApplyFriendRequest;
import com.zzcar.zzc.networks.requests.BuyIntegraRequest;
import com.zzcar.zzc.networks.requests.BuysecurityRequest;
import com.zzcar.zzc.networks.requests.CheckoutcartRequest;
import com.zzcar.zzc.networks.requests.DeleteFriendRequest;
import com.zzcar.zzc.networks.requests.FilterRequest;
import com.zzcar.zzc.networks.requests.ForgetPwdResquest;
import com.zzcar.zzc.networks.requests.GroupIdRequest;
import com.zzcar.zzc.networks.requests.IdRequest;
import com.zzcar.zzc.networks.requests.InfoidRequest;
import com.zzcar.zzc.networks.requests.LoginRequest;
import com.zzcar.zzc.networks.requests.NickRequest;
import com.zzcar.zzc.networks.requests.OrderNoRequest;
import com.zzcar.zzc.networks.requests.OrderidResquest;
import com.zzcar.zzc.networks.requests.PhotoRequest;
import com.zzcar.zzc.networks.requests.ProduceIdResquest;
import com.zzcar.zzc.networks.requests.RefreshLoginRequest;
import com.zzcar.zzc.networks.requests.SaveCommentRequest;
import com.zzcar.zzc.networks.requests.SavedemandRequest;
import com.zzcar.zzc.networks.requests.SendRegsmsRequest;
import com.zzcar.zzc.networks.responses.AcountOrderResponse;
import com.zzcar.zzc.networks.responses.ApplyFriendResponse;
import com.zzcar.zzc.networks.responses.BrandListResponse;
import com.zzcar.zzc.networks.responses.BulletinResponse;
import com.zzcar.zzc.networks.responses.CarChanelResponse;
import com.zzcar.zzc.networks.responses.CarDetailRespose;
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
import com.zzcar.zzc.networks.responses.IntegralDetailResponse;
import com.zzcar.zzc.networks.responses.LoginResponse;
import com.zzcar.zzc.networks.responses.MessageListResponse;
import com.zzcar.zzc.networks.responses.MineMsgResponse;
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
import com.zzcar.zzc.networks.responses.UserMsgResponse;
import com.zzcar.zzc.networks.responses.ValueTextResponse;
import com.zzcar.zzc.networks.responses.VerifiedResponse;

import org.androidannotations.annotations.rest.Get;

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
    Observable<ResponseParent<String>> saveverified(@Body VerifiedResponse verfied, @HeaderMap Map<String, String> header);

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
    Observable<ResponseParent<LoginResponse>> register(@Body ForgetPwdResquest request, @HeaderMap Map<String, String> header);

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
    @GET("goods/car")
    Observable<ResponseParent<SinglecarModel>> getcar(@Query("id") String id, @HeaderMap Map<String, String> header);

    /*获取好友列表*/
    @GET("friend/friend")
    Observable<ResponseParent<List<FridendListResponse>>> getfriendlist(@Query("name") String name, @HeaderMap Map<String, String> header);

    /*消息列表数据*/
    @GET("msg/news_msg")
    Observable<ResponseParent<List<MessageListResponse>>> getmessagelist(@HeaderMap Map<String, String> header);

    /*请求加为好友*/
    @POST("friend/apply_friend")
    Observable<ResponseParent<Boolean>> applyfriend(@Body ApplyFriendRequest request, @HeaderMap Map<String, String> header);

    /*好友验证消息 */
    @GET("msg/friend")
    Observable<ResponseParent<ApplyFriendResponse>> getapplyfriend(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*添加好友*/
    @POST("friend/add_friend")
    Observable<ResponseParent<Boolean>> addfriend(@Body ApplyFriendRequest request, @HeaderMap Map<String, String> header);

    /*获取某个用户的信息*/
    @GET("account/user")
    Observable<ResponseParent<UserMessageResponse>> getUserMessage(@Query("user_id") int user_id, @HeaderMap Map<String, String> header);

    /*刷新数据*/
    @POST(" account/refresh")
    Observable<ResponseParent<LoginResponse>> refreshlogin(@Body RefreshLoginRequest access_token, @HeaderMap Map<String, String> header);

    /*IM保存数据*/
    @POST("msg/add_chat_message")
    Observable<ResponseParent<Boolean>> addchatmessage(@Body AddMsgRequest request, @HeaderMap Map<String, String> header);

    /*首页广告图片*/
    @GET("index/car_ad")
    Observable<ResponseParent<List<HomeAdverResponse>>> getHomeAd(@HeaderMap Map<String, String> header);

    /*首页实况*/
    @GET("index/livemsg")
    Observable<ResponseParent<HomeLivemsgResponse>> getHomeLivemsg(@QueryMap Map<String, String> hashMap , @HeaderMap Map<String, String> header);

    /*首页推荐车源*/
    @GET("index/car_push")
    Observable<ResponseParent<List<HomeCarPushResponse>>> getCarpush(@HeaderMap Map<String, String> header);

    /*首页获取订阅列表*/
    @GET("goods/sub_cars")
    Observable<ResponseParent<HomeCarGetResponse>> getsSubcars(@QueryMap Map<String, String> hashMap , @HeaderMap Map<String, String> header);

    /*查询订阅*/
    @GET("account/get_subscribe")
    Observable<ResponseParent<MysubscribeResponse>> getmysubscribe(@HeaderMap Map<String, String> header);

    /*保存订阅*/
    @POST("account/save_subscribe")
    Observable<ResponseParent<Boolean>> savesubscribe(@Body MysubscribeResponse request, @HeaderMap Map<String, String> header);

    /*申请交易担保金*/
    @POST("account/buy_security")
    Observable<ResponseParent<String>> buysecurity(@Body BuysecurityRequest request, @HeaderMap Map<String, String> header);

    /*退交易担保金*/
    @POST("account/refund_security")
    Observable<ResponseParent<Boolean>> refundsecurity(@HeaderMap Map<String, String> header);

    /*直接购买，交易使用*/
    @POST("checkout/cart")
    Observable<ResponseParent<CheckoutcartResponse>> getSureorder(@Body CheckoutcartRequest request, @HeaderMap Map<String, String> header);

    /*订单支付，生成支付单号*/
    @POST("checkout/pay")
    Observable<ResponseParent<String>> payorder(@Body PayOrderModel request, @HeaderMap Map<String, String> header);

    /*我买到的*/
    @GET("order/buy_car_order")
    Observable<ResponseParent<OrderListResponse>> buycarorder(@QueryMap Map<String, String> hashMap , @HeaderMap Map<String, String> header);

    /*待支付订单生成*/
    @POST("checkout/order")
    Observable<ResponseParent<CheckoutcartResponse>> payWairorder(@Body OrderNoRequest request, @HeaderMap Map<String, String> header);

    /*取消订单*/
    @POST("order/cancel")
    Observable<ResponseParent<Integer>> cancelorder(@Body OrderidResquest request, @HeaderMap Map<String, String> header);

    /*买家确认*/
    @POST("order/car_buyer_confirm")
    Observable<ResponseParent<Boolean>> carbuyerconfirm(@Body OrderidResquest request, @HeaderMap Map<String, String> header);

    /*我卖出的*/
    @GET("order/sell_car_order")
    Observable<ResponseParent<OrderListResponse>> sellcarorder(@QueryMap Map<String, String> hashMap , @HeaderMap Map<String, String> header);

    /*卖家确认*/
    @POST("order/car_seller_confirm")
    Observable<ResponseParent<Boolean>> carsellerconfirm(@Body OrderidResquest request, @HeaderMap Map<String, String> header);

    /*订单详情*/
    @GET("order/car_detail")
    Observable<ResponseParent<OrderDetailResponse>> cardetail(@QueryMap Map<String, String> hashMap , @HeaderMap Map<String, String> header);

    /*我的询价*/
    @GET("info/my_supply")
    Observable<ResponseParent<MysupplyResponse>> getmysupply(@QueryMap Map<String, String> hashMap , @HeaderMap Map<String, String> header);

    /*新增/编辑车询价*/
    @POST("info/save_supply")
    Observable<ResponseParent<Boolean>> savesupply(@Body SaveSupplyModel request, @HeaderMap Map<String, String> header);

    /*删除我的询价*/
    @POST("info/delete_info")
    Observable<ResponseParent<Boolean>> deleteinfo(@Body InfoidRequest request, @HeaderMap Map<String, String> header);

    /*查询单条询价*/
    @GET("info/my_supply")
    Observable<ResponseParent<SingleSupplyResponse>> getsinglesupply(@Query("id") String id, @HeaderMap Map<String, String> header);

    /*我的求购*/
    @GET("info/my_demand")
    Observable<ResponseParent<MydemandResponse>> getmydemand(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*是否可以发布*/
    @GET("info/publish_integral")
    Observable<ResponseParent<PublishintegralResponse>> getPublishintegral(@Query("id") String id, @HeaderMap Map<String, String> header);

    /*新增/编辑车求购(管理)*/
    @POST("info/save_demand")
    Observable<ResponseParent<Boolean>> savedemand(@Body SavedemandRequest request, @HeaderMap Map<String, String> header);

    /*获取单条求购*/
    @GET("info/my_demand")
    Observable<ResponseParent<SavedemandResponse>> getsingelmydemand(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*商机求购*/
    @GET("info/demand")
    Observable<ResponseParent<MydemandResponse>> getdemand(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*商机询价*/
    @GET("info/supply")
    Observable<ResponseParent<SupplyResponse>> getsupplyList(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*商机求购*/
    @GET("info/demand")
    Observable<ResponseParent<DemendDetailResponse>> getDemendDetail(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*商机询价*/
    @GET("info/supply")
    Observable<ResponseParent<SupplyDetailResponse>> getSupplyDetail(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*业务消息*/
    @GET("msg/system")
    Observable<ResponseParent<SystemMsgResponse>> getSystemMsg(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

   /*公告明细*/
   @GET("msg/bulletin")
   Observable<ResponseParent<BulletinResponse>> getBulletin(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

   /*已读设置*/
   @POST("msg/read")
   Observable<ResponseParent<Boolean>> setread(@Body IdRequest request, @HeaderMap Map<String, String> header);


    /*删除好友*/
    @POST("friend/del_friend")
    Observable<ResponseParent<Boolean>> delfriend(@Body DeleteFriendRequest request, @HeaderMap Map<String, String> header);

    /*屏蔽或取消*/
    @POST("friend/filter_chat")
    Observable<ResponseParent<Boolean>> filterchat(@Body FilterRequest request, @HeaderMap Map<String, String> header);

    /*群成员*/
    @GET("msg/group_user")
    Observable<ResponseParent<GroupMenberResponse>> getGroupuser(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*群消息免打扰*/
    @POST("msg/group_tip")
    Observable<ResponseParent<Boolean>> setGrouptip(@Body GroupIdRequest request, @HeaderMap Map<String, String> header);

    /*收款详情*/
    @GET("account/receipt")
    Observable<ResponseParent<ReceiptDetailResponse>> getReceiptDetail(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*提现详情*/
    @GET("account/transfer")
    Observable<ResponseParent<TransferDetailResponse>> getTransferDetail(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);

    /*收支明细的收入*/
    @GET("account/order")
    Observable<ResponseParent<AcountOrderResponse>> getAcountOrder(@QueryMap Map<String, String> hashMap, @HeaderMap Map<String, String> header);


}
