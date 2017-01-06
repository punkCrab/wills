package com.wills.help.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * com.wills.help.db.bean
 * Created by lizhaoyong
 * 2017/1/6.
 */
@Entity
public class PointInfo{
    @Id
    private String posid;
    private String blockid;
    private String posname;

    @Generated(hash = 121487450)
    public PointInfo(String posid, String blockid, String posname) {
        this.posid = posid;
        this.blockid = blockid;
        this.posname = posname;
    }

    @Generated(hash = 1652148828)
    public PointInfo() {
    }

    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    public String getBlockid() {
        return blockid;
    }

    public void setBlockid(String blockid) {
        this.blockid = blockid;
    }

    public String getPosname() {
        return posname;
    }

    public void setPosname(String posname) {
        this.posname = posname;
    }
}
