package com.ums.eproject.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class GlideUtil {
    /**
     *
     * @param context context对象
     * @param imageUrl 图片网络地址
     * @param erroImageId 载入失败图片网络地址
     * @param placeImageId 占位图片网络地址
     * @param imageView 要载入图片的ImageView对象
     */
    public static void loadPicsFitWidth(Context context, final String imageUrl, int erroImageId, int placeImageId, final ImageView imageView) {
        Glide.with(context).load(imageUrl).skipMemoryCache(true).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                /*
                 * 在此处填写具体载入逻辑*/

                /*
                判断imageView对象是否为空
                 */
                if (imageView == null){
                    return false;
                }
                /*
                判断imageView的填充方式,如果不是fitxy的填充方式 设置其填充方式
                 */
                if(imageView.getScaleType()!=ImageView.ScaleType.FIT_XY){
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                /*
                进行宽度为matchparent时的适应imageView的高度计算
                 */
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                float scale = (float) vw /(float) resource.getIntrinsicWidth();
                int vh = Math.round(resource.getIntrinsicHeight() * scale);
                params.height = vh + imageView.getPaddingTop()+imageView.getPaddingBottom();
                imageView.setLayoutParams(params);
                return false;
            }
        }).placeholder(placeImageId).error(erroImageId).into(imageView);
    }

}
