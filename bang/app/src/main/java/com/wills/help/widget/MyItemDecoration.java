package com.wills.help.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wills.help.utils.ScreenUtils;

/**
 * com.wills.help.widget
 * Created by lizhaoyong
 * 2016/12/15.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration{
    private Context context;
    private int height = 0;

    public MyItemDecoration(Context context, int height) {
        this.context = context;
        this.height = height;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0,0,0, ScreenUtils.dip2px(context,height));
    }
}
