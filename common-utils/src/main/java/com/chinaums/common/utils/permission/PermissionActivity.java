package com.chinaums.common.utils.permission;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class PermissionActivity extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_CODE = 64;
    private boolean isRequireCheck;

    private String[] permission;
    private String key;

    private final String defaultTitle = "帮助";
    private final String defaultContent = "当前应用缺少必要权限。\n \n 请点击 \"设置\"-\"权限\"-打开所需权限。";
    private final String defaultCancel = "取消";
    private final String defaultEnsure = "设置";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra("permission")) {
            finish();
            return;
        }

        isRequireCheck = true;
        permission = getIntent().getStringArrayExtra("permission");
        key = getIntent().getStringExtra("key");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            if (UMSPermissionUtil.hasPermission(permission)) {
                permissionsGranted();
            } else {
                requestPermissions(permission); // 请求权限,回调时会触发onResume
                isRequireCheck = false;
            }
        } else {
            isRequireCheck = true;
        }
    }

    // 请求权限兼容低版本
    private void requestPermissions(String[] permission) {
        ActivityCompat.requestPermissions(this, permission, PERMISSION_REQUEST_CODE);
    }


    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //部分厂商手机系统返回授权成功时，厂商可以拒绝权限，所以要用PermissionChecker二次判断
        if (requestCode == PERMISSION_REQUEST_CODE && UMSPermissionUtil.isGranted(grantResults)
                && UMSPermissionUtil.hasPermission(permissions)) {
            permissionsGranted();
        } else {
            showMissingPermissionDialog();
        }
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(defaultTitle);
        builder.setMessage(defaultContent);
        builder.setNegativeButton(defaultCancel, (dialog, which) -> permissionsDenied());
        builder.setPositiveButton(defaultEnsure, (dialog, which) -> UMSPermissionUtil.startSettingIntent());

        builder.setCancelable(false);
        builder.show();
    }

    private void permissionsDenied() {
        PermissionListener listener = UMSPermissionUtil.fetchListener(key);
        if (listener != null) {
            listener.permissionDenied(permission);
        }
        finish();
    }

    // 全部权限均已获取
    private void permissionsGranted() {
        PermissionListener listener = UMSPermissionUtil.fetchListener(key);
        if (listener != null) {
            listener.permissionGranted(permission);
        }
        finish();
    }

    protected void onDestroy() {
        UMSPermissionUtil.fetchListener(key);
        super.onDestroy();
    }
}
