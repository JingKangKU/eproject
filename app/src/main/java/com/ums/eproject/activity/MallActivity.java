package com.ums.eproject.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mosect.lib.immersive.ImmersiveLayout;
import com.mosect.lib.immersive.LayoutAdapter;
import com.ums.eproject.R;
import com.ums.eproject.adapter.ViewPagerAdapter;
import com.ums.eproject.bean.DepositRuleBean;
import com.ums.eproject.bean.PdtCategory;
import com.ums.eproject.fragment.CommodityFragment;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.MsgUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

/**
 * 商城主页
 */
public class MallActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout title_view;
    private ViewPager viewPager;
    TabLayout tabLayout;
    ArrayList<CommodityFragment> fragments;
    ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);

        title_view = findViewById(R.id.title_view);
        findViewById(R.id.title_back).setOnClickListener(this);

        ImmersiveLayout immersiveLayout = new ImmersiveLayout(this);
        immersiveLayout.addAdapter(new LayoutAdapter() {
            @Override
            public void onAdjustLayoutPadding(ImmersiveLayout layout) {
                title_view.setPadding(0, layout.getPaddingTop(), 0, 0);
            }
        });
        // 请求沉浸式布局
        immersiveLayout.requestLayout();

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);


        getListPdtCategory();
    }

    private void initView(List<PdtCategory.DataBean> dataList) {
        //初始化数据
        fragments = new ArrayList<>();
        if (dataList.size()<=0){
            return;
        }
        for (int i = 0; i < dataList.size(); i++){
            fragments.add(new CommodityFragment(dataList.get(i)));
        }

        //设置ViewPager的适配器
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.size());
        //关联viewpager
        tabLayout.setupWithViewPager(viewPager);
        //设置滚动方式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }

    private void getListPdtCategory() {
        CommRequestApi.getInstance().getListPdtCategory(new HttpSubscriber<>(new SubscriberOnListener<PdtCategory>() {
            @Override
            public void onSucceed(PdtCategory data) {
                if (data.getCode() == 200) {
                    initView(data.getData());
                } else {
                    MsgUtil.showCustom(context, data.getMessage());
                }
            }
            @Override
            public void onError(int code, String msg) {
                Toasty.error(context, "数据返回异常   " + code + "   " + msg).show();

            }
        }, context));
    }


    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveLayout.darkStatusBar(this);
    }

}