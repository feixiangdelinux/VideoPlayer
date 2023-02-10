package com.ccg.plat;


import com.ccg.plat.entity.VideoBean;

import java.util.List;

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-9-4 下午2:04
 */
public class VideoListBean {
    private List<VideoBean> data;
    private String videoTag;

    public List<VideoBean> getData() {
        return data;
    }

    public void setData(List<VideoBean> data) {
        this.data = data;
    }

    public String getVideoTag() {
        return videoTag;
    }

    public void setVideoTag(String videoTag) {
        this.videoTag = videoTag;
    }
}
