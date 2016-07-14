package com.example.lennont.gankclientdemo.network;

import android.provider.MediaStore;

import com.example.lennont.gankclientdemo.bean.Goods;
import com.example.lennont.gankclientdemo.bean.GoodsResult;
import com.example.lennont.gankclientdemo.bean.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by LennonT on 2016/7/8.
 * 从Gank网上拉数据
 */
public class GankCloudApi {

    //region Field

    private final String Base_Url = "https://gank.io/api/";

    private CloudService service;

    public static GankCloudApi instance;

    /**每次加载条目*/
    public static final int LOAD_LIMIT = 20;

    //endregion

    //region ctor

    public static GankCloudApi getInstance() {
        if (null == instance) synchronized (GankCloudApi.class) {
            if (null == instance) {
                instance = new GankCloudApi();
            }
        }
        return instance;
    }

    public GankCloudApi() {

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        client.addInterceptor(interceptor);
        client.readTimeout(3000L, TimeUnit.MILLISECONDS);

        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(Base_Url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        service = retrofit.create(CloudService.class);
    }

    //endregion

    //region Public

    //获取福利照片信息
    public Observable<GoodsResult> getGankImages(int limit, int page) {
        return service.GetGankImage(limit, page);
    }

    //获取Android项目信息列表
    public Observable<GoodsResult> getAndroidGoods(int limit, int page) {
        return service.getAndroidGoods(limit, page);
    }

    //获取iOS项目信息列表
    public Observable<GoodsResult> getIOSGoods(int limit, int page) {
        return service.getIosGoods(limit, page);
    }

    //endregion

    //region InnerClass

    private interface CloudService {

        @GET("data/福利/{limit}/{page}")
        Observable<GoodsResult> GetGankImage(
                @Path("limit") int limit,
                @Path("page") int page);

        @GET("data/Android/{limit}/{page}")
        Observable<GoodsResult> getAndroidGoods(
                @Path("limit") int limit,
                @Path("page") int page
        );

        @GET("data/iOS/{limit}/{page}")
        Observable<GoodsResult> getIosGoods(
                @Path("limit") int limit,
                @Path("page") int page
        );
    }

    //endregion

}
