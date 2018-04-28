package com.bauwayhome.ec.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.bauwayhome.ec.Fragment.FragmentProductShow;
import com.bauwayhome.ec.Fragment.MyFragment0;
import com.bauwayhome.ec.Fragment.MyFragment1;
import com.bauwayhome.ec.Fragment.MyFragment2;
import com.bauwayhome.ec.Fragment.MyFragment3;
import com.bauwayhome.ec.Fragment.MyFragment4;
import com.bauwayhome.ec.Fragment.MyFragment5;

/**
 * Created by Danny on 2018/3/14
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 6;
    private MyFragment0 myFragment0 = null;
    private MyFragment1 myFragment1 = null;
    private MyFragment2 myFragment2 = null;
    private MyFragment3 myFragment3 = null;
    private MyFragment4 myFragment4 = null;
    private MyFragment5 myFragment5 = null;


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        myFragment0 = new MyFragment0();
        myFragment1 = new MyFragment1();
        myFragment2 = new MyFragment2();
        myFragment3 = new MyFragment3();
        myFragment4 = new MyFragment4();
        myFragment5 = new MyFragment5();
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
            case FragmentProductShow.PAGE_ONE:
                fragment = myFragment0;
                break;
            case FragmentProductShow.PAGE_TWO:
                fragment = myFragment2;
                break;
            case FragmentProductShow.PAGE_THREE:
                fragment = myFragment3;
                break;
            case FragmentProductShow.PAGE_FOUR:
                fragment = myFragment4;
                break;
            case FragmentProductShow.PAGE_FIVE:
                fragment = myFragment1;
                break;
            case FragmentProductShow.PAGE_SIX:
                fragment = myFragment5;
                break;
        }
        return fragment;
    }


}

