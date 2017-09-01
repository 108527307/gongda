package com.example.administrator.gongda.ui.activity.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.administrator.gongda.R;
import com.example.administrator.gongda.base.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/9/27.
 */

public class NewsAndOther extends BaseFragment {
    @Bind(R.id.sliding_tabsa)
    SlidingTabLayout mSlidingTabLayout;
    @Bind(R.id.tab_pagera)
    public ViewPager mViewPager;
    private List<String> titles= Arrays.asList("教务","考试");
    @Override
    public int getLayoutId() {
        return R.layout.newsandother;
    }
static public NewsAndOther newInstance(){
    return new NewsAndOther();
}
    @Override
    public void initViews() {
     mViewPager.setAdapter(new TabPagerAdapter(getChildFragmentManager()));
        mSlidingTabLayout.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }
    class TabPagerAdapter extends FragmentStatePagerAdapter{

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0)
            return NewsFragment.Instance();
           else{
               return examFragment.newsIntance();
            }
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
