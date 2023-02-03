package com.chinaums.common.utils;

import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.chinaums.common.utils.core.Utils;
import com.google.android.material.snackbar.Snackbar;

public class UMSToastUtil {

    private static Toast toast;
    private static Snackbar snackbar;

    /**
     * 默认toast
     *
     * @param message 内容
     */
    public static void showToast(String message) {
        boolean isMain = true;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            isMain = false;
        }
        if (!isMain) {
            Looper.prepare();
        }

        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(Utils.getContext(), message, Toast.LENGTH_SHORT);
        toast.show();

        if (!isMain) {
            Looper.loop();
        }
    }

    /**
     * 显示snackBar，不带action
     *
     * @param view    任意当前layout的view
     * @param message 内容
     */
    public static void showSnackBar(View view, String message) {
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    /**
     * 显示snackBar，带action
     *
     * @param view           任意当前layout的view
     * @param message        内容
     * @param actionText     action文本
     * @param actionListener action监听器
     */
    public static void showSnackBar(View view, String message, String actionText, View.OnClickListener actionListener) {
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        if (actionListener == null) {
            actionListener = v -> snackbar.dismiss();
        }
        snackbar.setAction(actionText, actionListener);
        snackbar.show();
    }

}
