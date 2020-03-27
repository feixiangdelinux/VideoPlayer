package com.ccg.videoplayer.entity;

import java.util.List;

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-27 下午3:14
 */
public class RoomBean {

    /**
     * data : [{"explain":"直播","roomName":"24小时直播","roomUrl":"http://siyou.nos-eastchina1.126.net/21/zhibo.json"},{"explain":"www.755pu.com","roomName":"电影一房","roomUrl":"https://siyou.nos-eastchina1.126.net/21/jj1.json"},{"explain":"www.755pu.com","roomName":"电影二房","roomUrl":"https://siyou.nos-eastchina1.126.net/21/jj2.json"},{"explain":"www.755pu.com","roomName":"电影三房","roomUrl":"https://siyou.nos-eastchina1.126.net/21/msp1.json"},{"explain":"www.755pu.com","roomName":"电影四房","roomUrl":"https://siyou.nos-eastchina1.126.net/21/msp2.json"},{"explain":"www.755pu.com","roomName":"电影五房","roomUrl":"https://siyou.nos-eastchina1.126.net/21/zzhmtyn.json"}]
     * timeStamp : 1024
     */

    private int timeStamp;
    private List<DataBean> data;

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * explain : 直播
         * roomName : 24小时直播
         * roomUrl : http://siyou.nos-eastchina1.126.net/21/zhibo.json
         */

        private String explain;
        private String roomName;
        private String roomUrl;

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public String getRoomUrl() {
            return roomUrl;
        }

        public void setRoomUrl(String roomUrl) {
            this.roomUrl = roomUrl;
        }
    }
}
