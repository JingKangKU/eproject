package com.ums.eproject.bean;

import java.io.Serializable;
import java.util.List;

public class PerPdtOrder implements Serializable {


    /**
     * code : 200
     * message : 操作成功
     * data : {"useScoreAmount":10,"actDiscountAmount":0,"userScoreDes":"可用100积分可抵用10元","product":{"id":5021025,"childmerId":null,"submerId":null,"feightTemplateId":null,"name":"花王洗衣机槽清洗剂","picUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/a8b50ebffab24be79633b5971f4df148.jpg","gallery":null,"price":50,"score":0,"subTitle":"花王洗衣机槽清洗剂","description":null,"originalPrice":50,"remark":null,"detailTitle":null,"detailDesc":null,"detailHtml":null,"productStore":100,"unitName":""},"useScore":100,"orderTotalScore":0,"actualPrice":50,"orderTotalPrice":50,"memberDiscountAmount":0,"expressFee":0,"goodsTotalPrice":50,"actDeduceAmount":0,"computeCoupons":[],"availableCouponLength":0}
     */

    private int code;
    private String message;
    /**
     * useScoreAmount : 10
     * actDiscountAmount : 0
     * userScoreDes : 可用100积分可抵用10元
     * product : {"id":5021025,"childmerId":null,"submerId":null,"feightTemplateId":null,"name":"花王洗衣机槽清洗剂","picUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/a8b50ebffab24be79633b5971f4df148.jpg","gallery":null,"price":50,"score":0,"subTitle":"花王洗衣机槽清洗剂","description":null,"originalPrice":50,"remark":null,"detailTitle":null,"detailDesc":null,"detailHtml":null,"productStore":100,"unitName":""}
     * useScore : 100
     * orderTotalScore : 0
     * actualPrice : 50.0
     * orderTotalPrice : 50.0
     * memberDiscountAmount : 0.0
     * expressFee : 0
     * goodsTotalPrice : 50.0
     * actDeduceAmount : 0
     * computeCoupons : []
     * availableCouponLength : 0
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

    public static class DataBean implements Serializable{
        private int useScoreAmount;
        private int actDiscountAmount;
        private String userScoreDes;
        /**
         * id : 5021025
         * childmerId : null
         * submerId : null
         * feightTemplateId : null
         * name : 花王洗衣机槽清洗剂
         * picUrl : http://121.40.88.223:9080/configweb/openapi/file/202301/30/a8b50ebffab24be79633b5971f4df148.jpg
         * gallery : null
         * price : 50.0
         * score : 0
         * subTitle : 花王洗衣机槽清洗剂
         * description : null
         * originalPrice : 50.0
         * remark : null
         * detailTitle : null
         * detailDesc : null
         * detailHtml : null
         * productStore : 100.0
         * unitName :
         */

        private ProductBean product;
        private int useScore;
        private int orderTotalScore;
        private double actualPrice;
        private double orderTotalPrice;
        private double memberDiscountAmount;
        private int expressFee;
        private double goodsTotalPrice;
        private int actDeduceAmount;
        private int availableCouponLength;
        private List<Long> computeCoupons;

        public int getUseScoreAmount() {
            return useScoreAmount;
        }

        public void setUseScoreAmount(int useScoreAmount) {
            this.useScoreAmount = useScoreAmount;
        }

        public int getActDiscountAmount() {
            return actDiscountAmount;
        }

        public void setActDiscountAmount(int actDiscountAmount) {
            this.actDiscountAmount = actDiscountAmount;
        }

        public String getUserScoreDes() {
            return userScoreDes;
        }

        public void setUserScoreDes(String userScoreDes) {
            this.userScoreDes = userScoreDes;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public int getUseScore() {
            return useScore;
        }

        public void setUseScore(int useScore) {
            this.useScore = useScore;
        }

        public int getOrderTotalScore() {
            return orderTotalScore;
        }

        public void setOrderTotalScore(int orderTotalScore) {
            this.orderTotalScore = orderTotalScore;
        }

        public double getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(double actualPrice) {
            this.actualPrice = actualPrice;
        }

        public double getOrderTotalPrice() {
            return orderTotalPrice;
        }

        public void setOrderTotalPrice(double orderTotalPrice) {
            this.orderTotalPrice = orderTotalPrice;
        }

        public double getMemberDiscountAmount() {
            return memberDiscountAmount;
        }

        public void setMemberDiscountAmount(double memberDiscountAmount) {
            this.memberDiscountAmount = memberDiscountAmount;
        }

        public int getExpressFee() {
            return expressFee;
        }

        public void setExpressFee(int expressFee) {
            this.expressFee = expressFee;
        }

        public double getGoodsTotalPrice() {
            return goodsTotalPrice;
        }

        public void setGoodsTotalPrice(double goodsTotalPrice) {
            this.goodsTotalPrice = goodsTotalPrice;
        }

        public int getActDeduceAmount() {
            return actDeduceAmount;
        }

        public void setActDeduceAmount(int actDeduceAmount) {
            this.actDeduceAmount = actDeduceAmount;
        }

        public int getAvailableCouponLength() {
            return availableCouponLength;
        }

        public void setAvailableCouponLength(int availableCouponLength) {
            this.availableCouponLength = availableCouponLength;
        }

        public List<Long> getComputeCoupons() {
            return computeCoupons;
        }

        public void setComputeCoupons(List<Long> computeCoupons) {
            this.computeCoupons = computeCoupons;
        }

        public static class ProductBean implements Serializable{
            private int id;
            private Object childmerId;
            private Object submerId;
            private Object feightTemplateId;
            private String name;
            private String picUrl;
            private Object gallery;
            private double price;
            private int score;
            private String subTitle;
            private Object description;
            private double originalPrice;
            private double quantity;
            private String remark;
            private Object detailTitle;
            private Object detailDesc;
            private Object detailHtml;
            private double productStore;
            private String unitName;

            public double getQuantity() {
                return quantity;
            }

            public void setQuantity(double quantity) {
                this.quantity = quantity;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getChildmerId() {
                return childmerId;
            }

            public void setChildmerId(Object childmerId) {
                this.childmerId = childmerId;
            }

            public Object getSubmerId() {
                return submerId;
            }

            public void setSubmerId(Object submerId) {
                this.submerId = submerId;
            }

            public Object getFeightTemplateId() {
                return feightTemplateId;
            }

            public void setFeightTemplateId(Object feightTemplateId) {
                this.feightTemplateId = feightTemplateId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public Object getGallery() {
                return gallery;
            }

            public void setGallery(Object gallery) {
                this.gallery = gallery;
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

            public String getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public double getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(double originalPrice) {
                this.originalPrice = originalPrice;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public Object getDetailTitle() {
                return detailTitle;
            }

            public void setDetailTitle(Object detailTitle) {
                this.detailTitle = detailTitle;
            }

            public Object getDetailDesc() {
                return detailDesc;
            }

            public void setDetailDesc(Object detailDesc) {
                this.detailDesc = detailDesc;
            }

            public Object getDetailHtml() {
                return detailHtml;
            }

            public void setDetailHtml(Object detailHtml) {
                this.detailHtml = detailHtml;
            }

            public double getProductStore() {
                return productStore;
            }

            public void setProductStore(double productStore) {
                this.productStore = productStore;
            }

            public String getUnitName() {
                return unitName;
            }

            public void setUnitName(String unitName) {
                this.unitName = unitName;
            }
        }
    }
}
