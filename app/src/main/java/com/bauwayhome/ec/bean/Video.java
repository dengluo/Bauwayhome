package com.bauwayhome.ec.bean;

/**
 * Created by shun8 on 2018/4/21.
 */

public class Video {
    private String videoTitle;   //视频标题
    private String videoPerson;     //视频发布人
    private String videoCount;        //视频播放量
    private String videoTime;    //视频发布时间
    private String videoImg;    //视频图片url
    private String videoUrl;    //视频链接地址

    public Video(String videoTitle, String videoPerson, String videoCount, String videoTime, String videoImg,String videoUrl) {
        this.videoTitle = videoTitle;
        this.videoPerson = videoPerson;
        this.videoCount = videoCount;
        this.videoTime = videoTime;
        this.videoImg = videoImg;
        this.videoUrl = videoUrl;
    }

    public String getVideoImg() {
        return videoImg;
    }

    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoPerson() {
        return videoPerson;
    }

    public void setVideoPerson(String videoPerson) {
        this.videoPerson = videoPerson;
    }

    public String getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(String videoCount) {
        this.videoCount = videoCount;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
