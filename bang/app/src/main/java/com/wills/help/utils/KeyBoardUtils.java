package com.wills.help.utils;

/**
 * com.wills.help.utils
 * Created by lizhaoyong
 * 2016/11/7.
 */

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 打开或关闭软键盘
 *
 * @author zhy
 *
 */
public class KeyBoardUtils
{
    /**
     * 打卡软键盘
     *
     * @param mEditText
     *            输入框
     * @param mContext
     *            上下文
     */
    public static void openKeybord( Context mContext , EditText mEditText)
    {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     *            输入框
     * @param mContext
     *            上下文
     */
    public static void closeKeybord(Context mContext ,EditText mEditText)
    {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}
