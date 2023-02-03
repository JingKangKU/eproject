package com.ums.eproject.bean;

import java.util.List;

public class DepositRuleBean {


    /**
     * code : 200
     * message : 操作成功
     * data : {"depositAmountList":[50,100,200],"canUserInput":1,"userInputMin":10,"userInputMax":500,"depositRuleList":[]}
     */

    private int code;
    private String message;
    /**
     * depositAmountList : [50,100,200]
     * canUserInput : 1
     * userInputMin : 10
     * userInputMax : 500
     * depositRuleList : []
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
        private int canUserInput;
        private double userInputMin;
        private double userInputMax;
        private List<Integer> depositAmountList;
        private List<?> depositRuleList;

        public int getCanUserInput() {
            return canUserInput;
        }

        public void setCanUserInput(int canUserInput) {
            this.canUserInput = canUserInput;
        }

        public double getUserInputMin() {
            return userInputMin;
        }

        public void setUserInputMin(double userInputMin) {
            this.userInputMin = userInputMin;
        }

        public double getUserInputMax() {
            return userInputMax;
        }

        public void setUserInputMax(double userInputMax) {
            this.userInputMax = userInputMax;
        }

        public List<Integer> getDepositAmountList() {
            return depositAmountList;
        }

        public void setDepositAmountList(List<Integer> depositAmountList) {
            this.depositAmountList = depositAmountList;
        }

        public List<?> getDepositRuleList() {
            return depositRuleList;
        }

        public void setDepositRuleList(List<?> depositRuleList) {
            this.depositRuleList = depositRuleList;
        }
    }
}
