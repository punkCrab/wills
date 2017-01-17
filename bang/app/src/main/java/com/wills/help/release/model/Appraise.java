package com.wills.help.release.model;

import java.util.List;

/**
 * com.wills.help.release.model
 * Created by lizhaoyong
 * 2017/1/11.
 */

public class Appraise {


    /**
     * state : 1
     * info : 成功
     * data : [{"appraiselabelid":"1","appraiselabel":"靠谱"},{"appraiselabelid":"2","appraiselabel":"帅气"},{"appraiselabelid":"3","appraiselabel":"美美哒"},{"appraiselabelid":"4","appraiselabel":"可爱"},{"appraiselabelid":"5","appraiselabel":"健谈"},{"appraiselabelid":"6","appraiselabel":"有面儿"},{"appraiselabelid":"7","appraiselabel":"局气"}]
     */

    private int state;
    private String info;
    private List<Label> data;

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

    public List<Label> getData() {
        return data;
    }

    public void setData(List<Label> data) {
        this.data = data;
    }

    public static class Label {
        /**
         * appraiselabelid : 1
         * appraiselabel : 靠谱
         */

        private String appraiselabelid;
        private String appraiselabel;
        private int select;

        public int getSelect() {
            return select;
        }

        public void setSelect(int select) {
            this.select = select;
        }

        public String getAppraiselabelid() {
            return appraiselabelid;
        }

        public void setAppraiselabelid(String appraiselabelid) {
            this.appraiselabelid = appraiselabelid;
        }

        public String getAppraiselabel() {
            return appraiselabel;
        }

        public void setAppraiselabel(String appraiselabel) {
            this.appraiselabel = appraiselabel;
        }
    }
}
