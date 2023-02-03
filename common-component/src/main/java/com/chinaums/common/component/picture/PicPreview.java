package com.chinaums.common.component.picture;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.chinaums.common.component.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;
/**
 * 图片预览
 */
public class PicPreview {

    private final Activity mContext;
    private final List<LocalMedia> mediaList = new ArrayList<>();
    private int position = 0;
    private int type = Type.NET_PIC;

    private PicPreview(@NonNull Activity activity) {
        mContext = activity;
    }

    public void start() {
        if (mediaList == null || mediaList.size() <= 0)
            return;

        if (position >= mediaList.size() || position < 0)
            position = 0;

        PictureSelector.create(mContext)
                .themeStyle(R.style.picture_default_style)
                .isNotPreviewDownload(true)
                .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .openExternalPreview(position, mediaList);
    }

    public static PicPreview create(Activity activity) {
        return new PicPreview(activity);
    }

    public PicPreview setData(List<String> pics) {
        mediaList.clear();
        for (String pic: pics) {
            LocalMedia localMedia = new LocalMedia();
            if (type == Type.BASE64_PIC && !pic.contains("base64")) {
                localMedia.setPath("data:image/png;base64," + pic);
            } else {
                localMedia.setPath(pic);
            }
            mediaList.add(localMedia);
        }
        return this;
    }

    public PicPreview setPosition(int position) {
        this.position = position;
        return this;
    }

    public PicPreview setType(int type) {
        this.type = type;
        return this;
    }

    public static class Type {
        public static int NET_PIC = 0;
        public static int LOCAL_PIC = 1;
        public static int BASE64_PIC = 2;
    }

}
