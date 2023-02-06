package com.ums.eproject.bean;

import java.util.List;

public class HomeBean {


    /**
     * code : 200
     * message : 操作成功
     * data : [{"visible":"1","type":3,"typeName":"功能区","list":[{"groupNo":1,"groupLayOutType":2,"details":[{"linkUrl":"","imageUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/986ec9c75e2e468e8cf71acb174c8fd7.png","funcName":"权益区域","orderNo":3,"linkType":0,"linkTypeAlias":"无","productId":34343242},{"linkUrl":"","imageUrl":"http://121.40.88.223:8601/tetapp/openapi/file/202301/10/23d2680cc8504db084cc4949821da37f.png","funcName":"出行头条","orderNo":2,"linkType":0,"linkTypeAlias":"无","productId":null},{"linkUrl":"","imageUrl":"http://121.40.88.223:8601/tetapp/openapi/file/202301/10/05e830e3bef64d0f90586d6503f911ab.png","funcName":"金融产品专区","orderNo":1,"linkType":0,"linkTypeAlias":"无","productId":null}]},{"groupNo":2,"groupLayOutType":1,"details":[{"linkUrl":"","imageUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/57a04124113441a09643a1af118349a9.png","funcName":"海底捞","orderNo":2,"linkType":0,"linkTypeAlias":"无","productId":null},{"linkUrl":"","imageUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/d7714af8b8004cce8407b63afc7bd6ad.png","funcName":"KFC","orderNo":1,"linkType":0,"linkTypeAlias":"无","productId":null}]}]}]
     */

    private int code;
    private String message;
    /**
     * visible : 1
     * type : 3
     * typeName : 功能区
     * list : [{"groupNo":1,"groupLayOutType":2,"details":[{"linkUrl":"","imageUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/986ec9c75e2e468e8cf71acb174c8fd7.png","funcName":"权益区域","orderNo":3,"linkType":0,"linkTypeAlias":"无","productId":34343242},{"linkUrl":"","imageUrl":"http://121.40.88.223:8601/tetapp/openapi/file/202301/10/23d2680cc8504db084cc4949821da37f.png","funcName":"出行头条","orderNo":2,"linkType":0,"linkTypeAlias":"无","productId":null},{"linkUrl":"","imageUrl":"http://121.40.88.223:8601/tetapp/openapi/file/202301/10/05e830e3bef64d0f90586d6503f911ab.png","funcName":"金融产品专区","orderNo":1,"linkType":0,"linkTypeAlias":"无","productId":null}]},{"groupNo":2,"groupLayOutType":1,"details":[{"linkUrl":"","imageUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/57a04124113441a09643a1af118349a9.png","funcName":"海底捞","orderNo":2,"linkType":0,"linkTypeAlias":"无","productId":null},{"linkUrl":"","imageUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/d7714af8b8004cce8407b63afc7bd6ad.png","funcName":"KFC","orderNo":1,"linkType":0,"linkTypeAlias":"无","productId":null}]}]
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
        private String visible;
        private int type;
        private String typeName;
        /**
         * groupNo : 1
         * groupLayOutType : 2
         * details : [{"linkUrl":"","imageUrl":"http://121.40.88.223:9080/configweb/openapi/file/202301/30/986ec9c75e2e468e8cf71acb174c8fd7.png","funcName":"权益区域","orderNo":3,"linkType":0,"linkTypeAlias":"无","productId":34343242},{"linkUrl":"","imageUrl":"http://121.40.88.223:8601/tetapp/openapi/file/202301/10/23d2680cc8504db084cc4949821da37f.png","funcName":"出行头条","orderNo":2,"linkType":0,"linkTypeAlias":"无","productId":null},{"linkUrl":"","imageUrl":"http://121.40.88.223:8601/tetapp/openapi/file/202301/10/05e830e3bef64d0f90586d6503f911ab.png","funcName":"金融产品专区","orderNo":1,"linkType":0,"linkTypeAlias":"无","productId":null}]
         */

        private List<ListBean> list;

        public String getVisible() {
            return visible;
        }

        public void setVisible(String visible) {
            this.visible = visible;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private int groupNo;
            private int groupLayOutType;

            private int linkType;
            private String linkUrl;
            private String imageUrl;
            private String navName;
            private String name;

            public int getLinkType() {
                return linkType;
            }

            public void setLinkType(int linkType) {
                this.linkType = linkType;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getNavName() {
                return navName;
            }

            public void setNavName(String navName) {
                this.navName = navName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            /**
             * linkUrl :
             * imageUrl : http://121.40.88.223:9080/configweb/openapi/file/202301/30/986ec9c75e2e468e8cf71acb174c8fd7.png
             * funcName : 权益区域
             * orderNo : 3
             * linkType : 0
             * linkTypeAlias : 无
             * productId : 34343242
             */

            private List<DetailsBean> details;

            public int getGroupNo() {
                return groupNo;
            }

            public void setGroupNo(int groupNo) {
                this.groupNo = groupNo;
            }

            public int getGroupLayOutType() {
                return groupLayOutType;
            }

            public void setGroupLayOutType(int groupLayOutType) {
                this.groupLayOutType = groupLayOutType;
            }

            public List<DetailsBean> getDetails() {
                return details;
            }

            public void setDetails(List<DetailsBean> details) {
                this.details = details;
            }

            public static class DetailsBean {
                private String linkUrl;
                private String imageUrl;
                private String funcName;
                private int orderNo;
                private int linkType;
                private String linkTypeAlias;
                private int productId;

                public String getLinkUrl() {
                    return linkUrl;
                }

                public void setLinkUrl(String linkUrl) {
                    this.linkUrl = linkUrl;
                }

                public String getImageUrl() {
                    return imageUrl;
                }

                public void setImageUrl(String imageUrl) {
                    this.imageUrl = imageUrl;
                }

                public String getFuncName() {
                    return funcName;
                }

                public void setFuncName(String funcName) {
                    this.funcName = funcName;
                }

                public int getOrderNo() {
                    return orderNo;
                }

                public void setOrderNo(int orderNo) {
                    this.orderNo = orderNo;
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

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }
            }
        }
    }
}
