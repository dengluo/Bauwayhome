package com.bauwayhome.ec.Fragment;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bauwayhome.ec.BuildConfig;
import com.bauwayhome.ec.R;
import com.bauwayhome.ec.activity.AboutUsActivity;
import com.bauwayhome.ec.activity.FunctionActivity;
import com.bauwayhome.ec.activity.VideoManagerActivity;
import com.bauwayhome.ec.zxing.activity.CaptureActivity;
import com.bestmafen.smablelib.component.SimpleSmaCallback;
import com.bestmafen.smablelib.component.SmaManager;
import com.blankj.utilcode.util.ToastUtils;

/**
 * Created by danny on 2017/12/28.
 */

public class FragmentHome extends Fragment implements View.OnClickListener {

    private Context context;
    private View view_main;
    //    private LinearLayout ll_home_iqos;
    private LinearLayout ll_home_news, ll_home_about_us, ll_home_product_list,ll_home_function,ll_home_bbs,ll_home_scan,ll_home_video;
    private SmaManager mSmaManager;
    private DoGoNews doGoNews;
    private DoGoProduct doGoProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = this.getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            mSmaManager = SmaManager.getInstance().init(context).addSmaCallback(new SimpleSmaCallback() {

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
        return view_main;
    }

    private void initView() {
        view_main = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_home, null);
//        ll_home_iqos = (LinearLayout) view_main.findViewById(R.id.ll_home_iqos);
//        ll_home_iqos.setOnClickListener(this);
        ll_home_news = (LinearLayout) view_main.findViewById(R.id.ll_home_news);
        ll_home_about_us = (LinearLayout) view_main.findViewById(R.id.ll_home_about_us);
        ll_home_product_list = (LinearLayout) view_main.findViewById(R.id.ll_home_product_list);
        ll_home_function = (LinearLayout) view_main.findViewById(R.id.ll_home_function);
        ll_home_bbs = (LinearLayout) view_main.findViewById(R.id.ll_home_bbs);
        ll_home_scan = (LinearLayout) view_main.findViewById(R.id.ll_home_scan);
        ll_home_video = (LinearLayout) view_main.findViewById(R.id.ll_home_video);
        ll_home_news.setOnClickListener(this);
        ll_home_about_us.setOnClickListener(this);
        ll_home_product_list.setOnClickListener(this);
        ll_home_function.setOnClickListener(this);
        ll_home_bbs.setOnClickListener(this);
        ll_home_scan.setOnClickListener(this);
        ll_home_video.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.ll_home_iqos:
//                startActivity(new Intent(context, IqosProductActivity.class));
//                break;
            case R.id.ll_home_video:
                startActivity(new Intent(context, VideoManagerActivity.class));
                break;
            case R.id.ll_home_function:
                startActivity(new Intent(context, FunctionActivity.class));
                break;
            case R.id.ll_home_product_list:
                doGoProduct.doGoProduct();
                break;
            case R.id.ll_home_about_us:
                startActivity(new Intent(context, AboutUsActivity.class));
                break;
            case R.id.ll_home_news:
                doGoNews.doGoNews();
                break;
            case R.id.ll_home_bbs:
                ToastUtils.showShortSafe(R.string.unopened);
                break;
            case R.id.ll_home_scan:
                startActivity(new Intent(context, CaptureActivity.class).putExtra("shebei", "device1"));
                break;
            default:
                break;
        }
    }

    public interface DoGoNews {
        public void doGoNews();
    }

    public DoGoNews getDoGoNews() {
        return doGoNews;
    }

    public void setDoGoNews(DoGoNews doGoNews) {
        this.doGoNews = doGoNews;
    }

    public interface DoGoProduct {
        public void doGoProduct();
    }

    public DoGoProduct getDoGoProduct() {
        return doGoProduct;
    }

    public void setDoGoProduct(DoGoProduct doGoProduct) {
        this.doGoProduct = doGoProduct;
    }
}
