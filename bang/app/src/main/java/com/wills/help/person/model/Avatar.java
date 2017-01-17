package com.wills.help.person.model;

/**
 * com.wills.help.person.model
 * Created by lizhaoyong
 * 2017/1/16.
 */

public class Avatar {


    /**
     * state : 1
     * info : 头像上传成功
     * data : {"avatar":"http://59.110.62.75/think/avatar/15311437664.jpg"}
     */

    private int state;
    private String info;
    private AvatarUrl data;

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

    public AvatarUrl getData() {
        return data;
    }

    public void setData(AvatarUrl data) {
        this.data = data;
    }

    public static class AvatarUrl {
        /**
         * avatar : http://59.110.62.75/think/avatar/15311437664.jpg
         */

        private String avatar;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
