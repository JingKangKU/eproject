package com.ums.eproject.bean;

import java.util.List;

public class OrderBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"pageNum":1,"pageSize":10,"totalPage":1,"total":3,"list":[{"orderId":520001008,"orderSn":"20230208200343506144321896969425","orderStatus":1,"orderStatusName":"待发货","payAmount":0,"payScore":0,"pdtLis":[{"productId":5021023,"picUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/5df99ffdaa1b49a98a76f97ffc7a6300.jpg","productName":"贝亲宝宝湿巾","productSn":"20230112011","price":20,"score":0,"quantity":1,"itemAmount":20,"itemScore":0}]},{"orderId":520001007,"orderSn":"20230208200338520185044633214445","orderStatus":0,"orderStatusName":"待付款","payAmount":20,"payScore":0,"pdtLis":[{"productId":5021023,"picUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/5df99ffdaa1b49a98a76f97ffc7a6300.jpg","productName":"贝亲宝宝湿巾","productSn":"20230112011","price":20,"score":0,"quantity":1,"itemAmount":20,"itemScore":0}]},{"orderId":520001003,"orderSn":"20230208180736388748093223293649","orderStatus":1,"orderStatusName":"待发货","payAmount":0,"payScore":0,"pdtLis":[{"productId":5021023,"picUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/5df99ffdaa1b49a98a76f97ffc7a6300.jpg","productName":"贝亲宝宝湿巾","productSn":"20230112011","price":20,"score":0,"quantity":0,"itemAmount":40,"itemScore":0}]}]}
     */

    private int code;
    private String message;
    /**
     * pageNum : 1
     * pageSize : 10
     * totalPage : 1
     * total : 3
     * list : [{"orderId":520001008,"orderSn":"20230208200343506144321896969425","orderStatus":1,"orderStatusName":"待发货","payAmount":0,"payScore":0,"pdtLis":[{"productId":5021023,"picUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/5df99ffdaa1b49a98a76f97ffc7a6300.jpg","productName":"贝亲宝宝湿巾","productSn":"20230112011","price":20,"score":0,"quantity":1,"itemAmount":20,"itemScore":0}]},{"orderId":520001007,"orderSn":"20230208200338520185044633214445","orderStatus":0,"orderStatusName":"待付款","payAmount":20,"payScore":0,"pdtLis":[{"productId":5021023,"picUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/5df99ffdaa1b49a98a76f97ffc7a6300.jpg","productName":"贝亲宝宝湿巾","productSn":"20230112011","price":20,"score":0,"quantity":1,"itemAmount":20,"itemScore":0}]},{"orderId":520001003,"orderSn":"20230208180736388748093223293649","orderStatus":1,"orderStatusName":"待发货","payAmount":0,"payScore":0,"pdtLis":[{"productId":5021023,"picUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/5df99ffdaa1b49a98a76f97ffc7a6300.jpg","productName":"贝亲宝宝湿巾","productSn":"20230112011","price":20,"score":0,"quantity":0,"itemAmount":40,"itemScore":0}]}]
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
        private int pageNum;
        private int pageSize;
        private int totalPage;
        private int total;
        /**
         * orderId : 520001008
         * orderSn : 20230208200343506144321896969425
         * orderStatus : 1
         * orderStatusName : 待发货
         * payAmount : 0.0
         * payScore : 0
         * pdtLis : [{"productId":5021023,"picUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/5df99ffdaa1b49a98a76f97ffc7a6300.jpg","productName":"贝亲宝宝湿巾","productSn":"20230112011","price":20,"score":0,"quantity":1,"itemAmount":20,"itemScore":0}]
         */

        private List<ListBean> list;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private long orderId;
            private String orderSn;
            private int orderStatus;
            private String orderStatusName;
            private double payAmount;
            private int payScore;
            private List<PdtLisBean> pdtLis;
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

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getOrderStatusName() {
                return orderStatusName;
            }

            public void setOrderStatusName(String orderStatusName) {
                this.orderStatusName = orderStatusName;
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
                private long score;
                private double quantity;
                private double itemAmount;
                private double itemScore;

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

                public long getScore() {
                    return score;
                }

                public void setScore(long score) {
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

                public double getItemScore() {
                    return itemScore;
                }

                public void setItemScore(double itemScore) {
                    this.itemScore = itemScore;
                }
            }
        }
    }
}
