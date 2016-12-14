package com.wills.help.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * com.wills.help.utils
 * Created by lizhaoyong
 * 2016/12/12.
 */

public class StringUtils {

    /**
     * MD5
     * @param string
     * @return
     */
    public static String getMD5(String string){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            try {
                messageDigest.update(string.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bytes  = messageDigest.digest();
        StringBuffer buffer = new StringBuffer();
        for (int i =0;i<bytes.length;i++){
            if (Integer.toHexString(0xFF & bytes[i]).length() == 1)
                buffer.append("0").append(Integer.toHexString(0xFF & bytes[i]));
            else
                buffer.append(Integer.toHexString(0xFF & bytes[i]));
        }
        return buffer.toString();
    }
}
