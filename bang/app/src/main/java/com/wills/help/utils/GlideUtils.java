package com.wills.help.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.signature.StringSignature;
import com.wills.help.R;

import java.io.ByteArrayOutputStream;

/**
 * Glide加载图片工具类
 * 作者：user on 2016/9/13 10:23
 */
public class GlideUtils {

    private static GlideUtils instance;

    public static GlideUtils getInstance() {
        if (instance == null) {
            synchronized (GlideUtils.class) {
                if (instance == null) {
                    instance = new GlideUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 加载网络图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void displayImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.default_pic)
                .crossFade()
                .thumbnail(0.1f)
                .into(imageView);
    }

    public void displayImage(Context context, int url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.default_pic)
                .crossFade()
                .thumbnail(0.1f)
                .into(imageView);
    }

    public void displayImage(Context context, Uri uri, ImageView imageView) {
        Glide.with(context)
                .load(uri)
                .placeholder(R.drawable.default_pic)
                .crossFade()
                .into(imageView);
    }

    public void displayImage(Context context, String url, ImageView imageView , final LoadImageListener listener) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.default_pic)
                .crossFade()
                .into(new GlideDrawableImageViewTarget(imageView){
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        listener.onResult(resource,animation);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        listener.onStart();
                    }
                });
    }

    public interface LoadImageListener{
        void onResult(GlideDrawable drawable, GlideAnimation anim);
        void onStart();
    }

    /**
     * 加载资源文件
     *
     * @param view
     * @param resId
     */
    public void displayImage(final ImageView view, @DrawableRes int resId) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        Glide.with(context)
                .load(resId)
                .crossFade()
                .into(view)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!view.isShown()) {
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * 圆形图片
     * @param context
     * @param url
     * @param view
     */
    public void displayCircleImage(final Context context, String url, ImageView view) {

        Glide.with(context).load(url).asBitmap().centerCrop()
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .placeholder(R.drawable.default_circle)
                .into(new BitmapImageViewTarget(view){
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                view.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * 圆形图片
     * @param context
     * @param bitmap
     * @param view
     */
    public void displayCircleImage(final Context context, Bitmap bitmap, ImageView view) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();

        Glide.with(context).load(bytes).asBitmap().centerCrop().placeholder(R.drawable.default_circle).into(new BitmapImageViewTarget(view){
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                view.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public void displayCircleImage(final Context context, @DrawableRes int resId, ImageView view) {

        Glide.with(context).load(resId).asBitmap().centerCrop().placeholder(R.drawable.default_circle).into(new BitmapImageViewTarget(view){
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                view.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * 加载bitmap
     *
     * @param context
     * @param url
     */
    public void loadBitmap(Context context, Object url, SimpleTarget<Bitmap> simpleTarget) {

        Glide.with(context).
                load(url).
                asBitmap().
                diskCacheStrategy(DiskCacheStrategy.NONE).
                dontAnimate().
                into(simpleTarget);
    }

    /**
     * 取消所有正在下载或等待下载的任务。
     */
    public  void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    public  void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context
     */
    public  void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

    /**
     * 清除所有缓存
     *
     * @param context
     */
    public  void cleanAll(Context context) {
        clearDiskCache(context);
        Glide.get(context).clearMemory();
    }
}
