package com.ums.eproject.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ums.eproject.R;
import com.ums.eproject.activity.BaseActivity;
import com.ums.eproject.adapter.UserBalanceAdapter;
import com.ums.eproject.adapter.UserOrderAdapter;
import com.ums.eproject.adapter.ViewPagerBalanceChangeAdapter;
import com.ums.eproject.adapter.ViewPagerOrderAdapter;
import com.ums.eproject.bean.BalanceBean;
import com.ums.eproject.bean.BookBalance;
import com.ums.eproject.bean.OrderBean;
import com.ums.eproject.fragment.BalanceChangeFragment;
import com.ums.eproject.fragment.OrderFragment;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.Constant;
import com.ums.eproject.utils.MsgUtil;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class UserBalanceChangeActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;

    TabLayout tabLayout;
    ViewPager viewpager;
    ArrayList<BalanceChangeFragment> fragments;
    ViewPagerBalanceChangeAdapter adapter;

    private TextView user_balance_amt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_balance_change);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);
        title_text.setText("余额记录查询");
        findViewById(R.id.title_back).setOnClickListener(this);

        // 请求沉浸式布局
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(layout -> title_view.setPadding(0, layout.getPaddingTop(), 0, 0));
        immersiveLayout.requestLayout();

        user_balance_amt = findViewById(R.id.user_balance_amt);

        tabLayout = findViewById(R.id.tabLayout);
        viewpager = findViewById(R.id.viewpager);

        initView();
        getAccountBalance();
    }

    private void initView() {
        //初始化数据
        fragments = new ArrayList<>();


        fragments.add(new BalanceChangeFragment("全部", Constant.bookBalance_All));
        fragments.add(new BalanceChangeFragment("支出", Constant.bookBalance_expenditure));
        fragments.add(new BalanceChangeFragment("收入", Constant.bookBalance_income));


        //设置ViewPager的适配器
        adapter = new ViewPagerBalanceChangeAdapter(getSupportFragmentManager(),fragments);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(fragments.size());
        //关联viewpager
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewpager);

    }

    private void getAccountBalance() {
        CommRequestApi.getInstance().getAccountBalance( new HttpSubscriber<>(new SubscriberOnListener<BalanceBean>() {
            @Override
            public void onSucceed(BalanceBean data) {
                if (data.getCode() == 200) {
                    user_balance_amt.setText(String.valueOf(data.getData().getBalance()));
                } else {
                    MsgUtil.showCustom(context, data.getMessage());
                }
            }
            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

            }
        }, context,false));
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