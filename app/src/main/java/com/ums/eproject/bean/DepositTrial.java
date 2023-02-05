package com.ums.eproject.bean;

public class DepositTrial {


    /**
     * code : 200
     * message : 操作成功
     * data : {"payAmount":200,"rechargeAmount":200,"rechargeGiftAmount":0,"rechargeDiscountAmount":0}
     */

    private int code;
    private String message;
    /**
     * payAmount : 200.0
     * rechargeAmount : 200.0
     * rechargeGiftAmount : 0
     * rechargeDiscountAmount : 0
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
        private double payAmount;
        private double rechargeAmount;
        private int rechargeGiftAmount;
        private int rechargeDiscountAmount;

        public double getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
        }

        public double getRechargeAmount() {
            return rechargeAmount;
        }

        public void setRechargeAmount(double rechargeAmount) {
            this.rechargeAmount = rechargeAmount;
        }

        public int getRechargeGiftAmount() {
            return rechargeGiftAmount;
        }

        public void setRechargeGiftAmount(int rechargeGiftAmount) {
            this.rechargeGiftAmount = rechargeGiftAmount;
        }

        public int getRechargeDiscountAmount() {
            return rechargeDiscountAmount;
        }

        public void setRechargeDiscountAmount(int rechargeDiscountAmount) {
            this.rechargeDiscountAmount = rechargeDiscountAmount;
        }
    }
}
