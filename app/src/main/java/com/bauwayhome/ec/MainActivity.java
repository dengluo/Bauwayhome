package com.bauwayhome.ec;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bauwayhome.ec.Fragment.FragmentHome;
import com.bauwayhome.ec.Fragment.FragmentMe;
import com.bauwayhome.ec.Fragment.FragmentProductShow;
import com.bauwayhome.ec.activity.WelcomeActivity;
import com.bauwayhome.ec.adapter.FragmentTabAdapter;
import com.bauwayhome.ec.base.BaseActivity;
import com.bestmafen.easeblelib.util.EaseUtils;
import com.bestmafen.smablelib.component.SimpleSmaCallback;
import com.bestmafen.smablelib.component.SmaCallback;
import com.bestmafen.smablelib.component.SmaManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    @BindView(R.id.rg_main)
    RadioGroup rg_main;
    //    @BindView(R.id.radio_studio)
//    RadioButton radio_studio;
    @BindView(R.id.radio_order_take)
    RadioButton radio_order_take;
    //    @BindView(R.id.radio_find)
//    RadioButton radio_find;
    @BindView(R.id.radio_me)
    RadioButton radio_me;

    FragmentTabAdapter tabAdapter;
    private SmaManager mSmaManager;
    private TextView mTvDebug;
    private DateFormat mDateFormat = new SimpleDateFormat("HH:mm:ss");
    String strDevice1 = "";
    private SharedPreferences sharedPreferences;
    private SmaCallback mSmaCallback;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComplete(Bundle savedInstanceState) {

        if (BuildConfig.DEBUG) {
//            mDebugViewManager.showDebugWindow();
            findViewById(R.id.v_debug).setVisibility(View.VISIBLE);
            mTvDebug = (TextView) findViewById(R.id.tv_debug);
        }
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        strDevice1 = intent.getStringExtra("deviceName1");
        tabAdapter = new FragmentTabAdapter(this,
                getFragments(), R.id.fl_main, rg_main);

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("count", MODE_PRIVATE);
        int count = sharedPreferences.getInt("count", 0);
        Log.d("print", String.valueOf(count));
        //判断程序是第几次运行，如果是第一次运行则跳转到引导页面
//        if (count == 0) {
//            Intent intent = new Intent();
//            intent.setClass(this, WelcomeActivity.class);
//            startActivity(intent);
//            this.finish();
//        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //存入数据
        editor.putInt("count", ++count);
        //提交修改
        editor.commit();

        mSmaManager = SmaManager.getInstance().addSmaCallback(mSmaCallback = new SimpleSmaCallback() {

            @Override
            public void onConnected(BluetoothDevice device, boolean isConnected) {

            }


            @Override
            public void onCharging(final float voltage) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
//                        mTvDcv.setText(String.valueOf(voltage));
//                        updateChargeTime();
                    }
                });
            }

            @Override
            public void onReadTemperature(final int temperature) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.e("temperature==", String.valueOf(temperature));
//                        mTvTemp.setText(String.valueOf(temperature));
                    }
                });
            }

            @Override
            public void onReadPuffCount(final int puff) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
//                        mTvUseNum.setText(String.valueOf(puff));
                    }
                });
            }

            @Override
            public void onReadChargeCount(final int count) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
//                        mTvChargeCount.setText(String.valueOf(count));
                    }
                });
            }

            @Override
            public void onReadHopesWendu(final int wendu) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.e("wendu==", String.valueOf(wendu));
                    }
                });
            }

            @Override
            public void onReadHopesTime(final int time) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.e("time==", String.valueOf(time));
                    }
                });
            }

            @Override
            public void onReadHopesFengsu(final int fengsu) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.e("fengsu==", String.valueOf(fengsu));
                    }
                });
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    private List<Fragment> getFragments() {
        List<Fragment> list_fm = new ArrayList<Fragment>();
        FragmentHome fg_home = new FragmentHome();
//        fg_home.setDoGo(new DoGoJieDan() {
//
//            @Override
//            public void doGo() {
//                // TODO Auto-generated method stub
//                radio_order_take.setChecked(true);
//            }
//        });
        list_fm.add(fg_home);// 首页电子烟
        list_fm.add(new FragmentProductShow());// 产品展示
        list_fm.add(new FragmentMe());// 我
        return list_fm;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

//            case R.id.btn:
//                startActivity(new Intent(this, CaptureActivity.class));
//                break;
//            case R.id.start_bluetooth:
//                //startActivity(new Intent(this, BluetoothActivity.class));
//                startActivity(new Intent(this, DeviceScanActivity.class));
//                break;
        }
    }

    public String getTimeStr() {
        return mDateFormat.format(new Date());
    }

    private String getValue(@NonNull byte[] extra) {
        StringBuilder sb = new StringBuilder();
        try {
            byte[] valuePart = Arrays.copyOfRange(extra, 2, 8);
            for (byte b : valuePart) {
                sb.append((char) b);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param type 0写 1读
     * @param data content
     */
    private synchronized void append(final String type, final byte[] data) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mTvDebug.append(getTimeStr() + "  " + type + "\n");
                mTvDebug.append(EaseUtils.byteArray2HexString(data));
                mTvDebug.append("  " + getValue(data));
                mTvDebug.append("\n\n");
            }
        });
    }

    private synchronized void append(final String value) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mTvDebug.append(getTimeStr() + "\n");
                mTvDebug.append("  " + value);
                mTvDebug.append("\n\n");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mSmaManager.removeSmaCallback(mSmaCallback);
        super.onDestroy();
    }
}
