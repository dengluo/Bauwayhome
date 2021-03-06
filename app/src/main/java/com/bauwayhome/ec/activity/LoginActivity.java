package com.bauwayhome.ec.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bauwayhome.ec.App.Constants;
import com.bauwayhome.ec.Fragment.FragmentMe;
import com.bauwayhome.ec.MainActivity;
import com.bauwayhome.ec.MyApplication;
import com.bauwayhome.ec.R;
import com.bauwayhome.ec.base.BaseActivity;
import com.bauwayhome.ec.bean.User;
import com.bauwayhome.ec.util.DialogUtil;
import com.bauwayhome.ec.util.NetworkUtil;
import com.bauwayhome.ec.util.PreferencesUtils;
import com.bauwayhome.ec.util.ToastUtil;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends BaseActivity implements View.OnClickListener, View.OnKeyListener {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.pwd)
    EditText et_pwd;
    @BindView(R.id.forget_pwd)
    TextView forgetPwd;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.image_pwd)
    ImageView imagePwd;
    @BindView(R.id.iv_return)
    ImageView iv_return;
    private boolean isopen = true;//用来标记密码是否可见

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initComplete(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        et_pwd.setImeOptions(EditorInfo.IME_ACTION_DONE);
        et_pwd.setOnKeyListener(this);
        imagePwd.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
        iv_return.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        String phone = userRxPreferences.getString(Constants.LOGIN_PHONE).get();
        Log.i("bmob", "phone：" + phone);
        if (!TextUtils.isEmpty(phone)) {
            username.setText(phone);
        }
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                Message message = new Message();
                message.what = 19;
                FragmentMe.mHandler.sendMessage(message);
                finish();
                break;
            case R.id.login:
                login();
                break;
            case R.id.register:
//                ToastUtil.showShortToast(mContext, "register");
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.forget_pwd:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            case R.id.image_pwd:
                if (isopen) {
                    et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    imagePwd.setImageDrawable(getResources().getDrawable(R.drawable.general_password_show));
                } else {
                    et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imagePwd.setImageDrawable(getResources().getDrawable(R.drawable.general_password_hidden));
                }
                isopen = !isopen;
                break;
            default:
                ToastUtil.showLongToast(LoginActivity.this, "id有误！");
                break;
        }
    }

    private void login() {
        if (!NetworkUtil.isNetworkAvailable(this)) {
            ToastUtil.showShortToast(LoginActivity.this, "网络连接异常!");
            return;
        }
        final String email = username.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            ToastUtil.showShortToast(this, getString(R.string.plz_input_phone));
            return;
        }
        final String pwd = et_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showShortToast(this, getString(R.string.plz_input_pwd));
            return;
        }
        DialogUtil.progressDialog(LoginActivity.this, getString(R.string.login_now), false);

        Log.e("email:",email);
        Log.e("pwd:",pwd);
        BmobUser.loginByAccount(email, pwd, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
//                    Log.e(TAG, userRxPreferences.getString(Constants.LOGIN_EMAIL).get()+"//"+userRxPreferences.getString(Constants.LOGIN_PHONE).get());
                    userRxPreferences.getString(Constants.LOGIN_EMAIL).set(email);
                    userRxPreferences.getString(Constants.LOGIN_PWD).set(pwd);
                    userRxPreferences.getString(Constants.SessionToken).set(user.getSessionToken());
                    PreferencesUtils.putEntity(LoginActivity.this, user);
                    Message message = new Message();
                    message.what = 19;
                    FragmentMe.mHandler.sendMessage(message);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    MyApplication.IS_LOGIN = true;
                    LoginActivity.this.finish();
                } else {
                    Log.e(TAG, "done: " + e.getErrorCode() + ":" + e.getMessage());
                    switch (e.getErrorCode()) {
                        case 101:
                            ToastUtil.showShortToast(LoginActivity.this, "用户名或密码错误");
                            break;
                        case 9001:
                            ToastUtil.showShortToast(LoginActivity.this, "Application Id为空，请初始化");
                            break;
                        case 9010:
                            ToastUtil.showShortToast(LoginActivity.this, "网络超时");
                            break;
                        case 9016:
                            ToastUtil.showShortToast(LoginActivity.this, "无网络连接，请检查您的手机网络.");
                            break;
                        default:
                            ToastUtil.showShortToast(LoginActivity.this, "登录异常");
                            break;
                    }
                }
                DialogUtil.hide();
            }
        });
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == keyEvent.getAction()) {
            hintKbTwo();
            login();
            return true;
        }


        return false;
    }

    //此方法只是关闭软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    protected void onDestroy() {
        DialogUtil.hide();//dismissDialog();
        super.onDestroy();
    }

}
