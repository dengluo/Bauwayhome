package com.bauwayhome.ec.activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

import com.bauwayhome.ec.R;
import com.squareup.picasso.Picasso;

import fm.jiecao.jcvideoplayer_lib.JCUtils;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Danny on 2018/4/14.
 */

public class VideoManagerActivity extends AppCompatActivity implements View.OnClickListener {
    WebView mWebView,webview2;
    ImageView iv_return;
    private View mCustomView = null;
    private ProgressDialog dialog;

    private String s = "<html><head><meta charset=\"utf-8\" /><title>swf</title></head><body>"
            + "<iframe src='http://player.youku.com/embed/XMjg4ODU2MDY5Ng==' frameborder=0 'allowfullscreen' 'webkitallowfullscreen' 'mozallowfullscreen'></iframe></body></html>";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_manager);
        mWebView = (WebView) findViewById(R.id.webview);
        webview2 = (WebView) findViewById(R.id.webview2);
        iv_return = (ImageView) findViewById(R.id.iv_return);
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.addJavascriptInterface(new JCCallBack(), "jcvd");
//        mWebView.loadUrl("file:///android_asset/jcvd.html");
        initWebView();
        initWebView2();

        if (getPhoneAndroidSDK() >= 14) {// 4.0 需打开硬件加速
            getWindow().setFlags(0x1000000, 0x1000000);
        }
        mWebView.loadData(s, "text/html; charset=UTF-8", null);
        iv_return.setOnClickListener(this);
    }

    public static int getPhoneAndroidSDK() {
        // TODO Auto-generated method stub
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return version;

    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        // settings.setPluginsEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);

        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    private void initWebView2(){
        //WebView加载本地资源
//        webView.loadUrl("file:///android_asset/example.html");
        //WebView加载web资源
        webview2.loadUrl("https://www.soku.com/search_video/q_herbstick?f=1&kb=040200000000000__herbstick&spm=a2hww.20027244.#qheader_search~10");
        webview2.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        //覆盖WebView默认通过第三方或者是系统浏览器打开网页的行为，使得网页可以在WebView中打开
        webview2.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候是控制网页在WebView中去打开，如果为false调用系统浏览器或第三方浏览器打开
                return super.shouldOverrideUrlLoading(view, url);
            }
            //WebViewClient帮助WebView去处理一些页面控制和请求通知
        });
        //启用支持Javascript
        WebSettings settings = webview2.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            webview2.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //WebView加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //页面加载
        webview2.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //newProgress   1-100之间的整数
                if (newProgress == 100) {
                    //页面加载完成，关闭ProgressDialog
                    closeDialog();
                } else {
                    //网页正在加载，打开ProgressDialog
                    openDialog(newProgress);
                }
            }

            private void openDialog(int newProgress) {
                if (dialog == null) {
                    dialog = new ProgressDialog(VideoManagerActivity.this);
                    dialog.setTitle("正在加载");
                    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    dialog.setProgress(newProgress);
                    dialog.setCancelable(true);
                    dialog.show();
                } else {
                    dialog.setProgress(newProgress);
                }
            }

            private void closeDialog() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        });
    }
    class MyWebChromeClient extends WebChromeClient {

        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        private int mOriginalOrientation = 1;

        @Override
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
            // TODO Auto-generated method stub
            onShowCustomView(view, mOriginalOrientation, callback);
            super.onShowCustomView(view, callback);

        }

        public void onShowCustomView(View view, int requestedOrientation,
                                     WebChromeClient.CustomViewCallback callback) {
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
//            if (getPhoneAndroidSDK() >= 14) {
//                fullscreen_custom_content.addView(view);
//                mCustomView = view;
//                mCustomViewCallback = callback;
//                mOriginalOrientation = getRequestedOrientation();
//                mContentView.setVisibility(View.INVISIBLE);
//                mFullscreenContainer.setVisibility(View.VISIBLE);
//                mFullscreenContainer.bringToFront();
//
//                setRequestedOrientation(mOriginalOrientation);
//            }

        }

        public void onHideCustomView() {
//            mContentView.setVisibility(View.VISIBLE);
//            if (mCustomView == null) {
//                return;
//            }
//            mCustomView.setVisibility(View.GONE);
//            mFullscreenContainer.removeView(mCustomView);
//            mCustomView = null;
//            mFullscreenContainer.setVisibility(View.GONE);
//            try {
//                mCustomViewCallback.onCustomViewHidden();
//            } catch (Exception e) {
//            }
//            // Show the content view.
//
//            setRequestedOrientation(mOriginalOrientation);
        }

    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                this.finish();
                break;
            default:
                break;
        }
    }

    public class JCCallBack {

        @JavascriptInterface
        public void adViewJieCaoVideoPlayer(final int width, final int height, final int top, final int left, final int index) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (index == 0) {
                        JCVideoPlayerStandard webVieo = new JCVideoPlayerStandard(VideoManagerActivity.this);
                        webVieo.setUp("http://video.jiecao.fm/11/16/c/68Tlrc9zNi3JomXpd-nUog__.mp4",
                                JCVideoPlayer.SCREEN_LAYOUT_LIST, "嫂子骑大马");
                        Picasso.with(VideoManagerActivity.this)
                                .load("http://img4.jiecaojingxuan.com/yopx/2016/11/16/1d935cc5-a1e7-4779-bdfa-20fd7a60724c.jpg@!640_360")
                                .into(webVieo.thumbImageView);
                        ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(ll);
                        layoutParams.y = JCUtils.dip2px(VideoManagerActivity.this, top);
                        layoutParams.x = JCUtils.dip2px(VideoManagerActivity.this, left);
                        layoutParams.height = JCUtils.dip2px(VideoManagerActivity.this, height);
                        layoutParams.width = JCUtils.dip2px(VideoManagerActivity.this, width);
                        mWebView.addView(webVieo, layoutParams);
                    } else {
                        JCVideoPlayerStandard webVieo = new JCVideoPlayerStandard(VideoManagerActivity.this);
                        webVieo.setUp("http://video.jiecao.fm/11/14/xin/%E5%90%B8%E6%AF%92.mp4",
                                JCVideoPlayer.SCREEN_LAYOUT_LIST, "嫂子失态了");
                        Picasso.with(VideoManagerActivity.this)
                                .load("http://img4.jiecaojingxuan.com/2016/11/14/a019ffc1-556c-4a85-b70c-b1b49811d577.jpg@!640_360")
                                .into(webVieo.thumbImageView);
                        ViewGroup.LayoutParams ll = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(ll);
                        layoutParams.y = JCUtils.dip2px(VideoManagerActivity.this, top);
                        layoutParams.x = JCUtils.dip2px(VideoManagerActivity.this, left);
                        layoutParams.height = JCUtils.dip2px(VideoManagerActivity.this, height);
                        layoutParams.width = JCUtils.dip2px(VideoManagerActivity.this, width);
                        mWebView.addView(webVieo, layoutParams);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
