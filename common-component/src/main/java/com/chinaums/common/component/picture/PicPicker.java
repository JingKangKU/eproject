package com.chinaums.common.component.picture;

import android.Manifest;
import android.app.Activity;

import androidx.annotation.NonNull;

import com.chinaums.common.utils.permission.PermissionListener;
import com.chinaums.common.utils.permission.UMSPermissionUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.List;

/**
 * 图片选择器
 * 使用结束后需调用PictureFileUtils.deleteAllCacheDirFile(this)清除缓存
 */
public class PicPicker {

    private final Activity activity;
    private int maxNum = 1;
    private int mode;
    private boolean isCompress = true;
    private OnPicPickedListener listener;

    private PicPicker(@NonNull Activity activity) {
        this.activity = activity;
    }

    public void start() {
        UMSPermissionUtil.requestPermission(new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                if (mode == Mode.CAMERA) {
                    PictureSelector.create(activity)
                            .openCamera(PictureMimeType.ofImage())
                            .forResult(new OnResultCallbackListener<LocalMedia>() {
                                @Override
                                public void onResult(List<LocalMedia> result) {
                                    if (listener == null)
                                        return;
                                    listener.onPicPicked(result);
                                }

                                @Override
                                public void onCancel() {
                                    // 取消
                                }
                            });
                } else {
                    PictureSelector.create(activity)
                            .openGallery(PictureMimeType.ofImage())
                            .imageEngine(GlideEngine.createGlideEngine())
                            .maxSelectNum(maxNum)
                            .isCompress(isCompress)
                            .forResult(new OnResultCallbackListener<LocalMedia>() {
                                @Override
                                public void onResult(List<LocalMedia> result) {
                                    if (listener == null)
                                        return;
                                    listener.onPicPicked(result);
                                }

                                @Override
                                public void onCancel() {
                                    // 取消
                                }
                            });
                }
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {

            }
        }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static PicPicker create(Activity activity) {
        return new PicPicker(activity);
    }

    public static class Mode {
        public static int ALBUM = 0;
        public static int CAMERA = 1;
    }

    public PicPicker maxNum(int val) {
        maxNum = val;
        return this;
    }

    public PicPicker mode(int val) {
        mode = val;
        return this;
    }

    public PicPicker isCompress(boolean val) {
        isCompress = val;
        return this;
    }

    public PicPicker listener(OnPicPickedListener val) {
        listener = val;
        return this;
    }

    public interface OnPicPickedListener {
        /**
         * 图片选中
         */
        void onPicPicked(List<LocalMedia> pics);
    }
}
