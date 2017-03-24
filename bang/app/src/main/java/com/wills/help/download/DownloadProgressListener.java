package com.wills.help.download;

/**
 * com.wills.help.download
 * Created by lizhaoyong
 * 2017/3/23.
 */

public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
