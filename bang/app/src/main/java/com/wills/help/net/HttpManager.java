package com.wills.help.net;

import com.wills.help.utils.AppConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.schedulers.Schedulers;

/**
 * com.lzy.mmd.utils
 * Created by lizhaoyong
 */
public class HttpManager {

    public static Retrofit retrofit;
    private static final int DEFAULT_TIMEOUT = 20;
    public static ApiInterface apiInterface;

    public static Retrofit getInstance(){
        if (retrofit == null){
            synchronized (HttpManager.class){
                if (retrofit == null){
                    retrofit = new Retrofit.Builder()
                            .baseUrl(AppConfig.HOST)
                            .client(genericClient())
//                            .addConverterFactory(GsonConverterFactory.create())
                            .addConverterFactory(MyConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                            .build();
                }
            }
        }
        return retrofit;
    }

    /**
     * 获取接口
     * @return
     */
    public static ApiInterface getApiInterface(){
        if (apiInterface == null){
            synchronized (ApiInterface.class){
                if (apiInterface == null){
                    apiInterface = getInstance().create(ApiInterface.class);
                }
            }
        }
        return apiInterface;
    }

    public static OkHttpClient genericClient(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request().newBuilder()
//                        .addHeader("","")//可以从这里添加请求头
//                        .build();
//                Response response = chain.proceed(request);
//                String headerValue = request.cacheControl().toString();
//                if (!StringUtils.isNullOrEmpty(headerValue)){
//                    response = response.newBuilder().removeHeader("Pragma").header("Cache-Control" , "max-age=" + 30).build();
//                }
//                return response;
//            }
//        })
                .build();
        return client;
    }


    public static MultipartBody.Part getPart( String key ,File file){
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData(key, file.getName(), photoRequestBody);
        return photo;
    }
}
