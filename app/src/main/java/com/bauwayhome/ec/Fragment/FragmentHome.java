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
import com.bauwayhome.ec.activity.IqosProductActivity;
import com.bestmafen.smablelib.component.SimpleSmaCallback;
import com.bestmafen.smablelib.component.SmaManager;

/**
 * Created by danny on 2017/12/28.
 */

public class FragmentHome extends Fragment implements View.OnClickListener {

    private Context context;
    private View view_main;
    private LinearLayout ll_home_iqos;
    private SmaManager mSmaManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = this.getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
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
        ll_home_iqos = (LinearLayout) view_main.findViewById(R.id.ll_home_iqos);
        ll_home_iqos.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home_iqos:
                startActivity(new Intent(context, IqosProductActivity.class));
                break;
            default:
                break;
        }
    }
}
