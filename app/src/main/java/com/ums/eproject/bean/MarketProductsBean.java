package com.ums.eproject.bean;

import java.util.List;

public class MarketProductsBean {
    /**
     * pageNum : 1
     * pageSize : 4
     * totalPage : 1
     * total : 4
     * list : [{"id":6061010,"name":"全民营销测试","subjectCatogryName":"test修改","picUrl":null,"unitName":""},{"id":6061000,"name":"test修改","subjectCatogryName":"test修改","picUrl":"http://localhost:8484/configweb/openapi/file/202301/11/5c93d169f9c448e195ea7f291c15788c.png","unitName":"测试子公司"},{"id":6061011,"name":"全民营销测试2","subjectCatogryName":"test修改","picUrl":null,"unitName":""},{"id":6061001,"name":"测试商品营销","subjectCatogryName":"test修改","picUrl":"http://localhost:8484/configweb/openapi/file/202301/10/8c8a429fa9d84e35b2038b52a1879535.png","unitName":""}]
     */

    private int pageNum;
    private int pageSize;
    private int totalPage;
    private int total;
    /**
     * id : 6061010
     * name : 全民营销测试
     * subjectCatogryName : test修改
     * picUrl : null
     * unitName :
     */

    private List<MarketProductBean> list;

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

    public List<MarketProductBean> getList() {
        return list;
    }

    public void setList(List<MarketProductBean> list) {
        this.list = list;
    }

    public static class MarketProductBean {
        private int id;
        private String name;
        private String subjectCatogryName;
        private Object picUrl;
        private String unitName;

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

        public String getSubjectCatogryName() {
            return subjectCatogryName;
        }

        public void setSubjectCatogryName(String subjectCatogryName) {
            this.subjectCatogryName = subjectCatogryName;
        }

        public Object getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(Object picUrl) {
            this.picUrl = picUrl;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }
    }
}
