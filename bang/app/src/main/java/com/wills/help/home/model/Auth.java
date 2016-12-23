package com.wills.help.home.model;

import java.io.Serializable;

/**
 * Created by Wills on 2016/12/23.
 */

public class Auth implements Serializable{

    private int imgId;
    private String title;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
