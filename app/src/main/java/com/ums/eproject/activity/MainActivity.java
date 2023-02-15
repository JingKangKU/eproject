package com.ums.eproject.activity;

import static com.chinaums.common.utils.CommonConst.REQUEST_SCAN;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinaums.common.component.ScanActivity;
import com.chinaums.common.utils.CommonConst;
import com.chinaums.common.utils.permission.PermissionListener;
import com.chinaums.common.utils.permission.UMSPermissionUtil;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.ums.eproject.R;
import com.ums.eproject.bean.AuthBean;
import com.ums.eproject.bean.BaseRequest;
import com.ums.eproject.bean.CBPayResultBean;
import com.ums.eproject.bean.MemberBean;
import com.ums.eproject.bean.UserBean;
import com.ums.eproject.fragment.CodeFragment;
import com.ums.eproject.fragment.HomeFragment;
import com.ums.eproject.fragment.UserFragment;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.SPUtils;
import com.ums.eproject.utils.UIHelp;
import com.ums.eproject.view.ConfirmDialog;

import org.json.JSONObject;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout home_title, user_title, main_bottom;
    private FragmentManager fragmentManager;
    private UserFragment userFragment;

    private HomeFragment homeFragment;
    private CodeFragment codeFragment;
    private TextView bar_user, bar_code, bar_main;
    private int paddingTop = 0;

    private LinearLayout linear_user_audit;
    private Context context;

    //UserFlagMent相关
    private TextView user_info_mobile, user_info_card_type;
    private LinearLayout linear_user_audit_state_succ, linear_user_audit_state_none;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        home_title = findViewById(R.id.title_view);
        user_title = findViewById(R.id.user_title);
        main_bottom = findViewById(R.id.main_bottom);
        bar_main = findViewById(R.id.bar_main);
        bar_code = findViewById(R.id.bar_code);
        bar_user = findViewById(R.id.bar_user);

        //扫码
        home_title.findViewById(R.id.iv_scan).setOnClickListener(this);


        findViewById(R.id.linear_bar_user).setOnClickListener(this);
        findViewById(R.id.linear_bar_main).setOnClickListener(this);
        findViewById(R.id.linear_bar_code).setOnClickListener(this);

        //user页面的头布局子组件点击
        findViewById(R.id.linear_user_audit).setOnClickListener(this);
        user_info_mobile = findViewById(R.id.user_info_mobile);
        user_info_card_type = findViewById(R.id.user_info_card_type);
        linear_user_audit_state_succ = findViewById(R.id.linear_user_audit_state_succ);
        linear_user_audit_state_none = findViewById(R.id.linear_user_audit_state_none);
        linear_user_audit_state_succ.setOnClickListener(this);
        linear_user_audit_state_none.setOnClickListener(this);


        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(new LayoutAdapter() {
            @Override
            public void onAdjustLayoutPadding(ImmersiveLayout layout) {
                paddingTop = layout.getPaddingTop();
                home_title.setPadding(0, layout.getPaddingTop(), 0, 0);
                user_title.setPadding(0, layout.getPaddingTop(), 0, 0);
                main_bottom.setPadding(0, 0, 0, layout.getPaddingBottom() + 10);
            }
        });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();

        fragmentManager = getSupportFragmentManager();


        clickMainBtn();

        permissionGranted();
    }

    public void setUserFragmentTitleInfo(MemberBean memberBean) {
        user_info_mobile.setText(memberBean.getData().getMobile());
        user_info_card_type.setText(memberBean.getData().getMemberTypeAlias());
        if (memberBean.getData().getIsVerified() == 1) { //0未实名  1已实名
            linear_user_audit_state_succ.setVisibility(View.VISIBLE);
        } else {
            linear_user_audit_state_none.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.linear_bar_main:
                clickMainBtn();
                break;
            case R.id.linear_bar_code:
                clickCodeBtn();
                break;
            case R.id.linear_bar_user:
                clickUserBtn();
                break;
            case R.id.linear_user_audit:
                goToAudit();
                break;
            case R.id.linear_user_audit_state_succ:
                goToAuditState(Constant.AUDIT_STATE_SUCCESS);
                break;
            case R.id.linear_user_audit_state_none:
                goToAuditState(Constant.AUDIT_STATE_NONE);
                break;
            case R.id.iv_scan:
                gotoScan();
                break;
        }
    }

    private void permissionGranted() {
        UMSPermissionUtil.requestPermission(new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {

            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {

            }
        },
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void goToAudit() {

        UIHelp.startActivity(this, AuditActivity.class);

    }


    private void goToAuditState(int auditState) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("auditState", auditState);
        UIHelp.startActivity(this, AuditStateActivity.class, bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCAN && resultCode == RESULT_OK) {//二维码扫描
            try {
                String scanResult = data.getExtras().getString("result");
                busFeeC2BPay(scanResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void gotoScan() {
        UMSPermissionUtil.requestPermission(new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                Intent i = new Intent(MainActivity.this, ScanActivity.class);
                i.putExtra("scanTip", "泰e通免密乘车");
                startActivityForResult(i, REQUEST_SCAN);
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {

            }
        }, Manifest.permission.CAMERA);
    }

    private void clickMainBtn() {
        ImmersiveLayout.darkStatusBar(this);
        bar_main.setTextColor(Color.parseColor("#2288D9"));
        bar_code.setTextColor(Color.parseColor("#ffffff"));
        bar_user.setTextColor(Color.parseColor("#ff3c3b3b"));

        home_title.setVisibility(View.VISIBLE);
        user_title.setVisibility(View.GONE);

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        if (userFragment != null) {
            transaction.hide(userFragment);
        }
        if (codeFragment != null) {
            transaction.hide(codeFragment);
        }
        if (!homeFragment.isAdded()) {
            transaction.add(R.id.fragment, homeFragment);
        } else {
            transaction.show(homeFragment);
        }
        transaction.commit();
    }

    private void clickCodeBtn() {
        ImmersiveLayout.lightStatusBar(this);
        bar_main.setTextColor(Color.parseColor("#ff3c3b3b"));
        bar_code.setTextColor(Color.parseColor("#ff3c3b3b"));
        bar_user.setTextColor(Color.parseColor("#ff3c3b3b"));

        home_title.setVisibility(View.GONE);
        user_title.setVisibility(View.GONE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (codeFragment == null) {
            codeFragment = new CodeFragment(paddingTop);
        }
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (userFragment != null) {
            transaction.hide(userFragment);
        }
        if (!codeFragment.isAdded()) {
            transaction.add(R.id.fragment, codeFragment);
        } else {
            transaction.show(codeFragment);
        }
        transaction.commit();
    }

    private void clickUserBtn() {
        ImmersiveLayout.darkStatusBar(this);
        bar_main.setTextColor(Color.parseColor("#ff3c3b3b"));
        bar_code.setTextColor(Color.parseColor("#ffffff"));
        bar_user.setTextColor(Color.parseColor("#2288D9"));

        home_title.setVisibility(View.GONE);
        user_title.setVisibility(View.VISIBLE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (userFragment == null) {
            userFragment = new UserFragment();
        }
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (codeFragment != null) {
            transaction.hide(codeFragment);
        }
        if (!userFragment.isAdded()) {
            transaction.add(R.id.fragment, userFragment);
        } else {
            transaction.show(userFragment);
        }
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 顶部整体比较亮，则启用明亮状态栏模式
//         ImmersiveLayout.lightStatusBar(this);
        // 顶部整体比较暗，则启用暗黑状态栏模式
//         ImmersiveLayout.darkStatusBar(this);
        // 底部整体比较亮，则启用明亮导航栏模式
        ImmersiveLayout.lightNavigationBar(this);
        // 底部整体比较暗，则启用暗黑导航栏模式
        // ImmersiveLayout.darkNavigationBar(this);
    }

    private void busFeeC2BPay(String result) {
        CommRequestApi.getInstance().busFeeC2BPay(result, new HttpSubscriber<>(new SubscriberOnListener<BaseRequest<CBPayResultBean>>() {
            @Override
            public void onSucceed(BaseRequest<CBPayResultBean> bean) {
                if (bean.getCode() == 200) {
                    if (null != bean.getData()) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", bean.getData());
                        UIHelp.startActivity(MainActivity.this, TravelResultActivity.class, bundle);
                    }
                } else {
                    MsgUtil.showCustom(context, bean.getMessage());
                }
            }

            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

            }
        }, context));
    }

    @Override
    public void finish() {
        ConfirmDialog confirmDialog = new ConfirmDialog(context, "确认退出泰e通？");
        confirmDialog.setOnAction(new ConfirmDialog.OnAction() {
            @Override
            public void onCancleClicked(ConfirmDialog confirmDialog) {
                confirmDialog.dismiss();
            }

            @Override
            public void onConfirmClicked(ConfirmDialog confirmDialog) {
                confirmDialog.dismiss();
                mFinish();

            }
        });
        confirmDialog.show();


    }

    private void mFinish() {
        super.finish();
    }
}