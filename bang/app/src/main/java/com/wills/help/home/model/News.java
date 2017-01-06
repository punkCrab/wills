package com.wills.help.home.model;

import java.util.List;

/**
 * com.wills.help.home.model
 * Created by lizhaoyong
 * 2016/11/15.
 */

public class News {
    private int id;
    /**
     * state : 1
     * info : success
     * data : [{"newsid":"1","title":"2017","newsurl":"http://mp.weixin.qq.com/s/x1gitgzxJSigE3l_KidNLg"},{"newsid":"2","title":" ","newsurl":"https://mp.weixin.qq.com/s/VvK9qwWoNFZ7ZYiM_v9zA"},{"newsid":"3","title":"","newsurl":"http://mp.weixin.qq.com/s/DL04LxQuIzZ0pU3hnvnveg"}]
     */

    private int state;
    private String info;
    /**
     * newsid : 1
     * title : 2017
     * newsurl : http://mp.weixin.qq.com/s/x1gitgzxJSigE3l_KidNLg
     */

    private List<NewsInfo> data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<NewsInfo> getData() {
        return data;
    }

    public void setData(List<NewsInfo> data) {
        this.data = data;
    }

    public static class NewsInfo {
        private String newsid;
        private String title;
        private String newsurl;

        public String getNewsid() {
            return newsid;
        }

        public void setNewsid(String newsid) {
            this.newsid = newsid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNewsurl() {
            return newsurl;
        }

        public void setNewsurl(String newsurl) {
            this.newsurl = newsurl;
        }
    }
}
