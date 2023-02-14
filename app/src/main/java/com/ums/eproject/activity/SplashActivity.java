package com.ums.eproject.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chinaums.common.utils.permission.PermissionListener;
import com.chinaums.common.utils.permission.UMSPermissionUtil;
import com.luck.picture.lib.tools.SPUtils;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.ums.eproject.R;
import com.ums.eproject.app.AppContext;
import com.ums.eproject.bean.BaseRequest;
import com.ums.eproject.bean.CBPayResultBean;
import com.ums.eproject.bean.DynamicLink;
import com.ums.eproject.bean.StartAdvertise;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.DoubleUitl;
import com.ums.eproject.utils.MsgUtil;

import com.ums.eproject.utils.StrUtil;
import com.ums.eproject.utils.UIHelp;
import com.ums.eproject.view.CountDownView;

import java.util.TimerTask;

import es.dmoral.toasty.Toasty;

public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private ImageView splash_img;

    private CountDownView countDownView;

    private boolean isJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(new LayoutAdapter() {
            @Override
            public void onAdjustLayoutPadding(ImmersiveLayout layout) {
//                home_title.setPadding(0, layout.getPaddingTop(), 0, 0);
            }
        });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();

        initCountDownView();

        splash_img = findViewById(R.id.splash_img);
        double wsh = UIHelp.getWsH(this);
        new Handler().postDelayed(new Runnable(){ public void run() {
            listStartAdvertise(wsh);

        } }, 0);

    }

    private void initCountDownView() {
        countDownView = findViewById(R.id.countDownView);
        countDownView.setOnCountDownListener(new CountDownView.OnCountDownListener() {
            @Override
            public void countDownFinished() {
                toLoginOrMain();

            }
        });

        countDownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownView.stopCountDdwn();
                toLoginOrMain();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_bar_main:

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.lightStatusBar(this);
//        ImmersiveLayout.lightNavigationBar(this);



    }

    private void listStartAdvertise(double screenRate) {
        CommRequestApi.getInstance().listStartAdvertise(screenRate,new HttpSubscriber<>(new SubscriberOnListener<StartAdvertise>() {
            @Override
            public void onSucceed(StartAdvertise data) {
                if (data.getCode() == 200){
                    if (data.getData().size()>0){
                        ImmersiveLayout.darkStatusBar(SplashActivity.this);
                        setImageViewUrl(data.getData().get(0).getImageUrl());
                    }else{
                        toLoginOrMain();
                    }
                }else{
                    toLoginOrMain();
                    MsgUtil.showCustom(context,data.getMessage());
                }
            }
            @Override
            public void onError(int code, String msg) {
                toLoginOrMain();
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

            }
        }, context));
    }

    private void setImageViewUrl(String url) {
        countDownView.startCountDown();
        Glide.with(context).load(url).into(splash_img);
//        toLoginOrMain();
    }

    private void toLoginOrMain() {
        if (isJump){
            return;
        }
        isJump = true;

        String tokenReq = SPUtils.getInstance().getString("tokenReq");
        if (StrUtil.isEmpty(tokenReq)){
            UIHelp.startActivity(context,LoginActivity.class);
        }else{
            UIHelp.startActivity(context,MainActivity.class);
        }
        finish();
    }
    @Override
    public void onBackPressed() {
        //拦截返回事件
//        super.onBackPressed();
    }


}