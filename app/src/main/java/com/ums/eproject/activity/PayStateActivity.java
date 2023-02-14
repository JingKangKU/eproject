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
import com.ums.eproject.utils.UIHelp;

public class PayStateActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;
    private ImageView pay_state_img;
    private TextView pay_state_desc;
    private int payState;
    private int jumpType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_state);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);
        findViewById(R.id.title_back).setOnClickListener(this);
        title_text.setText("支付结果");
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(new LayoutAdapter() {
            @Override
            public void onAdjustLayoutPadding(ImmersiveLayout layout) {
                title_view.setPadding(0, layout.getPaddingTop(), 0, 0);
            }
        });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();

        pay_state_img = findViewById(R.id.pay_state_img);
        pay_state_desc = findViewById(R.id.pay_state_desc);


        Bundle bundle = getIntent().getBundleExtra("bundle");
        payState = bundle.getInt("payState",-1);
        jumpType = bundle.getInt("jumpType");

        if (payState == Constant.pay_State_success){
            pay_state_img.setImageResource(R.mipmap.audit_ing);
//        }else if(payState == Constant.pay_State_error){
//            pay_state_img.setImageResource(R.mipmap.audit_err);
        } else{
            pay_state_img.setImageResource(R.mipmap.audit_succ);
        }
        pay_state_desc.setText(Constant.getPayStateLabel(payState));
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

    @Override
    public void finish() {
        if (jumpType == Constant.jumpType_goods){
            UIHelp.startActivity(context,GoodsDetailActivity.class);
        }else if (jumpType == Constant.jumpType_topup){
            UIHelp.startActivity(context,TopupActivity.class);
        }

        super.finish();
    }
}