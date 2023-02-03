package com.ums.eproject.bean;

public class SingleOption {
    private String singleStr;
    private String selectFlag;
    private double depositAmount;

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public SingleOption(String singleStr, String selectFlag) {
        this.singleStr = singleStr;
        this.selectFlag = selectFlag;
    }

    public String getSingleStr() {
        return singleStr;
    }

    public void setSingleStr(String singleStr) {
        this.singleStr = singleStr;
    }

    public String getSelectFlag() {
        return selectFlag;
    }

    public void setSelectFlag(String selectFlag) {
        this.selectFlag = selectFlag;
    }
}
