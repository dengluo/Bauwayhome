package com.bauwayhome.ec.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.adapter.VideoMoreAdapter;
import com.bauwayhome.ec.base.BaseActivity;
import com.bauwayhome.ec.bean.Video;
import com.bauwayhome.ec.util.NetworkUtil;
import com.bauwayhome.ec.util.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MoreVideoActivity1 extends BaseActivity {

    private static final String TAG = "MoreVideoActivity1";
    @BindView(R.id.iv_return)
    ImageView mIvReturn;
    @BindView(R.id.gv_video_more1)
    GridView gv_video_more1;

    private VideoMoreAdapter adapter;
    private List<Video> videoList;
    private Handler handler;
    private Context context;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_more_video1;
    }

    @Override
    protected void initComplete(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        videoList = new ArrayList<>();
        getVideos();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    adapter = new VideoMoreAdapter(context, videoList);
                    gv_video_more1.setAdapter(adapter);
                    gv_video_more1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Video video = videoList.get(position);
                            Intent intent = new Intent(context, VideoDisplayActvivity.class);
                            intent.putExtra("v_url", "http:" + video.getVideoUrl());
                            startActivity(intent);
                        }
                    });
                }
            }
        };
    }

    private void getVideos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取虎扑新闻20页的数据，网址格式为：https://voice.hupu.com/nba/第几页
//                    for (int i = 1; i <= 4; i++) {
                    Document doc = Jsoup.connect("http://list.youku.com/albumlist/show/id_51713847.html?spm=a2h0j.11185381.Drama.5~5~H2~A").get();
                    Elements titleLinks = doc.getElementsByClass("info-list");//解析来获取每条视频的标题
                    Elements usernameLinks = doc.getElementsByClass("txt-oneline");//解析来获取每条视频的发布者
                    Elements playcountLinks = doc.select("div.yk-col4.yk-pack.p-list.mb16");//解析来获取每条视频的播放量
//                    Elements dataLinks = doc.getElementsByClass("r");//解析来获取每条视频的发布时间
                    Elements imgLinks = doc.getElementsByClass("quic");//解析来获取每条视频的缩略图
                    Elements urlLinks = doc.getElementsByClass("p-thumb");//解析来获取每条视频播放地址
                    Video video;
                    for (int j = 0; j < titleLinks.size(); j++) {
                        String title = titleLinks.get(j).select("a").text();
                        String username = usernameLinks.get(j).select("a").text();
                        String playcount = playcountLinks.get(j).select("span").text();
                        String playcount2 = playcount.substring(playcount.indexOf("葆威电子烟视频 ") + 8, playcount.length());
                        String img = imgLinks.get(j).select("img").first().attr("src");
                        String uri = urlLinks.get(j).select("a").attr("href");
//                        Log.e("data", img);
                        video = new Video(title, username, playcount2, "", img, uri);
                        videoList.add(video);
                    }
//                    }
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        context = this;
        if (!NetworkUtil.isNetworkAvailable(context)){
            ToastUtil.showShortToast(context, "网络连接异常!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtil.isNetworkAvailable(context)){
            ToastUtil.showShortToast(context, "网络连接异常!");
        }
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
