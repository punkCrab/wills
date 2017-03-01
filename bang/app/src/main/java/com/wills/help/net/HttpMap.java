package com.wills.help.net;

import com.wills.help.utils.AESUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * com.wills.help.net
 * Created by lizhaoyong
 * 2017/3/1.
 */

public class HttpMap {
    private Map<String,String> map;

    public HttpMap() {
        map = new HashMap<>();
    }

    public void put(String key,String value){
        getMap().put(key, AESUtils.encrypt(value));
    }

    public Map<String, String> getMap() {
        if (map == null){
            map = new HashMap<>();
        }
        return map;
    }
}
