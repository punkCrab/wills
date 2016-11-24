package com.wills.help.net;

import android.text.TextUtils;

import com.wills.help.utils.AppConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.lzy.mmd.utils
 * Created by lizhaoyong
 */
public class HttpManager {

    public static Retrofit retrofit;
    private static final int DEFAULT_TIMEOUT = 20;


    public static Retrofit getInstance(){
        if (retrofit == null){
            synchronized (HttpManager.class){
                if (retrofit == null){
                    retrofit = new Retrofit.Builder()
                            .baseUrl(AppConfig.HOST)
                            .client(genericClient())
//                            .addConverterFactory(GsonConverterFactory.create())
                            .addConverterFactory(CustomGsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static OkHttpClient genericClient(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("","")//可以从这里添加请求头
                        .build();
                Response response = chain.proceed(request);
                String headerValue = request.cacheControl().toString();
                if (!TextUtils.isEmpty(headerValue)){
                    response = response.newBuilder().removeHeader("Pragma").header("Cache-Control" , "max-age=" + 30).build();
                }
                return response;
            }
        })
                .build();
        return client;
    }

    public void httpSubscribe(Observable observable , Subscriber subscriber){
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }
}
