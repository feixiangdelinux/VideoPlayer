package com.ccg.plat.entity;

import java.util.List;

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-27 下午5:21
 */
public class FinalVideoBean {
    private List<VideoBean> data;
    private long timeStamp;

    public List<VideoBean> getData() {
        return data;
    }

    public void setData(List<VideoBean> data) {
        this.data = data;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
