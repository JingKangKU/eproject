package com.chinaums.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.chinaums.common.utils.core.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * bitmap处理工具类
 */
public class UMSBitmapUtil {

    /**
     * bitmap转成byte数组
     */
    public static byte[] bitmapToByte(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    /**
     * byte数组转成bitmap
     */
    public static Bitmap byteToBitmap(byte[] bytes) {
        return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Drawable转成Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        return drawable == null ? null : ((BitmapDrawable) drawable).getBitmap();
    }

    /**
     * Bitmap转成Drawable
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(Utils.getContext().getResources(), bitmap);
    }

    /**
     * 旋转图片
     * @param angle  角度 90 180 270等
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int angle) {
        // 旋转图片
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 返回新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 将图片保存到本地
     *
     * @param bitmap 位图
     * @param file   存储的文件
     */
    public static void bitmapToFile(Bitmap bitmap, File file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩bitmap 质量压缩
     *
     * @param quality 0-100 越小压缩越强
     */
    public static Bitmap compressBitmapQuality(Bitmap bitmap, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 压缩bitmap 采样率压缩
     *
     * @param sampleSize 如2 即宽高为原来的一半，面积为原来的四分之一
     */
    public static Bitmap compressBitmapOption(Bitmap bitmap, int sampleSize) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inSampleSize = sampleSize;
        ByteArrayInputStream isBm = new ByteArrayInputStream(out.toByteArray());
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /**
     * 压缩bitmap 缩放压缩
     *
     * @param widthScale  如0.5f 即为原来宽高的一半
     * @param heightScale 如0.5f 即为原宽高的一半
     */
    public static Bitmap compressBitmapMatrix(Bitmap bitmap, float widthScale, float heightScale) {
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        Bitmap temp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return temp;
    }

    /**
     * 合并Bitmap,保持原有大小不变
     *
     * @param backBitmap  背景Bitmap
     * @param frontBitmap 前景Bitmap
     * @return 合成后的Bitmap
     */
    public static Bitmap combineBitmap(Bitmap backBitmap, Bitmap frontBitmap) {
        Bitmap bmp;

        int width = Math.max(backBitmap.getWidth(), frontBitmap.getWidth());
        int height = Math.max(backBitmap.getHeight(), frontBitmap.getHeight());

        bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(backBitmap, 0, 0, null);
        canvas.drawBitmap(frontBitmap, 0, 0, paint);

        return bmp;
    }

    /**
     * 放大缩小图片
     *
     * @param bitmap 源Bitmap
     * @param w      宽
     * @param h      高
     * @return 目标Bitmap
     */
    private static Bitmap zoom(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 生成水印图片 水印在右下角
     *
     * @param bitmap     原bitmap
     * @param markBitmap 水印图片
     */
    public static Bitmap getMarkBitmap(Bitmap bitmap, Bitmap markBitmap) {
        if (bitmap == null) {
            return null;
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int ww = markBitmap.getWidth();
        int wh = markBitmap.getHeight();
        // create the new blank bitmap
        Bitmap newB = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newB);
        // draw src into
        cv.drawBitmap(bitmap, 0, 0, null);// 在 0，0坐标开始画入src
        // draw watermark into
        cv.drawBitmap(markBitmap, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
        // save all clip
        cv.save();// 保存
        // store
        cv.restore();// 存储
        return newB;
    }

    /**
     * 圆bitmap
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.TRANSPARENT);
        canvas.drawCircle(width / 2f, height / 2f, width / 2f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
