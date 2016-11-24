package com.wills.help.photo.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import com.wills.help.R;
import com.wills.help.base.BaseFragment;
import com.wills.help.utils.GlideUtils;
import com.wills.help.utils.ScreenUtils;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * com.wills.help.photo.ui
 * Created by lizhaoyong
 * 2016/11/22.
 */

public class ImageFragment extends BaseFragment implements PhotoViewAttacher.OnPhotoTapListener{

    private String url;
    private PhotoView photoView;
    private boolean full = false;

    public static ImageFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString("url",url);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.photo_preview_item,null);
        photoView = (PhotoView) view.findViewById(R.id.image);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        url = getArguments().getString("url");
        photoView.setOnPhotoTapListener(this);
        GlideUtils.getInstance().displayImage(getAppCompatActivity(), "file://"+url , photoView);
    }

    @Override
    public void onPhotoTap(View view, float v, float v1) {
//        listener.onFull(!full);
        full(!full);
    }


    private void full(boolean enable) {
        if (enable) {
            full = true;
            WindowManager.LayoutParams lp = getAppCompatActivity().getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getAppCompatActivity().getWindow().setAttributes(lp);
            getAppCompatActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            setAnimator(0);
        } else {
            full = false;
            WindowManager.LayoutParams attr = getAppCompatActivity().getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getAppCompatActivity().getWindow().setAttributes(attr);
            getAppCompatActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            setAnimator(1);
        }
    }

    /**
     *
     * @param type 0是隐藏1是显示
     */
    private void setAnimator(final int type) {
        ObjectAnimator animator = null;
        ObjectAnimator animator1 = null;
        int distance = getAppCompatActivity().findViewById(R.id.toolbar).getHeight() + ScreenUtils.getStatusHeight(getAppCompatActivity());
        int distance1 = getAppCompatActivity().findViewById(R.id.rl_choose).getHeight();
        if (type == 0) {
            animator = ObjectAnimator.ofFloat(getAppCompatActivity().findViewById(R.id.toolbar), "translationY", -distance);
            animator1 = ObjectAnimator.ofFloat(getAppCompatActivity().findViewById(R.id.rl_choose), "translationY", distance1);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    getAppCompatActivity().getSupportActionBar().hide();
                }
            });
            animator1.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    getAppCompatActivity().findViewById(R.id.rl_choose).setVisibility(View.GONE);
                }
            });
        } else {
            animator = ObjectAnimator.ofFloat(getAppCompatActivity().findViewById(R.id.toolbar), "translationY", -distance, 0);
            animator1 = ObjectAnimator.ofFloat(getAppCompatActivity().findViewById(R.id.rl_choose), "translationY", distance1, 0);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    getAppCompatActivity().getSupportActionBar().show();
                }
            });
            animator1.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    getAppCompatActivity().findViewById(R.id.rl_choose).setVisibility(View.VISIBLE);
                }
            });
        }
        animator.setDuration(200);
        animator1.setDuration(200);
        animator.setInterpolator(new LinearInterpolator());
        animator1.setInterpolator(new LinearInterpolator());
        animator.start();
        animator1.start();
    }

}
