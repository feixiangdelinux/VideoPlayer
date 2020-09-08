package com.example.moduletwo.entity;

import java.util.List;

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-9-7 下午5:45
 */
public class RoomListBean {

    private List<String> videoUrl;
    private List<String> videoTag;

    public List<String> getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(List<String> videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<String> getVideoTag() {
        return videoTag;
    }

    public void setVideoTag(List<String> videoTag) {
        this.videoTag = videoTag;
    }
}
