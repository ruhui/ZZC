package com.zzcar.zzc.networks;

import com.zzcar.zzc.service.ZZCService;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;

/**
 * 描述：调用接口初始化API
 * 创建作者： 黄如辉
 * 创建时间： 2017/4/10 13:57
 **/
public class ApiClient {
    /* 测试URL */
    public static final String BASE_URL_TEST = "http://47.89.48.28:5001/";
    /* 超时时间*/
    private static final int DEFAULT_TIMEOUT = 5;

    private HashMap<String,  Subscription> hashMap = new HashMap<>();

    private Retrofit retrofit;
    private ZZCService apiService;

    //构造方法私有
    private ApiClient() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//        httpClientBuilder.addInterceptor()

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL_TEST)
                .build();

        apiService = retrofit.create(ZZCService.class);
    }


    public static ZZCService getApiService() {

        return ApiClientHolder.INSTANCE.apiService;
    }


    private static class ApiClientHolder {
        public static ApiClient INSTANCE = new ApiClient();
    }

}
