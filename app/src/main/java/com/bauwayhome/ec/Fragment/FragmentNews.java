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
 * Created by danny on 2018/4/11.
 * 新闻
 */

public class FragmentNews extends Fragment implements View.OnClickListener {

    private View view_main;
    private Context context;
    private List<News> newsList;
    private NewsAdapter adapter;
    private Handler handler;
    private ListView lv;
    private String newsUrl = "http://m.bauway.cn";

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
                R.layout.fragment_news, null);

        newsList = new ArrayList<>();
        lv = (ListView) view_main.findViewById(R.id.news_lv);
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
                            Log.e("getNewsUrl","__"+ news.getNewsUrl());
                            intent.putExtra("news_url", newsUrl+news.getNewsUrl());
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
//                    for (int i = 1; i <= 4; i++) {
                        Document doc = Jsoup.connect("http://m.bauway.cn/news/").get();
                    Elements titleLinks = doc.getElementsByClass("list_cont").select("li");//解析来获取每条新闻的标题与链接地址
//                        Elements descLinks = doc.select("p.articlelist-datebtnsolid-summary.mt5");//解析来获取每条新闻的简介
//                        Elements timeLinksMonth = doc.select("div.articlelist-datebtnsolid-date");   //解析来获取每条新闻的时间月份
//                        Elements timeLinksDay = doc.select("span.article-column-time-days-data");   //解析来获取每条新闻的时间日期
                        Log.e("title", Integer.toString(titleLinks.size()));
//                        Log.e("titleLinks", titleLinks.toString());
//                        Log.e("descLinks", descLinks.toString());
                        News news;
                        for (int j = 0; j < titleLinks.size(); j++) {
                            String title = titleLinks.get(j).select("a").text();
                            String title2 = title.substring(0,title.length()-4);
                            String uri = titleLinks.get(j).select("a").attr("href");
                            String desc = titleLinks.get(j).select("p").text();
                            String date = titleLinks.get(j).select("span").text();
                            String date1 = date.substring(0,date.length()-2);
                            String date2 = date.substring(date.length()-2,date.length());
//                            String img = titleLinks.get(j).select("img").text();
//                            String day = timeLinksDay.get(j).select("span").text();
                            if (desc.length() > 50) {
                                news = new News(title2, uri, "  " + desc.substring(0, 50), date1+"-"+date2);
                            } else {
                                news = new News(title2, uri, "  " + desc.substring(0, desc.length()), date1+"-"+date2);
                            }

//                            String time = timeLinks.get(j).select("span.other-left").select("a").text();
                            newsList.add(news);
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
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
