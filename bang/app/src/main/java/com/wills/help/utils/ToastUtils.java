package com.wills.help.utils;

import android.widget.Toast;

import com.wills.help.base.App;

/**
 * com.wills.help.utils
 * Created by lizhaoyong
 * 2016/11/7.
 */

public class ToastUtils {

    private static Toast toast = null;

    public static void toast(String text) {
        if(toast == null)
        {
            toast = Toast.makeText(App.getApp(), text, Toast.LENGTH_SHORT);  //正常执行
        }
        else {
            toast.setText(text);  //用于覆盖前面未消失的提示信息
        }
        toast.show();
    }
    public static void toast(int stringId) {
        String text = App.getApp().getString(stringId);
        if(toast == null)
        {
            toast = Toast.makeText(App.getApp(), text, Toast.LENGTH_SHORT);  //正常执行
        }
        else {
            toast.setText(text);  //用于覆盖前面未消失的提示信息
        }
        toast.show();
    }
}
