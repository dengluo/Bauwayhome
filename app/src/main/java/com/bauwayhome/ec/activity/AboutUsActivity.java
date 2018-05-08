package com.bauwayhome.ec.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.base.BaseActivity;
import com.bauwayhome.ec.util.MyUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {

    private static final String TAG = "AboutUsActivity";
    @BindView(R.id.iv_return)
    ImageView mIvReturn;
    @BindView(R.id.tv_aboutus_version)
    TextView tv_aboutus_version;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initComplete(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        String vsersion = MyUtil.getVersion(this);
        tv_aboutus_version.setText("V"+vsersion);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.iv_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                this.finish();
                break;
            default:
                break;
        }
    }
}
