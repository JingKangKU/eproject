package com.ums.eproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.ums.eproject.R;
import com.ums.eproject.utils.Constant;

public class PayStateActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text,audit_state_desc,audit_state_card_type;
    private ImageView audit_state_img;
    private int auditState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_state);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);
        findViewById(R.id.title_back).setOnClickListener(this);
        title_text.setText("泰e通商城GO");
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(new LayoutAdapter() {
            @Override
            public void onAdjustLayoutPadding(ImmersiveLayout layout) {
                title_view.setPadding(0, layout.getPaddingTop(), 0, 0);
            }
        });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();

        audit_state_img = findViewById(R.id.audit_state_img);
        audit_state_desc = findViewById(R.id.audit_state_desc);
        audit_state_card_type = findViewById(R.id.audit_state_card_type);
        audit_state_card_type.setText("成人卡");
//
//        Bundle bundle = getIntent().getBundleExtra("bundle");
//        auditState = bundle.getInt("auditState");

        if (auditState == Constant.AUDIT_STATE_SUCCESS){
            audit_state_img.setImageResource(R.mipmap.audit_ing);
        }else if(auditState == Constant.AUDIT_STATE_ERR){
            audit_state_img.setImageResource(R.mipmap.audit_err);
        }else{
            audit_state_img.setImageResource(R.mipmap.audit_succ);
        }
        audit_state_desc.setText(Constant.getAuditStateLabel(auditState));
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

        }
    }

}