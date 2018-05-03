package com.bauwayhome.ec.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.activity.NewsDisplayActvivity;
import com.bauwayhome.ec.adapter.NewsAdapter;
import com.bauwayhome.ec.bean.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司新闻
 * Created by Danny on 2018/3/14 .
 */
public class NewsFragment1 extends Fragment implements View.OnClickListener {

    private View view_main;
    private Context context;
    private List<News> newsList;
    private NewsAdapter adapter;
    private Handler handler;
    private ListView lv;
    private String newsUrl = "http://www.bauway.cn";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = this.getActivity();
        inintView();
        return view_main;
    }

    private void inintView() {
        view_main = LayoutInflater.from(getActivity()).inflate(
                R.layout.news_content1, null);

        newsList = new ArrayList<>();
        lv = (ListView) view_main.findViewById(R.id.lv_news1);
        getNews();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    adapter = new NewsAdapter(context, newsList);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            News news = newsList.get(position);
                            Intent intent = new Intent(context, NewsDisplayActvivity.class);
                            Log.e("getNewsUrl", "__" + news.getNewsUrl());
                            intent.putExtra("news_url", news.getNewsUrl());
                            startActivity(intent);
                        }
                    });
                }
            }
        };
    }

    private void getNews() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取虎扑新闻20页的数据，网址格式为：https://voice.hupu.com/nba/第几页
                    for (int i = 1; i <= 5; i++) {
                    Document doc = Jsoup.connect("http://www.bauway.cn/ic47137-p"+ Integer.toString(i)+".html").get();
                    Elements titleLinks = doc.select("div.colorful_long_title");//解析来获取每条新闻的简介和标题
                    Elements dataLinks = doc.select("div.colorful_long_left");//解析来获取每条新闻的简介
                    Elements pictureLinks = doc.select("div.articlelist-picture");//解析来获取每条新闻的图片
                    Log.e("title", Integer.toString(titleLinks.size()));
                    News news;
                    for (int j = 0; j < titleLinks.size(); j++) {
                        String title = titleLinks.get(j).select("a").text();
//                            String title2 = title.substring(0, title.length() - 4);
                        String uri = newsUrl + titleLinks.get(j).getElementsByClass("article-column-links articleList-links-singleline").select("a").attr("href");
                        String desc = titleLinks.get(j).select("p").text();
                        String date = dataLinks.get(j).select("p").text();
                        String img = "http:" + pictureLinks.get(j).select("img").first().attr("src");
                        if (desc.length() > 50) {
                            news = new News(title, uri, "  " + desc.substring(0, 50), date, img);
                        } else {
                            news = new News(title, uri, "  " + desc.substring(0, desc.length()), date, img);
                        }
                        newsList.add(news);
                    }
                    }
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
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
