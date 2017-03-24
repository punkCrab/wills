package com.wills.help.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 判断手机号码
     * @param phoneNumber
     * @return
     */
    public static boolean availablePhone(String phoneNumber) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(14[0-9]))\\d{8}$");
        Matcher m = pattern.matcher(phoneNumber);
        return m.matches();
    }

    /***
     * 判断字符串是否为空
     * @param text
     * @return
     */
    public static boolean isNullOrEmpty(String text) {
        if (text == null || "".equals(text.trim()) || text.trim().length() == 0
                || "null".equals(text.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断金钱
     * @param number 数字
     * @param prev 小数点前 后1位
     * @return
     */
    public static boolean moneyVerify(String number, int prev) {
        Pattern p = Pattern.compile("^\\d{1," + prev + "}(\\.\\d{1})?$");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    /**
     * 返回星星
     * @param len
     * @return
     */
    private static String getStar(int len){
        String str = "";
        for (int i = 0; i < len; i++) {
            str += "*";
        }
        return str;
    }

    /**
     * 隐藏中间字符串
     * @param str
     * @param starLen
     * @param endLen
     * @return
     */
    public static String getStarStr(String str, int starLen, int endLen) {
        if (isNullOrEmpty(str))
            return "";
        if (str.length() < (starLen + endLen))
            return str;
        return str.substring(0, starLen)
                + getStar(str.length() - starLen - endLen)
                + str.substring(str.length() - endLen);
    }

    /**
     * 获取支付宝帐号
     * @param str
     * @return
     */
    public static String getZFB(String str){
        if (str.contains("@")){
            return getStarStr(str.split("@")[0],3,0)+"@"+str.split("@")[1];
        }else {
            return getStarStr(str,3,4);
        }
    }

    /**
     * 获取文件大小
     * @param var0
     * @return
     */
    public static String getFileSize(long var0) {
        DecimalFormat var2 = new DecimalFormat("###.00");
        return var0 < 1024L ? var0 + "bytes" : (var0 < 1048576L ? var2.format((double) ((float) var0 / 1024.0F))
                + "KB" : (var0 < 1073741824L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F))
                + "MB" : (var0 < 0L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F / 1024.0F))
                + "GB" : "error")));
    }
}
