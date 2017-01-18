package com.wills.help.widget.banner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.wills.help.R;
import com.wills.help.home.model.Banner;
import com.wills.help.utils.GlideUtils;
import com.wills.help.utils.StringUtils;

import java.util.List;

/**
 * Auto scroll poster, need a DisplayImageOptions.
 *
 * @author 笨鸟不乖
 * @version 2.0.0
 * @email benniaobuguai@gmail.com
 * @Modify 2015-11-08
 * @since 2015-3-21
 */

public class AutoScrollPoster extends AutoScrollableView<com.wills.help.home.model.Banner.BannerInfo>{

    private static final String TAG = "AutoScrollPoster";

    private ScaleType mScaleType = null;
    private boolean mNeedLoadAnimation = true;
    private Drawable mLoadIndeterminateDrawable = null;

    private LayoutInflater mLayoutInflater;

    private LinearLayout linearLayout;
    private ImageView currentImageView;
    private List<Banner.BannerInfo> picLists;

    private Context context;

    public AutoScrollPoster(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoScrollPoster(Context context) {
        super(context);
        init();
    }

    private void init() {
        mLayoutInflater = LayoutInflater.from(getContext());
    }

    /**
     * 设置圆点
     * @param linearLayout
     */
    public void setIndicateLayout(Context context , AutoScrollPoster autoScrollPoster , List<com.wills.help.home.model.Banner.BannerInfo> picLists, LinearLayout linearLayout){
        this.context=context;
        this.picLists = picLists;
        this.linearLayout = linearLayout;
        addItems(picLists);
        autoScrollPoster.setOnPageChangeListener(new AutoPageChangeListener());
        initSwicher();
    }


    /**
     * Options for scaling the bounds of an image to the bounds of this view.
     *
     * @param scaleType
     */
    public void setScaleType(ScaleType scaleType) {
        this.mScaleType = scaleType;
    }

    /**
     * Needs a load animation.
     *
     * @param needAnimation
     */
    public void needLoadAnimation(boolean needAnimation) {
        this.mNeedLoadAnimation = needAnimation;
    }

    /**
     * {@link android.widget.ProgressBar#setIndeterminateDrawable(Drawable)}
     */
    public void setLoadIndeterminateDrawable(Drawable drawable) {
        this.mLoadIndeterminateDrawable = drawable;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        final View imageLayout = mLayoutInflater.inflate(R.layout.item_banner, null);
        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        if (mScaleType != null) {
            imageView.setScaleType(mScaleType);
        }

        final Banner.BannerInfo picbean = (Banner.BannerInfo) getItem(position);
        // Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view
        //  which implements ImageAware interface)
        // mImageLoader.displayImage(imageUri, imageView);
        if (!StringUtils.isNullOrEmpty(picbean.getRequesturl())){
            GlideUtils.getInstance().displayImage(context,picbean.getRequesturl(),imageView);
            view.addView(imageLayout, 0);
        }else {
            imageView.setImageResource(R.drawable.flash);
            imageView.setClickable(false);
            imageView.setEnabled(false);
            view.addView(imageLayout,0);
        }
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mOnItemViewClickListener != null) {
                    mOnItemViewClickListener.onItemViewClick(v, picbean.getRequesturl());
                }
            }
        });
        return imageLayout;
    }

    /**
     * item view click listener.
     */
    private OnItemViewClickListener mOnItemViewClickListener;

    public void setOnItemViewClickListener(OnItemViewClickListener listener) {
        this.mOnItemViewClickListener = listener;
    }

    /**
     * ItemView click
     *
     * @author 笨鸟不乖
     * @version 1.0.0
     * @email benniaobuguai@gmail.com
     * @Modify 2015-4-23
     * @since 2015-4-24
     */
    public static interface OnItemViewClickListener {
        void onItemViewClick(View view, Object object);
    }

    public class AutoPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (picLists!=null&&picLists.size()>0){
                swicherChange(position%picLists.size());
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public void initSwicher(){
        int count = picLists.size();
        if (count>1){
            linearLayout.removeAllViews();
            for(int i = 0; i < count; i++){
                ImageView img = new ImageView(getContext());
                img.setImageResource(R.drawable.banner_false);
                img.setPadding(0, 0, 0, 0);
                linearLayout.addView(img);
            }
            swicherChange(0);
        }else{
            linearLayout.setVisibility(View.GONE);
        }
    }

    //轮播点
    public void swicherChange(int position){
        if (currentImageView!=null)
            currentImageView.setImageResource(R.drawable.banner_false);
        currentImageView = (ImageView) linearLayout.getChildAt(position);
        if(currentImageView!=null){
            currentImageView.setImageResource(R.drawable.banner_true);
        }
    }
}

