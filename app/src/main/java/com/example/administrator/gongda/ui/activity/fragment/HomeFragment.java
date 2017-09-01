package com.example.administrator.gongda.ui.activity.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.gongda.R;
import com.example.administrator.gongda.Utils.Cachecontrol;
import com.example.administrator.gongda.base.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/9/21.
 */
public class HomeFragment extends BaseFragment {
    @Bind(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;
    @Bind(R.id.tab_pager)
   public ViewPager mViewPager;
    private List<String> titles= Arrays.asList("第一周","第二周","第三周","第四周","第五周","第六周","第七周","第八周","第九周","第十周","第十一周","第十二周","第十三周","第十四周","第十五周","第十六周","第十七周","第十八周","第十九周","第二十周","第二十一周");
    public static HomeFragment newInstance(){
        return new HomeFragment();
    }
    @Override
    public int getLayoutId()
    {

        return R.layout.fragment_home;
    }

    @Override
    public void initViews()
    {


        mViewPager.setAdapter(new TabPagerAdapter(getChildFragmentManager()));
       // SharedPreferences sharedPreferences=getActivity().getSharedPreferences("userXiaoinfo", Context.MODE_PRIVATE);
       // int dangqinazhou=sharedPreferences.getInt("dangqianzhou",0)-1;

        mSlidingTabLayout.setViewPager(mViewPager);
       // mViewPager.setCurrentItem(dangqinazhou);
        mViewPager.setCurrentItem(new Cachecontrol().getWeek(getActivity()));
    }


    private class TabPagerAdapter extends FragmentStatePagerAdapter
    {


        public TabPagerAdapter(FragmentManager fm)
        {

            super(fm);

        }

        @Override
        public Fragment getItem(int position)
        {
            Log.i("po",position+"");
            return CourseFragment.newInstance(position+1);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {

            return titles.get(position);
        }

        @Override
        public int getCount()
        {

            return titles.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            View view=((CourseFragment)object).getView();
            mViewPager.removeView(view);
            container.removeView(view);
            ((CourseFragment)object).onDestroy();
        }
    }
}
