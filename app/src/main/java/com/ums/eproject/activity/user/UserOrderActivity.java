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
import com.ums.eproject.adapter.ViewPagerAdapter;
import com.ums.eproject.adapter.ViewPagerOrderAdapter;
import com.ums.eproject.bean.PdtCategory;
import com.ums.eproject.fragment.CommodityFragment;
import com.ums.eproject.fragment.OrderFragment;
import com.ums.eproject.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class UserOrderActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view, title_right;
    private TextView title_text;

    TabLayout order_tabLayout;
    ViewPager order_viewpager;
    ArrayList<OrderFragment> fragments;
    ViewPagerOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);

        title_view = findViewById(R.id.title_view);
        title_right = findViewById(R.id.title_right);
        title_right.setVisibility(View.GONE);
        title_text = findViewById(R.id.title_text);
        title_text.setText("我的订单");

        // 请求沉浸式布局
        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(layout -> title_view.setPadding(0, layout.getPaddingTop(), 0, 0));
        immersiveLayout.requestLayout();

        order_viewpager = findViewById(R.id.order_viewpager);
        order_tabLayout = findViewById(R.id.order_tabLayout);
        initView();
    }
    private void initView() {
        //初始化数据
        fragments = new ArrayList<>();


        fragments.add(new OrderFragment("全部", Constant.orderStatus_all));
        fragments.add(new OrderFragment("待支付", Constant.orderStatus_unpaid));
        fragments.add(new OrderFragment("待收货", Constant.orderStatus_harvested));
        fragments.add(new OrderFragment("已完成", Constant.orderStatus_success));
        fragments.add(new OrderFragment("已取消", Constant.orderStatus_cancel_and_refund));


        //设置ViewPager的适配器
        adapter = new ViewPagerOrderAdapter(getSupportFragmentManager(),fragments);
        order_viewpager.setAdapter(adapter);
        order_viewpager.setOffscreenPageLimit(fragments.size());
        //关联viewpager
        order_tabLayout.setupWithViewPager(order_viewpager);
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