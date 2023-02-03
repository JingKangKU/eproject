package com.chinaums.common.utils;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.TypedValue;

import com.chinaums.common.utils.core.Utils;

/**
 * 屏幕相关工具类
 */
public class UMSScreenUtil {

    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight() {
        return Utils.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth() {
        return Utils.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 判断是否横屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isLandscape() {
        return Utils.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPortrait() {
        return Utils.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusHeight() {
        int statusHeight = -1;
        int resId = Utils.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            statusHeight = Utils.getContext().getResources().getDimensionPixelSize(resId);
        } else {
            statusHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    statusHeight, Resources.getSystem().getDisplayMetrics());
        }
        return statusHeight;
    }

    /**
     * dp转px
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                Utils.getContext().getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,  spVal,
                Utils.getContext().getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     */
    public static float px2dp(float pxVal) {
        final float scale = Utils.getContext().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     */
    public static float px2sp(float pxVal) {
        return (pxVal / Utils.getContext().getResources().getDisplayMetrics().scaledDensity);
    }
}
