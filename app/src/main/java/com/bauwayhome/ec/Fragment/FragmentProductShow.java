package com.bauwayhome.ec.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.View.PicsView;
import com.bauwayhome.ec.View.PicsView.OnClickListener;
import com.bauwayhome.ec.adapter.MyFragmentPagerAdapter;
import com.bauwayhome.ec.util.NetworkUtil;
import com.bauwayhome.ec.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import ezy.ui.view.BannerView;

/**
 * Created by danny on 2017/12/28.
 * 产品展示
 */

public class FragmentProductShow extends Fragment implements View.OnClickListener {

    private View view_main;
    private RadioGroup rg_tab_bar;
    private RadioButton rb_pro1,rb_pro2,rb_pro3,rb_pro4;
    private ViewPager vpager;
    private PicsView mPicsViewpager;
    private MyFragmentPagerAdapter mAdapter;
//    private Kanner kanner;
    private Context context;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR= 3;

    public static int[] urls = {//750x500
            R.mipmap.banner001,
            R.mipmap.banner002,
            R.mipmap.banner003,
            R.mipmap.banner004
    };
    private String[] titles = { "", "", "", "", "", "" };


    public static class BannerItem {
        public int image;
        public String title;

        @Override
        public String toString() {
            return title;
        }
    }

    public static class BannerViewFactory implements BannerView.ViewFactory<BannerItem> {
        @Override
        public View create(BannerItem item, int position, ViewGroup container) {
            ImageView iv = new ImageView(container.getContext());
//            RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA);
//            Glide.with(container.getContext().getApplicationContext()).load(item.image).apply(options).into(iv);
            return iv;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = this.getActivity();
        if (!NetworkUtil.isNetworkAvailable(context)){
            ToastUtil.showShortToast(context, "网络连接异常!");
        }
        inintView();
        rb_pro1.setChecked(true);
        return view_main;
    }

    private void inintView() {
        view_main = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_product, null);
        mAdapter = new MyFragmentPagerAdapter(this.getFragmentManager());
        rg_tab_bar = (RadioGroup) view_main.findViewById(R.id.rg_tab_bar);
        rb_pro1 = (RadioButton) view_main.findViewById(R.id.rb_pro1);
        rb_pro2 = (RadioButton) view_main.findViewById(R.id.rb_pro2);
        rb_pro3 = (RadioButton) view_main.findViewById(R.id.rb_pro3);
        rb_pro4 = (RadioButton) view_main.findViewById(R.id.rb_pro4);
        mPicsViewpager = (PicsView) view_main.findViewById(R.id.picsviewpager);
        List<ImageView> imgList = new ArrayList<>();
        for(int i = 0; i < urls.length; i++){
            ImageView iv = new ImageView(context);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(urls[i]);
            imgList.add(iv);
        }
        // 初始化数据
        mPicsViewpager.addTitlesAndImages(titles, imgList);
        //mPicsViewpager.addImages(imgList);
        // 设置点击事件
        mPicsViewpager.setClickListener(new OnClickListener() {

            @Override
            public void onClick(int position) {
                System.out.println("点击有效");
//                Toast.makeText(context, "点击有效，位置为：" + position, Toast.LENGTH_SHORT).show();
            }
        });
//        kanner = (Kanner) view_main.findViewById(R.id.kanner);
//        kanner.setImagesRes(urls);

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
                    case R.id.rb_pro3:
                        vpager.setCurrentItem(PAGE_THREE);
                        break;
                    case R.id.rb_pro4:
                        vpager.setCurrentItem(PAGE_FOUR);
                        break;
                }
            }
        });

        vpager = (ViewPager) view_main.findViewById(R.id.vpager);
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
                        case PAGE_THREE:
                            rb_pro3.setChecked(true);
                            break;
                        case PAGE_FOUR:
                            rb_pro4.setChecked(true);
                            break;
                    }
                }
            }
        });

//        List<BannerItem> list = new ArrayList<>();
//        for (int i = 0; i < urls.length; i++) {
//            BannerItem item = new BannerItem();
//            item.image = urls[i];
////            item.title = titles[i];
//            list.add(item);
//        }
//
//        final BannerView banner1 = (BannerView) view_main.findViewById(R.id.banner1);
//        banner1.setViewFactory(new BannerViewFactory());
//        banner1.setDataList(list);
//        banner1.start();
    }

    @Override
    public void onDestroy() {
//        kanner.removeCallbacksAndMessages();
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.setUserVisibleHint(hidden);
        if (hidden) {
            //可见时执行的操作
//            Log.e("isVisibleToUser11",hidden+"");
        } else {
            //不可见时执行的操作
//            Log.e("isVisibleToUser22",hidden+"");
            if (!NetworkUtil.isNetworkAvailable(context)){
                ToastUtil.showShortToast(context, "网络连接异常!");
                return;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
