package com.example.moduletwo.entity;

import java.io.Serializable;
import java.util.Objects;

public class VideoBean implements Serializable {

    private int id;
    private String name;
    private String url;
    private String e;
    private String i;
    private String tags;
    private String pUrl;
    private String vUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getpUrl() {
        return pUrl;
    }

    public void setpUrl(String pUrl) {
        this.pUrl = pUrl;
    }

    public String getvUrl() {
        return vUrl;
    }

    public void setvUrl(String vUrl) {
        this.vUrl = vUrl;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", e='" + e + '\'' +
                ", i='" + i + '\'' +
                ", tags='" + tags + '\'' +
                ", pUrl='" + pUrl + '\'' +
                ", vUrl='" + vUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoBean videoBean = (VideoBean) o;
        return Objects.equals(name, videoBean.name) &&
                Objects.equals(pUrl, videoBean.pUrl) &&
                Objects.equals(vUrl, videoBean.vUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pUrl, vUrl);
    }
}
