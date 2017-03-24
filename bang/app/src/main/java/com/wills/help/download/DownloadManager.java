package com.wills.help.download;

import com.wills.help.utils.AppConfig;
import com.wills.help.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.download
 * Created by lizhaoyong
 * 2017/3/23.
 */

public class DownloadManager {
    public Retrofit retrofit;
    private static final int DEFAULT_TIMEOUT = 20;
    private DownloadInterface downloadService;
    private static DownloadManager instance;
    private DownloadProgressListener listener;

    public static DownloadManager getInstance(DownloadProgressListener listener) {
        if (instance == null){
            instance = new DownloadManager(listener);
        }
        return instance;
    }

    public DownloadManager(DownloadProgressListener listener) {
        this.listener = listener;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConfig.HOST)
                    .client(genericClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build();
        }
        return retrofit;
    }

    public DownloadInterface getDownloadService() {
        if (downloadService == null) {
            downloadService = getRetrofit().create(DownloadInterface.class);
        }
        return downloadService;
    }

    public OkHttpClient genericClient(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(listener);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                         Request request = chain.request().newBuilder()
                        .addHeader("Accept-Encoding","identity")//可以从这里添加请求头
                        .build();
                        Response response = chain.proceed(request);
                        return response;
                    }
                })
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return client;
    }

    public void download(String url , final File file, Subscriber subscriber){
        getDownloadService().download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {

                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        try {
                            FileUtils.writeFile(inputStream,file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
