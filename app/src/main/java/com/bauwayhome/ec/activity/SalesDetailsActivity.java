package com.bauwayhome.ec.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by danny on 2018/4/10.
 */

public class SalesDetailsActivity extends BaseActivity {
    @BindView(R.id.iv_return)
    ImageView mIvReturn;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_sales_details;
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

    }

    @Override
    protected void init(Bundle savedInstanceState) {
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
