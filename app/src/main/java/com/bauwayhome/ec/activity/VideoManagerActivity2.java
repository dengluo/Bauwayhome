package com.bauwayhome.ec.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.adapter.VideoAdapter;
import com.bauwayhome.ec.base.BaseActivity;
import com.bauwayhome.ec.bean.Video;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Danny on 2018/4/21.
 */

public class VideoManagerActivity2 extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.gv_video)
    GridView gv_video;
    @BindView(R.id.iv_return)
    ImageView iv_return;

    private VideoAdapter adapter;
    private List<Video> videoList;
    private Handler handler;
    private Context context;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_video_manager2;
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
        videoList = new ArrayList<>();
        getVideos();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    adapter = new VideoAdapter(context, videoList);
                    gv_video.setAdapter(adapter);
                    gv_video.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Video video = videoList.get(position);
                            Intent intent = new Intent(context, VideoDisplayActvivity.class);
                            intent.putExtra("v_url", "http:"+video.getVideoUrl());
                            Log.e("111", "2222==" + video.getVideoUrl());
                            startActivity(intent);
                        }
                    });
                }
            }
        };
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        context = this;
    }

    private void getVideos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取虎扑新闻20页的数据，网址格式为：https://voice.hupu.com/nba/第几页
//                    for (int i = 1; i <= 4; i++) {
                    Document doc = Jsoup.connect("http://www.soku.com/search_video/q_herbstick?f=1&kb=04112010yv41000__").get();
                    Elements titleLinks = doc.getElementsByClass("v-meta-title");//解析来获取每条视频的标题
                    Elements usernameLinks = doc.getElementsByClass("username");//解析来获取每条视频的发布者
                    Elements playcountLinks = doc.getElementsByClass("pub");//解析来获取每条视频的播放量
                    Elements dataLinks = doc.getElementsByClass("r");//解析来获取每条视频的发布时间
                    Elements imgLinks = doc.getElementsByClass("v-thumb");//解析来获取每条视频的缩略图
                    Elements urlLinks = doc.getElementsByClass("v-link");//解析来获取每条视频播放地址
                    Log.e("title", Integer.toString(titleLinks.size()));
                    Video video;
                    for (int j = 0; j < titleLinks.size(); j++) {
                        String title = titleLinks.get(j).select("a").attr("title");
                        String title2 = title.substring(0, title.length() - 4);
                        String username = usernameLinks.get(j).select("a").text();
                        String playcount = playcountLinks.get(j).select("span").text();
                        String data = dataLinks.get(j).select("span").text();
                        String img = imgLinks.get(j).select("img").first().attr("src");
                        String uri = urlLinks.get(j).select("a").attr("href");
//                        Log.e("data", data);
                        video = new Video(title2, username, playcount, data, img, uri);
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

    @OnClick({R.id.iv_return})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_return:
                finish();
                break;
        }
    }
}
