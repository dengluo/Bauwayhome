package com.bauwayhome.ec.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.adapter.NewsFragmentPagerAdapter;
import com.bauwayhome.ec.base.BaseActivity;
import com.bauwayhome.ec.util.NetworkUtil;
import com.bauwayhome.ec.util.ToastUtil;

import butterknife.OnClick;

/**
 * Created by danny on 2018/4/11.
 * 新闻
 */

public class NewsActivity extends BaseActivity{

    private Context context;
    private RadioGroup rg_tab_bar;
    private RadioButton rb_pro1, rb_pro2;
    private ViewPager vpager;
    private NewsFragmentPagerAdapter mAdapter;
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_news;
    }

    @Override
    protected void initComplete(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        context = this;
        if (!NetworkUtil.isNetworkAvailable(context)) {
            ToastUtil.showShortToast(context, "网络连接异常!");
        }
        inintView();
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    private void inintView() {
        mAdapter = new NewsFragmentPagerAdapter(this.getSupportFragmentManager());
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_pro1 = (RadioButton) findViewById(R.id.rb_pro1);
        rb_pro2 = (RadioButton) findViewById(R.id.rb_pro2);
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

        vpager = (ViewPager) findViewById(R.id.newsPager);
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
    protected void onDestroy() {
        super.onDestroy();
        if (!NetworkUtil.isNetworkAvailable(context)) {
            ToastUtil.showShortToast(context, "网络连接异常!");
            return;
        }
    }

    @OnClick({R.id.iv_return})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_return:
                finish();
                break;
            default:
                break;
        }
    }
}
