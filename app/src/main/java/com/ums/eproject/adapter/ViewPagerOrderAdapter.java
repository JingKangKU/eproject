package com.ums.eproject.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ums.eproject.fragment.CommodityFragment;
import com.ums.eproject.fragment.OrderFragment;

import java.util.ArrayList;

public class ViewPagerOrderAdapter extends FragmentPagerAdapter {
    private final ArrayList<OrderFragment> fragments;

    public ViewPagerOrderAdapter(FragmentManager fm, ArrayList<OrderFragment> fragments) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments=fragments;
    }

    /**
     * 根据位置返回对应的fragment
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    //返回总数
    @Override
    public int getCount() {
        return fragments.size();
    }
    //得到页面的标题
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }
}




