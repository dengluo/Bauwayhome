package com.bauwayhome.ec.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.base.BaseActivity;
import com.bauwayhome.ec.bean.User;
import com.bauwayhome.ec.util.CountDownTimerUtils;
import com.bauwayhome.ec.util.NetworkUtil;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ValidateActivity extends BaseActivity {

    private static final String TAG = "ValidateActivity";
    @BindView(R.id.et_validate_email)
    EditText et_validate_email;
    @BindView(R.id.et_validate_code)
    EditText et_validate_code;
    @BindView(R.id.verification_code)
    TextView verification_code;
    @BindView(R.id.bt_validate)
    Button bt_validate;
    @BindView(R.id.iv_return)
    ImageView iv_return;

    private User mUser;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_validate;
    }

    @Override
    protected void initComplete(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        setOnListener(new ForgetPasswordActivity.UpdateUIVericationCode() {
            @Override
            public void setupdateUIVericationCode() {
                upDatePhoneVerificationCodeUI();
            }
        });
    }

    @Override
    protected void initData() {
        mUser = getUserEntity();
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    /**
     * 更新验证码UI
     */
    private void upDatePhoneVerificationCodeUI() {
        verification_code.post(new Runnable() {
            @Override
            public void run() {
                verification_code.setText("已发送");
                CountDownTimerUtils timer = new CountDownTimerUtils(verification_code, 60000, 1000);
                timer.start();
            }
        });

    }

    @OnClick({R.id.iv_return, R.id.bt_validate, R.id.verification_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                this.finish();
                break;
            case R.id.bt_validate://验证
                verifacityCode();
                break;
            case R.id.verification_code://验证码
                hideKeyboard();
                requestVerificationCode();
                break;
            default:
                break;
        }
    }

    //请求验证码
    private void requestVerificationCode() {
        if (!NetworkUtil.isNetworkAvailable(this)) {
            ToastUtils.showShort(R.string.toast_yzm_2);
            return;
        }
        String phone = et_validate_email.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort("手机号码不能为空!");
            return;
        }
        if (!phone.equals(mUser.getMobilePhoneNumber())) {
            ToastUtils.showShort("手机号码输入错误!");
            return;
        }
        BmobSMS.requestSMSCode(phone, "register",new QueryListener<Integer>() {

            @Override
            public void done(Integer smsId, BmobException ex) {
                if(ex==null){//验证码发送成功
                    myListener.setupdateUIVericationCode();
                }else{
//                    toast("errorCode = "+ex.getErrorCode()+",errorMsg = "+ex.getLocalizedMessage());
                    ToastUtils.showShort("必须是有效的手机号码");
                }
            }
        });

    }

    //验证验证码
    private void verifacityCode() {
        String validate_code = et_validate_code.getText().toString().trim();
        String phone = et_validate_email.getText().toString().trim();
        if (!NetworkUtil.isNetworkAvailable(this)) {
            ToastUtils.showShort(R.string.toast_yzm_2);
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort("手机号码不能为空!");
            return;
        }
        if (!phone.equals(mUser.getMobilePhoneNumber())) {
            ToastUtils.showShort("手机号码输入错误!");
            return;
        }
        if (TextUtils.isEmpty(validate_code)) {
            ToastUtils.showShort(R.string.plz_input_yzm);
            return;
        }

        BmobSMS.verifySmsCode(phone,validate_code, new UpdateListener() {

            @Override
            public void done(BmobException ex) {
                if(ex==null){//短信验证码已验证成功
                    Log.e("bmob", "验证通过");
                    mUser.setSMSBOOL(true);
                    Log.e("user.getObjectId()=", mUser.getObjectId() + "");
                    mUser.update(mUser.getObjectId() + "", new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.i("bmob", "更新成功");
                                finish();
                            } else {
                                Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                }else{
//                    toast("验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                    ToastUtils.showShort("短信验证失败");
                }
            }
        });
    }

    public interface UpdateUIVericationCode {
        public void setupdateUIVericationCode();
    }

    private ForgetPasswordActivity.UpdateUIVericationCode myListener;

    //回调方法
    public void setOnListener(ForgetPasswordActivity.UpdateUIVericationCode myListener) {
        this.myListener = myListener;
    }
}
