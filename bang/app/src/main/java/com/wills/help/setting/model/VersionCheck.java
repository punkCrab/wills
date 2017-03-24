package com.wills.help.setting.model;

import java.io.Serializable;

/**
 * com.wills.help.setting.model
 * Created by lizhaoyong
 * 2017/3/23.
 */

public class VersionCheck implements Serializable{

    private static final long serialVersionUID = -3147482420016255919L;

    private int state;
    private String info;
    private VersionInfo data;

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

    public VersionInfo getData() {
        return data;
    }

    public void setData(VersionInfo data) {
        this.data = data;
    }

    public static class VersionInfo implements Serializable{
        private static final long serialVersionUID = -4154925186116255073L;
        private String version;
        private String url;
        private String content;
        private boolean rule;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isRule() {
            return rule;
        }

        public void setRule(boolean rule) {
            this.rule = rule;
        }
    }
}
