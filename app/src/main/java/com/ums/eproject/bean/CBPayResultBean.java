package com.ums.eproject.bean;

import java.io.Serializable;

public class CBPayResultBean implements Serializable {
    private static final long serialVersionUID = -5807658338691936868L;
    private String busScanType;
    private String busNo;
    private String carNo;
    private String rideDate;
    private String busPrice;
    private int isTransfer;
    private double realAmount;
    private long payCardFlowId;
    private String tradeAuditNo;
    private double balance;
    private long leftScore;

    @Override
    public String toString() {
        return "CBPayResultBean{" +
                "busScanType='" + busScanType + '\'' +
                ", busNo='" + busNo + '\'' +
                ", carNo='" + carNo + '\'' +
                ", rideDate='" + rideDate + '\'' +
                ", busPrice='" + busPrice + '\'' +
                ", isTransfer=" + isTransfer +
                ", realAmount=" + realAmount +
                ", payCardFlowId=" + payCardFlowId +
                ", tradeAuditNo='" + tradeAuditNo + '\'' +
                ", balance=" + balance +
                ", leftScore=" + leftScore +
                '}';
    }

    public String getBusScanType() {
        return busScanType;
    }

    public void setBusScanType(String busScanType) {
        this.busScanType = busScanType;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getRideDate() {
        return rideDate;
    }

    public void setRideDate(String rideDate) {
        this.rideDate = rideDate;
    }

    public String getBusPrice() {
        return busPrice;
    }

    public void setBusPrice(String busPrice) {
        this.busPrice = busPrice;
    }

    public int getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(int isTransfer) {
        this.isTransfer = isTransfer;
    }

    public double getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(double realAmount) {
        this.realAmount = realAmount;
    }

    public long getPayCardFlowId() {
        return payCardFlowId;
    }

    public void setPayCardFlowId(long payCardFlowId) {
        this.payCardFlowId = payCardFlowId;
    }

    public String getTradeAuditNo() {
        return tradeAuditNo;
    }

    public void setTradeAuditNo(String tradeAuditNo) {
        this.tradeAuditNo = tradeAuditNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getLeftScore() {
        return leftScore;
    }

    public void setLeftScore(long leftScore) {
        this.leftScore = leftScore;
    }
}
