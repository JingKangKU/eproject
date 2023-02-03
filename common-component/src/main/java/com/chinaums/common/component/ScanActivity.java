package com.chinaums.common.component;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.chinaums.common.component.picture.PicPicker;
import com.chinaums.common.utils.UMSStringUtil;
import com.chinaums.common.utils.UMSToastUtil;
import com.chinaums.common.utils.core.Utils;

import java.util.Calendar;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

/**
 * 二维码、条码扫码页
 * 进入前需申请相机、相册权限
 */
public class ScanActivity extends AppCompatActivity implements QRCodeView.Delegate {

    private ZBarView mZBarView;
    private TextView mTvClose, mTvAlbum;
    private ImageButton btnFlash;
    private boolean isFlashOn = false;
    private String scanTip;


    public static final int RESULT_ERROR = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        scanTip = getIntent().getStringExtra("scanTip");
        mZBarView = findViewById(R.id.view_scan);
        if (UMSStringUtil.isNotEmpty(scanTip))
            mZBarView.getScanBoxView().setQRCodeTipText(scanTip);
        mTvClose = findViewById(R.id.tv_back);
        mTvAlbum = findViewById(R.id.tv_album);
        btnFlash = (ImageButton) findViewById(R.id.btn_flash);
        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!isFlashOn) {
                        mZBarView.openFlashlight();
                        isFlashOn = true;
                    } else {
                        mZBarView.closeFlashlight();
                        isFlashOn = false;
                    }

                } catch (Exception e) {
                    UMSToastUtil.showToast("闪光灯操作异常：" + e.getMessage());
                }
//                if (isFlashOn) {
//                    // 关闭闪光灯
//                    btnFlash.setImageResource(R.drawable.flash_on);
//                } else {
//                    // 开启闪光灯
//                    btnFlash.setImageResource(R.drawable.flash_off);
//                }
            }
        });

        mTvClose.setOnClickListener(view -> finish());
        mTvAlbum.setOnClickListener(view -> {
            PicPicker.create(this)
                    .mode(PicPicker.Mode.ALBUM)
                    .maxNum(1)
                    .isCompress(false)
                    .listener(pics -> {
                        mZBarView.showScanRect();
                        mZBarView.decodeQRCode(pics.get(0).getPath());
                    })
                    .start();
        });

        mZBarView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZBarView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        mZBarView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void onStop() {
        mZBarView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZBarView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        if (result == null || result.length() <= 0) {
            UMSToastUtil.showToast("未能正确识别二维码，请重试");
            mZBarView.startSpotAndShowRect();
            return;
        }

        Intent intent = getIntent();
        intent.putExtra("result", result);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        // 打开相机出错
        Intent intent = getIntent();
        intent.putExtra("error", "打开相机出错");
        setResult(RESULT_ERROR, intent);
        finish();
    }
}
