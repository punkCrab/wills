package com.wills.help.net;

import com.wills.help.utils.AppConfig;

/**
 * com.wills.help.net
 * Created by lizhaoyong
 * 2016/11/7.
 */

public class ApiException extends RuntimeException {
    private int mErrorCode;

    public ApiException(int errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
    }

    public int getErrorCode(){
        return mErrorCode;
    }

    /**
     * 判断是否是token失效
     *
     * @return 失效返回true, 否则返回false;
     */
    public boolean isTokenExpried() {
        return mErrorCode == AppConfig.TOKEN_EXPRIED;
    }
}
