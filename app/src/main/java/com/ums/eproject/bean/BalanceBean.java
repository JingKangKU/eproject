package com.ums.eproject.bean;

public class BalanceBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"balance":1692.45,"dynamicCode":"022636895772520184"}
     */

    private int code;
    private String message;
    /**
     * balance : 1692.45
     * dynamicCode : 022636895772520184
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private double balance;
        private String dynamicCode;

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String getDynamicCode() {
            return dynamicCode;
        }

        public void setDynamicCode(String dynamicCode) {
            this.dynamicCode = dynamicCode;
        }
    }
}
