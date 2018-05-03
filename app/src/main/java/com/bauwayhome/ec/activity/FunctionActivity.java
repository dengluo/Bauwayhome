package com.bauwayhome.ec.activity;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bauwayhome.ec.BuildConfig;
import com.bauwayhome.ec.R;
import com.bauwayhome.ec.View.RoundNavigationIndicator;
import com.bauwayhome.ec.base.BaseActivity;
import com.bestmafen.smablelib.component.SimpleSmaCallback;
import com.bestmafen.smablelib.component.SmaManager;
import com.nie.ngallerylibrary.GalleryViewPager;
import com.nie.ngallerylibrary.ScalePageTransformer;
import com.nie.ngallerylibrary.adater.MyPageradapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by danny on 2018/4/13.
 */

public class FunctionActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "FunctionActivity";
    @BindView(R.id.iv_return)
    ImageView mIvReturn;
    @BindView(R.id.ll_home_iqos)
    LinearLayout ll_home_iqos;
    @BindView(R.id.viewpager)
    GalleryViewPager mViewPager;
    @BindView(R.id.indicator)
    RoundNavigationIndicator indicator;
    @BindView(R.id.tv_function_name)
    TextView tv_function_name;

    private SmaManager mSmaManager;
    private SimpleAdapter mPagerAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_function;
    }

    @Override
    protected void initComplete(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.function_bg2);
        list.add(R.drawable.function_bg2);
        list.add(R.drawable.function_bg2);
        list.add(R.drawable.function_bg2);

        //设置OffscreenPageLimit
        mViewPager.setOffscreenPageLimit(Math.min(list.size(), 5));
        mPagerAdapter.addAll(list);
    }

    @Override
    protected void initView() {
        ll_home_iqos.setOnClickListener(this);
        mViewPager.setPageTransformer(true, new ScalePageTransformer());
        findViewById(R.id.root).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });

        //初始化圆点导航指示器
        indicator=(RoundNavigationIndicator) findViewById(R.id.indicator);
        indicator.setLenght(4);
        indicator.setSelected(0);
        indicator.draw();
        mPagerAdapter = new SimpleAdapter(this);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                //根据viewpager的改变修正圆点导航指示器
                indicator.setSelected(position);
                indicator.draw();
                switch (position){
                    case 0:
                        tv_function_name.setText("Fyhit CS Box");
                        break;
                    case 1:
                        tv_function_name.setText("Fyhit Pad");
                        break;
                    case 2:
                        tv_function_name.setText("Fyhit CS Plus");
                        break;
                    case 3:
                        tv_function_name.setText("Fyhit Slider");
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });


    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            mSmaManager = SmaManager.getInstance().init(this).addSmaCallback(new SimpleSmaCallback() {

                @Override
                public void onConnected(BluetoothDevice device, boolean isConnected) {
                    if (isConnected) {
                        Log.e("device", "==device==" + device.getName() + "==" + device.getAddress());
                        mSmaManager.setNameAndAddress(device.getName(), device.getAddress());
                        mSmaManager.mEaseConnector.setAddress(device.getAddress());
                    }
                }

                @Override
                public void onWrite(byte[] data) {
                    if (BuildConfig.DEBUG) {
                        //                    append("  ->  onWrite", data);
                    }
                }

                @Override
                public void onRead(byte[] data) {

                }
            });
        }
        mSmaManager.connect(true);
        mSmaManager = SmaManager.getInstance();
        initView();
    }

    public class SimpleAdapter extends MyPageradapter {

        private final List<Integer> mList;
        private final Context mContext;

        public SimpleAdapter(Context context) {
            mList = new ArrayList<>();
            mContext = context;
        }

        public void addAll(List<Integer> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup container) {
            ImageView imageView = null;
            if (convertView == null) {
                imageView = new ImageView(mContext);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setTag(position);
            imageView.setImageResource(mList.get(position));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((mViewPager.getCurrentItem() ) == position) {
//                        Toast.makeText(mContext, "点击的位置是:::"+position, Toast.LENGTH_SHORT).show();
                        switch (position){
                            case 0:
                                startActivity(new Intent(FunctionActivity.this, IqosProductActivity.class));
                                break;
                            case 1:

                                break;
                            case 2:

                                break;
                            case 3:

                                break;
                        }
                    }

                }
            });
//            }

            return imageView;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }

    @OnClick({R.id.iv_return, R.id.ll_home_iqos})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home_iqos:
                startActivity(new Intent(this, IqosProductActivity.class));
                break;
            case R.id.iv_return:
                this.finish();
                break;
            default:
                break;
        }
    }
}
