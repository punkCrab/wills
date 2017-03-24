package com.wills.help.download;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * com.wills.help.download
 * Created by lizhaoyong
 * 2017/3/23.
 */

public interface DownloadInterface {

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
