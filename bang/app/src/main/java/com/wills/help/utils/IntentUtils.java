package com.wills.help.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.List;

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

    /**
     *
     * @param activity
     * @param cls
     * @param bundle
     * @param requestCode 统一三位，系统的1开头，用户信息2开头，快递3开头
     */
    public static void startActivityForResult(Activity activity, Class<?> cls, Bundle bundle ,int requestCode) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent,requestCode);
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
