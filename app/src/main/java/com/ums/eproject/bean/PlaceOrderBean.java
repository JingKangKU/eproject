package com.ums.eproject.bean;

import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 下单所需参数
 */
public class PlaceOrderBean {

    private int comPayType;//支付方式  2: 微信; 4:支付宝; 5：云闪付; 7，余额

    private long productId;//商品ID

//    private String skuId;//商品对应的库存ID

    private int shippingType;//运输方式

    private long receiveAddrId;//收获地址ID

    private double quantity;//商品数量

    private double totalAmount;//订单总金额

    private double totalScore;//订单总金额

    private double realPayAmount;//实际付款金额

    private double memCardAmount;//余额支付时，必传字段

    private double realPayScore;//实际付款积分

    private String remark;//备注

    public double getMemCardAmount() {
        return memCardAmount;
    }

    public void setMemCardAmount(double memCardAmount) {
        this.memCardAmount = memCardAmount;
    }

    public int getComPayType() {
        return comPayType;
    }

    public void setComPayType(int comPayType) {
        this.comPayType = comPayType;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getShippingType() {
        return shippingType;
    }

    public void setShippingType(int shippingType) {
        this.shippingType = shippingType;
    }

    public long getReceiveAddrId() {
        return receiveAddrId;
    }

    public void setReceiveAddrId(long receiveAddrId) {
        this.receiveAddrId = receiveAddrId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public double getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(double realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getRealPayScore() {
        return realPayScore;
    }

    public void setRealPayScore(double realPayScore) {
        this.realPayScore = realPayScore;
    }

    public JSONObject toJson() {
        try {
            return new JSONObject(new GsonBuilder().create().toJson(this, PlaceOrderBean.class)) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
