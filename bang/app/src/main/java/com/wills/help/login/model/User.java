package com.wills.help.login.model;

import java.io.Serializable;

/**
 * com.lzy.mmd.bean
 * Created by lizhaoyong
 * 2016/8/3.
 */
public class User implements Serializable{


    /**
     * state : 1
     * info : 成功
     * data : {"userid":"{829D6E4E-B14B-DFB0-1827-055DD9C46951}","username":"15652956043","phone_num":"15652956043","avatar":"","sex":"0","nickname":"","realname":"","school_num":"","classname":"","usertype":"0","typename":"","usergroup":"0","authid":"1"}
     */

    private int state;
    private String info;
    /**
     * userid : {829D6E4E-B14B-DFB0-1827-055DD9C46951}
     * username : 15652956043
     * phone_num : 15652956043
     * avatar :
     * sex : 0
     * nickname :
     * realname :
     * school_num :
     * classname :
     * usertype : 0
     * typename :
     * usergroup : 0
     * authid : 1
     * createtime : 1
     */

    private UserInfo data;

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

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

    public static class UserInfo implements Serializable{
        private String userid;
        private String username;
        private String phone_num;
        private String avatar;
        private String sex;
        private String nickname;
        private String realname;
        private String school_num;
        private String classname;
        private String usertype;
        private String typename;
        private String usergroup;
        private String authid;
        private String createtime;
        private String school;

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone_num() {
            return phone_num;
        }

        public void setPhone_num(String phone_num) {
            this.phone_num = phone_num;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getSchool_num() {
            return school_num;
        }

        public void setSchool_num(String school_num) {
            this.school_num = school_num;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getUsergroup() {
            return usergroup;
        }

        public void setUsergroup(String usergroup) {
            this.usergroup = usergroup;
        }

        public String getAuthid() {
            return authid;
        }

        public void setAuthid(String authid) {
            this.authid = authid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "userid='" + userid + '\'' +
                    ", username='" + username + '\'' +
                    ", phone_num='" + phone_num + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", sex='" + sex + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", realname='" + realname + '\'' +
                    ", school_num='" + school_num + '\'' +
                    ", classname='" + classname + '\'' +
                    ", usertype='" + usertype + '\'' +
                    ", typename='" + typename + '\'' +
                    ", usergroup='" + usergroup + '\'' +
                    ", authid='" + authid + '\'' +
                    ", createtime='" + createtime + '\'' +
                    ", school='" + school + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "state=" + state +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }
}
