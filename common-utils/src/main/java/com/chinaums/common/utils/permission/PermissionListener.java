package com.chinaums.common.utils.permission;

import androidx.annotation.NonNull;

public interface PermissionListener {

    /**
     * 通过授权
     */
    void permissionGranted(@NonNull String[] permission);

    /**
     * 拒绝授权
     */
    void permissionDenied(@NonNull String[] permission);
}
