package com.ums.eproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.ScrollBoundaryDecider;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.impl.ScrollBoundaryDeciderAdapter;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.ums.eproject.R;
import com.ums.eproject.activity.MainActivity;


/**
 * Created by jk
 * Data 2018/9/25
 */

public class UIHelp {
    private static PopupWindow window_mode1;
    private static PopupWindow window_mode2;

    /* 界面跳转 */

    public static void startActivity(Context context, Class clazz) {
        context.startActivity(new Intent(context, clazz));
    }

    public static void startActivityNewTask(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);

    }

    public static void startActivity(Activity context, Class clazz, int reqCode) {
        Intent intent = new Intent(context, clazz);
        context.startActivityForResult(intent, reqCode);
    }

    public static void startActivity(Activity context, Class clazz, Bundle bundle, int reqCode) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra("bundle", bundle);
        context.startActivityForResult(intent, reqCode);
    }

    /*  应用退出  */
    public static void exitAPP(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("exit", true);
        context.startActivity(intent);
        //System.exit(0);
    }

    public static void initSmartRefreshLayout(SmartRefreshLayout refreshLayout) {
//        refreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
//        refreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
//        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
//        refreshLayout.setHeaderHeight(100);//Header标准高度（显示下拉高度>=标准高度 触发刷新）
//        refreshLayout.setFooterHeight(100);//Footer标准高度（显示上拉高度>=标准高度 触发加载）
//        refreshLayout.setEnableOverScrollDrag(false);//是否启用越界拖动（仿苹果效果）1.0.4
//        refreshLayout.setHeaderMaxDragRate(2);//最大显示下拉高度/Header标准高度
//        refreshLayout.setFooterMaxDragRate(2);//最大显示下拉高度/Footer标准高度
//        refreshLayout.setHeaderTriggerRate(1);//触发刷新距离 与 HeaderHeight 的比率1.0.4
//        refreshLayout.setFooterTriggerRate(1);//触发加载距离 与 FooterHeight 的比率1.0.4
//
//        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
//        refreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
//        refreshLayout.setEnableAutoLoadMore(true);//是否启用列表惯性滑动到底部时自动加载更多
//        refreshLayout.setEnablePureScrollMode(false);//是否启用纯滚动模式
//        refreshLayout.setEnableNestedScroll(false);//是否启用嵌套滚动
//        refreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
//        refreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
//        refreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
//        refreshLayout.setEnableFooterTranslationContent(true);//是否上拉Footer的时候向上平移列表或者内容
//        refreshLayout.setEnableLoadMoreWhenContentNotFull(true);//是否在列表不满一页时候开启上拉加载功能
//        refreshLayout.setEnableScrollContentWhenRefreshed(true);//是否在刷新完成时滚动列表显示新的内容 1.0.5
//        refreshLayout.setDisableContentWhenRefresh(false);//是否在刷新的时候禁止列表的操作
//        refreshLayout.setDisableContentWhenLoading(false);//是否在加载的时候禁止列表的操作
//        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener());//设置多功能监听器
//        refreshLayout.setScrollBoundaryDecider(new ScrollBoundaryDeciderAdapter());//自定义滚动边界
//        refreshLayout.setRefreshHeader(new ClassicsHeader(context));//设置Header
//        refreshLayout.setRefreshFooter(new ClassicsFooter(context));//设置Footer
//        refreshLayout.setRefreshContent(new View(context));//设置刷新Content（用于非xml布局代替addView）1.0.4
//        refreshLayout.autoRefresh();//自动刷新
//        refreshLayout.autoLoadMore();//自动加载
//        refreshLayout.autoRefreshAnimationOnly();//自动刷新，只显示动画不执行刷新
//        refreshLayout.autoLoadMoreAnimationOnly();//自动加载，只显示动画不执行加载
//        refreshLayout.finishLoadMore();//结束加载
//        refreshLayout.finishRefresh(3000);//延迟3000毫秒后结束刷新
//        refreshLayout.finishLoadMore(3000);//延迟3000毫秒后结束加载
//        refreshLayout.finishRefresh(false);//结束刷新（刷新失败）
//        refreshLayout.finishLoadMore(false);//结束加载（加载失败）
//        refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
//        refreshLayout.closeHeaderOrFooter();//关闭正在打开状态的 Header 或者 Footer（1.1.0）
//        refreshLayout.resetNoMoreData();//恢复没有更多数据的原始状态 1.0.4（1.1.0删除）
//        refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态 1.0.5
    }

    public static void closePopupWindow1(Activity activity) {
        if (window_mode1 != null) {
            if (window_mode1.isShowing()) {
                window_mode1.dismiss();
                window_mode1 = null;
                lighton(activity);
            }
        }
    }

    public static void closePopupWindow2(Activity activity) {
        if (window_mode2 != null) {
            if (window_mode2.isShowing()) {
                window_mode2.dismiss();
                window_mode2 = null;
                lighton(activity);
            }
        }
    }


    private static int makeDropDownMeasureSpec(int measureSpec) {
        int mode;
        if (measureSpec == ViewGroup.LayoutParams.WRAP_CONTENT) {
            mode = View.MeasureSpec.UNSPECIFIED;
        } else {
            mode = View.MeasureSpec.EXACTLY;
        }
        return View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(measureSpec), mode);
    }


    /*暗*/
    public static void lightoff(Activity context) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.3f;
        context.getWindow().setAttributes(lp);
    }

    /*亮*/
    public static void lighton(Activity context) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 1f;
        context.getWindow().setAttributes(lp);
    }


    public static void showPopupWindow(final Activity activity, View view, View contentView, int transdefault) {
        lightoff(activity);

        ImageView item_1, item_2, item_3, item_4;
        item_1 = contentView.findViewById(R.id.popup_item1_selected);
        item_2 = contentView.findViewById(R.id.popup_item2_selected);
        item_3 = contentView.findViewById(R.id.popup_item3_selected);
        item_4 = contentView.findViewById(R.id.popup_item4_selected);
        ImageView[] itemViews = new ImageView[]{item_1, item_2, item_3, item_4};
        for (int i = 0; i < itemViews.length; i++) {
            if (i == transdefault) {
                itemViews[i].setImageResource(R.mipmap.selected);
            } else {
                itemViews[i].setImageResource(R.mipmap.select);
            }
        }

        window_mode1 = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window_mode1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window_mode1.setOutsideTouchable(true);
        window_mode1.setTouchable(true);
        window_mode1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lighton(activity);
            }
        });
        window_mode1.setAnimationStyle(R.style.pop_animTranslate);
        contentView.measure(makeDropDownMeasureSpec(window_mode1.getWidth()), (window_mode1.getHeight()));
        window_mode1.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    //获取屏幕的宽度
    public static int getScreenWidth(Activity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            int width = activity.getWindowManager().getCurrentWindowMetrics().getBounds().width();

            Insets insets = activity.getWindowManager().getCurrentWindowMetrics().getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
          return width - insets.right - insets.left;
        } else {
            //获取减去系统栏的屏幕的高度和宽度
            DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
            return displayMetrics.widthPixels;
        }

    }

    //获取屏幕的高度
    public static int getScreenHeight(Activity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            int width = activity.getWindowManager().getCurrentWindowMetrics().getBounds().width();
            int height = activity.getWindowManager().getCurrentWindowMetrics().getBounds().height();

            Insets insets = activity.getWindowManager().getCurrentWindowMetrics().getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return  (height - insets.bottom - insets.top);
        } else {
            //获取减去系统栏的屏幕的高度和宽度
            DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
            return displayMetrics.heightPixels;
        }
    }


}
