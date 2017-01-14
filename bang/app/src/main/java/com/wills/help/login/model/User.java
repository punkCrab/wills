package com.wills.help.login.model;

import com.wills.help.db.bean.UserInfo;

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

    @Override
    public String toString() {
        return "User{" +
                "state=" + state +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }
}
