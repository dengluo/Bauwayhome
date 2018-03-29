package com.bauwayhome.ec.activity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
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

public class IqosProductActivity extends BaseActivity {

    private static final String TAG = "IqosProductActivity";
    @BindView(R.id.iv_return)
    ImageView mIvReturn;
    @BindView(R.id.ll_view_device_index)
    LinearLayout ll_view_device_index;
    @BindView(R.id.ll_set_device_index)
    LinearLayout ll_set_device_index;
    @BindView(R.id.ll_unbind_device)
    LinearLayout ll_unbind_device;
    @BindView(R.id.ll_view_device_index_context)
    LinearLayout ll_view_device_index_context;
    @BindView(R.id.ll_set_device_index_context)
    LinearLayout ll_set_device_index_context;
    @BindView(R.id.iv_view_device_index)
    ImageView iv_view_device_index;
    @BindView(R.id.iv_set_device_index)
    ImageView iv_set_device_index;

    private Boolean b1 = false;
    private Boolean b2 = false;
    private Boolean b3 = false;
    private SmaManager mSmaManager;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_iqos_product;
    }

    @Override
    protected void initComplete(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mSmaManager = SmaManager.getInstance().addSmaCallback(new SimpleSmaCallback() {

            @Override
            public void onConnected(BluetoothDevice device, boolean isConnected) {
                if (isConnected) {
                    Log.e("device", "==device==" + device.getName() + "==" + device.getAddress());
                    mSmaManager.setNameAndAddress(device.getName(), device.getAddress());
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
                if (BuildConfig.DEBUG) {
//                    append("  ->  onRead", data);
                }
            }
        });
        mSmaManager.connect(true);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.iv_return, R.id.ll_view_device_index, R.id.ll_set_device_index, R.id.ll_unbind_device})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_unbind_device:
                startActivity(new Intent(this, BindDeviceActivity.class));
                break;
            case R.id.ll_set_device_index:
                if (!b2) {
                    ll_set_device_index_context.setVisibility(View.VISIBLE);
                    iv_set_device_index.setBackgroundResource(R.drawable.icon_jiantou_gray2);
                    b2 = true;
                } else {
                    ll_set_device_index_context.setVisibility(View.GONE);
                    iv_set_device_index.setBackgroundResource(R.drawable.icon_jiantou_gray);
                    b2 = false;
                }

                break;
            case R.id.ll_view_device_index:
                if (!b1) {
                    ll_view_device_index_context.setVisibility(View.VISIBLE);
                    iv_view_device_index.setBackgroundResource(R.drawable.icon_jiantou_gray2);
                    b1 = true;
                } else {
                    ll_view_device_index_context.setVisibility(View.GONE);
                    iv_view_device_index.setBackgroundResource(R.drawable.icon_jiantou_gray);
                    b1 = false;
                }
                break;
            case R.id.iv_return:
                this.finish();
                break;
            default:
                break;
        }
    }
}
