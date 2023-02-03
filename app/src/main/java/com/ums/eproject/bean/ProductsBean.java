package com.ums.eproject.bean;

import java.util.List;

public class ProductsBean {


    /**
     * code : 200
     * message : 操作成功
     * data : {"pageNum":1,"pageSize":1,"totalPage":1,"total":1,"list":[{"id":5021014,"categoryId":5011006,"name":"艾草抑菌舒睡枕","subTitle":"艾草抑菌舒睡枕","picUrl":"http://121.40.88.223:8601/tetapp/openapi/file/202301/30/f5b435db49bc4cedb2aa08b4d43707ae.png","originalPrice":199,"price":79,"score":0,"productStore":1000}]}
     */

    private int code;
    private String message;
    /**
     * pageNum : 1
     * pageSize : 1
     * totalPage : 1
     * total : 1
     * list : [{"id":5021014,"categoryId":5011006,"name":"艾草抑菌舒睡枕","subTitle":"艾草抑菌舒睡枕","picUrl":"http://121.40.88.223:8601/tetapp/openapi/file/202301/30/f5b435db49bc4cedb2aa08b4d43707ae.png","originalPrice":199,"price":79,"score":0,"productStore":1000}]
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
        private long total;
        /**
         * id : 5021014
         * categoryId : 5011006
         * name : 艾草抑菌舒睡枕
         * subTitle : 艾草抑菌舒睡枕
         * picUrl : http://121.40.88.223:8601/tetapp/openapi/file/202301/30/f5b435db49bc4cedb2aa08b4d43707ae.png
         * originalPrice : 199.0
         * price : 79.0
         * score : 0
         * productStore : 1000.0
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

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private long id;
            private long categoryId;
            private String name;
            private String subTitle;
            private String picUrl;
            private double originalPrice;
            private double price;
            private long score;
            private double productStore;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public long getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(long categoryId) {
                this.categoryId = categoryId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public double getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(double originalPrice) {
                this.originalPrice = originalPrice;
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

            public double getProductStore() {
                return productStore;
            }

            public void setProductStore(double productStore) {
                this.productStore = productStore;
            }
        }
    }
}
