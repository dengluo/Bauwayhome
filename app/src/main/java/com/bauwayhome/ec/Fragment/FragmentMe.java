package com.bauwayhome.ec.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bauwayhome.ec.App.Constants;
import com.bauwayhome.ec.MyApplication;
import com.bauwayhome.ec.R;
import com.bauwayhome.ec.activity.AboutUsActivity;
import com.bauwayhome.ec.activity.HelpDocsActivity;
import com.bauwayhome.ec.activity.LoginActivity;
import com.bauwayhome.ec.activity.PersonInfoActivity;
import com.bauwayhome.ec.interfaces.DialogCallback;
import com.bauwayhome.ec.util.DialogUtil;
import com.bauwayhome.ec.util.MyUtil;
import com.bauwayhome.ec.util.NetworkUtil;
import com.bauwayhome.ec.util.PreferencesUtils;
import com.bauwayhome.ec.util.ToastUtil;
import com.blankj.utilcode.util.ToastUtils;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by danny on 2018/3/5.
 */

public class FragmentMe extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragmentMe";
    private Context context;
    private View view_main;
    private LinearLayout ll_fragme_exit;
    private LinearLayout ll_fragme_accountinfo,ll_fragme_help_docs,ll_fragme_about_us;
    private TextView tv_account_name,tv_account_nick,tv_version_num;
    public RxSharedPreferences userRxPreferences;
    private Intent csintent,piintent;
    public static Handler mHandler;//接受登录时发送过来的消息

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = this.getActivity();
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 19:
                        if (!NetworkUtil.isNetworkAvailable(context)){
                            ToastUtil.showShortToast(context, "网络连接异常!");
                            return;
                        }

                        if (MyApplication.IS_LOGIN){
                            tv_account_name.setText(userRxPreferences.getString(Constants.LOGIN_EMAIL).get());
                            ll_fragme_exit.setVisibility(View.VISIBLE);
                        }else {
                            tv_account_name.setText(getText(R.string.now_login));
                            ll_fragme_exit.setVisibility(View.GONE);
                        }
                        break;
                }
            }
        };
        initView();
        initDate();
        return view_main;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.setUserVisibleHint(hidden);
        if (hidden) {
            //可见时执行的操作
//            Log.e("isVisibleToUser11",hidden+"");
        } else {
            //不可见时执行的操作
//            Log.e("isVisibleToUser22",hidden+"");
            if (!NetworkUtil.isNetworkAvailable(context)){
                ToastUtil.showShortToast(context, "网络连接异常!");
                return;
            }

            if (MyApplication.IS_LOGIN){
                tv_account_name.setText(userRxPreferences.getString(Constants.LOGIN_EMAIL).get());
                ll_fragme_exit.setVisibility(View.VISIBLE);
            }else {
                tv_account_name.setText(getText(R.string.now_login));
                ll_fragme_exit.setVisibility(View.GONE);
            }
        }
    }

    private void initView() {
        view_main = LayoutInflater.from(getActivity()).inflate(
                R.layout.fragment_me, null);
        ll_fragme_exit = (LinearLayout) view_main.findViewById(R.id.ll_fragme_exit);
        ll_fragme_accountinfo = (LinearLayout) view_main.findViewById(R.id.ll_fragme_accountinfo);
        ll_fragme_help_docs = (LinearLayout) view_main.findViewById(R.id.ll_fragme_help_docs);
        ll_fragme_about_us = (LinearLayout) view_main.findViewById(R.id.ll_fragme_about_us);
        tv_account_name = (TextView) view_main.findViewById(R.id.tv_account_name);
        tv_account_nick = (TextView) view_main.findViewById(R.id.tv_account_nick);
        tv_version_num  = (TextView) view_main.findViewById(R.id.tv_version_num);
        ll_fragme_exit.setOnClickListener(this);
        ll_fragme_accountinfo.setOnClickListener(this);
        ll_fragme_help_docs.setOnClickListener(this);
        ll_fragme_about_us.setOnClickListener(this);
    }

    /*
        Bmob查询数据
         */
    public void queryData(){
        if (!NetworkUtil.isNetworkAvailable(context)){
            ToastUtil.showShortToast(context, "网络连接异常!");
            return;
        }
        String phone = userRxPreferences.getString(Constants.LOGIN_EMAIL).get();
        BmobQuery query =new BmobQuery("_User");
        query.addWhereEqualTo("username", phone);
        query.setLimit(2);
        query.order("createdAt");
        //v3.5.0版本提供`findObjectsByTable`方法查询自定义表名的数据
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray ary, BmobException e) {
                if(e==null){
                    Log.i("bmob","查询成功："+ary.toString());
                    try {
                        JSONObject object = (JSONObject) ary.get(0);
                        tv_account_nick.setText(object.optJSONArray("info").getString(0));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void initDate() {
        String vsersion = MyUtil.getVersion(context);
        tv_version_num.setText("V"+vsersion);
        if (userRxPreferences == null) {
            SharedPreferences preferences = context.getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE);
            userRxPreferences = RxSharedPreferences.create(preferences);
        }
        String accountname = userRxPreferences.getString(Constants.LOGIN_EMAIL).get();
        String pwd = userRxPreferences.getString(Constants.LOGIN_PWD).get();
        Log.e(TAG, accountname+"//"+pwd);
        if (MyApplication.IS_LOGIN){
            tv_account_name.setText(accountname);
            ll_fragme_exit.setVisibility(View.VISIBLE);
        }else {
            tv_account_name.setText(getText(R.string.now_login));
            ll_fragme_exit.setVisibility(View.GONE);
        }

        queryData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_fragme_exit:
                jumpLogin();
                break;
            case R.id.ll_fragme_accountinfo:
                if (MyApplication.IS_LOGIN){
                    jumpPersonInfo();
                }else {
                    exitApp();
                }

                break;
            case R.id.ll_fragme_help_docs:
                startActivity(new Intent(context, HelpDocsActivity.class));
                break;
            case R.id.ll_fragme_about_us:
                startActivity(new Intent(context, AboutUsActivity.class));
                break;
            default:
                break;
        }
    }

    public void jumpLogin() {
        DialogUtil.defaultDialog(context, getString(R.string.confirm_log_out_app), null, null, new
                DialogCallback() {

                    @Override
                    public void execute(Object dialog, Object content) {
                        //确认退出
                        MyApplication.IS_LOGIN = false;
                        exitApp();
                    }
                });
    }

    private void exitApp() {
        BmobUser.logOut();
        PreferencesUtils.clearEntity(context);
        ToastUtils.cancel();
//        myApplication.exit();
        csintent = new Intent(context, LoginActivity.class);
//                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(csintent);
    }

    public void jumpPersonInfo() {
        piintent = new Intent(context, PersonInfoActivity.class);
        startActivity(piintent);
    }

}
