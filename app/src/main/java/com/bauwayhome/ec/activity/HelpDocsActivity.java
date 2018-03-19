package com.bauwayhome.ec.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class HelpDocsActivity extends BaseActivity {

    private static final String TAG = "HelpDocsActivity";
    @BindView(R.id.iv_return)
    ImageView mIvReturn;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_help_docs;
    }

    @Override
    protected void initComplete(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

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
