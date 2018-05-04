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
import com.bauwayhome.ec.adapter.VideoAdapter;
import com.bauwayhome.ec.adapter.VideoAdapter2;
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

/**
 * Created by Danny on 2018/4/21.
 */

public class VideoManagerActivity2 extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.gv_video)
    GridView gv_video;
    @BindView(R.id.gv_video2)
    GridView gv_video2;
    @BindView(R.id.iv_return)
    ImageView iv_return;
    @BindView(R.id.iv_more_video1)
    ImageView iv_more_video1;
    @BindView(R.id.iv_more_video2)
    ImageView iv_more_video2;

    private VideoAdapter adapter;
    private VideoAdapter2 adapter2;
    private List<Video> videoList, videoList2;
    private Handler handler, handler2;
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
        videoList2 = new ArrayList<>();
        getVideos();
        getVideos2();
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
                            intent.putExtra("v_url", "http:" + video.getVideoUrl());
//                            Log.e("111", "2222==" + video.getVideoUrl());
                            startActivity(intent);
                        }
                    });
                }
            }
        };

        handler2 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 2) {
                    adapter2 = new VideoAdapter2(context, videoList2);
                    gv_video2.setAdapter(adapter2);
                    gv_video2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Video video = videoList2.get(position);
                            Intent intent = new Intent(context, VideoDisplayActvivity.class);
                            intent.putExtra("v_url", "http:" + video.getVideoUrl());
//                            Log.e("111", "2222==" + video.getVideoUrl());
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

    private void getVideos2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取虎扑新闻20页的数据，网址格式为：https://voice.hupu.com/nba/第几页
//                    for (int i = 1; i <= 4; i++) {
                    Document doc = Jsoup.connect("http://list.youku.com/albumlist/show/id_51704089.html?spm=a2h1n.8251843.0.0&&ascending=0").get();
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
//                        Log.e("img==", img);
                        video = new Video(title, username, playcount2, "", img, uri);
                        videoList2.add(video);
                    }
//                    }
                    Message msg = new Message();
                    msg.what = 2;
                    handler2.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @OnClick({R.id.iv_return,R.id.iv_more_video1,R.id.iv_more_video2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.iv_more_video1:
                startActivity(new Intent(context, MoreVideoActivity1.class));
                break;
            case R.id.iv_more_video2:
                startActivity(new Intent(context, MoreVideoActivity2.class));
                break;
        }
    }
}
