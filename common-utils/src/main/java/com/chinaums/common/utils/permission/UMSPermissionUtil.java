package com.chinaums.common.utils.permission;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.content.PermissionChecker;

import com.chinaums.common.utils.core.Utils;

import java.util.HashMap;

/**
 * 权限申请工具类
 */
public class UMSPermissionUtil {

    private static final HashMap<String, PermissionListener> listenerMap = new HashMap<>();

    /**
     * 启动app设置授权界面
     */
    public static void startSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        localIntent.setData(Uri.fromParts("package", Utils.getContext().getPackageName(), null));
        Utils.getContext().startActivity(localIntent);
    }

    /**
     * 判断权限是否授权
     */
    public static boolean hasPermission(@NonNull String... permissions) {
        if (permissions.length == 0) {
            return false;
        }
        for (String per : permissions) {
            int result = PermissionChecker.checkSelfPermission(Utils.getContext(), per);
            if (result != PermissionChecker.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断一组授权结果是否为授权通过
     */
    public static boolean isGranted(@NonNull int... grantResult) {
        if (grantResult.length == 0) {
            return false;
        }

        for (int result : grantResult) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 申请授权，当用户拒绝时，会显示默认一个默认的Dialog提示用户
     * @param permission 要申请的权限
     */
    public static void requestPermission(PermissionListener listener, String... permission) {
        if (listener == null)
            return;

        if (hasPermission(permission)) {
            listener.permissionGranted(permission);
        } else {
            if (Build.VERSION.SDK_INT < 23) {
                listener.permissionDenied(permission);
            } else {
                String key = String.valueOf(System.currentTimeMillis());
                listenerMap.put(key, listener);
                Intent intent = new Intent(Utils.getContext(), PermissionActivity.class);
                intent.putExtra("permission", permission);
                intent.putExtra("key", key);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getContext().startActivity(intent);
            }
        }
    }

    protected static PermissionListener fetchListener(String key) {
        return listenerMap.remove(key);
    }
}
