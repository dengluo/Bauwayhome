package com.bauwayhome.ec.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.adapter.NewsFragmentPagerAdapter;

/**
 * Created by danny on 2018/4/11.
 * 新闻
 */

public class FragmentNews extends Fragment implements View.OnClickListener {

    private View view_main;
    private Context context;
    private RadioGroup rg_tab_bar;
    private RadioButton rb_pro1,rb_pro2;
    private ViewPager vpager;
    private NewsFragmentPagerAdapter mAdapter;
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = this.getActivity();
        inintView();
        return view_main;
    }

    private void inintView() {
        view_main = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_news, null);
        mAdapter = new NewsFragmentPagerAdapter(this.getFragmentManager());
        rg_tab_bar = (RadioGroup) view_main.findViewById(R.id.rg_tab_bar);
        rb_pro1 = (RadioButton) view_main.findViewById(R.id.rb_pro1);
        rb_pro2 = (RadioButton) view_main.findViewById(R.id.rb_pro2);
        rb_pro1.setChecked(true);

        rg_tab_bar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_pro1:
                        vpager.setCurrentItem(PAGE_ONE);
                        break;
                    case R.id.rb_pro2:
                        vpager.setCurrentItem(PAGE_TWO);
                        break;
                }
            }
        });

        vpager = (ViewPager) view_main.findViewById(R.id.newsPager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
                if (state == 2) {
                    switch (vpager.getCurrentItem()) {
                        case PAGE_ONE:
                            rb_pro1.setChecked(true);
                            break;
                        case PAGE_TWO:
                            rb_pro2.setChecked(true);
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
