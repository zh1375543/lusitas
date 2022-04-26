package com.wzx.tipcontent.adapter;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by muzi on 2017/8/29.
 * 727784430@qq.com
 */

public class FragmentPushAdapter extends FragmentPagerAdapter {
    String[] title = null;
    private List<Fragment> mFragmentList;
    private  int  mCurrentFragment;
    private Fragment fragment;

    public FragmentPushAdapter(String[] title, FragmentManager fm, List<Fragment> mFragmentList) {
        super(fm);
        this.title = title;
        this.mFragmentList=mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }
    //用于区分具体属于哪个fragment
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = position+1;
        fragment= (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return title.length;
    }

    public int getCurrentFragment() {
        return mCurrentFragment;
    }
    public Fragment getFragment() {
        return fragment;
    }
}