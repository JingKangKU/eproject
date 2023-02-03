package com.ums.eproject.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;
import com.ums.eproject.R;
import com.ums.eproject.adapter.ViewPagerAdapter;
import com.ums.eproject.bean.DepositRuleBean;
import com.ums.eproject.https.HttpSubscriber;
import com.ums.eproject.https.SubscriberOnListener;
import com.ums.eproject.https.comm.CommRequestApi;
import com.ums.eproject.utils.MsgUtil;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

/**
 * 废弃
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MallFragment extends Fragment {

    private ViewPager viewPager;
    TabLayout tabLayout;
    ArrayList<CommodityFragment> fragments;
    ViewPagerAdapter adapter;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mall, container, false);

        context = getActivity();

        viewPager = view.findViewById(R.id.viewpager);

        tabLayout = view.findViewById(R.id.tabLayout);

        initView();

        return view;
    }

    private void initView() {
        //初始化数据
        fragments = new ArrayList<>();
//        fragments.add(new CommodityFragment("推荐"));
//        fragments.add(new CommodityFragment("能源"));
//        fragments.add(new CommodityFragment("石油"));
//        fragments.add(new CommodityFragment("酒店"));
//
//        fragments.add(new CommodityFragment("积分商城"));
//        fragments.add(new CommodityFragment("其他"));


        //设置ViewPager的适配器
        adapter = new ViewPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        //关联viewpager
        tabLayout.setupWithViewPager(viewPager);
        //设置滚动方式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }


}