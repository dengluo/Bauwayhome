package com.bauwayhome.ec.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by danny on 2018/4/10.
 */

public class ProductDetailsActivity extends BaseActivity {
    @BindView(R.id.mywebview)
    WebView mywebview;
    @BindView(R.id.iv_return)
    ImageView mIvReturn;

    private String uri = "";

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_product_details;
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
        uri = getIntent().getStringExtra("uri");
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mywebview.getSettings().setSupportMultipleWindows(true);
        mywebview.setWebViewClient(new WebViewClient());
        mywebview.setWebChromeClient(new WebChromeClient());
        mywebview.getSettings().setAllowFileAccess(true);// 设置允许访问文件数据
        mywebview.getSettings().setSupportZoom(true);
        mywebview.getSettings().setBuiltInZoomControls(true);
        mywebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mywebview.getSettings().setCacheMode(mywebview.getSettings().LOAD_CACHE_ELSE_NETWORK);
        mywebview.getSettings().setDomStorageEnabled(true);
        mywebview.getSettings().setDatabaseEnabled(true);
        mywebview.loadUrl(uri);
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
