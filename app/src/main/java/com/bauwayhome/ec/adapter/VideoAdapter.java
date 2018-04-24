package com.bauwayhome.ec.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bauwayhome.ec.R;
import com.bauwayhome.ec.bean.Video;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import java.util.List;

/**
 * Created by danny on 2018/4/11.
 */

public class VideoAdapter extends BaseAdapter {

    private List<Video> videoList;
    private View view;
    private Context mContext;
    private ViewHolder viewHolder;

    public VideoAdapter(Context mContext, List<Video> videoList) {
        this.videoList = videoList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.video_item, parent,
                    false);
            LinearLayout ll_video_item = (LinearLayout) view.findViewById(R.id.ll_video_item);
            DisplayMetrics dm = new DisplayMetrics();//获取当前显示的界面大小
            ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
            int height = dm.heightPixels;//高度
            ll_video_item.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    height / 4));
            viewHolder = new ViewHolder();
            viewHolder.videoTitle = (TextView) view
                    .findViewById(R.id.video_title);
            viewHolder.videoPerson = (TextView) view.findViewById(R.id.video_person);
            viewHolder.videoCount = (TextView) view.findViewById(R.id.video_count);
            viewHolder.videoTime = (TextView) view.findViewById(R.id.video_data);
            viewHolder.videoImg = (ImageView) view.findViewById(R.id.video_img);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.videoTitle.setText(videoList.get(position).getVideoTitle());
        viewHolder.videoPerson.setText(videoList.get(position).getVideoPerson());
        viewHolder.videoCount.setText(videoList.get(position).getVideoCount());
        viewHolder.videoTime.setText(videoList.get(position).getVideoTime());
        Glide.with(mContext).load("http:" + videoList.get(position).getVideoImg() + ".jpg").priority(Priority.HIGH).into(viewHolder.videoImg);
        return view;
    }

    class ViewHolder {
        TextView videoTitle;
        TextView videoPerson;
        TextView videoCount;
        TextView videoTime;
        ImageView videoImg;
    }

}
