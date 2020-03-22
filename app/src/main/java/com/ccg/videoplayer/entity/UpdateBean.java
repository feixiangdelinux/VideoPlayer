package com.ccg.videoplayer.entity;

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-21 上午12:24
 */
public class UpdateBean {

    /**
     * data : {"apkUrl":"http://siyou.nos-eastchina1.126.net/21/%E5%B0%8F%E9%BB%84%E4%BA%BA%E6%92%AD%E6%94%BE%E5%99%A8.apk","desc":"如果不能下载进QQ群    463208733","token":"95704868873751766","version":22}
     * timeStamp : 1584725808097
     */

    private DataBean data;
    private long timeStamp;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static class DataBean {
        /**
         * apkUrl : http://siyou.nos-eastchina1.126.net/21/%E5%B0%8F%E9%BB%84%E4%BA%BA%E6%92%AD%E6%94%BE%E5%99%A8.apk
         * desc : 如果不能下载进QQ群    463208733
         * token : 95704868873751766
         * version : 22
         */

        private String apkUrl;
        private String desc;
        private String token;
        private int version;

        public String getApkUrl() {
            return apkUrl;
        }

        public void setApkUrl(String apkUrl) {
            this.apkUrl = apkUrl;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }
}
