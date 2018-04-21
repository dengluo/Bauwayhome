package com.bauwayhome.ec.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.bauwayhome.ec.Fragment.FragmentNews;
import com.bauwayhome.ec.Fragment.NewsFragment1;
import com.bauwayhome.ec.Fragment.NewsFragment2;

/**
 * Created by Danny on 2018/3/14
 */
public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 2;
    private NewsFragment1 newsFragment1 = null;
    private NewsFragment2 newsFragment2 = null;


    public NewsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        newsFragment1 = new NewsFragment1();
        newsFragment2 = new NewsFragment2();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case FragmentNews.PAGE_ONE:
                fragment = newsFragment1;
                break;
            case FragmentNews.PAGE_TWO:
                fragment = newsFragment2;
                break;
        }
        return fragment;
    }


}

