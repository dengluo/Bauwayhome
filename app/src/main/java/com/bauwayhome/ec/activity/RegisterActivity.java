package com.bauwayhome.ec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bauwayhome.ec.App.Constants;
import com.bauwayhome.ec.R;
import com.bauwayhome.ec.base.BaseActivity;
import com.bauwayhome.ec.bean.User;
import com.bauwayhome.ec.util.CountDownTimerUtils;
import com.bauwayhome.ec.util.DialogUtil;
import com.bauwayhome.ec.util.MyUtil;
import com.bauwayhome.ec.util.NetworkUtil;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";

    @BindView(R.id.et_register_email)
    EditText et_phone_code;
    @BindView(R.id.et_register_pwd)
    EditText mEtRegisterPwd;
    @BindView(R.id.et_register_pwd_again)
    EditText mEtRegisterPwdAgain;
    @BindView(R.id.bt_register)
    Button mBtRegister;
    @BindView(R.id.iv_return)
    ImageView mIvReturn;
    @BindView(R.id.txt_agreement)
    TextView mtvagreement;
    @BindView(R.id.et_register_code)
    EditText et_register_code;//验证码输入
    @BindView(R.id.verification_code)
    TextView erificationCode;//发过来的验证码
    @BindView(R.id.chekbox_agreement)//是否接受条款
            CheckBox chekbox_agreement;
    @BindView(R.id.chekbox_agreement_myz)//是否开启30天免验证
            CheckBox chekbox_agreement_myz;
    @BindView(R.id.ll_register_code)
    LinearLayout ll_register_code;

    private User mUser;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected void initComplete(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        setOnListener(new UpdateUIVericationCode() {
            @Override
            public void setupdateUIVericationCode() {
                upDatePhoneVerificationCodeUI();
            }
        });
    }

    @Override
    protected void initData() {
        BmobSMS.initialize(this, Constants.BMOB_ID);
        mUser = getUserEntity();
    }

    @Override
    protected void initView() {
        String emailHistory = userRxPreferences.getString(Constants.LOGIN_PHONE).get();
        if (!TextUtils.isEmpty(emailHistory)) {
            et_phone_code.setTag(emailHistory);
            et_phone_code.setSelection(et_phone_code.getText().toString().length());
        }
        chekbox_agreement_myz.setChecked(false);
        chekbox_agreement_myz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ll_register_code.setVisibility(View.GONE);
                } else {
                    ll_register_code.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    /**
     * 更新验证码UI
     */
    private void upDatePhoneVerificationCodeUI() {
        erificationCode.post(new Runnable() {
            @Override
            public void run() {
                erificationCode.setText("已发送");
                CountDownTimerUtils timer = new CountDownTimerUtils(erificationCode, 60000, 1000);
                timer.start();
            }
        });

    }

    @OnClick({R.id.iv_return, R.id.bt_register, R.id.verification_code, R.id.txt_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                this.finish();
                break;
            case R.id.bt_register://机构注册
                register();
                break;
            case R.id.verification_code://验证码
                hideKeyboard();
                requestVerificationCode();
                break;
            case R.id.txt_agreement://服务条款
                Intent intent = new Intent(RegisterActivity.this, AgreementActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }

    //请求验证码
    private void requestVerificationCode() {
        if (!NetworkUtil.isNetworkAvailable(this)) {
            ToastUtils.showShort("网络连接异常!");
            return;
        }
        String phone = et_phone_code.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort("手机号码不能为空!");
            return;
        }
        if (!MyUtil.isMobileNO(phone)) {
            ToastUtils.showShort(R.string.plz_phone_format);
            return;
        }
        BmobSMS.requestSMSCode(this, phone, "register", new RequestSMSCodeListener() {
            @Override
            public void done(Integer smsId, cn.bmob.sms.exception.BmobException ex) {
                Log.e("bmob", ex + "短信id11：" + smsId);//用于查询本次短信发送详情
                if (ex == null) {//验证码发送成功
                    Log.e("bmob", "短信id：" + smsId);//用于查询本次短信发送详情
                    //erificationCode.setText("已发送");
                    myListener.setupdateUIVericationCode();
                } else {
                    ToastUtils.showShort(R.string.plz_phone_format);
                }
            }
        });

    }

    private void register() {
        final String et_phone = et_phone_code.getText().toString().trim();
        if (TextUtils.isEmpty(et_phone)) {
            ToastUtils.showShort(R.string.plz_input_phone);
            return;
        }
        if (!MyUtil.isMobileNO(et_phone)) {
            ToastUtils.showShort(R.string.plz_phone_format);
            return;
        }
        String register_code = et_register_code.getText().toString().trim();
        if (TextUtils.isEmpty(register_code) && !chekbox_agreement_myz.isChecked()) {
            ToastUtils.showShort(R.string.plz_input_yzm);
            return;
        }
        String pwd = mEtRegisterPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort(R.string.plz_input_pwd);
            return;
        }
        String pwdAgain = mEtRegisterPwdAgain.getText().toString().trim();
        if (TextUtils.isEmpty(pwdAgain)) {
            ToastUtils.showShort(R.string.plz_input_pwd_again);
            return;
        }
        if (!pwd.equals(pwdAgain)) {
            ToastUtils.showShort(R.string.pwd_input_diff);
            return;
        }
        if (!chekbox_agreement.isChecked()) {
            ToastUtils.showShort("请勾选服务条款");
            return;
        }
        DialogUtil.progressDialog(mContext, getString(R.string.register_now), false);
        mUser.setUsername(et_phone_code.getText().toString().trim());
        mUser.setMobilePhoneNumber(et_phone_code.getText().toString().trim());
        mUser.setPassword(mEtRegisterPwd.getText().toString().trim());
        mUser.setMobilePhoneNumberVerified(true);
        mUser.setApp_name(AppUtils.getAppName());
        mUser.setIsPerson(false);
        if (chekbox_agreement_myz.isChecked()) {
            mUser.setSMSBOOL(false);
        } else {
            mUser.setSMSBOOL(true);
        }
        if (chekbox_agreement_myz.isChecked()){
            mUser.signUp( new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (e == null) {
                        // ToastUtils.showShort(R.string.register_success_plz_check_email);
                        ToastUtils.showShort(R.string.register_success_plz_check_phone);
                        userRxPreferences.getString(Constants.LOGIN_PHONE).set(et_phone_code.getText().toString().trim());
                        startActivity(new Intent(mContext, PerfectInfoActivity.class));
                        RegisterActivity.this.finish();
                    } else {
                        Log.e(TAG, "done: " + e.getErrorCode() + ":" + e.getMessage());
                        if (203 == e.getErrorCode()) {
                            ToastUtils.showShort(R.string.phone_already_register);
                        } else if(207 == e.getErrorCode()) {
                            ToastUtils.showShort(R.string.code_error);
                        } else {
                            ToastUtils.showShort(R.string.register_failure);
                        }
                    }
                    DialogUtil.hide();
                }
            });
        }else{
            Log.e("register_code",register_code);
            mUser.signOrLogin(register_code, new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (e == null) {
                        // ToastUtils.showShort(R.string.register_success_plz_check_email);
                        ToastUtils.showShort(R.string.register_success_plz_check_phone);
                        userRxPreferences.getString(Constants.LOGIN_PHONE).set(et_phone_code.getText().toString().trim());
                        startActivity(new Intent(mContext, PerfectInfoActivity.class));
                        RegisterActivity.this.finish();
                    } else {
                        Log.e(TAG, "done: " + e.getErrorCode() + ":" + e.getMessage());
                        if (203 == e.getErrorCode()) {
                            ToastUtils.showShort(R.string.phone_already_register);
                        } else if(207 == e.getErrorCode()) {
                            ToastUtils.showShort(R.string.code_error);
                        } else {
                            ToastUtils.showShort(R.string.register_failure);
                        }
                    }
                    DialogUtil.hide();
                }
            });
        }
    }

    public interface UpdateUIVericationCode {
        public void setupdateUIVericationCode();
    }

    private UpdateUIVericationCode myListener;

    //回调方法
    public void setOnListener(UpdateUIVericationCode myListener) {
        this.myListener = myListener;
    }
}
