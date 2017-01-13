package com.wills.help.person.model;

/**
 * com.wills.help.person.model
 * Created by lizhaoyong
 * 2017/1/13.
 */

public class Wallet {


    /**
     * state : 1
     * info : 成功
     * data : {"money":"32"}
     */

    private int state;
    private String info;
    private Money data;

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

    public Money getData() {
        return data;
    }

    public void setData(Money data) {
        this.data = data;
    }

    public static class Money {
        /**
         * money : 32
         */

        private String money;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
