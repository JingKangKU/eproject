package com.ums.eproject.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.ums.eproject.R;
import com.ums.eproject.activity.BaseActivity;
import com.ums.eproject.activity.CommonWebViewActivity;
import com.ums.eproject.adapter.ViewPagerOrderAdapter;
import com.ums.eproject.fragment.OrderFragment;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.UIHelp;

import java.util.ArrayList;

public class UserAgreementListActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement_list);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);
        title_text.setText("协议列表");
        findViewById(R.id.title_back).setOnClickListener(this);
        // 请求沉浸式布局
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(layout -> title_view.setPadding(0, layout.getPaddingTop(), 0, 0));
        immersiveLayout.requestLayout();

        findViewById(R.id.user_agreement_yh).setOnClickListener(this);
        findViewById(R.id.user_agreement_ys).setOnClickListener(this);
        findViewById(R.id.user_agreement_mm).setOnClickListener(this);

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
            case R.id.user_agreement_yh:
                showAgreement("用户服务协议",Constant.agreement_yh);
                break;
            case R.id.user_agreement_ys:
                showAgreement("隐私政策",Constant.agreement_ys);
                break;
            case R.id.user_agreement_mm:
                showAgreement("余额免密支付协议",Constant.agreement_mm);
                break;
        }
    }

    private void showAgreement(String titleText,String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        bundle.putString("titleText",titleText);
        bundle.putBoolean("supportZoom",true);
        UIHelp.startActivity(context, CommonWebViewActivity.class,bundle);
    }


}