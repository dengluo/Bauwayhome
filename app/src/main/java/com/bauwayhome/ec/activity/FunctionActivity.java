package com.bauwayhome.ec.activity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bauwayhome.ec.BuildConfig;
import com.bauwayhome.ec.R;
import com.bauwayhome.ec.base.BaseActivity;
import com.bestmafen.smablelib.component.SimpleSmaCallback;
import com.bestmafen.smablelib.component.SmaManager;

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

    private SmaManager mSmaManager;

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

    }

    @Override
    protected void initView() {
        ll_home_iqos.setOnClickListener(this);
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
