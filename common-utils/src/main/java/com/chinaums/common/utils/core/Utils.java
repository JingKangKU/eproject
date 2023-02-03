package com.chinaums.common.utils.core;

import android.content.Context;

import com.chinaums.common.utils.exception.GlobalException;

public class Utils {

    private static Utils instance;
    private static Context uContext;

    public Utils(Context context) {
        uContext = context;
    }

    public static Utils getInstance(Context context) {
        if (instance == null) instance = new Utils(context);
        return instance;
    }

    public static Context getContext() {
        if (uContext == null) {
            throw new GlobalException(GlobalException.APPLICATION_CONTEXT_IS_NULL);
        }
        return uContext;
    }
}
