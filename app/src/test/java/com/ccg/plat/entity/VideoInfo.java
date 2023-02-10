package com.ccg.plat.entity;

public class VideoInfo {
    private int id;
    private int status;
    private String vUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getvUrl() {
        return vUrl;
    }

    public void setvUrl(String vUrl) {
        this.vUrl = vUrl;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "id=" + id +
                ", status=" + status +
                ", vUrl='" + vUrl + '\'' +
                '}';
    }
}
