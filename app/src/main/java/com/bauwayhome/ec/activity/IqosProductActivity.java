package com.bauwayhome.ec.activity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.base.BaseActivity;
import com.bauwayhome.ec.common.MyConstants2;
import com.bauwayhome.ec.interfaces.DialogCallback;
import com.bauwayhome.ec.util.DialogUtil;
import com.bestmafen.smablelib.component.SimpleSmaCallback;
import com.bestmafen.smablelib.component.SmaCallback;
import com.bestmafen.smablelib.component.SmaManager;
import com.blankj.utilcode.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    @BindView(R.id.tv_loop_time)
    TextView mTvLoopTime;
    @BindView(R.id.tv_dcv)
    TextView mTvDcv;
    @BindView(R.id.tv_charge_count)
    TextView mTvChargeCount;
    @BindView(R.id.tv_charge_tv)
    TextView mTvChargeTv;
    @BindView(R.id.tv_temp)
    TextView mTvTemp;
    @BindView(R.id.tv_use_num)
    TextView mTvUseNum;
    @BindView(R.id.tv_device_state)
    TextView tv_device_state;
    @BindView(R.id.bt_off)
    TextView mBtOff;
    @BindView(R.id.bt_switch_shake)
    SwitchCompat mBtSwitchShake;
    @BindView(R.id.bt_on)
    TextView mBtOn;
    @BindView(R.id.bt_loop_number_subtract)
    ImageButton mBtLoopNumberSubtract;
    @BindView(R.id.et_set_loop_number_plan)
    EditText mEtSetLoopNumberPlan;
    @BindView(R.id.bt_loop_number_add)
    ImageButton mBtLoopNumberAdd;
    @BindView(R.id.bt_temp_add)
    ImageButton mBtTempAdd;
    @BindView(R.id.bt_time_subtract)
    ImageButton mBtTimeSubtract;
    @BindView(R.id.et_time)
    EditText mEtTime;
    @BindView(R.id.bt_time_add)
    ImageButton mBtTimeAdd;
    @BindView(R.id.bt_temp_subtract)
    ImageButton mBtTempSubtract;
    @BindView(R.id.et_temp)
    EditText mEtTemp;

    private Integer mPuffPerDay;
    private Integer mPuffPerLoop;
    private Integer mTemperature;
    private Integer mDurationPerLoop;
    private Boolean b1 = false;
    private Boolean b2 = false;
    private Boolean b3 = false;
    private SmaManager mSmaManager;
    private SmaCallback mSmaCallback;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("HH:mm");

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_iqos_product;
    }

    @Override
    protected void initComplete(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        if (mSmaManager.getNameAndAddress()[0].equals("")) {
            tv_device_state.setText(R.string.connect_device);
        } else {
            tv_device_state.setText(mSmaManager.getNameAndAddress()[0]);
        }
        mEtSetLoopNumberPlan.setText(String.valueOf(mPuffPerLoop = userRxPreferences.getInteger(MyConstants2
                .SP_PUFF_PER_LOOP, MyConstants2.DEFAULT_PUFF_PER_LOOP).get()));
        mEtTemp.setText(String.valueOf(mTemperature = userRxPreferences.getInteger(MyConstants2.SP_TEMPERATURE,
                MyConstants2.DEFAULT_TEMPERATURE).get()));
        mEtTime.setText(String.valueOf(mDurationPerLoop = userRxPreferences.getInteger(MyConstants2.SP_DURATION_PER_LOOP,
                MyConstants2.DEFAULT_DURATION_PER_LOOP).get()));

        mBtSwitchShake.setChecked(/*isVibrationEnabled =*/ userRxPreferences.getBoolean(MyConstants2.SP_IS_VIBRATION_ENABLED,
                MyConstants2.DEFAULT_VIBRATION_ENABLED).get());

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mSmaManager = SmaManager.getInstance().addSmaCallback(mSmaCallback = new SimpleSmaCallback() {

            @Override
            public void onConnected(BluetoothDevice device, boolean isConnected) {

            }

            @Override
            public void onReadSmokeCount(final int temperature) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.e("口数=", temperature + "");
                        mTvUseNum.setText(String.valueOf(temperature));
                    }
                });
            }

            @Override
            public void onReadVoltage(final float voltage) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.e("电压=", voltage + "");
                        mTvDcv.setText(String.valueOf(voltage));
                    }
                });
            }

            @Override
            public void onCharging(final float voltage) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.e("电压=", voltage + "");
                        mTvDcv.setText(String.valueOf(voltage));
                        updateChargeTime();
                    }
                });
            }

            @Override
            public void onReadTemperature(final int temperature) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.e("温度=", temperature + "");
                        mTvTemp.setText(String.valueOf(temperature));
                    }
                });
            }

            @Override
            public void onReadPuffCount(final int puff) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.e("口数=", puff + "");
                        mTvUseNum.setText(String.valueOf(puff));
                    }
                });
            }

            @Override
            public void onReadChargeCount(final int count) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.e("充电次数=", count + "");
                        mTvChargeCount.setText(String.valueOf(count));
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

        mEtSetLoopNumberPlan.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mSmaManager.isConnected) {
                    ToastUtils.showShortSafe(R.string.device_not_connected);
                    return;
                }

                String temp = s.toString();
                if (TextUtils.isEmpty(temp)) {
                    ToastUtils.showShort(R.string.parameter_not_null);
                } else {
                    mPuffPerLoop = Integer.valueOf(temp);
                    if (mPuffPerLoop < MyConstants2.LOOP_NUM_MIN || mPuffPerLoop > MyConstants2.LOOP_NUM_MAX) {
                        //超出范围的值，不下发指令
                        ToastUtils.showShort(getString(R.string.loop_use_num_plan)
                                + getString(R.string.use_num_plan_suggest)
                                + MyConstants2.LOOP_NUM_MIN + "~" + MyConstants2.LOOP_NUM_MAX
                                + getString(R.string.between));
                    } else {
                        userRxPreferences.getInteger(MyConstants2.SP_PUFF_PER_LOOP).set(mPuffPerLoop);
                        mSmaManager.write(SmaManager.SET.PUFF_PER_LOOP, String.valueOf(mPuffPerLoop));
                    }
                }
            }
        });

        mEtTemp.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mSmaManager.isConnected) {
                    ToastUtils.showShortSafe(R.string.device_not_connected);
                    return;
                }

                String temp = s.toString();
                if (TextUtils.isEmpty(temp)) {
                    ToastUtils.showShort(R.string.parameter_not_null);
                } else {
                    mTemperature = Integer.valueOf(temp);
                    if (mTemperature < MyConstants2.TEMP_MIN || mTemperature > MyConstants2.TEMP_MAX) {
                        //超出范围的值，不下发指令
                        ToastUtils.showShort(getString(R.string.temp_set)
                                + getString(R.string.use_num_plan_suggest)
                                + MyConstants2.TEMP_MIN + "~" + MyConstants2.TEMP_MAX
                                + getString(R.string.between));
                    } else {
                        userRxPreferences.getInteger(MyConstants2.SP_TEMPERATURE).set(mTemperature);
                        mSmaManager.write(SmaManager.SET.TEMPERATURE, String.valueOf(mTemperature));
                    }
                }
            }
        });

        mEtTime.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mSmaManager.isConnected) {
                    ToastUtils.showShortSafe(R.string.device_not_connected);
                    return;
                }

                String temp = s.toString();
                if (TextUtils.isEmpty(temp)) {
                    ToastUtils.showShort(R.string.parameter_not_null);
                } else {
                    mDurationPerLoop = Integer.valueOf(temp);
                    if (mDurationPerLoop < MyConstants2.TIME_MIN || mDurationPerLoop > MyConstants2.TIME_MAX) {
                        //超出范围的值，不下发指令
                        ToastUtils.showShort(getString(R.string.work_time_set)
                                + getString(R.string.use_num_plan_suggest)
                                + MyConstants2.TIME_MIN + "~" + MyConstants2.TIME_MAX
                                + getString(R.string.between));
                    } else {
                        userRxPreferences.getInteger(MyConstants2.SP_DURATION_PER_LOOP).set(mDurationPerLoop);
                        Log.e("TIME_PER_LOOP", String.valueOf(mDurationPerLoop));
                        mSmaManager.write(SmaManager.SET.TIME_PER_LOOP, String.valueOf(mDurationPerLoop));
                    }
                }
            }
        });

        mBtSwitchShake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!mSmaManager.isConnected) {
                    ToastUtils.showShortSafe(R.string.device_not_connected);
                    return;
                }

                mSmaManager.write(b ? SmaManager.SET.VIBRATION_ENABLED : SmaManager.SET.VIBRATION_DISABLED);
                userRxPreferences.getBoolean(MyConstants2.SP_IS_VIBRATION_ENABLED).set(b);
            }
        });

    }

    private long updateTime = 0L;

    private void updateChargeTime() {
        long now = System.currentTimeMillis();
//        L.w("updateChargeTime -> now = " + now + " , updateTime = " + updateTime);
        if (now - updateTime < 1000 * 60) return;

        updateTime = now;
        long start = userRxPreferences.getLong(MyConstants2.SP_CHARGING_START).get();
        long end = userRxPreferences.getLong(MyConstants2.SP_CHARGING_END).get();
//        L.w("updateChargeTime -> start = " + start + " , end = " + end);
        if (start != 0 && end != 0 && end > start) {
            mTvChargeTv.setText(mDateFormat.format(new Date(start)) + "-" + mDateFormat.format(new Date(end)));
        }
    }

    @OnClick({R.id.iv_return, R.id.ll_view_device_index, R.id.ll_set_device_index, R.id.ll_unbind_device, R.id.bt_off,
            R.id.bt_on, R.id.bt_loop_number_subtract, R.id.bt_loop_number_add, R.id.bt_temp_subtract,
            R.id.bt_temp_add, R.id.bt_time_subtract, R.id.bt_time_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_time_subtract:
                if (getTime()) return;

                if (mDurationPerLoop > MyConstants2.TIME_MIN + 5) {
                    if (!mSmaManager.isConnected) {
                        ToastUtils.showShortSafe(R.string.device_not_connected);
                        return;
                    }
                    mDurationPerLoop -= 5;
                    mEtTime.setText(String.valueOf(mDurationPerLoop));
                }
                break;
            case R.id.bt_time_add:
                if (getTime()) return;

                if (mDurationPerLoop < MyConstants2.TIME_MAX - 5) {
                    if (!mSmaManager.isConnected) {
                        ToastUtils.showShortSafe(R.string.device_not_connected);
                        return;
                    }

                    mDurationPerLoop += 5;
                    mEtTime.setText(String.valueOf(mDurationPerLoop));
                }
                break;
            case R.id.bt_temp_subtract:
                if (getTempData()) return;

                if (mTemperature > MyConstants2.TEMP_MIN) {
                    if (!mSmaManager.isConnected) {
                        ToastUtils.showShortSafe(R.string.device_not_connected);
                        return;
                    }

                    mTemperature--;
                    mEtTemp.setText(String.valueOf(mTemperature));
                }
                break;
            case R.id.bt_temp_add:
                if (getTempData()) return;

                if (mTemperature < MyConstants2.TEMP_MAX) {
                    if (!mSmaManager.isConnected) {
                        ToastUtils.showShortSafe(R.string.device_not_connected);
                        return;
                    }

                    mTemperature++;
                    mEtTemp.setText(String.valueOf(mTemperature));
                }
                break;
            case R.id.bt_loop_number_subtract:
                if (getLoopData()) return;
                if (mPuffPerLoop > MyConstants2.LOOP_NUM_MIN) {
                    if (!mSmaManager.isConnected) {
                        ToastUtils.showShortSafe(R.string.device_not_connected);
                        return;
                    }

                    mPuffPerLoop--;
                    mEtSetLoopNumberPlan.setText(String.valueOf(mPuffPerLoop));
                }
                break;
            case R.id.bt_loop_number_add:
                if (getLoopData()) return;
                if (mPuffPerLoop < MyConstants2.LOOP_NUM_MAX) {
                    if (!mSmaManager.isConnected) {
                        ToastUtils.showShortSafe(R.string.device_not_connected);
                        return;
                    }

                    mPuffPerLoop++;
                    mEtSetLoopNumberPlan.setText(String.valueOf(mPuffPerLoop));
                }
                break;
            case R.id.bt_off:
                mBtSwitchShake.setChecked(false);
                userRxPreferences.getBoolean(MyConstants2.SP_IS_VIBRATION_ENABLED).set(false);
                break;
            case R.id.bt_on:
                userRxPreferences.getBoolean(MyConstants2.SP_IS_VIBRATION_ENABLED).set(true);
                mBtSwitchShake.setChecked(true);
                break;
            case R.id.ll_unbind_device:
                Log.e("ll_unbind_device", "==" + mSmaManager.getNameAndAddress()[0]);
                if (!mSmaManager.getNameAndAddress()[0].equals("")) {
                    DialogUtil.defaultDialog(mContext, getString(R.string.confirm_unbind_device), null, null, new
                            DialogCallback() {

                                @Override
                                public void execute(Object dialog, Object content) {
                                    //确认解绑
                                    SmaManager.getInstance().unbind();
                                    tv_device_state.setText(R.string.connect_device);
                                }
                            });
                } else {
                    startActivity(new Intent(this, BindDeviceActivity.class));
                    finish();
                }

                break;
            case R.id.ll_set_device_index:
                if (!mSmaManager.isConnected) {
                    ToastUtils.showShortSafe(R.string.device_not_connected);
                    return;
                }
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
                if (!mSmaManager.isConnected) {
                    ToastUtils.showShortSafe(R.string.device_not_connected);
                    return;
                }
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

    private boolean getTime() {
        String time = mEtTime.getText().toString().trim();
        if (TextUtils.isEmpty(time)) {
            ToastUtils.showShort(R.string.parameter_not_null);
            return true;
        }
        mDurationPerLoop = Integer.valueOf(time);
        return false;
    }

    private boolean getTempData() {
        String temp = mEtTemp.getText().toString().trim();
        if (TextUtils.isEmpty(temp)) {
            ToastUtils.showShort(R.string.parameter_not_null);
            return true;
        }
        mTemperature = Integer.valueOf(temp);
        return false;
    }

    private boolean getLoopData() {
        String loopNum = mEtSetLoopNumberPlan.getText().toString().trim();
        if (TextUtils.isEmpty(loopNum)) {
            ToastUtils.showShort(R.string.parameter_not_null);
            return true;
        }
        mPuffPerLoop = Integer.valueOf(loopNum);
        return false;
    }

    @Override
    protected void onDestroy() {
        mSmaManager.removeSmaCallback(mSmaCallback);
        super.onDestroy();
    }
}
