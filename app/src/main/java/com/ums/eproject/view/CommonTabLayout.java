package com.ums.eproject.view;


import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;

public class CommonTabLayout extends TabLayout {
    private static final int TabViewNumber = 5;
    private static final String SCROLLABLE_TAB_MIN_WIDTH = "mScrollableTabMinWidth";
    public CommonTabLayout(Context context) {
        super(context);
        initTabMinWidth();
    }

    public CommonTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTabMinWidth();
    }

    public CommonTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTabMinWidth();
    }
    private void initTabMinWidth() {
        int screenWidth=getResources().getDisplayMetrics().widthPixels;
        int tabMinWidth = screenWidth / TabViewNumber;

        Field field;
        try {
            field = TabLayout.class.getDeclaredField(SCROLLABLE_TAB_MIN_WIDTH);
            field.setAccessible(true);
            field.set(this, tabMinWidth);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
