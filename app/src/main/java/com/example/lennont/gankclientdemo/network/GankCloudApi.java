package com.example.lennont.gankclientdemo.network;

import android.provider.MediaStore;

import com.example.lennont.gankclientdemo.bean.Goods;
import com.example.lennont.gankclientdemo.bean.GoodsResult;
import com.example.lennont.gankclientdemo.bean.Image;

import java.io.IOException;
import java.util.ArrayList;

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

    private CloudService service;

    public static GankCloudApi instance;

    public static GankCloudApi getInstance() {
        if (null == instance) synchronized (GankCloudApi.class) {
            if (null == instance) {
                instance = new GankCloudApi();
            }
        }
        return instance;
    }

    /**每次加载条目*/
    public static final int LOAD_LIMIT = 20;


    public GankCloudApi() {

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        client.addInterceptor(interceptor);

        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl("https://gank.io/api/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        service = retrofit.create(CloudService.class);
    }

    //获取福利照片
    public Observable<GoodsResult> GetGankImages(int limit, int page){
        return service.GetGankImage(limit, page);
    }


    public interface CloudService {

        @GET("data/福利/{limit}/{page}")
        Observable<GoodsResult> GetGankImage(
                @Path("limit") int limit,
                @Path("page") int page);
    }



}
