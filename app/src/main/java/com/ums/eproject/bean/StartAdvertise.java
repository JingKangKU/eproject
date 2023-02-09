package com.ums.eproject.bean;

import java.util.List;

public class StartAdvertise {


    /**
     * code : 200
     * message : 操作成功
     * data : [{"id":6081004,"name":"启动页广告","imageUrl":"http://121.40.88.223:9080/configweb/openapi/file/202302/07/524b356b88114194ba703fd0d3300e47.png","linkType":1,"linkTypeAlias":"H5","productId":null,"linkUrl":"https://baidu.com","orderNo":1}]
     */

    private int code;
    private String message;
    /**
     * id : 6081004
     * name : 启动页广告
     * imageUrl : http://121.40.88.223:9080/configweb/openapi/file/202302/07/524b356b88114194ba703fd0d3300e47.png
     * linkType : 1
     * linkTypeAlias : H5
     * productId : null
     * linkUrl : https://baidu.com
     * orderNo : 1
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String name;
        private String imageUrl;
        private int linkType;
        private String linkTypeAlias;
        private Object productId;
        private String linkUrl;
        private int orderNo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getLinkType() {
            return linkType;
        }

        public void setLinkType(int linkType) {
            this.linkType = linkType;
        }

        public String getLinkTypeAlias() {
            return linkTypeAlias;
        }

        public void setLinkTypeAlias(String linkTypeAlias) {
            this.linkTypeAlias = linkTypeAlias;
        }

        public Object getProductId() {
            return productId;
        }

        public void setProductId(Object productId) {
            this.productId = productId;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }
    }
}
