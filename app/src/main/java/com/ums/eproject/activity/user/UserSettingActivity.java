package com.ums.eproject.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.tools.SPUtils;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.ums.eproject.R;
import com.ums.eproject.activity.BaseActivity;
import com.ums.eproject.activity.LoginActivity;
import com.ums.eproject.bean.MemberBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.UIHelp;

import es.dmoral.toasty.Toasty;

public class UserSettingActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;


    private LinearLayout user_setting_agreement,user_setting_reLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);
        title_text.setText("设置");
        findViewById(R.id.title_back).setOnClickListener(this);

        // 请求沉浸式布局
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(layout -> title_view.setPadding(0, layout.getPaddingTop(), 0, 0));
        immersiveLayout.requestLayout();

        findViewById(R.id.user_setting_agreement).setOnClickListener(this);
        findViewById(R.id.user_setting_reLogin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.user_setting_agreement:
                UIHelp.startActivity(context,UserAgreementListActivity.class);
                break;
            case R.id.user_setting_reLogin:
                reLogin();
                break;
        }
    }

    private void reLogin() {
        SPUtils.getInstance().put("login_name","");
        SPUtils.getInstance().put("tokenReq", "");
        SPUtils.getInstance().put("token", "");
        SPUtils.getInstance().put("tokenHead", "");

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
    }
}