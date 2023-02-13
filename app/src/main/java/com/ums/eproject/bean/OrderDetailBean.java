package com.ums.eproject.bean;

import java.util.List;

public class OrderDetailBean {


    /**
     * code : 200
     * message : 操作成功
     * data : {"orderId":520001008,"orderSn":"20230208200343506144321896969425","shippingType":"快递","totalAmount":20,"totalScore":0,"orderStatus":"20230208200343506144321896969425","orderStatusName":"待发货","orderPayType":"余额支付","pdtLis":[{"productId":5021023,"picUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/5df99ffdaa1b49a98a76f97ffc7a6300.jpg","productName":"贝亲宝宝湿巾","productSn":"20230112011","price":20,"score":0,"quantity":1,"itemAmount":20,"itemScore":0}],"payAmount":0,"payScore":0,"paymentDate":"2023-02-08 20:03:44","payMethod":"余额支付","billType":"不开发票","billHeader":null,"billContent":null,"billReceiverPhone":null,"billReceiverEmail":null,"deliveryCompany":null,"deliverySn":null,"receiverName":"靖康","receiverPhone":"19941566866","receiverPostCode":null,"receiverProvince":"北京市","receiverCity":"市辖区","receiverCounty":"东城区","receiverDetailAddress":"你好","remark":"","createDate":"2023-02-08 20:03:44","deliveryDate":null,"activityAmount":0,"scoreAmount":0,"couponAmount":0,"discountAmount":0,"memCardAmount":20,"outTransactionNo":null,"memberDiscountAmount":null,"deductScore":null}
     */

    private int code;
    private String message;
    /**
     * orderId : 520001008
     * orderSn : 20230208200343506144321896969425
     * shippingType : 快递
     * totalAmount : 20.0
     * totalScore : 0
     * orderStatus : 20230208200343506144321896969425
     * orderStatusName : 待发货
     * orderPayType : 余额支付
     * pdtLis : [{"productId":5021023,"picUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/5df99ffdaa1b49a98a76f97ffc7a6300.jpg","productName":"贝亲宝宝湿巾","productSn":"20230112011","price":20,"score":0,"quantity":1,"itemAmount":20,"itemScore":0}]
     * payAmount : 0.0
     * payScore : 0
     * paymentDate : 2023-02-08 20:03:44
     * payMethod : 余额支付
     * billType : 不开发票
     * billHeader : null
     * billContent : null
     * billReceiverPhone : null
     * billReceiverEmail : null
     * deliveryCompany : null
     * deliverySn : null
     * receiverName : 靖康
     * receiverPhone : 19941566866
     * receiverPostCode : null
     * receiverProvince : 北京市
     * receiverCity : 市辖区
     * receiverCounty : 东城区
     * receiverDetailAddress : 你好
     * remark :
     * createDate : 2023-02-08 20:03:44
     * deliveryDate : null
     * activityAmount : 0.0
     * scoreAmount : 0.0
     * couponAmount : 0.0
     * discountAmount : 0.0
     * memCardAmount : 20.0
     * outTransactionNo : null
     * memberDiscountAmount : null
     * deductScore : null
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
        private long orderId;
        private String orderSn;
        private String shippingType;
        private double totalAmount;
        private int totalScore;
        private String orderStatus;
        private String orderStatusName;
        private String orderPayType;
        private double payAmount;
        private int payScore;
        private String paymentDate;
        private String payMethod;
        private String billType;
        private Object billHeader;
        private Object billContent;
        private Object billReceiverPhone;
        private Object billReceiverEmail;
        private String deliveryCompany;
        private String deliverySn;
        private String receiverName;
        private String receiverPhone;
        private Object receiverPostCode;
        private String receiverProvince;
        private String receiverCity;
        private String receiverCounty;
        private String receiverDetailAddress;
        private String remark;
        private String createDate;
        private String deliveryDate;
        private double activityAmount;
        private double scoreAmount;
        private double couponAmount;
        private double discountAmount;
        private double memCardAmount;
        private Object outTransactionNo;
        private Object memberDiscountAmount;
        private Object deductScore;
        /**
         * productId : 5021023
         * picUrl : http://121.40.88.223:9080/configweb/openapi/file/202301/30/5df99ffdaa1b49a98a76f97ffc7a6300.jpg
         * productName : 贝亲宝宝湿巾
         * productSn : 20230112011
         * price : 20.0
         * score : 0
         * quantity : 1.0
         * itemAmount : 20.0
         * itemScore : 0
         */

        private List<PdtLisBean> pdtLis;

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getShippingType() {
            return shippingType;
        }

        public void setShippingType(String shippingType) {
            this.shippingType = shippingType;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public int getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(int totalScore) {
            this.totalScore = totalScore;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatusName() {
            return orderStatusName;
        }

        public void setOrderStatusName(String orderStatusName) {
            this.orderStatusName = orderStatusName;
        }

        public String getOrderPayType() {
            return orderPayType;
        }

        public void setOrderPayType(String orderPayType) {
            this.orderPayType = orderPayType;
        }

        public double getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
        }

        public int getPayScore() {
            return payScore;
        }

        public void setPayScore(int payScore) {
            this.payScore = payScore;
        }

        public String getPaymentDate() {
            return paymentDate;
        }

        public void setPaymentDate(String paymentDate) {
            this.paymentDate = paymentDate;
        }

        public String getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(String payMethod) {
            this.payMethod = payMethod;
        }

        public String getBillType() {
            return billType;
        }

        public void setBillType(String billType) {
            this.billType = billType;
        }

        public Object getBillHeader() {
            return billHeader;
        }

        public void setBillHeader(Object billHeader) {
            this.billHeader = billHeader;
        }

        public Object getBillContent() {
            return billContent;
        }

        public void setBillContent(Object billContent) {
            this.billContent = billContent;
        }

        public Object getBillReceiverPhone() {
            return billReceiverPhone;
        }

        public void setBillReceiverPhone(Object billReceiverPhone) {
            this.billReceiverPhone = billReceiverPhone;
        }

        public Object getBillReceiverEmail() {
            return billReceiverEmail;
        }

        public void setBillReceiverEmail(Object billReceiverEmail) {
            this.billReceiverEmail = billReceiverEmail;
        }





        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getReceiverPhone() {
            return receiverPhone;
        }

        public void setReceiverPhone(String receiverPhone) {
            this.receiverPhone = receiverPhone;
        }

        public Object getReceiverPostCode() {
            return receiverPostCode;
        }

        public void setReceiverPostCode(Object receiverPostCode) {
            this.receiverPostCode = receiverPostCode;
        }

        public String getReceiverProvince() {
            return receiverProvince;
        }

        public void setReceiverProvince(String receiverProvince) {
            this.receiverProvince = receiverProvince;
        }

        public String getReceiverCity() {
            return receiverCity;
        }

        public void setReceiverCity(String receiverCity) {
            this.receiverCity = receiverCity;
        }

        public String getReceiverCounty() {
            return receiverCounty;
        }

        public void setReceiverCounty(String receiverCounty) {
            this.receiverCounty = receiverCounty;
        }

        public String getReceiverDetailAddress() {
            return receiverDetailAddress;
        }

        public void setReceiverDetailAddress(String receiverDetailAddress) {
            this.receiverDetailAddress = receiverDetailAddress;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getDeliveryCompany() {
            return deliveryCompany;
        }

        public void setDeliveryCompany(String deliveryCompany) {
            this.deliveryCompany = deliveryCompany;
        }

        public String getDeliverySn() {
            return deliverySn;
        }

        public void setDeliverySn(String deliverySn) {
            this.deliverySn = deliverySn;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public double getActivityAmount() {
            return activityAmount;
        }

        public void setActivityAmount(double activityAmount) {
            this.activityAmount = activityAmount;
        }

        public double getScoreAmount() {
            return scoreAmount;
        }

        public void setScoreAmount(double scoreAmount) {
            this.scoreAmount = scoreAmount;
        }

        public double getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(double couponAmount) {
            this.couponAmount = couponAmount;
        }

        public double getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(double discountAmount) {
            this.discountAmount = discountAmount;
        }

        public double getMemCardAmount() {
            return memCardAmount;
        }

        public void setMemCardAmount(double memCardAmount) {
            this.memCardAmount = memCardAmount;
        }

        public Object getOutTransactionNo() {
            return outTransactionNo;
        }

        public void setOutTransactionNo(Object outTransactionNo) {
            this.outTransactionNo = outTransactionNo;
        }

        public Object getMemberDiscountAmount() {
            return memberDiscountAmount;
        }

        public void setMemberDiscountAmount(Object memberDiscountAmount) {
            this.memberDiscountAmount = memberDiscountAmount;
        }

        public Object getDeductScore() {
            return deductScore;
        }

        public void setDeductScore(Object deductScore) {
            this.deductScore = deductScore;
        }

        public List<PdtLisBean> getPdtLis() {
            return pdtLis;
        }

        public void setPdtLis(List<PdtLisBean> pdtLis) {
            this.pdtLis = pdtLis;
        }

        public static class PdtLisBean {
            private long productId;
            private String picUrl;
            private String productName;
            private String productSn;
            private double price;
            private int score;
            private double quantity;
            private double itemAmount;
            private int itemScore;

            public long getProductId() {
                return productId;
            }

            public void setProductId(long productId) {
                this.productId = productId;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getProductSn() {
                return productSn;
            }

            public void setProductSn(String productSn) {
                this.productSn = productSn;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public double getQuantity() {
                return quantity;
            }

            public void setQuantity(double quantity) {
                this.quantity = quantity;
            }

            public double getItemAmount() {
                return itemAmount;
            }

            public void setItemAmount(double itemAmount) {
                this.itemAmount = itemAmount;
            }

            public int getItemScore() {
                return itemScore;
            }

            public void setItemScore(int itemScore) {
                this.itemScore = itemScore;
            }
        }
    }
}
