package com.wills.help.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.List;
import java.util.Map;

/**
 * com.wills.help.utils
 * Created by lizhaoyong
 * 2016/11/16.
 */

public class IntentUtils {

    /**
     * 隐式启动,跳转
     *
     * @param context
     * @param action
     */
    public static void startActivity(Context context, Intent action) {
        PackageManager packageManager = context.getPackageManager();
        List activities = packageManager.queryIntentActivities(action, PackageManager.MATCH_DEFAULT_ONLY);
        boolean isSafe = activities.size() > 0;
        if (isSafe) {
            context.startActivity(action);
        }
    }

    /**
     * 一般的跳转
     *
     * @param context
     * @param cls
     */
    public static void startActivity(Context context, Class<?> cls) {
        Intent i = new Intent(context, cls);
        context.startActivity(i);
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent,requestCode);
    }

    public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode) {
        Intent intent = new Intent(activity, cls);
        activity.startActivityForResult(intent,requestCode);
    }

    public static void startActivity(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity activity, Class<?> cls, Bundle bundle ,int requestCode) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent,requestCode);
    }

    /**
     * 带参数的跳转
     * @param context
     * @param cls
     * @param map
     */
    public static void startActivity(Context context, Class<?> cls, Map<String, Object> map) {
        Intent i = new Intent(context, cls);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                i.putExtra(entry.getKey(), (String) entry.getValue());
            }else if (entry.getValue() instanceof Integer){
                i.putExtra(entry.getKey(), (Integer) entry.getValue());
            }else if (entry.getValue() instanceof Boolean){
                i.putExtra(entry.getKey(), (Boolean) entry.getValue());
            }
        }
        context.startActivity(i);
    }

    /**
     *
     * @param context
     * @param cls
     */
    public static void startFinishActivity(Context context, Class<?> cls){
        Intent i = new Intent(context, cls);
        context.startActivity(i);
        ((Activity)context).finish();
    }
}
