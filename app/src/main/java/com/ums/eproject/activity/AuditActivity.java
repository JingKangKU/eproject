package com.ums.eproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.ums.eproject.R;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.UIHelp;

public class AuditActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);
        title_text.setText("实名认证类型");
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(new LayoutAdapter() {
            @Override
            public void onAdjustLayoutPadding(ImmersiveLayout layout) {
                title_view.setPadding(0, layout.getPaddingTop(), 0, 0);
            }
        });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();

        findViewById(R.id.title_back).setOnClickListener(this);
        findViewById(R.id.rl_audit_cr).setOnClickListener(this);
        findViewById(R.id.rl_audit_xs).setOnClickListener(this);
        findViewById(R.id.rl_audit_yj).setOnClickListener(this);
        findViewById(R.id.rl_audit_ln).setOnClickListener(this);
        findViewById(R.id.rl_audit_yg).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.rl_audit_cr:
//                goToAudit(Constant.AUDIT_TYPE_CR_VAL);
                goToAudit(Constant.AUDIT_TYPE_LOGIN_AUDIT_VAL);
                break;
            case R.id.rl_audit_xs:
                goToAudit(Constant.AUDIT_TYPE_XS_VAL);
                break;
            case R.id.rl_audit_yj:
                goToAudit(Constant.AUDIT_TYPE_YJ_VAL);
                break;
            case R.id.rl_audit_ln:
                goToAudit(Constant.AUDIT_TYPE_LN_VAL);
                break;
            case R.id.rl_audit_yg:
                goToAudit(Constant.AUDIT_TYPE_YG_VAL);
                break;
        }
    }

    private void goToAudit(int auditTypeVal){
        Bundle bundle = new Bundle();
        bundle.putSerializable("auditTypeVal",auditTypeVal);
        UIHelp.startActivity(this,AuthenticationActivity.class,bundle);
    }

}