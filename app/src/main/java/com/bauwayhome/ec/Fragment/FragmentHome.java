package com.bauwayhome.ec.Fragment;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bauwayhome.ec.BuildConfig;
import com.bauwayhome.ec.MainActivity;
import com.bauwayhome.ec.R;
import com.bauwayhome.ec.activity.AboutUsActivity;
import com.bauwayhome.ec.activity.FunctionActivity;
import com.bauwayhome.ec.activity.NewsActivity;
import com.bauwayhome.ec.activity.VideoManagerActivity2;
import com.bauwayhome.ec.util.LanguageUtils;
import com.bauwayhome.ec.zxing.activity.CaptureActivity;
import com.bestmafen.smablelib.component.SimpleSmaCallback;
import com.bestmafen.smablelib.component.SmaManager;
import com.blankj.utilcode.util.ToastUtils;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 2017/12/28.
 */

public class FragmentHome extends Fragment implements View.OnClickListener {

    private Context context;
    private View view_main;
    //    private LinearLayout ll_home_iqos;
    private LinearLayout ll_home_news, ll_home_about_us, ll_home_product_list, ll_home_function, ll_home_bbs, ll_home_scan, ll_home_video;
    private SmaManager mSmaManager;
    private DoGoNews doGoNews;
    private DoGoProduct doGoProduct;
    private ImageView iv_home_switch_language;
    private TopRightMenu mTopRightMenu;

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
        iv_home_switch_language = (ImageView) view_main.findViewById(R.id.iv_home_switch_language);
        ll_home_news.setOnClickListener(this);
        ll_home_about_us.setOnClickListener(this);
        ll_home_product_list.setOnClickListener(this);
        ll_home_function.setOnClickListener(this);
        ll_home_bbs.setOnClickListener(this);
        ll_home_scan.setOnClickListener(this);
        ll_home_video.setOnClickListener(this);
        iv_home_switch_language.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home_switch_language:
                Activity activity = (Activity) context;
                mTopRightMenu = new TopRightMenu(activity);
                List<MenuItem> menuItems = new ArrayList<>();
                menuItems.add(new MenuItem(R.drawable.icon_jiantou_gray, "CN"));
                menuItems.add(new MenuItem(R.drawable.icon_jiantou_gray, "EN"));
//                menuItems.add(new MenuItem(R.drawable.icon_jiantou_gray, "JP"));
                mTopRightMenu
                        .setHeight(LinearLayout.LayoutParams.WRAP_CONTENT)     //默认高度480
                        .setWidth(320)      //默认宽度wrap_content
                        .showIcon(true)     //显示菜单图标，默认为true
                        .dimBackground(true)           //背景变暗，默认为true
                        .needAnimationStyle(true)   //显示动画，默认为true
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .addMenuList(menuItems)
//                        .addMenuItem(new MenuItem(R.drawable.icon_jiantou_gray, "IT"))
//                        .addMenuItem(new MenuItem(R.drawable.icon_jiantou_gray, "GM"))
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                Toast.makeText(context, "点击菜单:" + position, Toast.LENGTH_SHORT).show();
                                switch (position) {
                                    case 0:
                                        LanguageUtils.switchLanguage(context, 0);
                                        Intent intent0 = new Intent(context, MainActivity.class);
                                        intent0.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent0);
                                        break;
                                    case 1:
                                        LanguageUtils.switchLanguage(context, 1);
                                        Intent intent1 = new Intent(context, MainActivity.class);
                                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent1);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                        .showAsDropDown(iv_home_switch_language, -225, 0);
                break;
            case R.id.ll_home_video:
                startActivity(new Intent(context, VideoManagerActivity2.class));
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
//                doGoNews.doGoNews();
                startActivity(new Intent(context, NewsActivity.class));
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
