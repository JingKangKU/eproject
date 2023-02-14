package com.ums.eproject.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.ums.eproject.R;
import com.ums.eproject.activity.user.UserBalanceChangeActivity;
import com.ums.eproject.adapter.TopupAdapter;
import com.ums.eproject.bean.DepositRuleBean;
import com.ums.eproject.bean.DepositTrial;
import com.ums.eproject.bean.SingleOption;
import com.ums.eproject.fragment.BalanceChangeFragment;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.MsgUtil;
import com.ums.eproject.utils.StrUtil;
import com.ums.eproject.utils.UIHelp;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class TopupListActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;
    private FrameLayout fragment_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_list);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);

        findViewById(R.id.title_back).setOnClickListener(this);
        title_text.setText("充值记录");
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(new LayoutAdapter() {
            @Override
            public void onAdjustLayoutPadding(ImmersiveLayout layout) {
                title_view.setPadding(0, layout.getPaddingTop(), 0, 0);
            }
        });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();

        fragment_layout = findViewById(R.id.fragment_layout);

        initView();
    }

    private void initView() {

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new BalanceChangeFragment("", Constant.bookBalance_income)).commitAllowingStateLoss();

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_back) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
    }

}